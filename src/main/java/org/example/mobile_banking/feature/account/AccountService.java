package org.example.mobile_banking.feature.account;

import org.example.mobile_banking.feature.account.dto.AccountCreateRequest;
import org.example.mobile_banking.feature.account.dto.AccountRenameRequest;
import org.example.mobile_banking.feature.account.dto.AccountResponse;
import org.example.mobile_banking.feature.account.dto.AccountTransferLimitRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    /**
     *
     * @param accountCreateRequest{@link AccountCreateRequest}
     * @return {@link AccountResponse}
     */
    AccountResponse createAccount(AccountCreateRequest accountCreateRequest);

    /**
     * find all acounts
     * @param pageNumber is current page request from client
     * @param pageSize is size of records per page from client
     * @return {@link List<AccountResponse>}
     */
    Page<AccountResponse> findList(int pageNumber, int  pageSize);

    /**
     *
     * find account by acount no
     * @param actNo is no of account
     * @return {@link AccountResponse}
     */
    AccountResponse findByActNo(String actNo);

    AccountResponse renameAccount(String actNo, AccountRenameRequest accountRenameRequest);

    void hideAccount(String actNo);
    void updateTransferLimit(String actNo, AccountTransferLimitRequest accountTransferLimitRequest);


}
