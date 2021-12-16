package com.zjgsu.bookapp;

public class Book {
    private int id = -1;
    private String title;
    private String author;
    private int pages = 0;
    private float price = 0.0f;

    public Book() {
    }

    public Book(String title, String author, int pages, float price) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
