package org.example.mobile_banking.feature.account;

import lombok.RequiredArgsConstructor;
import org.example.mobile_banking.domain.Account;
import org.example.mobile_banking.domain.AccountType;
import org.example.mobile_banking.domain.User;
import org.example.mobile_banking.feature.account.dto.AccountCreateRequest;
import org.example.mobile_banking.feature.account.dto.AccountRenameRequest;
import org.example.mobile_banking.feature.account.dto.AccountResponse;
import org.example.mobile_banking.feature.account.dto.AccountTransferLimitRequest;
import org.example.mobile_banking.feature.accountType.AccountTypeRepository;
import org.example.mobile_banking.feature.user.UserRepository;
import org.example.mobile_banking.mapper.AccountMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{


    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    @Override
    public AccountResponse createAccount(AccountCreateRequest accountCreateRequest) {

        //Validate Account Type
        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountType())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account Type has not been found"));

        //Validate User
        User user = userRepository.findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found"));

        //Validate account no;
        if(accountRepository.existsByActNo(accountCreateRequest.actNo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Account No has already been exists");
        }
        //Validate balance
        if(accountCreateRequest.balance().compareTo(BigDecimal.valueOf(10))<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Balance should be greater than 10");
        }


        //Transfer DTO to domain model
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        account.setAccountType(accountType);
        account.setUser(user);

        //system  generate data
        account.setActName(user.getName());
        account.setIsHidden(false);
        account.setTransferLimit(BigDecimal.valueOf(1000));

        //save account into database and get latest data back
        account = accountRepository.save(account);

        /*return AccountResponse.builder()
                .alias(account.getAlias())
                .actName(account.getActName())
                .actNo(account.getActNo())
                .balance(account.getBalance())
                .accountType(account.getAccountType().getName())
                .build();*/
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public Page<AccountResponse> findList(int pageNumber, int pageSize) {

        Sort sortById  = Sort.by(Sort.Direction.DESC,"id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortById);
        Page<Account>accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);
    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        // Validate account no
        Account account = accountRepository.findByActNo(actNo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account no has not found"));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse renameAccount(String actNo, AccountRenameRequest accountRenameRequest) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account no has not been found"));
        account.setAlias(accountRenameRequest.alias());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public void hideAccount(String actNo) {

        // validate account no
        Account account = accountRepository.findByActNo(actNo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account no has not been found"));

        account.setIsHidden(true);
        accountRepository.save(account);
    }

    @Override
    public void updateTransferLimit(String actNo, AccountTransferLimitRequest accountTransferLimitRequest) {
        // validate account no
        Account account = accountRepository.findByActNo(actNo).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account no has not been found"));
        account.setTransferLimit(accountTransferLimitRequest.amount());
        accountRepository.save(account);
    }


}
