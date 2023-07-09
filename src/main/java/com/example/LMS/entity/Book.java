package com.example.LMS.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    @Transient
    private Double rate;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @OneToMany
    @Column(name = "review_list")
    private List<Review> reviewList;
    @OneToMany
    @Column(name = "book_copy")
    private List<BookCopy> bookCopyList;

    public void addReview(Review review){
        this.reviewList.add(review);
    }

    public void setAuthor(Author author) {
        this.author = author;
        this.author.addBook(this);
    }

    public List<BookCopy> getBookCopyList() {
        return bookCopyList;
    }

    public void setBookCopyList(List<BookCopy> bookCopyList) {
        this.bookCopyList = bookCopyList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
