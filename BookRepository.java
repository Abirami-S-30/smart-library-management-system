package com.library.repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.library.dao.BookDAO;
import com.library.model.Book;

public class BookRepository {

    private final BookDAO bookDAO;

    public BookRepository() {

        bookDAO = new BookDAO();
    }

    // ==========================================
    // ADD BOOK
    // ==========================================

    public boolean addBook(Book book) {

        return bookDAO.addBook(book);
    }

    // ==========================================
    // VIEW ALL BOOKS
    // ==========================================

    public List<Book> viewBooks() {

        return bookDAO.getAllBooks();
    }

    // ==========================================
    // SEARCH BOOK BY ID
    // ==========================================

    public Book searchBookById(int id) {

        return bookDAO.getBookById(id);
    }

    // ==========================================
    // DELETE BOOK
    // ==========================================

    public boolean deleteBook(int id) {

        return bookDAO.deleteBook(id);
    }

    // ==========================================
    // UPDATE BOOK
    // ==========================================

    public boolean updateBook(Book book) {

        return bookDAO.updateBook(book);
    }

    // ==========================================
    // SEARCH BY TITLE
    // ==========================================

    public List<Book> searchBookByTitle(String title) {

        return bookDAO.searchBookByTitle(title);
    }

    // ==========================================
    // SEARCH BY AUTHOR
    // ==========================================

    public List<Book> searchBookByAuthor(String author) {

        return bookDAO.searchBookByAuthor(author);
    }

    // ==========================================
    // GET OUT OF STOCK BOOKS
    // ==========================================

    public List<Book> getOutOfStockBooks() {

        return bookDAO.getOutOfStockBooks();
    }

    // ==========================================
    // SORT BY BOOK ID
    // ==========================================

    public List<Book> sortByBookId() {

        List<Book> books = bookDAO.getAllBooks();

        Collections.sort(
                books,
                Comparator.comparingInt(Book::getBookId)
        );

        return books;
    }

    // ==========================================
    // SORT BY TITLE
    // ==========================================

    public List<Book> sortByTitle() {

        List<Book> books = bookDAO.getAllBooks();

        Collections.sort(
                books,
                Comparator.comparing(
                        Book::getTitle,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return books;
    }

    // ==========================================
    // SORT BY PRICE
    // ==========================================

    public List<Book> sortByPrice() {

        List<Book> books = bookDAO.getAllBooks();

        Collections.sort(
                books,
                Comparator.comparingDouble(Book::getPrice)
        );

        return books;
    }

    // ==========================================
    // SORT BY QUANTITY
    // ==========================================

    public List<Book> sortByQuantity() {

        List<Book> books = bookDAO.getAllBooks();

        Collections.sort(
                books,
                Comparator.comparingInt(Book::getQuantity)
        );

        return books;
    }
}