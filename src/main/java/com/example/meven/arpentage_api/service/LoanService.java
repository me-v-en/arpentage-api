package com.example.meven.arpentage_api.service;

import com.example.meven.arpentage_api.model.Loan;
import com.example.meven.arpentage_api.model.LoanCreationRequest;
import com.example.meven.arpentage_api.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Iterable<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(long id) {
        return loanRepository.findById(id);
    }


    public void deleteLoanById(long id) {
        loanRepository.deleteById(id);
    }

    public Loan createLoan(Loan loan) {
        loan.setOngoing(true);
        return saveLoan(loan);
    }

    public Loan returnLoan(long id) {
        Optional<Loan> l = getLoanById(id);
        if(l.isPresent()){
            Loan loan = l.get();
            loan.setOngoing(false);
             saveLoan(loan);
             return loan;
        }
        // todo : handle error, return a confirmation of success
        return null;
    }
}
