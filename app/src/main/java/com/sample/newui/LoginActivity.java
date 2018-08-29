package com.sample.newui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editText_email_login , editText_password_login;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
                
        editText_email_login = findViewById(R.id.email_login);
        editText_password_login = findViewById(R.id.password_login);

        findViewById(R.id.signUp_text).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.signUp_text: {
              startActivity(new Intent(this, SignUpActivity.class));
              break;
          }
          case R.id.button_login: {
              loginUser();
              break;
          }
      }
    }

    private void loginUser() {
        String email = editText_email_login.getText().toString().trim();
        String password = editText_password_login.getText().toString().trim();

        if(email.isEmpty()){
            editText_email_login.setError("Email is required");
            editText_email_login.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_email_login.setError("Please enter a Valid E-mail ID");
            editText_email_login.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editText_password_login.setError("Password is required");
            editText_password_login.requestFocus();
            return;
        }

        if(password.length()<6){
            editText_password_login.setError("Minimum Length Of Password Should be 6");
            editText_password_login.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(getApplicationContext(),MyAccount.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        
    }
}
