package com.anad.mobile.post.Utils.FontUtils;


import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
public class FontUtil {
    public static void setTypeFaceNumber(Context context,View... views){
        Typeface font = Typeface.createFromAsset(context.getAssets(),"IRANSansMobile(FaNum).ttf");
        for (View view : views) {
            if(view instanceof TextView){
                ((TextView)view).setTypeface(font);
            }
        }
    }
}
