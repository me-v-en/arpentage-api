package com.example.meven.arpentage_api.repository;

import com.example.meven.arpentage_api.model.Book;
import com.example.meven.arpentage_api.model.Loan;
import com.example.meven.arpentage_api.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer> {
    Iterable<Loan> findByBook(Book book);

    Optional<Loan> findByBookAndOngoingTrue(Book book);

    Iterable<Loan> findByBorrower(Member borrower);

    Iterable<Loan> findByBorrowerAndBook(Member borrower, Book book);

    Optional<Loan> findByBorrowerAndBookAndOngoingTrue(Member borrower, Book book);
}
