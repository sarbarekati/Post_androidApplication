package com.anad.mobile.post.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by elias.mohammadi on 2018/01/24.
 */

public class ReportMenu {
    private String Text;
    private Drawable drawable;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
