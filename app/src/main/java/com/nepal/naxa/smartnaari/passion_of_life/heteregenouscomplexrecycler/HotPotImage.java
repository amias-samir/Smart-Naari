package com.nepal.naxa.smartnaari.passion_of_life.heteregenouscomplexrecycler;

/**
 * Created by samir on 12/27/2017.
 */

public class HotPotImage {

    String imagePath ;
    String author ;

    public HotPotImage(String imagePath, String author){
        this.imagePath = imagePath ;
        this.author = author ;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



}
