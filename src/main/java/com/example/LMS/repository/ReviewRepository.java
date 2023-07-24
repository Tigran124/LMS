package com.example.LMS.repository;

import com.example.LMS.entity.Book;
import com.example.LMS.entity.Review;
import com.example.LMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("from review r where r.user = ?1 and r.book = ?2")
    Optional<Review> findByUserAndBookId(User user, Book book);
}
