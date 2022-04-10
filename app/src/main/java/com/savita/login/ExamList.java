package com.savita.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamList extends AppCompatActivity {
     private RecyclerView recyclerView;
    String examString;
     private Button backButton;
     ArrayList examsList =new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        backButton = (Button) findViewById(R.id.back);
        setExamList();
    }
    public void setExamList(){
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        HashMap<String,String> defaultVlue = new HashMap<>();
        defaultVlue.put("exam","wait few second server down");

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            examString = mFirebaseRemoteConfig.getString("exam");
                            listOfExam(examString);
                          //  Log.d(TAG, "Config params updated: " + updated);
                         //   Toast.makeText(ExamList.this, examString,
                          //          Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ExamList.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void listOfExam(String examString){
       String[] exams = examString.split("@",0);
       for(int i=0;i<exams.length;i++){
           examsList.add(exams[i]);
  //         Toast.makeText(ExamList.this, String.valueOf(examsList.get(i)),
  //                 Toast.LENGTH_SHORT).show();
       }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if(examsList.size()==9){
            examsList.add("server down");
        }
        ExamAdapter examAdapter = new ExamAdapter(ExamList.this, examsList);
        recyclerView.setAdapter(examAdapter); // set the Adapter to RecyclerView
    }
}