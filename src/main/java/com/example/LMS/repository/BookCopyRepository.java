package com.example.LMS.repository;

import com.example.LMS.entity.BookCopy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    Page<BookCopy> findByBook_Id(Long id, Pageable pageable);

}
