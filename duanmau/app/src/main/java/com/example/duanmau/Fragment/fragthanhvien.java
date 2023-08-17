package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.thanhvienDAO;
import com.example.duanmau.Model.thanhvien;
import com.example.duanmau.R;
import com.example.duanmau.adapter.thanhvienadapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class fragthanhvien extends Fragment {

    RecyclerView recyclerView;
    thanhvienDAO thanhvienDAO;
    ArrayList<thanhvien> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragthanhvien, container,false);

        recyclerView = view.findViewById(R.id.recithanhvien);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatthemtv);

        thanhvienDAO = new thanhvienDAO(getContext());
        load();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.nutthemtv, null);
        builder.setView(view);

        EditText hoten = view.findViewById(R.id.tentv);
        EditText namsinh = view.findViewById(R.id.namsinh);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tentv =  hoten.getText().toString();
                String nam = namsinh.getText().toString();

                if (tentv.equals("") || nam.equals("")){
                    Toast.makeText(getContext(), "nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = thanhvienDAO.themthanhvien(tentv, nam);
                    if (check){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        load();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Hủy thao tác", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void load(){
        list = thanhvienDAO.getdsthanhvien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        thanhvienadapter thanhvienadapter = new thanhvienadapter(getContext(), list, thanhvienDAO);
        recyclerView.setAdapter(thanhvienadapter);
    }
}
