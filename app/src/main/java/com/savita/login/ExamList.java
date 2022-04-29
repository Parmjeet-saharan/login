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
import com.savita.firebase.IsKeyExist;
import com.savita.simplefunction.CallBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamList extends AppCompatActivity {
     private RecyclerView recyclerView;
     private Button backButton;
     List examsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        backButton = (Button) findViewById(R.id.back);
        setExamList();
    }
    public void setExamList(){
         String rPath = "exam_list";
        IsKeyExist keyExist = new  IsKeyExist();
        keyExist.isexist(rPath,rPath,ExamList.this);
        keyExist.setCallBackForIsKeyExist(new CallBack() {
            @Override
            public String setStringData(String data) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                String[] examArray = data.split(",");
                examsList =  Arrays.asList(examArray);
                if(examsList.size()==9){
                    examsList.add("server down");
                }
                ExamAdapter examAdapter = new ExamAdapter(ExamList.this, examsList);
                recyclerView.setAdapter(examAdapter); // set the Adapter to RecyclerView
                return null;
            }
        });
    }
}