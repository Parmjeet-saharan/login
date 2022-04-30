package com.savita.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.savita.firebase.FetchData;
import com.savita.firebase.IsKeyExist;
import com.savita.firebase.UpdateData;
import com.savita.firebase.querryData;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequireDetail extends AppCompatActivity {
    private TextView textView;
    String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2/1234/details";
    private Button step2,check;
    private EditText adharEdit;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private List detailList;
    private ArrayList realList =new ArrayList();
    private ArrayList textLlist = new ArrayList();
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private String exam_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_require_detail);
        adharEdit = (EditText) findViewById(R.id.addharId);
        textView = (TextView) findViewById(R.id.textView);
        step2 = (Button) findViewById(R.id.step2);
        check = (Button) findViewById(R.id.check);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        linearLayout = (LinearLayout) findViewById(R.id.dyanmic);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
         check.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String addhar = adharEdit.getText().toString().trim();
                 if (addhar.isEmpty()) {
                     adharEdit.setError("please enter correct value");
                     adharEdit.requestFocus();
                 }else {
                     arraylistOfDetails(RequireDetail.this);
                 }
             }
         });
        step2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  checkAdhar();
                if (validate(textLlist)) {
                    progressBar.setVisibility(View.VISIBLE);
                    for (int i = 0; i < realList.size(); i++) {
                        String key = (String) realList.get(i);
                        EditText editText = (EditText) textLlist.get(i);
                        String value = editText.getText().toString().trim();
                        UpdateData updateData = new UpdateData();
                        updateData.saveData(uid,key,value,RequireDetail.this,"users");
                        updateData.setCallBack(new CallBack() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public String setStringData(String data) {
                                Log.d("is data upload @@@2@@@@@@@@@@@  ", data);
                                return null;
                            }
                        });
                    }
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(RequireDetail.this, RequireDocument.class);
                    intent.putExtra("examName", exam_name);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private void arraylistOfDetails(Context context){
            Intent i = getIntent();
            Bundle b = i.getExtras();
            if(b!= null && b.containsKey("examName")) {
                exam_name = b.getString("examName");
                String rPath = "exams/ssc/require_details";
                IsKeyExist keyExist = new  IsKeyExist();
                keyExist.isexist(rPath,rPath,RequireDetail.this);
                keyExist.setCallBackForIsKeyExist(new CallBack() {
                    @Override
                    public String setStringData(String data) {
                        String[] dataList = data.split(",");
                        detailList = Arrays.asList(dataList);
                        if(detailList.size()==9){
                            detailList.add("not available");
                        }
                        String datapath = "basic";
                        String existPath = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2/1234/details";
                        FetchData fetchData = new FetchData();
                        fetchData.fetchAllData("users",existPath);
                        fetchData.setOnItemClickForFetchData(new FetchData.OnItemClick() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void getRealList(SomeFunction.dataReturn list) {
                                SomeFunction someFunction = new SomeFunction();
                                realList = someFunction.effectiveList(detailList,list.totalKey);
                                for(int i=0;i<realList.size();i++) {
                                    Log.d("check@@@@@@@@@@@@@@@@@@@@@", list.totalKey.get(i).toString()+" "+
                                            realList.get(i)+" "+detailList.get(i)+" "+i);
                                    EditText editText = new EditText(context);
                                    editText.setHint(String.valueOf(realList.get(i)));
                                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    editText.setPadding(20, 20, 20, 20);
                                    editText.setId(Integer.valueOf(i));
                                    linearLayout.addView(editText);
                                    textLlist.add(editText);
                                }
                            }
                        });
                        return null;
                    }
                });
            }
    }
    public boolean validate(ArrayList<EditText> editText){
        for(int i=0;i<editText.size();i++) {
            String value = editText.get(i).getText().toString().trim();
            if (value.isEmpty()) {
                editText.get(i).setError("please enter correct value");
                editText.get(i).requestFocus();
                return false;
            }
        }
        return true;
    }
}