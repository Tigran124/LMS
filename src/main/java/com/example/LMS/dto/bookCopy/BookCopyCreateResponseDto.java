package com.example.LMS.dto.bookCopy;

import com.example.LMS.entity.Availability;

public class BookCopyCreateResponseDto {

    private Long id;
    private Long bookId;
    private Availability availability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
