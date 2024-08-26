package com.example.meven.arpentage_api.controller;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.BookService;
import com.example.meven.arpentage_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/book/{id}")
    public Optional<Book> getBookById(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @GetMapping("/books")
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/book/new/owner/{ownerId}")
    public Book createBook(@PathVariable int ownerId, @RequestBody Book book) {
        // TODO : get the ownerId from the id of the user connected ?
        Optional<Member> m = memberService.getMemberById(ownerId);
        if (m.isPresent()) {
            Member member = m.get();
            book.setOwner(member);
            return bookService.saveBook(book);
        } else return null;
    }

    @PutMapping("/book/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> b = bookService.getBook(id);
        // If book exists, update his properties
        if (b.isPresent()) {
            Book bookToUpdate = b.get();
            if (book.getAuthor() != null) {
                bookToUpdate.setAuthor(book.getAuthor());
            }
            if (book.getTitle() != null) {
                bookToUpdate.setTitle(book.getTitle());
            }
            if (book.getDescription() != null) {
                bookToUpdate.setDescription(book.getDescription());
            }

            bookService.saveBook(bookToUpdate);
            return bookToUpdate;
            // If not, return null
            // TODO : Handle with exceptions ?
        } else return null;
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
