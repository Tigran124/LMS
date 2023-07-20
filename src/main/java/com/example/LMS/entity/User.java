package com.example.LMS.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity(name = "user_rep")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String email;
    @OneToMany
    @Column(name = "ordered_book_list")
    private List<BookCopy> orderedBook;
    @OneToMany
    @Column(name = "fav_book_list")
    private List<Book> favBook;
    @OneToMany
    @Column(name = "review_map")
    private Map<Long, Review> reviewMap;

    public void addBookCopy(BookCopy bookCopy){
        this.orderedBook.add(bookCopy);
    }

    public void addFavBook(Book book){
        this.favBook.add(book);
    }

    public void addReview(Long key, Review review){
        this.reviewMap.put(key, review);
    }

    public Map<Long, Review> getReviewMap() {
        return reviewMap;
    }

    public void setReviewMap(Map<Long, Review> reviewMap) {
        this.reviewMap = reviewMap;
    }

    public List<Book> getFavBook() {
        return favBook;
    }

    public void setFavBook(List<Book> favBook) {
        this.favBook = favBook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
