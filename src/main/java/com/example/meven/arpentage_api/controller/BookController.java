package com.example.meven.arpentage_api.controller;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.BookService;
import com.example.meven.arpentage_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        try {
            Iterable<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book book) {
        try {
            Book bookToUpdate = bookService.getBookById(id);

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
            return ResponseEntity.ok(bookToUpdate);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        try {

            bookService.deleteBook(id);
            return ResponseEntity.ok().build();

        } catch (
                IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (
                Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
