package com.savita.login;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {
    private Context context;
    private ArrayList details;
    private String uid;
    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int data,EditText editText); //pass any things

    }

    private static final int PICK_IMAGE_REQUEST = 22;
    public DetailAdapter(Context context, ArrayList details,String uid) {
        this.context = context;
        this.details = details;
        this.uid = uid;
    }

    @NonNull
    @Override
    public DetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_list, parent, false);
        DetailAdapter.MyViewHolder vh = new DetailAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String require_detail = (String) details.get(position);
     //   Toast.makeText(context, require_detail + " is here", Toast.LENGTH_LONG).show();
           holder.dName.setHint(String.valueOf(details.get(position)));
           holder.imageButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                onItemClick.getPosition(position,holder.editText);
               }
           });
           holder.button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  UploadImage uploadImage = new UploadImage();
                  String filepath = holder.editText.getText().toString().trim();
                  Uri uri = Uri.parse(filepath);
                   DocumentFile sourceFile = DocumentFile.fromSingleUri(context, uri);
                   boolean bool = sourceFile.exists();
                   Toast.makeText(context, bool + " is here", Toast.LENGTH_LONG).show();
                   holder.progressBar.setVisibility(View.VISIBLE);
                   uploadImage.uploadImage(uri,uid,require_detail,context);
                   uploadImage.setCallBackForUploadImage(new CallBack() {
                       @Override
                       public String setStringData(String data) {
                           holder.progressBar.setVisibility(View.GONE);
                           if(data=="true"){
                               holder.dStatus.setHint("successfully upload");
                               holder.dStatus.setVisibility(View.VISIBLE);
                           }else{
                               holder.dStatus.setHint("something went wrong ! try later");
                               holder.dStatus.setVisibility(View.VISIBLE);
                           }
                           return null;
                       }
                   });
                       holder.progressBar.setVisibility(View.GONE);

               }
           });

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click

            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText editText;// init the item view's
        TextView dName,dStatus;
        Button button;
        ProgressBar progressBar;
        ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            editText = (EditText) itemView.findViewById(R.id.nameOfDocument);
            dName = (TextView) itemView.findViewById(R.id.documentNmae);
            dStatus = (TextView) itemView.findViewById(R.id.documentStatus);
            button = (Button) itemView.findViewById(R.id.upload);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageView);
        }
    }

}


