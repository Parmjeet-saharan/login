package com.savita.navigate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
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

import com.savita.firebase.FetchData;
import com.savita.firebase.IsKeyExist;
import com.savita.login.DetailAdapter;
import com.savita.login.R;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;

public class CanUploadDocument extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout,linearLayout1;
    private TextView textView;
    private ProgressBar progressBar;
    private RadioButton r1,r2,r3;
    private Button button;
    private EditText path;
    private RadioGroup radioGroup;
    Uri filePath;
    public static final int PICK_IMAGE_REQUEST =22;
    private ArrayList realList =new ArrayList();
    private ArrayList requireList =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);
        button = (Button) findViewById(R.id.back);
        radioGroup = (RadioGroup) findViewById(R.id.rdgp);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        r1 = (RadioButton) findViewById(R.id.checkBox1);
        r2 = (RadioButton) findViewById(R.id.checkBox2);
        r3 = (RadioButton) findViewById(R.id.checkBox3);
         GetAadharList getAadharList = new GetAadharList();
         ArrayList aadharList = getAadharList.getAadhars(CanUploadDocument.this,r1,r2,r3);
        radioGroup.clearCheck();
        getAdapterData();
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
                    Toast.makeText(CanUploadDocument.this, "No answer has been selected",
                            Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                    st=radioButton.getText().toString().trim();
                    Toast.makeText(CanUploadDocument.this, "you selected  "+st,
                            Toast.LENGTH_SHORT).show();
                     linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    String uid = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2";
                    DetailAdapter detailAdapter = new DetailAdapter(CanUploadDocument.this, realList,uid);
                    recyclerView.setAdapter(detailAdapter); // set the Adapter to RecyclerView
                    detailAdapter.setOnItemClick(new DetailAdapter.OnItemClick() {
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
            DocumentFile sourceFile = DocumentFile.fromSingleUri(CanUploadDocument.this, filePath);
            boolean bool = sourceFile.exists();
            Toast.makeText(CanUploadDocument.this, String.valueOf(bool)+"  exist",
                    Toast.LENGTH_SHORT).show();
            path.setText(String.valueOf(filePath));

        }
    }
    public void getAdapterData(){
        String datapath = "basic";
        String existPath = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2/1234/document";
        IsKeyExist isKeyExist = new IsKeyExist();
        isKeyExist.isexist(datapath,"users",CanUploadDocument.this);
        isKeyExist.setCallBackForIsKeyExist(new CallBack() {
            @SuppressLint("LongLogTag")
            @Override
            public String setStringData(String data) {
                if(data!=null){
                    String[] datalist = data.split(",");
                    for(int i=0;i<datalist.length;i++){
                        requireList.add(datalist[i]);
                    }
                    FetchData fetchData = new FetchData();
                    fetchData.fetchAllData("users",existPath);
                    fetchData.setOnItemClickForFetchData(new FetchData.OnItemClick() {
                        @Override
                        public void getRealList(SomeFunction.dataReturn list) {
                            ArrayList existListOfDocument = list.totalKey;
                            Log.d("can upload@@@@@@@@@@@@@@@@@@@@@", existListOfDocument.get(0).toString());
                            SomeFunction someFunction = new SomeFunction();
                            realList = someFunction.effectiveList(requireList,existListOfDocument);
                        }
                    });
                }else{
                    realList.add("something went wrong");
                }
                return null;
            }
        });
    }
}