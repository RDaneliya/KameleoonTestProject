package com.example.kameleoontestproject.core.service.quotes;

import com.example.kameleoontestproject.core.dto.QuoteDto;
import com.example.kameleoontestproject.core.dto.ScoreDto;
import com.example.kameleoontestproject.core.mapper.QuoteMapper;
import com.example.kameleoontestproject.core.mapper.ScoreMapper;
import com.example.kameleoontestproject.database.entity.Quote;
import com.example.kameleoontestproject.database.repository.QuoteRepository;
import com.example.kameleoontestproject.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final QuoteMapper quoteMapper;
    private final ScoreMapper scoreMapper;

    @Override
    public List<Pair<QuoteDto, List<ScoreDto>>> getTopQuotes(int size) {
        Pageable topQuotesPageable = PageRequest.of(0, size, Sort.by("scores").descending());
        return quoteRepository.findAll(topQuotesPageable).stream()
                .map(this::getQuoteDtoListPair)
                .toList();
    }

    @Override
    public List<Pair<QuoteDto, List<ScoreDto>>> getFlopQuotes(int size) {
        Pageable flopQuotesPageable = PageRequest.of(0, size, Sort.by("scores").ascending());
        return quoteRepository.findAll(flopQuotesPageable).stream()
                              .map(this::getQuoteDtoListPair)
                              .toList();
    }

    @Override
    public List<Pair<QuoteDto, List<ScoreDto>>> getRecentQuotes(int size) {
        Pageable recentQuotesPageable = PageRequest.of(0, size, Sort.by("timestamp").descending());
        return quoteRepository.findAll(recentQuotesPageable).stream()
                              .map(this::getQuoteDtoListPair)
                              .toList();
    }

    @Override
    public List<Pair<QuoteDto, List<ScoreDto>>> getAllQuotes() {
        return quoteRepository.findAll().stream()
                .map(this::getQuoteDtoListPair)
                .toList();
    }

    @Override
    public List<QuoteDto> getQuotesByVotedUserId(Long userId) {
        Pageable recentVoted = PageRequest.of(0, 5, Sort.by("timestamp").descending());
        var quoteEntitiesList = quoteRepository.findByScores_User_Id(userId, recentVoted);
        return quoteEntitiesList.stream()
                .map(quoteMapper::toDto)
                .toList();
    }

    @Override
    public boolean checkIfQuoteExists(@NonNull String text) {
        return quoteRepository.existsByText(text);
    }

    @Override
    public void save(@NonNull QuoteDto quoteDto) {
        var user = userRepository.getReferenceById(quoteDto.authorId());
        var quoteEntity = quoteMapper.toEntity(quoteDto);
        quoteEntity.setAuthor(user);
        quoteRepository.save(quoteEntity);
    }

    private Pair<QuoteDto, List<ScoreDto>> getQuoteDtoListPair(Quote item) {
        var quoteDto = quoteMapper.toDto(item);
        var upvoteDtosList = item.getScores().stream()
                                 .map(scoreMapper::toDto)
                                 .toList();
        return Pair.of(quoteDto, upvoteDtosList);
    }
}
