package com.sample.newui;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);
        //setContentView(R.layout.activity_intro);

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_petpals)
                .title("PetPals")
                .description("Adopt or Own a pet")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_buying)
                .title("Buying")
                .description("Login -> Search for Suitable Pets -> Send request to Pet Owner -> Pet Owner Accepts request -> Contact details are shared")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccentLight)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_breeding)
                .title("Breeding")
                .description("Login -> Add Pet details -> Search for Pet breeding -> send request to Pet Owner -> Pet Owner Accepts request -> Contact details are shared")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccentLight)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.ic_pet_needs)
                .title("Pet Needs")
                .description("User Login -> Search For Pet food, Doctors, Other items, Dog shows,...")
                .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.design_default_color_primary)
                        .buttonsColor(R.color.colorAccent)
                        .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        .neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.ic_permission)
                        .title("Permissions")
                        .description("The application needs these permissions to work.")
                        .build());

        addSlide(new CustomSlide());
    }
    @Override
    public void onFinish() {
        super.onFinish();
    }
}
