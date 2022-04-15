package com.savita.login;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchData {
    public void fetchAllData(String rootRef,String uid){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child(rootRef).child(uid);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                       //  Object obj = task.getResult().getValue();
                    String data = String.valueOf(task.getResult().getValue());
                    SomeFunction someFunction = new SomeFunction();
                    SomeFunction.dataReturn twoList = someFunction.stringToList(data);
                     //   Log.d("firebase @@@@@@@@@@@@@@@@@", String.valueOf(data));


                }
            }
        });
    }
}
