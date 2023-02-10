package com.example.kameleoontestproject.core.mapper;

import com.example.kameleoontestproject.controller.quote.QuoteRequest;
import com.example.kameleoontestproject.controller.quote.QuoteResponse;
import com.example.kameleoontestproject.core.dto.QuoteDto;
import com.example.kameleoontestproject.database.entity.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuoteMapper {
    Quote toEntity(QuoteDto quoteDto);

    @Mapping(source = "author.id", target = "authorId")
    QuoteDto toDto(Quote quote);

    QuoteDto toDto(QuoteRequest quote);

    QuoteResponse toResponse(QuoteDto quoteDto);
}
