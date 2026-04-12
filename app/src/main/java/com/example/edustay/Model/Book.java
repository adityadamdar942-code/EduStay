package com.example.edustay.Model;

public class Book {

    private String title, author, image;

    public Book(String title, String author, String image) {
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getImage() { return image; }
}
