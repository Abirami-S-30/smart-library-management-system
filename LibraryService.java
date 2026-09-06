package com.library.service;

import java.time.LocalDate;
import java.util.List;

import com.library.model.Book;
import com.library.model.Issue;
import com.library.repository.BookRepository;
import com.library.repository.IssueRepository;
import com.library.util.FineCalculator;

public class LibraryService {

    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;

    public LibraryService(
            BookRepository bookRepository,
            IssueRepository issueRepository) {

        this.bookRepository = bookRepository;
        this.issueRepository = issueRepository;
    }

    // ==========================================
    // CHECK BOOK ID
    // ==========================================

    public boolean bookIdExists(int bookId) {

        return bookRepository.searchBookById(bookId) != null;
    }

    // ==========================================
    // CHECK ISSUE ID
    // ==========================================

    public boolean issueIdExists(int issueId) {

        return issueRepository.searchIssueById(issueId) != null;
    }

    // ==========================================
    // ADD BOOK
    // ==========================================

    public boolean addBook(Book book) {

        if (book == null) {
            return false;
        }

        // Duplicate Book ID
        if (bookRepository.searchBookById(book.getBookId()) != null) {
            return false;
        }

        if (book.getPrice() <= 0) {
            return false;
        }

        if (book.getQuantity() < 0) {
            return false;
        }

        // Quantity controls availability
        book.setAvailable(book.getQuantity() > 0);

        return bookRepository.addBook(book);
    }

    // ==========================================
    // VIEW BOOKS
    // ==========================================

    public List<Book> viewBooks() {

        return bookRepository.viewBooks();
    }

    // ==========================================
    // SEARCH BOOK BY ID
    // ==========================================

    public Book searchBook(int id) {

        return bookRepository.searchBookById(id);
    }

    // ==========================================
    // SEARCH BY TITLE
    // ==========================================

    public List<Book> searchBookByTitle(String title) {

        return bookRepository.searchBookByTitle(title);
    }

    // ==========================================
    // SEARCH BY AUTHOR
    // ==========================================

    public List<Book> searchBookByAuthor(String author) {

        return bookRepository.searchBookByAuthor(author);
    }

    // ==========================================
    // UPDATE BOOK
    // ==========================================

    public boolean updateBook(
            int bookId,
            String title,
            String author,
            String category,
            double price,
            int quantity) {

        Book book =
                bookRepository.searchBookById(bookId);

        // Book does not exist
        if (book == null) {
            return false;
        }

        // Invalid data
        if (price <= 0 || quantity < 0) {
            return false;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setPrice(price);
        book.setQuantity(quantity);

        // Quantity controls availability
        book.setAvailable(quantity > 0);

        return bookRepository.updateBook(book);
    }

    // ==========================================
    // DELETE BOOK
    // ==========================================

    public boolean deleteBook(int id) {

        Book book =
                bookRepository.searchBookById(id);

        if (book == null) {
            return false;
        }

        /*
         * Do not delete a book if any copy is
         * currently issued.
         */
        List<Issue> activeIssues =
                issueRepository.searchIssuesByBookId(id);

        if (!activeIssues.isEmpty()) {
            return false;
        }

        return bookRepository.deleteBook(id);
    }

    // ==========================================
    // ISSUE BOOK
    // ==========================================

    public boolean issueBook(
            int issueId,
            int bookId,
            String studentName) {

        // Find the book
        Book book =
                bookRepository.searchBookById(bookId);

        if (book == null) {
            return false;
        }

        // IMPORTANT:
        // Quantity decides whether a copy is available.
        if (book.getQuantity() <= 0) {
            return false;
        }

        // Issue ID must be unique
        if (issueRepository.searchIssueById(issueId) != null) {
            return false;
        }

        if (studentName == null ||
                studentName.trim().isEmpty()) {
            return false;
        }

        // Create issue
        Issue issue = new Issue(
                issueId,
                bookId,
                studentName,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        // --------------------------------------
        // DECREASE QUANTITY BY 1
        // --------------------------------------

        int oldQuantity =
                book.getQuantity();

        book.setQuantity(
                oldQuantity - 1
        );

        /*
         * If quantity becomes 0,
         * book becomes unavailable.
         *
         * If quantity is still greater than 0,
         * another student can issue the same book.
         */
        book.setAvailable(
                book.getQuantity() > 0
        );

        // Update book
        boolean bookUpdated =
                bookRepository.updateBook(book);

        if (!bookUpdated) {
            return false;
        }

        // Add issue record
        boolean issueAdded =
                issueRepository.issueBook(issue);

        if (!issueAdded) {

            // Restore quantity
            book.setQuantity(oldQuantity);

            book.setAvailable(
                    oldQuantity > 0
            );

            bookRepository.updateBook(book);

            return false;
        }

        return true;
    }

    // ==========================================
    // RETURN BOOK
    // ==========================================

    public double returnBook(int issueId) {

        // Find the active issue
        Issue issue =
                issueRepository.searchIssueById(issueId);

        if (issue == null) {
            return -1;
        }

        // Find book
        Book book =
                bookRepository.searchBookById(
                        issue.getBookId()
                );

        if (book == null) {
            return -1;
        }

        // Current date
        LocalDate returnDate =
                LocalDate.now();

        // Calculate fine
        double fine =
                FineCalculator.calculateFine(
                        issue.getDueDate(),
                        returnDate
                );

        // --------------------------------------
        // INCREASE QUANTITY BY 1
        // --------------------------------------

        int oldQuantity =
                book.getQuantity();

        book.setQuantity(
                oldQuantity + 1
        );

        // At least one copy is now available
        book.setAvailable(true);

        boolean bookUpdated =
                bookRepository.updateBook(book);

        if (!bookUpdated) {
            return -1;
        }

        // Save return date and fine
        boolean returned =
                issueRepository.returnBook(
                        issueId,
                        returnDate,
                        fine
                );

        if (!returned) {

            // Restore quantity
            book.setQuantity(oldQuantity);

            book.setAvailable(
                    oldQuantity > 0
            );

            bookRepository.updateBook(book);

            return -1;
        }

        return fine;
    }

    // ==========================================
    // VIEW ISSUED BOOKS
    // ==========================================

    public List<Issue> viewIssuedBooks() {

        return issueRepository.viewIssuedBooks();
    }

    // ==========================================
    // SORT BY BOOK ID
    // ==========================================

    public List<Book> sortByBookId() {

        return bookRepository.sortByBookId();
    }

    // ==========================================
    // SORT BY TITLE
    // ==========================================

    public List<Book> sortByTitle() {

        return bookRepository.sortByTitle();
    }

    // ==========================================
    // SORT BY PRICE
    // ==========================================

    public List<Book> sortByPrice() {

        return bookRepository.sortByPrice();
    }

    // ==========================================
    // SORT BY QUANTITY
    // ==========================================

    public List<Book> sortByQuantity() {

        return bookRepository.sortByQuantity();
    }

    // ==========================================
    // DASHBOARD
    // ==========================================

    public int getTotalBooks() {

        return bookRepository.viewBooks().size();
    }

    public int getAvailableBooks() {

        int count = 0;

        for (Book book :
                bookRepository.viewBooks()) {

            if (book.getQuantity() > 0) {
                count++;
            }
        }

        return count;
    }

    public int getIssuedBooks() {

        return issueRepository
                .viewIssuedBooks()
                .size();
    }

    public int getOutOfStockBooks() {

        int count = 0;

        for (Book book :
                bookRepository.viewBooks()) {

            if (book.getQuantity() == 0) {
                count++;
            }
        }

        return count;
    }

    public int getTotalQuantity() {

        int total = 0;

        for (Book book :
                bookRepository.viewBooks()) {

            total += book.getQuantity();
        }

        return total;
    }
}