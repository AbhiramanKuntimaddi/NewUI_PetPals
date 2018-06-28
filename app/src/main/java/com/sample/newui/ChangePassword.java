package com.sample.newui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button button_back = findViewById(R.id.back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(ChangePassword.this)
                        .setTitle("Do you want to go back?")
                        .setMessage("Password will remain the same.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(ChangePassword.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        Button button_submit = findViewById(R.id.submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Implement Change Password Code Here!",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(ChangePassword.this)
                .setTitle("Do you want to go back?")
                .setMessage("Password will remain the same.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ChangePassword.this, MainActivity.class));
                    }
                })
                .setNegativeButton("No", null)
                .show();


        //super.onBackPressed();
    }

}
