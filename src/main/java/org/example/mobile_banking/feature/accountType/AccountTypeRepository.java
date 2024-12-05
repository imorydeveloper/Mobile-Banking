package org.example.mobile_banking.feature.accountType;

import org.example.mobile_banking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {


    boolean existsByAlias(String alias);

    //SELECT * FROM AccountType WHERE alias = ?
    Optional<AccountType>findByAlias(String alias);
}
