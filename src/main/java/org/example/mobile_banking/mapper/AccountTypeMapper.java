package org.example.mobile_banking.mapper;

import org.example.mobile_banking.domain.AccountType;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeCreateRequest;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeResponse;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {


    // partially map
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromAccountTypeUpdateRequest(AccountTypeUpdateRequest accountTypeUpdateRequest,@MappingTarget AccountType accountType);


    List<AccountTypeResponse>toAccountTypeResponses(List<AccountType> accountTypes);
    AccountType fromAccountTypeRequest(AccountTypeCreateRequest accountTypeCreateRequest);

    AccountType formAccountTypeUpdate(AccountTypeUpdateRequest accountTypeUpdateRequest);

    AccountTypeResponse toAccountTypeResponse(AccountType accountType);

}
