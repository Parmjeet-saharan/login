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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.login.DetailAdapter;
import com.savita.login.R;
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

            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.progressBar.setVisibility(View.GONE);

            }
        });

        // implement setOnClickListener event on item view.
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click

            }
        });

    }

    @Override
    public int getItemCount() {
        return details.totalKey.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button edit,remove,download;
        ProgressBar progressBar;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            textView = (TextView) itemView.findViewById(R.id.id_name);
            edit = (Button) itemView.findViewById(R.id.edit);
            remove = (Button) itemView.findViewById(R.id.remove);
            download = (Button) itemView.findViewById(R.id.download);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}

