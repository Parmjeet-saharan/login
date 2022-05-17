package com.savita.navigate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.firebase.FirebaseImage;
import com.savita.login.R;
import com.savita.simplefunction.CallBack;
import com.savita.simplefunction.ConstantVar;
import com.savita.simplefunction.SomeFunction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadedDocumentAdapter extends RecyclerView.Adapter<UploadedDocumentAdapter.MyViewHolder> {
    private Context context;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn details = new SomeFunction.dataReturn(totalList,totalKey);
    private String uid;
    UploadedDocumentAdapter.OnItemClick onItemClick;

    public void setOnItemClick(UploadedDocumentAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int data,EditText editText); //pass any things

    }
    public UploadedDocumentAdapter(Context context, SomeFunction.dataReturn details, String uid) {
        this.context = context;
        this.details = details;
        this.uid = uid;
    }

    @NonNull
    @Override
    public UploadedDocumentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_document, parent, false);
        UploadedDocumentAdapter.MyViewHolder vh = new UploadedDocumentAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull UploadedDocumentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String require_detail = (String) details.totalKey.get(position);
        //   Toast.makeText(context, require_detail + " is here", Toast.LENGTH_LONG).show();
        holder.textView.setHint(require_detail);
        String link = details.totalList.get(position).get(require_detail);
        Log.d("uploaded adapter #####", "onBindViewHolder: "+link);
        Picasso.get().load(link).into(holder.imageView);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = ConstantVar.userRootPath+"/"+uid+"/"+require_detail+".jpg";
                String pa = uid+"/"+ConstantVar.exist_document;
             FirebaseImage firebaseImage = new FirebaseImage();
             firebaseImage.deleteImage(path,pa,require_detail,context);
             firebaseImage.setCallBackForUploadImage(new CallBack() {
                 @Override
                 public String setStringData(String data) {
                     if(data.equals("true")) {
                         holder.imageView.setVisibility(View.GONE);
                         holder.edit.setVisibility(View.GONE);
                         holder.remove.setVisibility(View.GONE);
                         holder.download.setVisibility(View.GONE);
                     }else {
                         Toast.makeText(context,"something went wrong try again!",Toast.LENGTH_LONG).show();
                     }
                     return null;
                 }
             });
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"downloading start",Toast.LENGTH_LONG).show();
                FirebaseImage firebaseImage = new FirebaseImage();
                firebaseImage.downloasImage(context,require_detail,link);
            }
        });

        // implement setOnClickListener event on item view.
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edit.setVisibility(View.GONE);
                holder.remove.setVisibility(View.GONE);
                holder.download.setVisibility(View.GONE);
                holder.editText.setVisibility(View.VISIBLE);
                holder.upload.setVisibility(View.VISIBLE);
                onItemClick.getPosition(position,holder.editText);

                // display a toast with person name on item click
            }
        });
        holder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseImage firebaseImage = new FirebaseImage();
                String filepath = holder.editText.getText().toString().trim();
                Uri uri = Uri.parse(filepath);
                DocumentFile sourceFile = DocumentFile.fromSingleUri(context, uri);
                boolean bool = sourceFile.exists();
                Toast.makeText(context, bool + " is here", Toast.LENGTH_LONG).show();
                holder.progressBar.setVisibility(View.VISIBLE);
                firebaseImage.uploadImage(uri,uid,require_detail,context);
                firebaseImage.setCallBackForUploadImage(new CallBack() {
                    @Override
                    public String setStringData(String data) {
                        holder.progressBar.setVisibility(View.GONE);
                        if(data=="true"){
                            holder.dStatus.setHint("successfully upload");
                            holder.dStatus.setVisibility(View.VISIBLE);
                            holder.edit.setVisibility(View.VISIBLE);
                            holder.remove.setVisibility(View.VISIBLE);
                            holder.download.setVisibility(View.VISIBLE);
                            holder.editText.setVisibility(View.GONE);
                            holder.upload.setVisibility(View.GONE);
                            Picasso.get().load(uri).into(holder.imageView);

                        }else{
                            holder.dStatus.setHint("something went wrong ! try later");
                            holder.dStatus.setVisibility(View.VISIBLE);
                            holder.edit.setVisibility(View.VISIBLE);
                            holder.remove.setVisibility(View.VISIBLE);
                            holder.download.setVisibility(View.VISIBLE);
                            holder.editText.setVisibility(View.GONE);
                            holder.upload.setVisibility(View.GONE);
                        }
                        return null;
                    }
                });
                holder.progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.totalKey.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,dStatus;
        Button edit,remove,download,upload;
        ProgressBar progressBar;
        ImageView imageView;
        EditText editText;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            textView = (TextView) itemView.findViewById(R.id.id_name);
            dStatus = (TextView) itemView.findViewById(R.id.textView);
            edit = (Button) itemView.findViewById(R.id.edit);
            remove = (Button) itemView.findViewById(R.id.remove);
            upload = (Button) itemView.findViewById(R.id.upload);
            download = (Button) itemView.findViewById(R.id.download);
            editText = (EditText) itemView.findViewById(R.id.editText);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}

