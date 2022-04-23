package com.savita.navigate;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
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

    public StatusAdapter(Context context, ArrayList details, String uid) {
        this.context = context;
        this.details = details;
        this.uid = uid;
    }

    @NonNull
    @Override
    public StatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_status, parent, false);
        StatusAdapter.MyViewHolder vh = new StatusAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String require_detail = (String) details.get(position);
        //   Toast.makeText(context, require_detail + " is here", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView exame_name,status;
        ProgressBar progressBar;


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            exame_name = (TextView) itemView.findViewById(R.id.exam_name);
            status = (TextView) itemView.findViewById(R.id.status);
            progressBar = (ProgressBar) itemView.findViewById(R.id.determinateBar);
        }
    }
}
