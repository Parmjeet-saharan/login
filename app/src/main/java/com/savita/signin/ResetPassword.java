package com.savita.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.savita.login.R;

public class ResetPassword extends AppCompatActivity {
    private EditText email;
    private Button resetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = (EditText) findViewById(R.id.editEmail);
        resetPassword = (Button) findViewById(R.id.buttonReset);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailId = email.getText().toString().trim();
                resetPasswordfunc(mailId);
            }
        });

    }
    public void resetPasswordfunc(String mailId){
        if(mailId.isEmpty()){
            email.setError("please enter your email");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mailId).matches()){
            email.setError("please enter valid email");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(mailId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this,"check your mail id",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(ResetPassword.this,"failed ! try again",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return;
    }
}