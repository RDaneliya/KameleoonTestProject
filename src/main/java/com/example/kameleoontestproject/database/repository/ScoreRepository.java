package com.example.kameleoontestproject.database.repository;

import com.example.kameleoontestproject.database.entity.Score;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    boolean existsByUser_IdAndQuote_Id(@NonNull Long userId, @NonNull Long quoteId);

    List<Score> findByUser_IdOrderByTimestampDesc(Long id, Pageable pageable);
    List<Score> findByUser_IdOrderByTimestampDesc(Long id);

}
