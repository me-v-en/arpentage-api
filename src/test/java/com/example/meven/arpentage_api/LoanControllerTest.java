package com.example.meven.arpentage_api;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Loan;
import com.example.meven.arpentage_api.model.LoanCreationRequest;
import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.BookService;
import com.example.meven.arpentage_api.service.LoanService;
import com.example.meven.arpentage_api.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Specify that JUnit use only one instance of the class for all tests, not one for each
// Allows to use non-static @BeforeAll setup methods
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;
    @Autowired
    private BookService bookService;
    @Autowired
    private LoanService loanService;

    private Member bookOwner;
    private Member borrower;
    private Book book;
    private Loan loan;

    @BeforeAll
    void setUp() {
        // Setup a default bookOwner obj
        Member o = new Member();
        o.setPseudo("Lender");
        o.setMail("test@example.com");
        bookOwner = memberService.saveMember(o);

        // Setup a default borrower obj
        Member b = new Member();
        b.setPseudo("Lender");
        b.setMail("test@example.com");
        borrower = memberService.saveMember(b);

        // Setup a default book obj
        Book bk = new Book();
        bk.setTitle("Book Title");
        bk.setAuthor("Author");
        bk.setDescription("Description");
        bk.setOwner(bookOwner);
        book = bookService.saveBook(bk);

        loan = loanService.createLoan( borrower, book);
    }

    @AfterAll
    public void tearDown() {
        loanService.deleteLoanById(loan.getId());
        bookService.deleteBook(book.getId());
        memberService.deleteMemberById(bookOwner.getId());
        memberService.deleteMemberById(borrower.getId());
    }

    @Test
    public void getLoanTest() throws Exception {
        // Test the API endpoint
        mockMvc.perform(get("/loan/"+loan.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loan.getId()));
    }

    @Test
    public void returnLoanTest() throws Exception {
        // Test the API endpoint
        mockMvc.perform(post("/loan/"+loan.getId()+"/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ongoing").value(false));
    }


    @Test
    public void createLoanTest() throws Exception {
        //Setup the loan object
        LoanCreationRequest request = new LoanCreationRequest();

        request.setBorrowerId(borrower.getId());
        // Create a new book obj
        Book bk = new Book();
        bk.setTitle("Book Title");
        bk.setAuthor("Author");
        bk.setDescription("Description");
        bk.setOwner(bookOwner);
        bk = bookService.saveBook(bk);

        request.setBookId(bk.getId());

        // Create the JSON
        String payload = objectMapper.writeValueAsString(request);

        // Test the API endpoint
        mockMvc.perform(post("/loan/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());
    }

    @Test
    public void createLoanWithIncompleteRequestTest() throws Exception {
        //Setup the loan object
        LoanCreationRequest request = new LoanCreationRequest();

        request.setBorrowerId(borrower.getId());

        // Create the JSON
        String payload = objectMapper.writeValueAsString(request);

        // Test the API endpoint
        mockMvc.perform(post("/loan/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createLoanWithLendedBookTest() throws Exception {
        // Create a loan with a book
        loanService.createLoan( borrower, book);

        // Create another loan of the same book
        LoanCreationRequest request = new LoanCreationRequest();
        request.setBorrowerId(borrower.getId());
        request.setBookId(book.getId());

        // Create the JSON
        String payload = objectMapper.writeValueAsString(request);

        // Test the API endpoint
        mockMvc.perform(post("/loan/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }


}
