package com.example.vanh1200.galleryex;

public class Image {
    private String mDate;
    private String mPath;

    public Image() {
    }

    public Image(String date, String path) {
        mDate = date;
        mPath = path;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }
}
