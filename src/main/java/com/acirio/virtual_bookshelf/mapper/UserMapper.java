package com.acirio.virtual_bookshelf.mapper;

import com.acirio.virtual_bookshelf.dto.UserAdminResponseDto;
import com.acirio.virtual_bookshelf.dto.UserRegisterRequestDto;
import com.acirio.virtual_bookshelf.dto.UserResponseDto;
import com.acirio.virtual_bookshelf.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", defaultValue = "ROLE_USER")
    UserModel toEntity(UserRegisterRequestDto userRegisterRequestDto);

    UserResponseDto toResponse(UserModel userModel);

    UserAdminResponseDto toAdminResponse(UserModel userModel);

}
