package com.example.LMS.configuration;

import com.example.LMS.entity.Availability;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.BookCopy;
import com.example.LMS.repository.BookCopyRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookCopyFactory {

    private final BookCopyRepository bookCopyRepository;

    public BookCopyFactory(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    @Transactional
    public void createBookCopy(Long amount, Book book){
        for (int i = 0; i < amount; i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(book);
            bookCopy.setAvailability(Availability.AVAILABLE);
            bookCopyRepository.save(bookCopy);
        }
    }
}
