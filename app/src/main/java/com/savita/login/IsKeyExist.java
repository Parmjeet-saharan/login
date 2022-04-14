package com.savita.login;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IsKeyExist {
    CallBack callBack;
    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }
    public void isexist(String key, String rootRef, Context context){
        String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(rootRef);
            mDatabase.child(uid).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                          callBack.setStringData("false");
                        //  Log.d(TAG, realList.get(j) +"#############" );
                        //           Toast.makeText(context, key + " is new data", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_LONG).show();

                }
            });
    }
}
