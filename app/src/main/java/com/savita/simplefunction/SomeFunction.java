package com.savita.firebase;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SomeFunction {
    public class dataReturn {
        ArrayList<HashMap<String,String>> totalList;
        ArrayList totalKey;
        ArrayList<HashMap<String,String>> detailList;
        ArrayList detailKey;
        ArrayList<HashMap<String,String>> documentList;
        ArrayList documentKey;
         dataReturn(ArrayList<HashMap<String,String>> totalList,ArrayList totalKey ,
                    ArrayList<HashMap<String,String>> detailList, ArrayList detailKey,
                    ArrayList<HashMap<String,String>> documentList,ArrayList documentKey){
             this.totalList = totalList;
             this.totalKey = totalKey;
             this.detailList = detailList;
             this.detailKey = detailKey;
             this.documentList = documentList;
             this.documentKey = documentKey;
         }
    }
    @SuppressLint("LongLogTag")
    public dataReturn stringToList(String str){
        String check = "https://firebasestorage.googleapis.com";
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        ArrayList arrayList = new ArrayList();
        ArrayList<HashMap<String,String>> detailList = new ArrayList<HashMap<String,String>>();;
        ArrayList detailKey =  new ArrayList();;
        ArrayList<HashMap<String,String>> documentList = new ArrayList<HashMap<String,String>>();;
        ArrayList documentKey =  new ArrayList();;
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.deleteCharAt((stringBuilder.length()-1));
        stringBuilder.deleteCharAt(0);
        str = stringBuilder.toString();
        String[] strings = str.split(",");
        for(int i=0;i<strings.length;i++){
            String[] strs = strings[i].split("=");
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put(strs[0],strs[1]);
            arrayList.add(strs[0]);
            list.add(hashMap);
            if(strs[1].contains(check)){
                documentKey.add(strs[0]);
                documentList.add(hashMap);
            }else{
                detailKey.add(strs[0]);
                detailList.add(hashMap);
            }
            Log.d("firebase @@@@@@@@   "+arrayList.get(i), list.get(i).get(strs[0]));
        }
        return new dataReturn(list,arrayList,detailList,detailKey,documentList,documentKey);
    }
}
