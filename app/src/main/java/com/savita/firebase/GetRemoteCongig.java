package com.savita.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.savita.login.ExamList;

import java.util.HashMap;
import java.util.concurrent.Executor;

public class GetRemoteCongig {
    public void getRemoteConfig(String key, Context context){
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        HashMap<String,String> defaultVlue = new HashMap<>();
        defaultVlue.put(key,"wait few second server down");

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            String examString = mFirebaseRemoteConfig.getString(key);
                            //  Log.d(TAG, "Config params updated: " + updated);
                            //   Toast.makeText(ExamList.this, examString,
                            //          Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
