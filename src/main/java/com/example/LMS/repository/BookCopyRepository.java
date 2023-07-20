package com.example.LMS.repository;

import com.example.LMS.entity.BookCopy;
import com.example.LMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    @Modifying
    @Query("update BookCopy b set b.user = ?1 where b.id = ?2")
    void updateById(User user, Long id);
}
