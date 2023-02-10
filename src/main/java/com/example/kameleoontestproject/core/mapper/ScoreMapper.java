package com.example.kameleoontestproject.core.mapper;

import com.example.kameleoontestproject.controller.scores.VoteRequest;
import com.example.kameleoontestproject.core.dto.ScoreDto;
import com.example.kameleoontestproject.database.entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ScoreMapper {
    Score toEntity(ScoreDto scoreDto);

    ScoreDto toDto(Score score);
    ScoreDto toDto(VoteRequest score);

}
