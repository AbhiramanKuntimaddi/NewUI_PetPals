package com.sample.newui;

import android.animation.Animator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class Activity_Search extends AppCompatActivity {
    private FrameLayout search_frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_frameLayout = findViewById(R.id.search_layout);

        if (savedInstanceState == null) {
            search_frameLayout.setVisibility(View.INVISIBLE);
            ViewTreeObserver viewTreeObserver = search_frameLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealTransition(); //
                    }
                });
            }
        }
    }

    private void circularRevealTransition() {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Set the 'X' and 'Y' values for your requirement, Here it is set for the fab being as the source of the circle reveal //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int X = 9 * search_frameLayout.getWidth()/10;
        int Y = 9 * search_frameLayout.getHeight()/10;
        int Duration = 500;

        float finalRadius = Math.max(search_frameLayout.getWidth(), search_frameLayout.getHeight()); //The final radius must be the end points of the current activity

        // create the animator for this view, with the start radius as zero
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(search_frameLayout, X, Y, 0, finalRadius);
        circularReveal.setDuration(Duration);

        // set the view visible and start the animation
        search_frameLayout.setVisibility(View.VISIBLE);
        // start the animation
        circularReveal.start();
    }
}
