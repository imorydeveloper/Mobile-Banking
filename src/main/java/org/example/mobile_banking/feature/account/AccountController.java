package org.example.mobile_banking.feature.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mobile_banking.feature.account.dto.AccountCreateRequest;
import org.example.mobile_banking.feature.account.dto.AccountRenameRequest;
import org.example.mobile_banking.feature.account.dto.AccountResponse;
import org.example.mobile_banking.feature.account.dto.AccountTransferLimitRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody AccountCreateRequest accountCreateRequest){
        return accountService.createAccount(accountCreateRequest);
    }

    @GetMapping
    Page<AccountResponse>findList(@RequestParam(required = false,defaultValue = "0") int pageNumber, @RequestParam(required = false,defaultValue = "25") int pageSize){
        return accountService.findList(pageNumber, pageSize);
    }
    @GetMapping("/{actNo}")
    public AccountResponse findByActNo(@PathVariable("actNo") String actNo){
        return accountService.findByActNo(actNo);
    }

    @PutMapping("/{actNo}/rename")
    AccountResponse renameAccount(@PathVariable("actNo") String actNo, @Valid @RequestBody AccountRenameRequest accountRenameRequest){
        return accountService.renameAccount(actNo,accountRenameRequest);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{actNo}/hide")
    void hideAccount(@PathVariable("actNo") String actNo){
        accountService.hideAccount(actNo);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{actNo}/transfer-limit")
    void updateTransferLimit(@PathVariable("actNo") String actNo, @Valid @RequestBody AccountTransferLimitRequest accountTransferLimitRequest){
        accountService.updateTransferLimit(actNo,accountTransferLimitRequest);
    }


}