package com.example.dante.liveitup;

/**
 * Created by Dante on 3/2/16.
 */
public class ListElement
{
    ListElement() {};

    ListElement(String tl, String tag) {
        textLabel = tl;
        this.tag = tag;
    }

    public String textLabel;
    public String tag;
}