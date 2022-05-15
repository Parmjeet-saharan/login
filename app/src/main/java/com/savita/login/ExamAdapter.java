package com.savita.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.simplefunction.ConstantVar;

import java.util.ArrayList;
import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.MyViewHolder> {
    private Context context;
    private List exams  ;

    public ExamAdapter(Context context, List exams) {
        this.context = context;
        this.exams = exams;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam, parent, false);
        MyViewHolder  vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String exam_name = (String) exams.get(position);
        holder.examName.setText(exam_name);
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent intent = new Intent(context,RequireDetail.class);
                intent.putExtra(ConstantVar.examName,exam_name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return exams.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView examName;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            examName = (TextView) itemView.findViewById(R.id.exam_name);
        }
    }
}
