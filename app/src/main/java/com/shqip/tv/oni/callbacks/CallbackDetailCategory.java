package com.shqip.tv.oni.callbacks;

import com.shqip.tv.oni.models.Category;
import com.shqip.tv.oni.models.Channel;

import java.util.ArrayList;
import java.util.List;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class CallbackDetailCategory {

    public String status = "";
    public int count = -1;
    public int count_total = -1;
    public int pages = -1;
    public Category category = null;
    public List<Channel> posts = new ArrayList<Channel>();

}
