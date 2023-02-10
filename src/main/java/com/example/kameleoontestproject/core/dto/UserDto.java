package com.example.kameleoontestproject.core.dto;

import java.io.Serializable;

public record UserDto(Long id, String username) implements Serializable {
}
