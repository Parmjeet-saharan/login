package com.savita.navigate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.savita.firebase.FetchData;
import com.savita.login.R;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadedDetails extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout,linearLayout1;
    private ProgressBar progressBar;
    private Button button;
    private RadioGroup radioGroup;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn realList = new SomeFunction.dataReturn(totalList,totalKey);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_details);
        button = (Button) findViewById(R.id.back);
        radioGroup = (RadioGroup) findViewById(R.id.rdgp);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        radioGroup.clearCheck();
        getData();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            String st;
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1){
                    Toast.makeText(UploadedDetails.this, "No answer has been selected",
                            Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                    st=radioButton.getText().toString().trim();
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
                    UpdatedDetailAdapter updatedDetailAdapter = new UpdatedDetailAdapter(UploadedDetails.this, realList,uid);
                    recyclerView.setAdapter(updatedDetailAdapter); // set the Adapter to RecyclerView
                }
            }
        });
    }
    public void getData(){
        String datapath = "basic";
        String existPath = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2/1234/details";
        FetchData fetchData = new FetchData();
        fetchData.fetchAllData("users",existPath);
        fetchData.setOnItemClickForFetchData(new FetchData.OnItemClick() {
            @SuppressLint("LongLogTag")
            @Override
            public void getRealList(SomeFunction.dataReturn list) {
                ArrayList existListOfDocument = list.totalKey;
                realList = list;
                Log.d("uploaded@@@@@@@@@@@@@@@@@@@@@", existListOfDocument.get(0).toString());
            }
        });
    }
}