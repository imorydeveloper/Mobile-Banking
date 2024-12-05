package org.example.mobile_banking.feature.auth;

import org.example.mobile_banking.domain.User;
import org.example.mobile_banking.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification,Long> {

    Optional<UserVerification> findByUserAndVerifiedCode(User user, String verifiedCode);

    List<UserVerification> user(User user);

    Optional<UserVerification>findByUser(User user);

}
