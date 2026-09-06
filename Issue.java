package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    private int issueId;
    private int bookId;
    private String studentName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;


    // ==========================================
    // DEFAULT CONSTRUCTOR
    // ==========================================

    public Issue() {

    }


    // ==========================================
    // PARAMETERIZED CONSTRUCTOR
    // ==========================================

    public Issue(
            int issueId,
            int bookId,
            String studentName,
            LocalDate issueDate,
            LocalDate dueDate) {

        this.issueId = issueId;
        this.bookId = bookId;
        this.studentName = studentName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.fine = 0.0;
    }


    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================

    public int getIssueId() {

        return issueId;
    }

    public void setIssueId(int issueId) {

        this.issueId = issueId;
    }


    public int getBookId() {

        return bookId;
    }

    public void setBookId(int bookId) {

        this.bookId = bookId;
    }


    public String getStudentName() {

        return studentName;
    }

    public void setStudentName(String studentName) {

        this.studentName = studentName;
    }


    public LocalDate getIssueDate() {

        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {

        this.issueDate = issueDate;
    }


    public LocalDate getDueDate() {

        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {

        this.dueDate = dueDate;
    }


    public LocalDate getReturnDate() {

        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {

        this.returnDate = returnDate;
    }


    public double getFine() {

        return fine;
    }

    public void setFine(double fine) {

        this.fine = fine;
    }


    // ==========================================
    // TOSTRING
    // ==========================================

    @Override
    public String toString() {

        return "=====================================\n"
                + "Issue ID      : " + issueId
                + "\nBook ID       : " + bookId
                + "\nStudent Name  : " + studentName
                + "\nIssue Date    : " + issueDate
                + "\nDue Date      : " + dueDate
                + "\nReturn Date   : "
                + (returnDate == null ? "Not Returned" : returnDate)
                + "\nFine          : ₹" + fine
                + "\n=====================================";
    }
}