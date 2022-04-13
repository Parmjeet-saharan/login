package com.savita.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadImage {
    boolean result = false;
    public boolean uploadImage(Uri file, String uid, String filenmae, Context context){
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
                       SaveDataInFirebase saveDataInFirebase = new SaveDataInFirebase();
                       saveDataInFirebase.saveData(uid,filenmae,String.valueOf(uri),context);
                   }
               });
            }

        })

         .addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(context,"fail"+e.getMessage(),Toast.LENGTH_LONG).show();
     //            progressDialog.dismiss();
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
      return result;
    }
}
