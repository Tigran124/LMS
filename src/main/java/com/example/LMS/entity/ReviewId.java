package com.example.LMS.entity;

import java.io.Serializable;

public class ReviewId implements Serializable {

    private User user;
    private Book book;

    public ReviewId() {
    }

    public ReviewId(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewId reviewId = (ReviewId) o;

        if (!user.equals(reviewId.user)) return false;
        return book.equals(reviewId.book);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + book.hashCode();
        return result;
    }
}
