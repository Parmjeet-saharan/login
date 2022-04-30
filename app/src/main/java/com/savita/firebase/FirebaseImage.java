package com.savita.firebase;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.savita.simplefunction.CallBack;

public class FirebaseImage {
    CallBack ncallBack;
    public void setCallBackForUploadImage(CallBack callBack){
        this.ncallBack = callBack;
    }
    public void uploadImage(Uri file, String uid, String filenmae, Context context){
        //      final ProgressDialog progressDialog = new ProgressDialog(context);
        //  progressDialog.setTitle("Uploading...");
        //    progressDialog.show();
        String name = filenmae+".jpg";
        final String TAG = "UploadImage";
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Users");
        StorageReference riversRef = storageReference.child(uid).child(name);
        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //            progressDialog.dismiss();
                Toast.makeText(context,"call sucess ",Toast.LENGTH_LONG).show();
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(context,"get uri ",Toast.LENGTH_LONG).show();
                        String uid2 = uid+"/document";
                        UpdateData saveDataInFirebase = new UpdateData();
                        saveDataInFirebase.saveData(uid2,filenmae,String.valueOf(uri),context,"users");
                        saveDataInFirebase.setCallBack(new CallBack() {
                            @Override
                            public String setStringData(String data) {
                                if(data=="true") {
                                    ncallBack.setStringData("true");
                                    return "true";
                                }else{
                                    ncallBack.setStringData("false");
                                    return "false";
                                }
                            }
                        });
                    }
                });
            }

        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"fail"+e.getMessage(),Toast.LENGTH_LONG).show();
                        //            progressDialog.dismiss();
                        ncallBack.setStringData("false");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                .getTotalByteCount());
                        //               progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                }) ;
    }

    public void deleteImage(String fullPath){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference desertRef = storageRef.child(fullPath);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
}
