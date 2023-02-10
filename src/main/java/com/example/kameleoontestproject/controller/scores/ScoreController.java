package com.example.kameleoontestproject.controller.scores;

import com.example.kameleoontestproject.core.mapper.ScoreMapper;
import com.example.kameleoontestproject.core.service.quotes.QuoteService;
import com.example.kameleoontestproject.core.service.score.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/vote")
@AllArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;
    private final QuoteService quoteService;
    private final ScoreMapper scoreMapper;

    @PostMapping("/upvote")
    public ResponseEntity<Void> upvote(@RequestBody VoteRequest request) {
        var scoreDto = scoreMapper.toDto(request);
        if (!scoreService.isVoted(scoreDto)) {
            scoreService.upvote(scoreDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/downvote")
    public ResponseEntity<Void> downvote(@RequestParam VoteRequest request) {
        var scoreDto = scoreMapper.toDto(request);
        if (!scoreService.isVoted(scoreDto)) {
            scoreService.downvote(scoreDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @GetMapping("/last")
    public List<VoteResponse> getUserVotes(@RequestParam Long userId) {
        var quoteDtosList = quoteService.getQuotesByVotedUserId(userId);
        return quoteDtosList.stream()
                            .map(item -> new VoteResponse(item.id(), item.text()))
                            .toList();
    }
}
