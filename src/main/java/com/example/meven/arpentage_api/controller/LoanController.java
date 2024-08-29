package com.example.meven.arpentage_api.controller;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Loan;
import com.example.meven.arpentage_api.model.LoanCreationRequest;
import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.BookService;
import com.example.meven.arpentage_api.service.LoanService;
import com.example.meven.arpentage_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BookService bookService;

    @GetMapping("/loan/{id}")
    public Optional<Loan> getLoanById(@PathVariable int id) {
        return loanService.getLoanById(id);
    }

    @PostMapping("/loan/new")
    public Loan createLoan(@RequestBody LoanCreationRequest LoanCreationRequest) {
        Loan loan = new Loan();

       Optional<Member> lender = memberService.getMemberById(LoanCreationRequest.getLenderId());
       if(lender.isPresent()) {
           loan.setLender(lender.get());
       }
       Optional<Member> borrower = memberService.getMemberById(LoanCreationRequest.getBorrowerId());
       if(borrower.isPresent()) {
           loan.setBorrower(borrower.get());
       }
       Optional<Book> book = bookService.getBookById(LoanCreationRequest.getBookId());
       if(book.isPresent()) {
           loan.setBook(book.get());
       }

        return loanService.createLoan(loan);
    }


    @PostMapping("/loan/{id}/return")
    public Loan returnLoan(@PathVariable int id) {
        return loanService.returnLoan(id);
    }
}
