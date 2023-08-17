package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.example.duanmau.DAO.phieumuonDAO;
import com.example.duanmau.DAO.sachDAO;
import com.example.duanmau.DAO.thanhvienDAO;
import com.example.duanmau.Model.phieumuon;
import com.example.duanmau.Model.sach;
import com.example.duanmau.Model.thanhvien;
import com.example.duanmau.R;
import com.example.duanmau.adapter.phieumuonadapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class fragphieumuon extends Fragment {
    phieumuonDAO phieumuonDAO;
    RecyclerView recyclerView;
    ArrayList<phieumuon> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_phieu, container, false);
        recyclerView = view.findViewById(R.id.reciphieu);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatadd);

        load();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    private void load(){
        phieumuonDAO = new phieumuonDAO(getContext());
        list = phieumuonDAO.getdsphieu();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        phieumuonadapter phieumuonadapter = new phieumuonadapter(list, getContext());
        recyclerView.setAdapter(phieumuonadapter);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.nutthem, null);
        Spinner sptv = view.findViewById(R.id.spinten);
        Spinner spsach = view.findViewById(R.id.spinsach);
        getdatathanhvien(sptv);
        getdatasach(spsach);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //lấy mã tv
                HashMap<String, Object> hstv = (HashMap<String, Object>) sptv.getSelectedItem();
                int matv = (int) hstv.get("matv");

                // lấy mã sách
                HashMap<String, Object> hssach = (HashMap<String, Object>) spsach.getSelectedItem();
                int masach = (int) hssach.get("masach");

                int tien = (int) hssach.get("giathue");

                themphieumuon(matv, masach,tien);

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Hủy thao tác", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getdatathanhvien(Spinner spintv){
        thanhvienDAO thanhvienDAO = new thanhvienDAO(getContext());
        ArrayList <thanhvien> list = thanhvienDAO.getdsthanhvien();

        ArrayList<HashMap<String , Object>> listhm = new ArrayList<>();
        for (thanhvien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getTentv());
            listhm.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listhm, android.R.layout.simple_list_item_1, new String[]{"hoten"}, new int[]{android.R.id.text1});
        spintv.setAdapter(simpleAdapter);
    }

    private void getdatasach(Spinner spsach){
        sachDAO sachDAO = new sachDAO(getContext());
        ArrayList <sach> list = sachDAO.getsach();

        ArrayList<HashMap<String , Object>> listhm = new ArrayList<>();
        for (sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach() );
            hs.put("tensach", sc.getTensach());
            hs.put("giathue", sc.getGiathue());
            listhm.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listhm, android.R.layout.simple_list_item_1, new String[]{"tensach"}, new int[]{android.R.id.text1});
        spsach.setAdapter(simpleAdapter);
    }

    private void  themphieumuon(int matv, int masach, int tien){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        phieumuon phieumuon = new phieumuon(matv, matt, masach, ngay, 0, tien);
        boolean kiemtra = phieumuonDAO.themphieumuon(phieumuon);
        if (kiemtra){
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            load();
        }else {
            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}





















