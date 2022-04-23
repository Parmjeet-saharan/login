package com.savita.navigate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.login.DetailAdapter;
import com.savita.login.R;

import java.util.ArrayList;

public class UploadedDocumentAdapter extends RecyclerView.Adapter<UploadedDocumentAdapter.MyViewHolder> {
    private Context context;
    private ArrayList details;
    private String uid;
    DetailAdapter.OnItemClick onItemClick;

    public void setOnItemClick(DetailAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int data, EditText editText); //pass any things

    }

    private static final int PICK_IMAGE_REQUEST = 22;

    public UploadedDocumentAdapter(Context context, ArrayList details, String uid) {
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
        String require_detail = (String) details.get(position);
        //   Toast.makeText(context, require_detail + " is here", Toast.LENGTH_LONG).show();
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
        return details.size();
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

