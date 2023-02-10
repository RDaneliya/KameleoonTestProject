package com.example.kameleoontestproject.controller.quote;

import com.example.kameleoontestproject.core.dto.QuoteDto;
import com.example.kameleoontestproject.core.dto.ScoreDto;
import com.example.kameleoontestproject.core.mapper.QuoteMapper;
import com.example.kameleoontestproject.core.service.quotes.QuoteService;
import com.example.kameleoontestproject.core.service.user.UserService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")
@AllArgsConstructor
public class QuoteController {
    private final QuoteMapper quoteMapper;
    private final QuoteService quoteService;
    private final UserService userService;

    private final int DEFAULT_QUOTE_LIST_SIZE = 10;

    @GetMapping("/all")
    public List<QuoteResponse> getAllQuotes() {
        return quoteService.getAllQuotes()
                           .stream()
                           .map(this::mapToQuoteResponse)
                           .toList();
    }

    @GetMapping("/top")
    @Validated
    public List<QuoteResponse> getTopQuotes(@RequestParam @Min(1) Optional<Integer> size) {
        var querySize = size.orElse(DEFAULT_QUOTE_LIST_SIZE);
        return quoteService.getTopQuotes(querySize)
                           .stream()
                           .map(this::mapToQuoteResponse)
                           .toList();

    }
    @GetMapping("/flop")
    @Validated
    public List<QuoteResponse> getFlopQuotes(@RequestParam @Min(1) Optional<Integer> size) {
        var querySize = size.orElse(DEFAULT_QUOTE_LIST_SIZE);
        return quoteService.getFlopQuotes(querySize)
                           .stream()
                           .map(this::mapToQuoteResponse)
                           .toList();

    }

    @GetMapping("/recent")
    @Validated
    public List<QuoteResponse> getRecentQuotes() {
        return quoteService.getRecentQuotes(DEFAULT_QUOTE_LIST_SIZE)
                           .stream()
                           .map(this::mapToQuoteResponse)
                           .toList();
    }

    @PostMapping
    public ResponseEntity<Void> createQuote(@RequestBody QuoteRequest request) {
        if (!quoteService.checkIfQuoteExists(request.text())) {
            var quoteDto = quoteMapper.toDto(request);
            quoteService.save(quoteDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @NonNull
    private QuoteResponse mapToQuoteResponse(@NonNull Pair<QuoteDto, List<ScoreDto>> item) {
        var quote = item.getFirst();
        var voteTimestamps = item.getSecond().stream()
                .map(ScoreDto::timestamp)
                .toList();
        var userEntity = userService.findById(quote.authorId());
        return new QuoteResponse(
                quote.id(),
                quote.text(),
                userEntity.username(),
                (long) quote.scores().size(),
                voteTimestamps
        );
    }
}
