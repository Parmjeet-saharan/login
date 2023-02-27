package com.savita.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savita.simplefunction.CallBack;

import java.util.HashMap;

public class UpdateData {
    CallBack callBack;
    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public void saveData(String uid, String key, String value, Context context,String root){
        HashMap<String, Object> map = new HashMap<>();
        map.put(key,value);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(root);
                    mDatabase.child(uid).updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,"update done ",Toast.LENGTH_LONG).show();
                                         callBack.setStringData("true");
                                    }else{
                                        Toast.makeText(context,"update fail ",Toast.LENGTH_LONG).show();
                                        callBack.setStringData("false");
                                    }
                                }
                            });
                //    Toast.makeText(context,"update fall ",Toast.LENGTH_LONG).show();

                }
}
