package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    List<UserTest> findAllByUserId(Long userId);
    Optional<UserTest> findByTestAndUserAndFinishedAtIsNull(TestEntity testEntity, User user);

    @Transactional
    @Modifying
    @Query("UPDATE UserTest ut SET ut.finishedAt = :date, ut.score = 0 WHERE ut.finishedAt IS NULL")
    int finishAllTests(@Param("date") Date date);
}
