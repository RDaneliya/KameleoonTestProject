package com.example.kameleoontestproject.controller.user;

import com.example.kameleoontestproject.core.mapper.UserMapper;
import com.example.kameleoontestproject.core.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequest request) {
        var userDto = userMapper.toDto(request);
        if (!userService.isExist(userDto)) {
            userService.createUser(userDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/all")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers()
                          .stream()
                          .map(userMapper::toResponse)
                          .toList();
    }
}
