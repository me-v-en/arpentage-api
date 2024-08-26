package com.example.meven.arpentage_api.service;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> getBook(long id) {
        return bookRepository.findById(id);
    }
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}
