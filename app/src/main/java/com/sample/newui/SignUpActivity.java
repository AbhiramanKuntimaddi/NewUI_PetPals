package com.sample.newui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_email_signUp , editText_password_signUp, editText_re_password_signUp ;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

         editText_email_signUp = findViewById(R.id.email_signUp);
         editText_password_signUp = findViewById(R.id.password_signUp);
         editText_re_password_signUp = findViewById(R.id.re_password_signUp);

        findViewById(R.id.button_signUp).setOnClickListener(this);
        findViewById(R.id.Login_text).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_signUp :
                registerUser();
                break;
            case R.id.Login_text :
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    private void registerUser() {
        String email = editText_email_signUp.getText().toString().trim();
        String password = editText_password_signUp.getText().toString().trim();
        String re_password = editText_re_password_signUp.getText().toString().trim();

        if(email.isEmpty()){
            editText_email_signUp.setError("Email is required");
            editText_email_signUp.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_email_signUp.setError("Please enter a Valid E-mail ID");
            editText_email_signUp.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editText_password_signUp.setError("Password is required");
            editText_password_signUp.requestFocus();
            return;
        }

        if(password.length()<6){
            editText_password_signUp.setError("Minimum Length Of Password Should be 6");
            editText_password_signUp.requestFocus();
            return;
        }

        if(!password.equals(re_password)){
            editText_re_password_signUp.setError("Passwords wont match");
            editText_re_password_signUp.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Registered Successfully.",Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Welcome To PetPals.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MyAccount.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User Already Exists. Try forgot Password.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
