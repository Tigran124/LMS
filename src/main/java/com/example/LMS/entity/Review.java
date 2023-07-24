package com.example.LMS.entity;

import jakarta.persistence.*;

@Entity(name = "review")
@Table(uniqueConstraints = @UniqueConstraint(name = "UniqueUserAndBook",
        columnNames = {"user_id", "book_id"}))
@IdClass(ReviewId.class)
public class Review {

    private Double rate;
    private String comment;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        this.book.addReview(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }
}
