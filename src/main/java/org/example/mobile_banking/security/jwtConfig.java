package org.example.mobile_banking.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class jwtConfig {

    // 1.Create Key pair
    //@Primary  it default bean to inject
    @Bean("accessTokenKeyPair")
    KeyPair accessTokenKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
    // 2.Create RSA key object using key pair
    @Bean("accessTokenRSAKey")
    RSAKey accessTokenRSAKey(@Qualifier("accessTokenKeyPair") KeyPair keyPair) throws NoSuchAlgorithmException {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
    }
    // 3. Create JWKSource
    @Bean("accessTokenJwkSource")
    JWKSource<SecurityContext> accessTokenJwkSource(@Qualifier("accessTokenRSAKey") RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    // 4. Use RSA public key for decoding
    @Primary
    @Bean("accessTokenJwtDecoder")
    JwtDecoder accessTokenJwtDecoder(@Qualifier("accessTokenRSAKey")RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    // 5. use JWKSource for Encoding
    // it need jwk source
    @Bean("accessTokenJwtEncoder")
    JwtEncoder accessTokenJwtEncoder(@Qualifier("accessTokenJwkSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    // refresh token keypair

    @Bean("refreshTokenKeyPair")
    KeyPair refreshTokenKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
    @Bean("refreshTokenRSAKey")
    RSAKey refreshTokenRSAKey(@Qualifier("refreshTokenKeyPair") KeyPair keyPair) throws NoSuchAlgorithmException {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
    }
    @Bean("refreshTokenJwtDecoder")
    JwtDecoder refreshTokenJwtDecoder(@Qualifier("refreshTokenRSAKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }
    @Bean("refreshTokenJwkSource")
    JWKSource<SecurityContext> refreshTokenJwkSource(@Qualifier("refreshTokenRSAKey") RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Bean("refreshTokenJwtEncoder")
    JwtEncoder refreshTokenJwtEncoder(@Qualifier("refreshTokenJwkSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

}
