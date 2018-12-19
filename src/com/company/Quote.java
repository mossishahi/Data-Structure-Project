package com.company;

import utility.Data;

import java.util.Comparator;

/**
 * Created by mostafa on 6/4/2017.
 */

public class Quote implements Comparator<Quote>, Comparable<Quote>  {
    int ID;
    User owner;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    String status = "normal";

    public Author getAuthor() {
        return author;
    }

    Author author;

    public Quote(String text) {
        this.text = text;
    }

    int vote;
    String text;
/*
    // Overriding the compareTo method
    public int compareTo(Quote d) {
        return compare(this,d);
    }

    // Overriding the compare method to sort the age
    public int compare(Quote o1, Quote o2) {
        int result = 1;
        if (o1.vote < o2.vote)
            result = -1;
        if (o1.vote < o2.vote)
            if (o1.getID() < o2.getID())
                result = -1;
        return result;
    }*/

    public int compareTo(Quote d) {
        return (int) this.vote - (int) d.vote;
    }

    // Overriding the compare method to sort the age
    public int compare(Quote d, Quote d1) {
        return (int) d.vote - (int) d1.vote;
    }


}

