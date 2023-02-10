package com.example.kameleoontestproject.database.repository;

import com.example.kameleoontestproject.database.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    boolean existsByText(String text);

    List<Quote> findByScores_User_Id(@NonNull Long id, Pageable pageable);
    List<Quote> findByScores_User_Id(@NonNull Long id);

}
