package org.example.mobile_banking.feature.accountType;


import org.example.mobile_banking.feature.accountType.dto.AccountTypeCreateRequest;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeResponse;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeUpdateRequest;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findList();
    void  creatAccountType(AccountTypeCreateRequest accountTypeCreateRequest);
    AccountTypeResponse updateByAlias(String alias, AccountTypeUpdateRequest accountTypeUpdateRequest);

    void deleteByAlias(String alias);
    AccountTypeResponse findByAlias(String alias);
}
