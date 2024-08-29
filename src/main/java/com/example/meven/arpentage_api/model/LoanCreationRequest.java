package com.example.meven.arpentage_api.model;

public class LoanCreationRequest {
    int lenderId;
    int borrowerId;
    int bookId;

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
