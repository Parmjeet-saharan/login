package com.savita.navigate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.savita.firebase.FetchData;
import com.savita.firebase.IsKeyExist;
import com.savita.login.DetailAdapter;
import com.savita.login.R;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.ConstantVar;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadedDocument extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout,linearLayout1;
    private ProgressBar progressBar;
    private Button button;
    private RadioButton r1,r2,r3;
    private String uid,aadhar;
    private EditText path;
    public static final int PICK_IMAGE_REQUEST =22;
    private FirebaseAuth mAuth;
    Uri filePath;
    private FirebaseUser currentUser;
    private RadioGroup radioGroup;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn realList = new SomeFunction.dataReturn(totalList,totalKey);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_document);
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
        ArrayList aadharList = getAadharList.getAadhars(UploadedDocument.this,r1,r2,r3);
        radioGroup.clearCheck();
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
                    Toast.makeText(UploadedDocument.this, "No answer has been selected",
                            Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                    aadhar=radioButton.getText().toString().trim();
                    getData();
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            DocumentFile sourceFile = DocumentFile.fromSingleUri(UploadedDocument.this, filePath);
            boolean bool = sourceFile.exists();
            Toast.makeText(UploadedDocument.this, String.valueOf(bool)+"  exist",
                    Toast.LENGTH_SHORT).show();
            path.setText(String.valueOf(filePath));

        }
    }
    public void getData(){
        String existPath = uid+"/"+aadhar+"/"+ ConstantVar.exist_document;
                    FetchData fetchData = new FetchData();
                    fetchData.fetchAllData(ConstantVar.rootPath,existPath);
                    fetchData.setOnItemClickForFetchData(new FetchData.OnItemClick() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void getRealList(SomeFunction.dataReturn list) {
                            String uid2 = uid+"/"+aadhar;
                            ArrayList existListOfDocument = list.totalKey;
                            realList = list;
                            button.setVisibility(View.VISIBLE);
                            Log.d("uploaded@@@@@@@@@@@@@@@@@@@@@", existListOfDocument.get(0).toString());
                            UploadedDocumentAdapter uploadedDocumentAdapter = new UploadedDocumentAdapter(UploadedDocument.this, realList,uid2);
                            recyclerView.setAdapter(uploadedDocumentAdapter); // set the Adapter to RecyclerView
                            uploadedDocumentAdapter.setOnItemClick(new UploadedDocumentAdapter.OnItemClick() {
                                @Override
                                public void getPosition(int data, EditText editText) {
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
    }
}