package com.example.LMS.repository;

import com.example.LMS.entity.Book;
import com.example.LMS.entity.Review;
import com.example.LMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Modifying
    @Query("update review r set r.comment = ?1, r.rate = ?2 where r.id = ?3")
    void updateById(String comment, Double rate, Long id);

    @Query("from review r where r.user = ?1 and r.book = ?2")
    Review findByUserAndBookId(User user, Book book);
}
