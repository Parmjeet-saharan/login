package com.savita.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savita.firebase.FetchData;
import com.savita.firebase.IsKeyExist;
import com.savita.payment.Payment;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.ConstantVar;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class  RequireDocument extends AppCompatActivity {
  private TextView textView,textView1;
  private Button button;
  private String exam_name;
  private String uid,aadhar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText path;
    Uri filePath;
  public static final int PICK_IMAGE_REQUEST =22;
    private static final String TAG = "RequireDocument";
    private ProgressBar progressBar;
  private RecyclerView recyclerView;
    private List detailList;
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
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        getData(RequireDocument.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequireDocument.this, Payment.class);
                intent.putExtra(ConstantVar.examName, exam_name);
                intent.putExtra(ConstantVar.aadhar,aadhar);
                startActivity(intent);
            }
        });
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
    private void getData(Context context){
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!= null && b.containsKey(ConstantVar.examName) && b.containsKey(ConstantVar.aadhar)) {
            exam_name = b.getString(ConstantVar.examName);
            aadhar = b.getString(ConstantVar.aadhar);
            String rPath = ConstantVar.examRootPath+"/"+exam_name+"/"+ConstantVar.require_document;
            IsKeyExist keyExist = new  IsKeyExist();
            keyExist.isexist(rPath,rPath,RequireDocument.this);
            keyExist.setCallBackForIsKeyExist(new CallBack() {
                @Override
                public String setStringData(String data) {
                    String[] dataList = data.split(",");
                    detailList = Arrays.asList(dataList);
                    if(detailList.size()==9){
                        detailList.add("not available");
                    }
                    String existPath = uid+"/"+aadhar+"/"+ConstantVar.exist_document;
                    FetchData fetchData = new FetchData();
                    fetchData.fetchAllData("users",existPath);
                    fetchData.setOnItemClickForFetchData(new FetchData.OnItemClick() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void getRealList(SomeFunction.dataReturn list) {
                            String uid2 = uid+"/"+aadhar;
                            SomeFunction someFunction = new SomeFunction();
                            realList = someFunction.effectiveList(detailList,list.totalKey);
                             //   Log.d("check@@@@@@@@@@@@@@@@@@@@@", list.totalKey.get(0).toString()+" "+
                           //             realList.get(0)+" "+detailList.get(0));
                            DetailAdapter detailAdapter = new DetailAdapter(RequireDocument.this, realList,uid2);
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
                    });
                    return null;
                }
            });
        }
    }
}