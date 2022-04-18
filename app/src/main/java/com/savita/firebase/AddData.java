package com.savita.firebase;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.savita.login.User;

public class AddData {
    public void addUser(String remail, String rpassword, Context context ,User user){
         FirebaseAuth auth;
         auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(remail,rpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                     addUserData(user,context);
                        }else {
                            Toast.makeText(context,"failed ! try again",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    public void addUserData(User user,Context context){
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "register successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "failed ! try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
