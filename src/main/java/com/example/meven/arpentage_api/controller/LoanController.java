package com.example.meven.arpentage_api.controller;

import com.example.meven.arpentage_api.model.Loan;
import com.example.meven.arpentage_api.model.LoanCreationRequest;
import com.example.meven.arpentage_api.service.BookService;
import com.example.meven.arpentage_api.service.LoanService;
import com.example.meven.arpentage_api.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping("/loan/{id}")
    public Optional<Loan> getLoanById(@PathVariable int id) {
        return loanService.getLoanById(id);
    }

    @PostMapping("/loan/new")
    public ResponseEntity<?> createLoan(@Valid @RequestBody LoanCreationRequest loanCreationRequest) {
        try {
            Loan createdLoan = loanService.createLoanFromRequest(loanCreationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }


    @PostMapping("/loan/{id}/return")
    public Loan returnLoan(@PathVariable int id) {
        return loanService.returnLoan(id);
    }
}
