package org.example.mobile_banking.feature.accountType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile_banking.domain.AccountType;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeCreateRequest;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeResponse;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeUpdateRequest;
import org.example.mobile_banking.mapper.AccountTypeMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AccountTypeResponse> findList() {
        Sort sortbyID = Sort.by(Sort.Direction.DESC,"id");
        List<AccountType>accountTypes = accountTypeRepository.findAll();
        return accountTypeMapper.toAccountTypeResponses(accountTypes);
    }

    @Override
    public void creatAccountType(AccountTypeCreateRequest accountTypeCreateRequest) {
        // validate alias
        if(accountTypeRepository.existsByAlias(accountTypeCreateRequest.alias())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"AccountTypeAlias has already exist");
        }

        AccountType accountType = accountTypeMapper.fromAccountTypeRequest((accountTypeCreateRequest));
        accountType.setIsDeleted(false);
        accountTypeRepository.save(accountType);

    }

    @Override
    public AccountTypeResponse updateByAlias(String alias,AccountTypeUpdateRequest accountTypeUpdateRequest) {
        //validate alias
        AccountType accountType = accountTypeRepository.findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"AccountType alias has not been found"));

        /*if(accountTypeUpdateRequest.description() != null)
            accountType.setDescription(accountTypeUpdateRequest.description());
        if(accountTypeUpdateRequest.isDeleted() != null)
            accountType.setIsDeleted(accountTypeUpdateRequest.isDeleted());*/


        log.info("Before map: {},{},{}",accountType.getId(),accountType.getDescription(),accountType.getIsDeleted());
        accountTypeMapper.fromAccountTypeUpdateRequest(accountTypeUpdateRequest,accountType);
        log.info("After map: {},{},{}",accountType.getId(),accountType.getDescription(),accountType.getIsDeleted());
         accountType = accountTypeRepository.save(accountType);
         return accountTypeMapper.toAccountTypeResponse(accountType);
    }

    @Override
    public void deleteByAlias(String alias) {
        // validate alias
        AccountType accountType = accountTypeRepository.findByAlias(alias).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type alias has not been found"));
        accountTypeRepository.delete(accountType);
    }

    @Override
    public AccountTypeResponse findByAlias(String alias) {
       // validate alias
       AccountType accountType  = accountTypeRepository.findByAlias(alias).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type alias has not been found"));
       return accountTypeMapper.toAccountTypeResponse(accountType);
    }

}
