package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.loaisachDAO;
import com.example.duanmau.DAO.sachDAO;
import com.example.duanmau.DAO.thanhvienDAO;
import com.example.duanmau.Model.loaisach;
import com.example.duanmau.Model.sach;
import com.example.duanmau.R;
import com.example.duanmau.adapter.sachadapter;
import com.example.duanmau.adapter.thanhvienadapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class fragsach extends Fragment {
    RecyclerView recyclerView;
    sachDAO sachDAO;
    ArrayList<sach> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragsach, container,false);

        recyclerView = view.findViewById(R.id.recisach);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatthemsach);

        sachDAO = new sachDAO(getContext());
        load();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    private void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.nutthemsach, null);
        builder.setView(view);
        EditText tensach = view.findViewById(R.id.tensach);
        EditText tienthue = view.findViewById(R.id.tienthue);
        Spinner spinner = view.findViewById(R.id.spsach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),getdsloaisach(), android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spinner.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten =  tensach.getText().toString();
                int tien = Integer.parseInt(tienthue.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spinner.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                if (ten.equals("")){
                    Toast.makeText(getContext(), "Nhập tên sách", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = sachDAO.themsach(ten, tien, maloai);
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

    private ArrayList<HashMap<String , Object>> getdsloaisach(){
        loaisachDAO loaisachDAO = new loaisachDAO(getContext());
        ArrayList<loaisach> list = loaisachDAO.getdsloaisach();
        ArrayList<HashMap<String, Object>> listhm = new ArrayList<>();

        for (loaisach loaisach : list){
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("maloai", loaisach.getId());
            hm.put("tenloai", loaisach.getTenloai());
            listhm.add(hm);
        }

        return listhm;
    }

    private void load(){
        list = sachDAO.getsach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        sachadapter sachadapter = new sachadapter(getContext(), list, getdsloaisach(), sachDAO);
        recyclerView.setAdapter(sachadapter);
    }
}
