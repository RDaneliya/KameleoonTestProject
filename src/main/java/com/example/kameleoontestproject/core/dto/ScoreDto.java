package com.example.kameleoontestproject.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ScoreDto(Long id, LocalDateTime timestamp, Long userId, Long quoteId) implements Serializable {
}
