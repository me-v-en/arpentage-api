package com.example.meven.arpentage_api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoanCreationRequest {

    @NotNull
    private Integer lenderId;

    @NotNull
    private Integer borrowerId;

    @NotNull
    private Integer bookId;

    // Default constructor required by Jackson
    public LoanCreationRequest() {}

    // Parameterized constructor for convenience
    public LoanCreationRequest(int lenderId, int borrowerId, int bookId) {
        this.lenderId = lenderId;
        this.borrowerId = borrowerId;
        this.bookId = bookId;
    }

    public int getLenderId() {
        return lenderId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public int getBookId() {
        return bookId;
    }
}
