package com.example.meven.arpentage_api.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Member lender;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Member borrower;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false, updatable = false, name = "lend_date")
    @CreationTimestamp
    private Date lendDate;

    @Column
    private boolean ongoing = true;

    public Loan(Member lender, Member borrower, Book book) {
        this.setLender(lender);
        this.setBorrower(borrower);
        this.setBook(book);
    }
    public int getId() {
        return id;
    }

    public Member getLender() {
        return lender;
    }

    public void setLender(Member lender) {
        this.lender = lender;
    }

    public Member getBorrower() {
        return borrower;
    }

    public void setBorrower(Member borrower) {
        this.borrower = borrower;
    }

    public Book getBookById() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }
}
