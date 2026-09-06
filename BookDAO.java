package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.util.DBConnection;

public class BookDAO {

    // ==========================================
    // ADD BOOK
    // ==========================================

    public boolean addBook(Book book) {

        String sql = "INSERT INTO books "
                + "(book_id, title, author, category, price, quantity, available) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, book.getBookId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getCategory());
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getQuantity());
            statement.setBoolean(7, book.isAvailable());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Error while adding book.");
            e.printStackTrace();

            return false;
        }
    }

    // ==========================================
    // GET ALL BOOKS
    // ==========================================

    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT book_id, title, author, category, "
                + "price, quantity, available "
                + "FROM books "
                + "ORDER BY book_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book();

                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setCategory(resultSet.getString("category"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQuantity(resultSet.getInt("quantity"));

                // Availability is determined from quantity
                book.setAvailable(book.getQuantity() > 0);

                books.add(book);
            }

        } catch (SQLException e) {

            System.out.println("Error while loading books.");
            e.printStackTrace();
        }

        return books;
    }

    // ==========================================
    // GET BOOK BY ID
    // ==========================================

    public Book getBookById(int id) {

        String sql = "SELECT book_id, title, author, category, "
                + "price, quantity, available "
                + "FROM books WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Book book = new Book();

                    book.setBookId(resultSet.getInt("book_id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setCategory(resultSet.getString("category"));
                    book.setPrice(resultSet.getDouble("price"));
                    book.setQuantity(resultSet.getInt("quantity"));

                    // Availability follows quantity
                    book.setAvailable(book.getQuantity() > 0);

                    return book;
                }
            }

        } catch (SQLException e) {

            System.out.println("Error while searching book.");
            e.printStackTrace();
        }

        return null;
    }

    // ==========================================
    // UPDATE BOOK
    // ==========================================

    public boolean updateBook(Book book) {

        String sql = "UPDATE books SET "
                + "title = ?, "
                + "author = ?, "
                + "category = ?, "
                + "price = ?, "
                + "quantity = ?, "
                + "available = ? "
                + "WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setDouble(4, book.getPrice());
            statement.setInt(5, book.getQuantity());

            // Always keep availability synchronized with quantity
            statement.setBoolean(6, book.getQuantity() > 0);

            statement.setInt(7, book.getBookId());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Error while updating book.");
            e.printStackTrace();

            return false;
        }
    }

    // ==========================================
    // DELETE BOOK
    // ==========================================

    public boolean deleteBook(int id) {

        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Error while deleting book.");
            e.printStackTrace();

            return false;
        }
    }

    // ==========================================
    // SEARCH BY TITLE
    // ==========================================

    public List<Book> searchBookByTitle(String title) {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT book_id, title, author, category, "
                + "price, quantity, available "
                + "FROM books "
                + "WHERE LOWER(title) LIKE LOWER(?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + title + "%");

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Book book = new Book();

                    book.setBookId(resultSet.getInt("book_id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setCategory(resultSet.getString("category"));
                    book.setPrice(resultSet.getDouble("price"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setAvailable(book.getQuantity() > 0);

                    books.add(book);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error while searching books by title.");
            e.printStackTrace();
        }

        return books;
    }

    // ==========================================
    // SEARCH BY AUTHOR
    // ==========================================

    public List<Book> searchBookByAuthor(String author) {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT book_id, title, author, category, "
                + "price, quantity, available "
                + "FROM books "
                + "WHERE LOWER(author) LIKE LOWER(?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + author + "%");

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Book book = new Book();

                    book.setBookId(resultSet.getInt("book_id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setCategory(resultSet.getString("category"));
                    book.setPrice(resultSet.getDouble("price"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setAvailable(book.getQuantity() > 0);

                    books.add(book);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error while searching books by author.");
            e.printStackTrace();
        }

        return books;
    }

    // ==========================================
    // GET OUT OF STOCK BOOKS
    // ==========================================

    public List<Book> getOutOfStockBooks() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT book_id, title, author, category, "
                + "price, quantity, available "
                + "FROM books "
                + "WHERE quantity = 0 "
                + "ORDER BY book_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book();

                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setCategory(resultSet.getString("category"));
                book.setPrice(resultSet.getDouble("price"));
                book.setQuantity(resultSet.getInt("quantity"));

                book.setAvailable(false);

                books.add(book);
            }

        } catch (SQLException e) {

            System.out.println("Error while loading out-of-stock books.");
            e.printStackTrace();
        }

        return books;
    }
}