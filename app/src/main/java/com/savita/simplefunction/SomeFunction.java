package com.savita.simplefunction;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SomeFunction {
    public static class dataReturn {
      public ArrayList<HashMap<String,String>> totalList;
      public ArrayList totalKey;
         public dataReturn(ArrayList<HashMap<String, String>> totalList, ArrayList totalKey){
             this.totalList = totalList;
             this.totalKey = totalKey;
         }
    }

    @SuppressLint("LongLogTag")
    public dataReturn stringToList(String str){
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.deleteCharAt((stringBuilder.length()-1));
        stringBuilder.deleteCharAt(0);
        str = stringBuilder.toString();
        String[] strings = str.split(",");
        for(int i=0;i<strings.length;i++){
            String[] strs = strings[i].split("=",2);
            Log.d("firebase @@@@@@@@   "+strings[i],str);
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put(strs[0],strs[1]);
            arrayList.add(strs[0]);
            list.add(hashMap);

            Log.d("firebase @@@@@@@@   "+arrayList.get(i), list.get(i).get(strs[0]));
        }
        return new dataReturn(list,arrayList);
    }
    public ArrayList effectiveList(List totalList,ArrayList existist){
        ArrayList realList = new ArrayList();
        for(int i=0;i<totalList.size();i++){
            if(!(existist.contains(totalList.get(i)))){
                realList.add(totalList.get(i));
            }
        }
        return realList;
    }
}
