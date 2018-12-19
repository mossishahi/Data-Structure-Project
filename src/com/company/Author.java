package com.company;

import java.util.ArrayList;

/**
 * Created by mostafa on 6/4/2017.
 */
public class Author {
    ArrayList<Quote> quotes=new ArrayList<>();
    String name;
    int ID;
    int numberOfQuotes=0;

    public Author(String name) {
        this.name = name;
    }

}
