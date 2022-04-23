package com.savita.firebase;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.savita.login.DetailAdapter;
import com.savita.simplefunction.SomeFunction;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchData {
    FetchData.OnItemClick onItemClick;

    public void setOnItemClickForFetchData(FetchData.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getRealList(SomeFunction.dataReturn list); //pass any things
    }
    public void fetchAllData(String rootRef,String path){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("QBua2xNPO5QGXRb1Ic9zDsc6u6Y2/1234/document ");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                       //  Object obj = task.getResult().getValue();
                    String data = String.valueOf(task.getResult().getValue());
            //        Log.d("firebase @@@@@@@@@@@@@@@@@", String.valueOf(data));
                    SomeFunction someFunction = new SomeFunction();
                    SomeFunction.dataReturn twoList = someFunction.stringToList(data);
                    onItemClick.getRealList(twoList);

                }
            }
        });
    }
}
