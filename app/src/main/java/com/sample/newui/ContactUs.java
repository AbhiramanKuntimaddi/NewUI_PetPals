package com.sample.newui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        final CardView cardView_india = findViewById(R.id.cardView);
        CardView cardView_USA = findViewById(R.id.cardView1);

        final TextView textView_India = findViewById(R.id.text_name1);
        final TextView textView_USA = findViewById(R.id.text_name2);

        final TextView textView_CardText_India = findViewById(R.id.card_text);
        final TextView textView_CardText_USA = findViewById(R.id.card_text1);

        cardView_india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Pressed on " + textView_India.getText().toString()+" "+textView_CardText_India.getText().toString(),Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+textView_CardText_India.getText().toString()+""));
                startActivity(intent);
            }
        });

        cardView_USA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Pressed on " + textView_USA.getText().toString()+" "+textView_CardText_USA.getText().toString(),Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+textView_CardText_USA.getText().toString()+""));
                startActivity(intent);
            }
        });
    }
}
