package com.nepal.naxa.smartnaari.passion_of_life.complexhotpotrecyclerview;

/**
 * Created by samir on 12/27/2017.
 */

public class HotPotBlogRecipe {

    String head;
    String body;
    String photo;
    String author;

    public HotPotBlogRecipe(String head, String body , String photo , String author) {

        this.head = head;
        this.body = body;
        this.photo = photo;
        this.author = author;
    }




    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
