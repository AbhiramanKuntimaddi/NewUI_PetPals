package com.sample.newui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;


public class ActivitySearch extends AppCompatActivity  {


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
                        doCircularReveal();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, MainActivity.class));
        doExitReveal();
        //super.onBackPressed();
    }

    private void doCircularReveal() {

        // get the center for the clipping circle
        //int centerX = (search_frameLayout.getLeft() + search_frameLayout.getRight()) / 2;
        int centerX = 9 * search_frameLayout.getWidth()/10;
        //int centerY = (search_frameLayout.getTop() + search_frameLayout.getBottom()) / 2;
        int centerY = 9 * search_frameLayout.getHeight()/10;

        int startRadius = 0;
        // get the final radius for the clipping circle
        int endRadius = Math.max(search_frameLayout.getWidth(), search_frameLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(search_frameLayout,
                        centerX, centerY, startRadius, endRadius);
        anim.setDuration(1000);
        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        search_frameLayout.setVisibility(View.VISIBLE);
        anim.start();
    }

    void doExitReveal() {

        // get the center for the clipping circle
        //int centerX = (search_frameLayout.getLeft() + search_frameLayout.getRight()) / 2;
        int centerX = 9 * search_frameLayout.getWidth()/10;
        //int centerY = (search_frameLayout.getTop() + search_frameLayout.getBottom()) / 2;
        int centerY = 9 * search_frameLayout.getHeight()/10;

        // get the initial radius for the clipping circle
        int initialRadius = search_frameLayout.getWidth();

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(search_frameLayout,
                        centerX, centerY, initialRadius, 0);
        anim.setDuration(1000);
        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                search_frameLayout.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();

    }


}