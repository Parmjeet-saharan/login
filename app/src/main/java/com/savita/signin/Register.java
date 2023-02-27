package com.savita.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.savita.login.R;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private Button login, register;
    private EditText  name , password , repassword , email;
    private TextView textView;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = (Button) findViewById(R.id.buttonLogin);
        register =(Button) findViewById(R.id.buttonRegister);
        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        repassword = (EditText) findViewById(R.id.confirmPassword);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this , MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rname = name.getText().toString().trim();
                String remail = email.getText().toString().trim();
                String rpassword = password.getText().toString().trim();
                String rrepassword = repassword.getText().toString().trim();
                validate(rname,remail,rpassword,rrepassword);
            }
        });
    }
   void validate(String rname, String remail, String rpassword, String rrepassword ){
        if(rname.isEmpty()){
            name.setError("please enter your name");
            name.requestFocus();
            return;
        }
       if(remail.isEmpty()){
           email.setError("please enter your email");
           email.requestFocus();
           return;
       }
       if(rpassword.length()<6){
           password.setError("please enter password with minimum 6 characters");
           password.requestFocus();
           return;
       }
       if(rpassword.isEmpty()){
           password.setError("please enter password");
           password.requestFocus();
           return;
       }
       if(rpassword == rrepassword){
           repassword.setError("password not match");
           repassword.requestFocus();
           return;
       }
       if(!Patterns.EMAIL_ADDRESS.matcher(remail).matches()){
           email.setError("please enter valid email");
           email.requestFocus();
           return;
       }
       progressBar.setVisibility(View.VISIBLE);
       auth.createUserWithEmailAndPassword(remail,rpassword)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()) {
                           User user = new User(rname, remail, rpassword);
                           FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {
                                       Toast.makeText(Register.this, "register successfully", Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.GONE);
                                   } else {
                                       Toast.makeText(Register.this, "failed ! try again", Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.GONE);
                                   }
                               }
                           });
                       }else {
                           Toast.makeText(Register.this,"failed ! try again",Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);
                       }

                   }
               });
       return;
    }
}