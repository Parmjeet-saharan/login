package com.savita.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.savita.firebase.querryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequireDetail extends AppCompatActivity {
    private TextView textView;
    private Button step2,check;
    private EditText adharEdit;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private ArrayList detailList =new ArrayList();
    private ArrayList realList =new ArrayList();
    private ArrayList textLlist = new ArrayList();
    private LinearLayout linearLayout;
    private ArrayList documentList = new ArrayList();
    private boolean isrun = true;
    private ProgressBar progressBar;
    private String exam_name;
    private String examKey;
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
             String addhar = adharEdit.getText().toString().trim();
         check.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

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
                        upDateData(key, value);
                    }
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(RequireDetail.this, RequireDocument.class);
                    intent.putExtra("examName", exam_name);
                    intent.putExtra("list",documentList);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onStart() {

        Intent i= getIntent();
        Bundle b = i.getExtras();
        if(b!=null && b.containsKey("examName") && isrun) {
            examKey = b.getString("examName");
            examKey = exam_name +"document";
            isrun = false;
            querryData querry = new querryData();
           querry.arraylistOfDetails(examKey,RequireDetail.this,documentList);
    //        Thread t1 = new Thread(new Runnable() {
     //           @Override
    //            public void run() {
   //             }
   //         });
    //        t1.start();
        }
        super.onStart();
    }
    private void arraylistOfDetails(Context context){
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!= null && b.containsKey("examName")) {
             exam_name = b.getString("examName");
            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(30)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            HashMap<String, String> defaultVlue = new HashMap<>();
            defaultVlue.put(exam_name, "wait few second server down");

            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean updated = task.getResult();
                                String details = mFirebaseRemoteConfig.getString(exam_name);
                                listOfDetail(details,context);
                                //  Log.d(TAG, "Config params updated: " + updated);
                                //   Toast.makeText(ExamList.this, examString,
                                //          Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RequireDetail.this, "Fetch failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void listOfDetail(String details , Context context){
        String[] detail = details.split("@",0);
        for(int i=0;i<detail.length;i++){
            String data = detail[i].replaceAll("\\s", "");
            detailList.add(data);
            String str = "editText"+ String.valueOf(i);
            createRealList(data ,context ,i );
        }
        if(detailList.size()==9){
            detailList.add("not available");
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

    public void upDateData(String key,String value){
        String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(uid).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {

                    Toast.makeText(RequireDetail.this,"hanuman ji this is exist ",Toast.LENGTH_LONG).show();
                }else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(key,value);
                    mDatabase.child(uid).updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RequireDetail.this,"update done ",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RequireDetail.this,"update fail ",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    Toast.makeText(RequireDetail.this,"update fall ",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RequireDetail.this,"something went wrong ",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void createRealList(String key, Context context,int i ){
        String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(uid).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null) {
                    realList.add(key);
                    EditText editText = new EditText(context);
                    editText.setHint(String.valueOf(key));
                    editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    editText.setPadding(20, 20, 20, 20);
                    editText.setId(Integer.valueOf(i));
                    linearLayout.addView(editText);
                    textLlist.add(editText);
              //      Toast.makeText(RequireDetail.this,key+" is new data",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RequireDetail.this,"something went wrong",Toast.LENGTH_LONG).show();

            }
        });
        }
}