package com.library.model;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bookId;
    private String title;
    private String author;
    private String category;
    private double price;
    private int quantity;
    private boolean available;

    // Default constructor
    public Book() {
    }

    // Parameterized constructor
    public Book(int bookId, String title, String author,
                String category, double price,
                int quantity, boolean available) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.available = available;
    }

    // Getters and Setters

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // toString()
    @Override
    public String toString() {

        return "Book ID : " + bookId +
                "\nTitle : " + title +
                "\nAuthor : " + author +
                "\nCategory : " + category +
                "\nPrice : " + price +
                "\nQuantity : " + quantity +
                "\nAvailable : " + available +
                "\n--------------------------------";
    }
}