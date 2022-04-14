package com.savita.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Payment extends AppCompatActivity {
    private TextView textView,textView1,textView2;
    private Button button;
    double catFees,ourFees,totalFees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        textView = (TextView) findViewById(R.id.catfees);
        textView1 = (TextView) findViewById(R.id.ourFees);
        textView2 = (TextView) findViewById(R.id.totalFees);
        button = (Button) findViewById(R.id.button);
        catFees =100;
    }
}