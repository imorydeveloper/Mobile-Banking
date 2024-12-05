package org.example.mobile_banking.feature.accountType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeCreateRequest;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeResponse;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createAccountType(@Valid @RequestBody AccountTypeCreateRequest accountTypeCreateRequest){
        accountTypeService.creatAccountType(accountTypeCreateRequest);
    }

    @GetMapping
    public List<AccountTypeResponse> findAccountTypeList() {
        return accountTypeService.findList();
    }

    @PatchMapping("/{alias}")
    public AccountTypeResponse updateByAlias(@PathVariable String alias, @RequestBody AccountTypeUpdateRequest accountTypeUpdateRequest){
        return accountTypeService.updateByAlias(alias, accountTypeUpdateRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{alias}")
    void deleteByAlias(@PathVariable("alias") String alias){
        accountTypeService.deleteByAlias(alias);
    }

    @GetMapping("/{alias}")
    AccountTypeResponse findByAlias(@PathVariable("alias") String alias){
        return accountTypeService.findByAlias(alias);
    }
}
