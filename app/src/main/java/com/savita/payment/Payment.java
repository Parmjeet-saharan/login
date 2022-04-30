package com.savita.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.ProgressDialog;
import com.paytm.pgsdk.PaytmOrder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;


import com.savita.login.R;

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
        textView.setHint("Your FORM FEES According to your catagery is "+ catFees);
        textView.setVisibility(View.VISIBLE);
        ourFees = 60;
        textView1.setHint("OUR Charges is "+ ourFees);
        textView1.setVisibility(View.VISIBLE);
        totalFees = 160;
        textView2.setHint("SO Total FEES is  "+ totalFees);
        textView2.setVisibility(View.VISIBLE);
      }

     }
