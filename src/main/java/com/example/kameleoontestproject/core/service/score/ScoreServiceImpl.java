package com.example.kameleoontestproject.core.service.score;

import com.example.kameleoontestproject.core.dto.ScoreDto;
import com.example.kameleoontestproject.core.mapper.ScoreMapper;
import com.example.kameleoontestproject.database.entity.Score;
import com.example.kameleoontestproject.database.repository.QuoteRepository;
import com.example.kameleoontestproject.database.repository.ScoreRepository;
import com.example.kameleoontestproject.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;

    @Override
    public void upvote(ScoreDto scoreDto) {
        var quote = quoteRepository.getReferenceById(scoreDto.quoteId());
        var user = userRepository.getReferenceById(scoreDto.userId());
        var scoreEntity = new Score();
        scoreEntity.setQuote(quote);
        scoreEntity.setUser(user);
        scoreRepository.save(scoreEntity);
    }


    @Override
    public void downvote(ScoreDto scoreDto) {
        scoreRepository.deleteById(scoreDto.id());
    }

    @Override
    public List<ScoreDto> getUserVotes(Long userId) {
        return scoreRepository.findByUser_IdOrderByTimestampDesc(userId)
                .stream()
                .map(scoreMapper::toDto)
                .toList();
    }
    @Override
    public List<ScoreDto> getUserVotes(Long userId, int size) {
        Pageable votesPageable = PageRequest.of(0, size, Sort.by("timestamp").descending());
        return scoreRepository.findByUser_IdOrderByTimestampDesc(userId, votesPageable)
                .stream()
                .map(scoreMapper::toDto)
                .toList();
    }

    @Override
    public boolean isVoted(ScoreDto scoreDto) {
        return scoreRepository.existsByUser_IdAndQuote_Id(scoreDto.userId(), scoreDto.quoteId());
    }
}
