package org.example.mobile_banking.mapper;

import org.example.mobile_banking.domain.User;
import org.example.mobile_banking.feature.auth.dto.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserRegisterRequest(RegisterRequest registerRequest);

}
