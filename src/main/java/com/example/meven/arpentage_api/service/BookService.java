package com.example.meven.arpentage_api.service;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public boolean isBookExisting(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.isPresent();
    }

    public Book getBookById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new IllegalArgumentException("Book not found");
        }
        return book.get();
    }
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(int id) {
        if(!isBookExisting(id)){
            throw new IllegalArgumentException("Book to delete not found");
        }
        bookRepository.deleteById(id);
    }
}
