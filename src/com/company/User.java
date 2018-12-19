package com.company;

import java.util.ArrayList;

/**
 * Created by mostafa on 6/4/2017.
 */
public class User {
    String name;
    ArrayList<Integer> votedQuotes = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    ArrayList<Quote> quotes = new ArrayList<>();
}
