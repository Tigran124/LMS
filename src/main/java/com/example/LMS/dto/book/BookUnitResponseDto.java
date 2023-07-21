package com.example.LMS.dto.book;

import com.example.LMS.dto.review.ReviewResponseDto;

import java.util.List;

public class BookUnitResponseDto {

    private String bookName;
    private Long authorId;
    private String authorName;
    private Double rate;
    private List<ReviewResponseDto> reviewList;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public List<ReviewResponseDto> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewResponseDto> reviewList) {
        this.reviewList = reviewList;
    }
}
