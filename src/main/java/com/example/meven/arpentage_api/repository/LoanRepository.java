package com.example.meven.arpentage_api.repository;

import com.example.meven.arpentage_api.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {
}
