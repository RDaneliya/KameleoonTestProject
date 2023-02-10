package com.example.kameleoontestproject.core.service.quotes;

import com.example.kameleoontestproject.core.dto.QuoteDto;
import com.example.kameleoontestproject.core.dto.ScoreDto;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;

import java.util.List;

public interface QuoteService {
    List<Pair<QuoteDto, List<ScoreDto>>> getTopQuotes(int size);

    List<Pair<QuoteDto, List<ScoreDto>>> getFlopQuotes(int size);

    List<Pair<QuoteDto, List<ScoreDto>>> getRecentQuotes(int size);

    List<Pair<QuoteDto, List<ScoreDto>>> getAllQuotes();

    List<QuoteDto> getQuotesByVotedUserId(Long userId);

    boolean checkIfQuoteExists(String text);

    void save(@NonNull QuoteDto quoteDto);
}
