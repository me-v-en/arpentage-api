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

    public @NotNull Integer getLenderId() {
        return lenderId;
    }

    public void setLenderId(@NotNull Integer lenderId) {
        this.lenderId = lenderId;
    }

    public @NotNull Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(@NotNull Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    public @NotNull Integer getBookId() {
        return bookId;
    }

    public void setBookId(@NotNull Integer bookId) {
        this.bookId = bookId;
    }
}
