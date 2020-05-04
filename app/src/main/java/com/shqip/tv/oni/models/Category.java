package com.shqip.tv.oni.models;

import java.io.Serializable;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class Category implements Serializable {

    public int cid = -1;
    public String category_name;
    public String category_image;

}
