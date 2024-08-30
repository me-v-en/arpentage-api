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

    private Member lender;
    private Member borrower;
    private Book book;
    private Loan loan;

    @BeforeAll
    void setUp() {
        // Setup a default lender obj
        Member l = new Member();
        l.setPseudo("Lender");
        l.setMail("test@example.com");
        lender = memberService.saveMember(l);

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
        bk.setOwner(lender);
        book = bookService.saveBook(bk);

        loan = loanService.createLoan(lender, borrower, book);
    }

    @AfterAll
    public void tearDown() {
        loanService.deleteLoanById(loan.getId());
        bookService.deleteBook(book.getId());
        memberService.deleteMemberById(lender.getId());
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

        request.setLenderId(lender.getId());
        request.setBorrowerId(borrower.getId());
        // Create a new book obj
        Book bk = new Book();
        bk.setTitle("Book Title");
        bk.setAuthor("Author");
        bk.setDescription("Description");
        bk.setOwner(lender);
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

        request.setLenderId(lender.getId());
        request.setBorrowerId(borrower.getId());

        // Create the JSON
        String payload = objectMapper.writeValueAsString(request);

        // Test the API endpoint
        mockMvc.perform(post("/loan/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

//    public void createLoanWithLendedBookTest() throws Exception {
//        LoanCreationRequest request = new LoanCreationRequest();
//        request.setLenderId(lender.getId());
//        request.setBorrowerId(borrower.getId());
//        request.setBookId(book.getId());
//
//        // Create the JSON
//        String payload = objectMapper.writeValueAsString(request);
//
//        // Test the API endpoint
//        mockMvc.perform(post("/loan/new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(payload))
//                .andExpect(status().isCreated());
//    }


}
