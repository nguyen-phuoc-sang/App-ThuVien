package com.example.duanmau.Fragment;

import android.companion.WifiDeviceFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.loaisachDAO;
import com.example.duanmau.Model.itemclick;
import com.example.duanmau.Model.loaisach;
import com.example.duanmau.R;
import com.example.duanmau.adapter.loaisachadapter;

import java.util.ArrayList;

public class fragloaisach extends Fragment {

    RecyclerView recyclerView;
    loaisachDAO loaisachDAO;
    EditText edt;
    int maloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_loai, container, false);

        recyclerView = view.findViewById(R.id.reloaisach);
        edt = view.findViewById(R.id.themloaisach);
        Button bt = view.findViewById(R.id.nutthemloai);
        Button bt2 = view.findViewById(R.id.nutsualoai);

        loaisachDAO = new loaisachDAO(getContext());

        load();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edt.getText().toString();

                if (tenloai.equals("")){
                    Toast.makeText(getContext(), "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (loaisachDAO.themloaisach(tenloai)){
                        load();
                        edt.setText("");
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edt.getText().toString();
                loaisach loaisach = new loaisach(maloai, tenloai);
                if (loaisachDAO.sualoai(loaisach)){
                    load();
                    edt.setText("");
                }else {
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void load(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<loaisach> list = loaisachDAO.getdsloaisach();
        loaisachadapter loaisachadapter = new loaisachadapter(getContext(), list, new itemclick() {
            @Override
            public void onClick(loaisach loaisach) {
                edt.setText(loaisach.getTenloai());
                maloai = loaisach.getId();
            }
        });
        recyclerView.setAdapter(loaisachadapter);
    }
}
