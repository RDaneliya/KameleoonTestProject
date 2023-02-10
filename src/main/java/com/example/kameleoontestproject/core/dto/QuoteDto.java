package com.example.kameleoontestproject.core.dto;

import java.io.Serializable;
import java.util.List;

public record QuoteDto(Long id, String text, Long authorId, List<ScoreDto> scores) implements Serializable {
}
