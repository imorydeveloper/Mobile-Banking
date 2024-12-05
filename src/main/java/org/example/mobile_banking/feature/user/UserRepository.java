package org.example.mobile_banking.feature.user;

import org.example.mobile_banking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {

    // SELECT * FROM users WHERE uuid = ?
    Optional<User> findByUuid(String uuid);

    //SELECT * FROM users WHERE phone_number = ?

    Optional<User> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    //SELECT EXISTS(SELECT * FROM users WHERE phone_number = ?
    Boolean existsByPhoneNumber(String phoneNumber);

    //SELECT EXISTS(SELECT * FROM users WHERE email = ?
    Boolean existsByEmail(String email);

    //SELECT EXISTS(SELECT * FROM users WHERE nationalCardId = ?
    Boolean existsByNationalCardId(String nationalCardId);


    //SELECT * FROM users WHERE email = ?

    Optional<User> findByEmail(String email);

}
