package com.savita.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.ArrayList;
import java.util.HashMap;

public class RequireDocument extends AppCompatActivity {
  private TextView textView,textView1;
  private Button button;
  private EditText path;
    Uri filePath;
  public static final int PICK_IMAGE_REQUEST =22;
    private static final String TAG = "RequireDocument";
    private ProgressBar progressBar;
  private RecyclerView recyclerView;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequireDocument.this,Payment.class);
                startActivity(intent);
            }
        });
        Intent i= getIntent();
        Bundle b = i.getExtras();
        if(b!=null && b.containsKey("list")){
            String exam_name = b.getString("examName");
            exam_name = exam_name+"document";
            realList = b.getStringArrayList("list");
            String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
            DetailAdapter detailAdapter = new DetailAdapter(RequireDocument.this, realList,uid);
            recyclerView.setAdapter(detailAdapter); // set the Adapter to RecyclerView
            detailAdapter.setOnItemClick(new DetailAdapter.OnItemClick() {
                @Override
                public void getPosition(int data,EditText editText) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    path=editText;
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(
                                    intent,
                                    "Select Image from here..."),
                            PICK_IMAGE_REQUEST);
                }
            });
        }
        //  new MyAsyncTask().execute();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            DocumentFile sourceFile = DocumentFile.fromSingleUri(RequireDocument.this, filePath);
            boolean bool = sourceFile.exists();
            Toast.makeText(RequireDocument.this, String.valueOf(bool)+"  exist",
                    Toast.LENGTH_SHORT).show();
            path.setText(String.valueOf(filePath));

        }
    }
    @Override
    protected void onStart() {
       //   arraylistOfDetails();
        super.onStart();
    }
}