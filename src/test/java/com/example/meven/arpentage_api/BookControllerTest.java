package com.example.meven.arpentage_api;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.BookService;
import com.example.meven.arpentage_api.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        Book book = new Book();
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setDescription("Description");
        Optional<Member> m = memberService.getMemberById(1);
        if (m.isPresent()) {
            book.setOwner(m.get());
        }

        testBook = bookService.saveBook(book);
    }

    @AfterEach
    public void tearDown() {
        bookService.deleteBook((long) testBook.getId());
    }


    @Test
    public void getBookByIdTest() throws Exception {
        mockMvc.perform(get("/book/" + testBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    public void getAllBooksTest() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[-1:].title").value(testBook.getTitle()));
    }

    @Test
    public void createBookTest() throws Exception {
        Book book = new Book();
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setDescription("Description");

        String payload = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/book/new/owner/1")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    public void updateBookTest() throws Exception {
        testBook.setTitle("New Title");
        String payload = objectMapper.writeValueAsString(testBook);

        mockMvc.perform(put("/book/" + testBook.getId())
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

@Test
public void deleteBookTest() throws Exception {
        int bookId = testBook.getId();
        mockMvc.perform(delete("/book/" + bookId))
                .andExpect(status().isOk());

        assertTrue(bookService.getBookById(bookId).isEmpty());
}

}
