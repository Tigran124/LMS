package com.example.LMS.configuration;

import com.example.LMS.builder.BookCopyCreateResponseBuilder;
import com.example.LMS.dto.bookCopy.BookCopyCreateResponseDto;
import com.example.LMS.entity.Availability;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.BookCopy;
import com.example.LMS.repository.BookCopyRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookCopyFactory {

    private final BookCopyRepository bookCopyRepository;

    public BookCopyFactory(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    public List<BookCopyCreateResponseDto> createBookCopy(Long amount, Book book){
        List<BookCopyCreateResponseDto> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(book);
            bookCopy.setAvailability(Availability.AVAILABLE);
            BookCopy savedBookCopy = bookCopyRepository.save(bookCopy);
            list.add(BookCopyCreateResponseBuilder.buildBookCopyResponseDto(savedBookCopy));
        }
        return list;
    }
}
