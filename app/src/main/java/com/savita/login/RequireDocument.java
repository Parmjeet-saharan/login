package com.savita.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;

public class RequireDocument extends AppCompatActivity {
  private TextView textView,textView1;
  private Button button;
  private ProgressBar progressBar;
  private RecyclerView recyclerView;
    private ArrayList detailList =new ArrayList();
    private ArrayList realList =new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_require_document);
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.step2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
    private void realList(){

    }
    private void arraylistOfDetails(Context context){
        String exam_name;
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!= null && b.containsKey("examName")) {
            exam_name = b.getString("examName")+"document";
            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
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

}