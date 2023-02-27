package com.savita.navigate.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.savita.login.ExamList;
import com.savita.login.R;
import com.savita.login.databinding.FragmentHomeBinding;
import com.savita.simplefunction.ConstantVar;

public class HomeFragment extends Fragment {
      View view;
      Button exam,id;;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        exam = (Button) view.findViewById(R.id.simpleButton1);
        id = (Button) view.findViewById(R.id.simpleButton2);
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ExamList.class);
                intent.putExtra(ConstantVar.applyType,ConstantVar.id);
                startActivity(intent);
                Toast.makeText(getActivity(), "Second Fragment", Toast.LENGTH_LONG).show();
            }
        });
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ExamList.class);
                intent.putExtra(ConstantVar.applyType,ConstantVar.examsApply);
                startActivity(intent);
                Toast.makeText(getActivity(), "Second Fragment", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}