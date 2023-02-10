package com.example.kameleoontestproject.core.service.user;

import com.example.kameleoontestproject.core.dto.UserDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {
    void createUser(@NonNull UserDto user);

    boolean isExist(@NonNull UserDto user);

    List<UserDto> getAllUsers();

    UserDto findById(@NonNull Long id);
}
