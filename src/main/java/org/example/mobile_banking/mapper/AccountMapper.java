package org.example.mobile_banking.mapper;

import org.example.mobile_banking.domain.Account;
import org.example.mobile_banking.feature.account.dto.AccountCreateRequest;
import org.example.mobile_banking.feature.account.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    //Map Account to AccountResponse
    //target = AccountResponse
    //Source = Account
    //@Mapping(target = "accountType",source = "accountType.alias")
    AccountResponse toAccountResponse(Account account);
    @Mapping(target = "accountType.alias",source = "accountType")
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);

}
