package com.example.meven.arpentage_api.service;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Loan;
import com.example.meven.arpentage_api.model.LoanCreationRequest;
import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.repository.BookRepository;
import com.example.meven.arpentage_api.repository.LoanRepository;
import com.example.meven.arpentage_api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookService bookService;

    public Optional<Loan> getLoanById(int id) {
        return loanRepository.findById(id);
    }

    public void deleteLoanById(int id) {
        loanRepository.deleteById(id);
    }


    public Loan createLoanFromRequest(LoanCreationRequest request) {
        final Member lender = memberService.getMemberById(request.getLenderId())
                .orElseThrow(() -> new IllegalArgumentException("Lender member not found with ID: " + request.getLenderId()));

        final Member borrower = memberService.getMemberById(request.getBorrowerId())
                .orElseThrow(() -> new IllegalArgumentException("Borrower member not found with ID: " + request.getBorrowerId()));

        final Book book = bookService.getBookById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + request.getBookId()));

        // TODO Test :
        // Is the book owned by the lender
        // Is the lender already lending this book

        return createLoan(lender, borrower, book);
    }

    public Loan createLoan(Member lender, Member borrower, Book book) {
        Loan loan = new Loan(lender, borrower, book);
        return saveLoan(loan);
    }
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }



    public Loan returnLoan(int id) {
        Optional<Loan> l = getLoanById(id);
        if (l.isPresent()) {
            Loan loan = l.get();
            loan.setOngoing(false);
            saveLoan(loan);
            return loan;
        }
        // todo : handle error, return a confirmation of success
        return null;
    }


}
