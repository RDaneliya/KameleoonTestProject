package com.example.kameleoontestproject.core.mapper;

import com.example.kameleoontestproject.controller.user.UserRequest;
import com.example.kameleoontestproject.controller.user.UserResponse;
import com.example.kameleoontestproject.core.dto.UserDto;
import com.example.kameleoontestproject.database.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    UserDto toDto(UserRequest userRequest);

    UserResponse toResponse(UserDto userDto);

}
