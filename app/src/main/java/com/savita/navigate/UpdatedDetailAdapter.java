package com.savita.navigate;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.savita.firebase.FirebaseImage;
import com.savita.login.DetailAdapter;
import com.savita.login.R;
import com.savita.simplefunction.SomeFunction;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdatedDetailAdapter extends RecyclerView.Adapter<UpdatedDetailAdapter.MyViewHolder> {
    private Context context;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn details = new SomeFunction.dataReturn(totalList,totalKey);
    private String uid;
    DetailAdapter.OnItemClick onItemClick;

    public void setOnItemClick(DetailAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int data, EditText editText); //pass any things

    }

    private static final int PICK_IMAGE_REQUEST = 22;

    public UpdatedDetailAdapter(Context context, SomeFunction.dataReturn details, String uid) {
        this.context = context;
        this.details = details;
        this.uid = uid;
    }

    @NonNull
    @Override
    public UpdatedDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_detail, parent, false);
        UpdatedDetailAdapter.MyViewHolder vh = new UpdatedDetailAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull UpdatedDetailAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String require_detail = (String) details.totalKey.get(position);
        //   Toast.makeText(context, require_detail + " is here", Toast.LENGTH_LONG).show();
        holder.dName.setHint(require_detail);
        holder.dvalue.setHint((details.totalList.get(position).get(require_detail)));
        holder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((holder.editText.getText().toString())==(holder.dvalue.getText().toString())){

                }else if((holder.editText.getText().toString())==""){

                }else {

                }
                holder.progressBar.setVisibility(View.GONE);

            }
        });

        // implement setOnClickListener event on item view.
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                holder.dvalue.setVisibility(View.GONE);
                holder.editText.setVisibility(View.VISIBLE);
                holder.upload.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.totalKey.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText editText;// init the item view's
        TextView dName, dvalue;
        Button edit,upload;
        ProgressBar progressBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            editText = (EditText) itemView.findViewById(R.id.detail);
            dName = (TextView) itemView.findViewById(R.id.id_name);
            dvalue = (TextView) itemView.findViewById(R.id.value);
            edit = (Button) itemView.findViewById(R.id.edit);
            upload = (Button) itemView.findViewById(R.id.upload);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
