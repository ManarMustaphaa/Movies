package com.example.manar_000.myapplication.app;

/**
 * Created by manar_000 on 9/16/2016.
 */
public class Review {

    private String author ;
    private String Content ;

    public Review(){

        author = "name";
        Content = "content";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String toString(){

        return getAuthor()+"\n \n"+getContent()+"\n \n";
    }
}
