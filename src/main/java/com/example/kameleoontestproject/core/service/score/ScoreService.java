package com.example.kameleoontestproject.core.service.score;

import com.example.kameleoontestproject.core.dto.ScoreDto;

import java.util.List;

public interface ScoreService {
    void upvote(ScoreDto scoreDto);

    void downvote(ScoreDto scoreDto);

    List<ScoreDto> getUserVotes(Long userId);

    List<ScoreDto> getUserVotes(Long userId, int size);

    boolean isVoted(ScoreDto scoreDto);
}
