package model;

import android.graphics.drawable.Drawable;

/**
 * Created by user on 2017/1/27.
 */

public class Category {
    private String title;
    private int imageID;


    public Category(String title, int imageID) {
        this.title = title;
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public int getImageID() {
        return imageID;
    }

}
