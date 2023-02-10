package com.example.kameleoontestproject.core.service.user;

import com.example.kameleoontestproject.core.dto.UserDto;
import com.example.kameleoontestproject.core.mapper.UserMapper;
import com.example.kameleoontestproject.database.repository.UserRepository;
import org.springframework.lang.NonNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public void createUser(@NonNull UserDto user) {
        userRepository.save(mapper.toEntity(user));
    }

    @Override
    public boolean isExist(@NonNull UserDto user) {
        return userRepository.existsByUsername(user.username());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                      .stream()
                      .map(mapper::toDto)
                      .toList();
    }

    @Override
    public UserDto findById(@NonNull Long id) {
        var userEntity = userRepository.getReferenceById(id);
        return mapper.toDto(userEntity);
    }


}
