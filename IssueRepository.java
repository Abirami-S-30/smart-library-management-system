package com.library.repository;

import java.time.LocalDate;
import java.util.List;

import com.library.dao.IssueDAO;
import com.library.model.Issue;

public class IssueRepository {

    private final IssueDAO issueDAO;

    public IssueRepository() {

        issueDAO = new IssueDAO();
    }

    // ==========================================
    // ISSUE BOOK
    // ==========================================

    public boolean issueBook(Issue issue) {

        return issueDAO.addIssue(issue);
    }

    // ==========================================
    // VIEW ISSUED BOOKS
    // ==========================================

    public List<Issue> viewIssuedBooks() {

        return issueDAO.getAllIssues();
    }

    // ==========================================
    // SEARCH ISSUE BY ISSUE ID
    // ==========================================

    public Issue searchIssueById(int issueId) {

        return issueDAO.getIssueById(issueId);
    }

    // ==========================================
    // SEARCH ALL ACTIVE ISSUES BY BOOK ID
    // ==========================================

    public List<Issue> searchIssuesByBookId(int bookId) {

        return issueDAO.getIssuesByBookId(bookId);
    }

    // ==========================================
    // RETURN BOOK
    // ==========================================

    public boolean returnBook(
            int issueId,
            LocalDate returnDate,
            double fine) {

        return issueDAO.returnIssue(
                issueId,
                returnDate,
                fine
        );
    }

    // ==========================================
    // UPDATE FINE
    // ==========================================

    public boolean updateFine(
            int issueId,
            double fine) {

        return issueDAO.updateFine(
                issueId,
                fine
        );
    }
}