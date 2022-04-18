package com.savita.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savita.login.Home;
import com.savita.login.MainActivity;
import com.savita.login.ResetPassword;

public class Login {
    public void signIn(String remail , String rpassword , Context context){
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(remail,rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(context,"open email for verify emailId",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context,remail+rpassword,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void restPassword(String mailId,Context context){
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(mailId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"check your mail id",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"failed ! try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
