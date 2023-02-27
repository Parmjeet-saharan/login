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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savita.firebase.FetchData;
import com.savita.firebase.IsKeyExist;
import com.savita.firebase.UpdateData;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.ConstantVar;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequireDetail extends AppCompatActivity {
    private TextView textView;
    private String uid,aadhar ;
    private Button step2,check;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private EditText adharEdit;
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
        linearLayout = (LinearLayout) findViewById(R.id.dyanmic);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aadhar = adharEdit.getText().toString().trim();
                if (aadhar.isEmpty()) {
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
                        updateData.saveData(uid,key,value,RequireDetail.this,ConstantVar.rootPath);
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
                    intent.putExtra(ConstantVar.examName, exam_name);
                    intent.putExtra(ConstantVar.aadhar,aadhar);
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
        if(b!= null && b.containsKey(ConstantVar.examName)) {
            exam_name = b.getString(ConstantVar.examName);
            String rPath =ConstantVar.examRootPath+"/"+exam_name+"/"+ConstantVar.require_details;
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
                    String existPath =uid+"/"+aadhar+"/"+ConstantVar.exist_details;
                    FetchData fetchData = new FetchData();
                    fetchData.fetchAllData(ConstantVar.rootPath,existPath);
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