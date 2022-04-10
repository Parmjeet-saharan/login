package com.savita.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class ExamList extends AppCompatActivity {
     private RecyclerView recyclerView;
     private Button backButton;
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
        backButton = (Button) findViewById(R.id.back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ExamAdapter examAdapter = new ExamAdapter(ExamList.this, personNames);
        recyclerView.setAdapter(examAdapter); // set the Adapter to RecyclerView
    }
}