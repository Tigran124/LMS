package com.example.LMS.configuration;

import com.example.LMS.entity.Author;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.User;
import com.example.LMS.repository.AuthorRepository;
import com.example.LMS.repository.BookRepository;
import com.example.LMS.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializer(UserRepository userRepository, AuthorRepository authorRepository, BookRepository bookRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createAdminUser();
        createAuthors();
//        createBooks();
    }

    private void createAdminUser(){
        User user = new User();
        user.setUsername("Tigran");
        user.setPassword(passwordEncoder.encode("1qaz2wsx"));
        user.setEmail("Tigran.h@mail.com");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
    private void createAuthors(){
        Author author1 = new Author();
        author1.setBookList(new ArrayList<>());
        author1.setAuthorName("Raffi");
        authorRepository.save(author1);
        Author author2 = new Author();
        author2.setBookList(new ArrayList<>());
        author2.setAuthorName("George R.R. Martin");
        authorRepository.save(author2);
    }
    private void createBooks(){
        Book book1 = new Book();
        book1.setBookName("Samvel");
        Optional<Author> author1 = authorRepository.findById(1L);
        if (author1.isEmpty()){
            throw new RuntimeException();
        }
        book1.setAuthor(author1.get());
        book1.setBookCopyList(new ArrayList<>());
        book1.setReviewList(new ArrayList<>());
        bookRepository.save(book1);
        Book book2 = new Book();
        book2.setBookName("Xent");
        book2.setAuthor(author1.get());
        bookRepository.save(book2);
        Book book3 = new Book();
        book3.setBookName("Kings Battle");
        Optional<Author> author2 = authorRepository.findById(2L);
        if (author2.isEmpty()){
            throw new RuntimeException();
        }
        book3.setAuthor(author2.get());
        bookRepository.save(book3);
        Book book4 = new Book();
        book4.setBookName("Bastards Battle");
        book4.setAuthor(author2.get());
        bookRepository.save(book4);
    }
}
