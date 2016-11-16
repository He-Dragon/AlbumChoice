package com.demo.albumchoice;

import java.util.ArrayList;

/**
 * Created by hecl on 2016/11/14.
 */

public class ImageBean {

    private String parentName;
    private ArrayList imageList;
    private int imageSize;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList imageList) {
        this.imageList = imageList;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }
}
