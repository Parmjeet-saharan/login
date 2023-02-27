package com.savita.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savita.simplefunction.CallBack;

public class IsKeyExist {
    CallBack callBack;
    public void setCallBackForIsKeyExist(CallBack callBack){
        this.callBack = callBack;
    }
    public void isexist(String path, String rootRef, Context context){
  //      String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
        DatabaseReference mDatabase ;
        if(rootRef.equals("users")){
            mDatabase = FirebaseDatabase.getInstance().getReference(rootRef);
        }else {
            mDatabase = FirebaseDatabase.getInstance("https://form-dc039.firebaseio.com").getReference();
        }
            mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                          callBack.setStringData("false");
                          Log.d("iskeyexist", "null"+"#############" );
                        //           Toast.makeText(context, key + " is new data", Toast.LENGTH_LONG).show();

                    }else{
                        String value = snapshot.getValue().toString().trim();
                        Log.d("iskeyexist", value);
                        callBack.setStringData(value);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_LONG).show();

                }
            });
    }
}
