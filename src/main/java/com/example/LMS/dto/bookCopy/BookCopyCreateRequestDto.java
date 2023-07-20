package com.example.LMS.dto.bookCopy;

public class BookCopyCreateRequestDto {

    private Long bookId;
    private Long amount;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
