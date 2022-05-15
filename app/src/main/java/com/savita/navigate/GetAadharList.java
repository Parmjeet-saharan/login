package com.savita.navigate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.savita.firebase.FetchData;
import com.savita.firebase.IsKeyExist;
import com.savita.login.R;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.ConstantVar;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.List;

public class GetAadharList {
    ArrayList arrayList = new ArrayList();
    public ArrayList getAadhars(Context context, RadioButton r1,RadioButton r2,RadioButton r3){
        String datapath = "QBua2xNPO5QGXRb1Ic9zDsc6u6Y2/"+ ConstantVar.adharlist;
        IsKeyExist isKeyExist = new IsKeyExist();
        isKeyExist.isexist(datapath,"users",context);
        isKeyExist.setCallBackForIsKeyExist(new CallBack() {
            @SuppressLint("LongLogTag")
            @Override
            public String setStringData(String data) {
                if(data!=null) {
                    String[] datalist = data.split(",");
                    for(int i=0;i<datalist.length;i++){
                        arrayList.add(datalist[i]);
                        Log.d("get Aadhar List $$$$$$$$$$$$$$$$$$$$$$$", "aadhar: "+datalist[i]);
                        if(i==0){
                            r1.setVisibility(View.VISIBLE);
                            r1.setText((CharSequence) datalist[i]);
                        }else if(i==1){
                            r2.setVisibility(View.VISIBLE);
                            r2.setText((CharSequence) datalist[i]);
                        }else if(i==2){
                            r3.setVisibility(View.VISIBLE);
                            r2.setText((CharSequence) datalist[i]);
                        }
                    }
                }
                return null;
            }
        });
      return arrayList;
    }
}
