package com.savita.navigate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.savita.login.DetailAdapter;
import com.savita.login.R;

import java.util.ArrayList;

public class CanUploadDocument extends AppCompatActivity {
    private RecyclerView recyclerView;
    private View include;
    private LinearLayout linearLayout,linearLayout1;
    private TextView textView;
    private ProgressBar progressBar;
    private Button button;
    private EditText path;
    private RadioGroup radioGroup;
    Uri filePath;
    public static final int PICK_IMAGE_REQUEST =22;
    private ArrayList realList =new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);
        include = (View) findViewById(R.id.included);
        button = (Button) include.findViewById(R.id.back);
        radioGroup = (RadioGroup) include.findViewById(R.id.rdgp);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) include.findViewById(R.id.linear);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        radioGroup.clearCheck();
        realList.add("your pic");
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
}