package com.anad.mobile.post.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by elias.mohammadi on 2018/03/17
 */

public class RecyclerOnClickListener implements RecyclerView.OnItemTouchListener {
    private RecyclerOnClick recyclerOnClick;
    private GestureDetector gestureDetector;

    public RecyclerOnClickListener(Context context,final RecyclerView rc,final RecyclerOnClick recyclerOnClick)
    {
        this.recyclerOnClick = recyclerOnClick;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        }
        );
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && recyclerOnClick!=null && gestureDetector.onTouchEvent(e))
        {
            recyclerOnClick.onClick(child,rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
