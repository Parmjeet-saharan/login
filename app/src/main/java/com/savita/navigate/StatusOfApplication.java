package com.savita.navigate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savita.firebase.FetchData;
import com.savita.login.R;
import com.savita.simplefunction.ConstantVar;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusOfApplication extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout,linearLayout1;
    private ProgressBar progressBar;
    private Button button;
    private String uid,aadhar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RadioButton r1,r2,r3;
    private RadioGroup radioGroup;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn realList = new SomeFunction.dataReturn(totalList,totalKey);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_of_application);
        button = (Button) findViewById(R.id.back);
        radioGroup = (RadioGroup) findViewById(R.id.rdgp);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        r1 = (RadioButton) findViewById(R.id.checkBox1);
        r2 = (RadioButton) findViewById(R.id.checkBox2);
        r3 = (RadioButton) findViewById(R.id.checkBox3);
        GetAadharList getAadharList = new GetAadharList();
        ArrayList aadharList = getAadharList.getAadhars(StatusOfApplication.this,r1,r2,r3);
        radioGroup.clearCheck();
        getData();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId==-1){
                    Toast.makeText(StatusOfApplication.this, "No answer has been selected",
                            Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                    aadhar=radioButton.getText().toString().trim();
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    StatusAdapter statusAdapter = new StatusAdapter(StatusOfApplication.this, realList,uid);
                    recyclerView.setAdapter(statusAdapter); // set the Adapter to RecyclerView
                }
            }
        });
    }
    public void getData(){
        String existPath = uid+"/"+aadhar+"/"+ ConstantVar.exam_statusPath;
        FetchData fetchData = new FetchData();
        fetchData.fetchAllData(ConstantVar.rootPath,existPath);
        fetchData.setOnItemClickForFetchData(new FetchData.OnItemClick() {
            @SuppressLint("LongLogTag")
            @Override
            public void getRealList(SomeFunction.dataReturn list) {
                ArrayList existListOfDocument = list.totalKey;
                realList = list;
                button.setVisibility(View.VISIBLE);
                Log.d("uploaded@@@@@@@@@@@@@@@@@@@@@", existListOfDocument.get(0).toString());
            }
        });
    }
}