package com.example.kameleoontestproject.controller.quote;


import java.util.List;

public record QuoteResponse(Long id, String text, String author, Long score, List<java.time.LocalDateTime> timestamps) {
}
