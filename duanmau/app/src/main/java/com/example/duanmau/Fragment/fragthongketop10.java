package com.example.duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.sachDAO;
import com.example.duanmau.Model.sach;
import com.example.duanmau.R;
import com.example.duanmau.adapter.top10Adapter;

import java.util.ArrayList;

public class fragthongketop10 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongketop10, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.top10);
        sachDAO thongke = new sachDAO(getContext());
        ArrayList<sach> list = thongke.gettop10();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        top10Adapter adapter = new top10Adapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
