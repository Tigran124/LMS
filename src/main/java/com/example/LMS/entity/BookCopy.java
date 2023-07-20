package com.example.LMS.entity;

import jakarta.persistence.*;

@Entity
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Availability availability;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setBook(Book book) {
        this.book = book;
        this.book.addBookCopy(this);
    }

    public void setUser(User user) {
        this.user = user;
        this.user.addBookCopy(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }
}
