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
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
    private Context context;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn details = new SomeFunction.dataReturn(totalList,totalKey);
    private String uid;

    public StatusAdapter(Context context, SomeFunction.dataReturn details, String uid) {
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
        String require_detail = (String) details.totalKey.get(position);
        //   Toast.makeText(context, require_detail + " is here", Toast.LENGTH_LONG).show();
        holder.exame_name.setHint(require_detail);
        SomeFunction someFunction = new SomeFunction();
        SomeFunction.dataReturn twoList = someFunction.stringToList((details.totalList.get(position).get(require_detail)));
        holder.status.setHint((twoList.totalList.get(0).get((twoList.totalKey.get(0)))));
    }

    @Override
    public int getItemCount() {
        return details.totalKey.size();
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
