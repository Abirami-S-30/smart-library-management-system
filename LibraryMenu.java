package com.library.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.library.model.Book;
import com.library.model.Issue;
import com.library.service.LibraryService;

public class LibraryMenu {

    private final Scanner sc = new Scanner(System.in);

    private final LibraryService service;

    public LibraryMenu(LibraryService service) {
        this.service = service;
    }

    // ==========================================
    // START MENU
    // ==========================================

    public void start() {

        int choice;

        do {

            System.out.println("\n=================================================");
            System.out.println("        SMART LIBRARY MANAGEMENT SYSTEM");
            System.out.println("=================================================");
            System.out.println("1.  Add Book");
            System.out.println("2.  View Books");
            System.out.println("3.  Search Book by ID");
            System.out.println("4.  Search Book by Title");
            System.out.println("5.  Search Book by Author");
            System.out.println("6.  Update Book");
            System.out.println("7.  Delete Book");
            System.out.println("8.  Issue Book");
            System.out.println("9.  Return Book");
            System.out.println("10. View Issued Books");
            System.out.println("11. Sort Books");
            System.out.println("12. Dashboard");
            System.out.println("13. Exit");
            System.out.println("=================================================");
            System.out.print("Enter Your Choice : ");

            choice = sc.nextInt();

            switch (choice) {

            case 1:
                addBook();
                break;

            case 2:
                viewBooks();
                break;

            case 3:
                searchBook();
                break;

            case 4:
                searchBookByTitle();
                break;

            case 5:
                searchBookByAuthor();
                break;

            case 6:
                updateBook();
                break;

            case 7:
                deleteBook();
                break;

            case 8:
                issueBook();
                break;

            case 9:
                returnBook();
                break;

            case 10:
                viewIssuedBooks();
                break;

            case 11:
                sortBooks();
                break;

            case 12:
                dashboard();
                break;

            case 13:
                System.out.println(
                        "Thank You for using Smart Library Management System."
                );
                break;

            default:
                System.out.println("Invalid Choice.");
            }

        } while (choice != 13);
    }

    // ==========================================
    // ADD BOOK
    // ==========================================

    private void addBook() {

        System.out.print("\nBook ID : ");
        int id = sc.nextInt();

        // Check duplicate immediately
        if (service.bookIdExists(id)) {

            System.out.println(
                    "Book already exist."
            );

            return;
        }

        sc.nextLine();

        System.out.print("Title : ");
        String title = sc.nextLine();

        System.out.print("Author : ");
        String author = sc.nextLine();

        System.out.print("Category : ");
        String category = sc.nextLine();

        System.out.print("Price : ");
        double price = sc.nextDouble();

        System.out.print("Quantity : ");
        int quantity = sc.nextInt();

        if (price <= 0) {

            System.out.println(
                    "Price must be greater than 0."
            );

            return;
        }

        if (quantity < 0) {

            System.out.println(
                    "Quantity cannot be negative."
            );

            return;
        }

        Book book = new Book(
                id,
                title,
                author,
                category,
                price,
                quantity,
                quantity > 0
        );

        if (service.addBook(book)) {

            System.out.println(
                    "Book Added Successfully."
            );

        } else {

            System.out.println(
                    "Book Cannot Be Added."
            );
        }
    }

    // ==========================================
    // VIEW BOOKS
    // ==========================================

    private void viewBooks() {

        List<Book> books =
                service.viewBooks();

        if (books.isEmpty()) {

            System.out.println(
                    "No Books Available."
            );

            return;
        }

        for (Book book : books) {

            System.out.println(book);
        }
    }

    // ==========================================
    // SEARCH BOOK
    // ==========================================

    private void searchBook() {

        System.out.print(
                "\nEnter Book ID : "
        );

        int id = sc.nextInt();

        Book book =
                service.searchBook(id);

        if (book != null) {

            System.out.println(book);

        } else {

            System.out.println(
                    "Entered Book ID " + id
                    + " is not available."
            );
        }
    }

    // ==========================================
    // DELETE BOOK
    // ==========================================

    private void deleteBook() {

        System.out.print(
                "\nEnter Book ID : "
        );

        int id = sc.nextInt();

        // First check whether book exists
        if (!service.bookIdExists(id)) {

            System.out.println(
                    "Entered Book ID " + id
                    + " is not available."
            );

            System.out.println(
                    "Sorry, you can't delete this book."
            );

            return;
        }

        /*
         * Check whether there are active issues.
         *
         * If deleteBook() returns false because an
         * issued copy exists, show the proper message.
         */
        if (service.deleteBook(id)) {

            System.out.println(
                    "Book Deleted Successfully."
            );

        } else {

            System.out.println(
                    "Book ID " + id
                    + " currently has issued copies."
            );

            System.out.println(
                    "Sorry, you can't delete this book."
            );

            System.out.println(
                    "Please return all issued copies first."
            );
        }
    }

    // ==========================================
    // ISSUE BOOK
    // ==========================================

    private void issueBook() {

        System.out.print(
                "\nIssue ID : "
        );

        int issueId = sc.nextInt();

        // Check Issue ID first
        if (service.issueIdExists(issueId)) {

            System.out.println(
                    "Issue ID " + issueId
                    + " already exists."
            );

            System.out.println(
                    "Please enter a different Issue ID."
            );

            return;
        }

        System.out.print(
                "Book ID : "
        );

        int bookId = sc.nextInt();

        // Check Book ID
        Book book =
                service.searchBook(bookId);

        if (book == null) {

            System.out.println(
                    "Entered Book ID " + bookId
                    + " is not available."
            );

            System.out.println(
                    "Sorry, you can't issue this book."
            );

            return;
        }

        // ======================================
        // QUANTITY CHECK
        // ======================================

        if (book.getQuantity() <= 0) {

            System.out.println(
                    "Book ID " + bookId
                    + " is out of stock."
            );

            System.out.println(
                    "No copies are currently available."
            );

            return;
        }

        /*
         * IMPORTANT:
         *
         * We DO NOT check whether this Book ID
         * is already issued.
         *
         * Quantity decides how many times the book
         * can be issued.
         */

        sc.nextLine();

        System.out.print(
                "Student Name : "
        );

        String student =
                sc.nextLine().trim();

        if (student.isEmpty()) {

            System.out.println(
                    "Student name cannot be empty."
            );

            return;
        }

        /*
         * issueBook() returns BOOLEAN.
         */
        boolean issued =
                service.issueBook(
                        issueId,
                        bookId,
                        student
                );

        if (issued) {

            Book updatedBook =
                    service.searchBook(bookId);

            System.out.println(
                    "\nBook Issued Successfully."
            );

            System.out.println(
                    "Issue ID          : "
                    + issueId
            );

            System.out.println(
                    "Book ID           : "
                    + bookId
            );

            System.out.println(
                    "Student Name      : "
                    + student
            );

            System.out.println(
                    "Issue Date        : "
                    + LocalDate.now()
            );

            System.out.println(
                    "Due Date          : "
                    + LocalDate.now().plusDays(7)
            );

            if (updatedBook != null) {

                System.out.println(
                        "Remaining Quantity: "
                        + updatedBook.getQuantity()
                );

                if (updatedBook.getQuantity() == 0) {

                    System.out.println(
                            "Status            : OUT OF STOCK"
                    );

                } else {

                    System.out.println(
                            "Status            : AVAILABLE"
                    );
                }
            }

        } else {

            System.out.println(
                    "Book Cannot Be Issued."
            );
        }
    }

    // ==========================================
    // RETURN BOOK
    // ==========================================

    private void returnBook() {

        /*
         * Return using Issue ID.
         *
         * This is important because the same Book ID
         * can have multiple active issues.
         */
        System.out.print(
                "\nEnter Issue ID : "
        );

        int issueId = sc.nextInt();

        // Find issue first
        Issue issueToReturn = null;

        for (Issue issue :
                service.viewIssuedBooks()) {

            if (issue.getIssueId() == issueId) {

                issueToReturn = issue;
                break;
            }
        }

        if (issueToReturn == null) {

            System.out.println(
                    "Issue ID " + issueId
                    + " is not available."
            );

            System.out.println(
                    "Sorry, this book cannot be returned."
            );

            return;
        }

        double fine =
                service.returnBook(issueId);

        if (fine >= 0) {

            LocalDate returnDate =
                    LocalDate.now();

            System.out.println(
                    "\nBook Returned Successfully."
            );

            System.out.println(
                    "Issue ID    : "
                    + issueToReturn.getIssueId()
            );

            System.out.println(
                    "Book ID     : "
                    + issueToReturn.getBookId()
            );

            System.out.println(
                    "Student Name: "
                    + issueToReturn.getStudentName()
            );

            System.out.println(
                    "Issue Date  : "
                    + issueToReturn.getIssueDate()
            );

            System.out.println(
                    "Due Date    : "
                    + issueToReturn.getDueDate()
            );

            System.out.println(
                    "Return Date : "
                    + returnDate
            );

            System.out.println(
                    "Fine        : ₹"
                    + fine
            );

            Book returnedBook =
                    service.searchBook(
                            issueToReturn.getBookId()
                    );

            if (returnedBook != null) {

                System.out.println(
                        "Available Quantity : "
                        + returnedBook.getQuantity()
                );
            }

        } else {

            System.out.println(
                    "Book Return Failed."
            );
        }
    }

    // ==========================================
    // VIEW ISSUED BOOKS
    // ==========================================

    private void viewIssuedBooks() {

        List<Issue> issues =
                service.viewIssuedBooks();

        if (issues.isEmpty()) {

            System.out.println(
                    "No Issued Books."
            );

            return;
        }

        for (Issue issue : issues) {

            System.out.println(issue);
        }
    }

    // ==========================================
    // UPDATE BOOK
    // ==========================================

    private void updateBook() {

        System.out.print(
                "\nEnter Book ID : "
        );

        int id = sc.nextInt();

        /*
         * Check ID BEFORE asking for new details.
         */
        if (!service.bookIdExists(id)) {

            System.out.println(
                    "Entered Book ID " + id
                    + " is not available."
            );

            System.out.println(
                    "Sorry, you can't able to update."
            );

            return;
        }

        sc.nextLine();

        System.out.print(
                "New Title : "
        );

        String title =
                sc.nextLine();

        System.out.print(
                "New Author : "
        );

        String author =
                sc.nextLine();

        System.out.print(
                "New Category : "
        );

        String category =
                sc.nextLine();

        System.out.print(
                "New Price : "
        );

        double price =
                sc.nextDouble();

        System.out.print(
                "New Quantity : "
        );

        int quantity =
                sc.nextInt();

        if (price <= 0) {

            System.out.println(
                    "Price must be greater than 0."
            );

            return;
        }

        if (quantity < 0) {

            System.out.println(
                    "Quantity cannot be negative."
            );

            return;
        }

        /*
         * updateBook() returns BOOLEAN.
         */
        boolean updated =
                service.updateBook(
                        id,
                        title,
                        author,
                        category,
                        price,
                        quantity
                );

        if (updated) {

            System.out.println(
                    "Book Updated Successfully."
            );

            System.out.println(
                    "Updated Quantity : "
                    + quantity
            );

            if (quantity == 0) {

                System.out.println(
                        "Status           : OUT OF STOCK"
                );

            } else {

                System.out.println(
                        "Status           : AVAILABLE"
                );
            }

        } else {

            System.out.println(
                    "Book Cannot Be Updated."
            );
        }
    }

    // ==========================================
    // SEARCH BY TITLE
    // ==========================================

    private void searchBookByTitle() {

        sc.nextLine();

        System.out.print(
                "\nEnter Book Title : "
        );

        String title =
                sc.nextLine();

        List<Book> books =
                service.searchBookByTitle(title);

        if (books.isEmpty()) {

            System.out.println(
                    "No Books Found."
            );

            return;
        }

        for (Book book : books) {

            System.out.println(book);
        }
    }

    // ==========================================
    // SEARCH BY AUTHOR
    // ==========================================

    private void searchBookByAuthor() {

        sc.nextLine();

        System.out.print(
                "\nEnter Author Name : "
        );

        String author =
                sc.nextLine();

        List<Book> books =
                service.searchBookByAuthor(author);

        if (books.isEmpty()) {

            System.out.println(
                    "No Books Found."
            );

            return;
        }

        for (Book book : books) {

            System.out.println(book);
        }
    }

    // ==========================================
    // SORT BOOKS
    // ==========================================

    private void sortBooks() {

        System.out.println(
                "\n========= SORT BOOKS ========="
        );

        System.out.println(
                "1. Sort by Book ID"
        );

        System.out.println(
                "2. Sort by Title"
        );

        System.out.println(
                "3. Sort by Price"
        );

        System.out.println(
                "4. Sort by Quantity"
        );

        System.out.print(
                "Enter Choice : "
        );

        int choice =
                sc.nextInt();

        List<Book> books;

        switch (choice) {

        case 1:
            books = service.sortByBookId();
            break;

        case 2:
            books = service.sortByTitle();
            break;

        case 3:
            books = service.sortByPrice();
            break;

        case 4:
            books = service.sortByQuantity();
            break;

        default:

            System.out.println(
                    "Invalid Choice."
            );

            return;
        }

        if (books.isEmpty()) {

            System.out.println(
                    "No Books Available."
            );

            return;
        }

        System.out.println(
                "\n============== SORTED BOOKS =============="
        );

        for (Book book : books) {

            System.out.println(book);
        }
    }

    // ==========================================
    // DASHBOARD
    // ==========================================

    private void dashboard() {

        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "             LIBRARY DASHBOARD"
        );

        System.out.println(
                "=========================================="
        );

        System.out.printf(
                "%-24s : %d%n",
                "Total Books",
                service.getTotalBooks()
        );

        System.out.printf(
                "%-24s : %d%n",
                "Available Books",
                service.getAvailableBooks()
        );

        System.out.printf(
                "%-24s : %d%n",
                "Issued Copies",
                service.getIssuedBooks()
        );

        System.out.printf(
                "%-24s : %d%n",
                "Out of Stock Books",
                service.getOutOfStockBooks()
        );

        System.out.printf(
                "%-24s : %d%n",
                "Total Available Quantity",
                service.getTotalQuantity()
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "\n               BOOK AVAILABILITY"
        );

        System.out.println(
                "========================================================================"
        );

        List<Book> books =
                service.viewBooks();

        if (books.isEmpty()) {

            System.out.println(
                    "No Books Available."
            );

            return;
        }

        System.out.printf(
                "%-10s %-32s %-12s %-15s%n",
                "Book ID",
                "Book Name",
                "Quantity",
                "Status"
        );

        System.out.println(
                "-----------------------------------------------------------------------"
        );

        for (Book book : books) {

            String status;

            if (book.getQuantity() == 0) {

                status = "OUT OF STOCK";

            } else {

                status = "AVAILABLE";
            }

            System.out.printf(
                    "%-10d %-32s %-12d %-15s%n",
                    book.getBookId(),
                    book.getTitle(),
                    book.getQuantity(),
                    status
            );
        }

        System.out.println(
                "========================================================================"
        );
    }
}