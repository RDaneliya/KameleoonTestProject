package com.example.kameleoontestproject.database.repository;

import com.example.kameleoontestproject.database.entity.User;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(@NonNull String username);

}
