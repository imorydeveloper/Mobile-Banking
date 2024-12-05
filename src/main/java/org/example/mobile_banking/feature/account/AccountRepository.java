package org.example.mobile_banking.feature.account;

import org.example.mobile_banking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    // SELECT EXISTS(SELECT * FROM Account WHERE actNo = ?)
    boolean existsByActNo(String actNo);
    Optional<Account> findByActNo(String actNo);
}
