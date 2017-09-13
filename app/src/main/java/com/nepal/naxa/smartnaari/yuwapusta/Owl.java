package com.nepal.naxa.smartnaari.yuwapusta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 9/12/17
 * by nishon.tan@gmail.com
 */

public class Owl {


    private HashMap<String, String> nameAndPhotos;

    public Owl(HashMap<String, String> nameAndPhotos) {
        this.nameAndPhotos = nameAndPhotos;
    }

    public HashMap<String, String> getNameAndPhotos() {
        return nameAndPhotos;
    }

    public void setNameAndPhotos(HashMap<String, String> nameAndPhotos) {
        this.nameAndPhotos = nameAndPhotos;
    }


    public static List<Owl> getOwlsList() {
        List<Owl> owlList = new ArrayList<>();
        HashMap<String, String> nameAndPhotos = new HashMap<>();
        Owl owl;

        nameAndPhotos.put("Uttam", "http://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");
        nameAndPhotos.put("Uttam", "http://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");
        nameAndPhotos.put("Uttam", "http://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");

        owl = new Owl(nameAndPhotos);
        owlList.add(owl);

        nameAndPhotos.put("Uttam", "http://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");
        nameAndPhotos.put("Uttam", "http://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");
        nameAndPhotos.put("Uttam", "http://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");

        owl = new Owl(nameAndPhotos);
        owlList.add(owl);

        return owlList;

    }
}
