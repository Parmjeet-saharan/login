package com.savita.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;
public class querryData {
    String result = "not found";
    public void arraylistOfDetails(String exam_name, Context context , ArrayList list){
            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(30)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            HashMap<String, String> defaultVlue = new HashMap<>();
            defaultVlue.put(exam_name, "wait few second server down");

            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean updated = task.getResult();
                                  result= mFirebaseRemoteConfig.getString(exam_name);
                                    createRealList(result,context,list);
                                //     Log.d(TAG, exam_name+details +"@@@@@@@@@@@@@@: " + updated);
                              //     Toast.makeText(context, result+" @@@@@@@@@@@ "+exam_name,
                              //            Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "Fetch failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }
    public void createRealList(String str, Context context, ArrayList list){
        String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
       String[] detail = str.split("@",0);
        for(int i=0;i<detail.length;i++){
            String key =  detail[i].replaceAll("\\s", "");
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            mDatabase.child(uid).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null) {
                        list.add(key);
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
}
