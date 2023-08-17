package com.example.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.DAO.sachDAO;
import com.example.duanmau.R;

import java.util.Calendar;

public class fragdoanhthu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_doanhthu, container, false);

        EditText batdau = view.findViewById(R.id.batdau);
        EditText ketthuc= view.findViewById(R.id.ketthuc);
        Button hien = view.findViewById(R.id.nutthongke);
        TextView ketqua = view.findViewById(R.id.ketqua);

        Calendar calendar = Calendar.getInstance();

        batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String ngay = "" ;
                        String thang = "";
                        if(day < 10){
                            ngay = "0" + day;
                        }else {
                            ngay = String.valueOf(day);
                        }

                        if ((month + 1) < 10){
                            thang = "0" + (month + 1);
                        }else {
                            thang = String.valueOf((month + 1));
                        }
                        batdau.setText(year+"/"+thang+"/"+ngay);
                    }
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        ketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String ngay = "" ;
                        String thang = "";
                        if(day < 10){
                            ngay = "0" + day;
                        }else {
                            ngay = String.valueOf(day);
                        }

                        if ((month + 1) < 10){
                            thang = "0" + (month + 1);
                        }else {
                            thang = String.valueOf((month + 1));
                        }
                        ketthuc.setText(year+"/"+thang+"/"+ngay);
                    }
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        hien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachDAO sachDAO = new sachDAO(getContext());
                String start = batdau.getText().toString();
                String end = ketthuc.getText().toString();
                int doanhthu = sachDAO.getdoanhthu(start, end);
                ketqua.setText("Doanh thu: " + doanhthu + "VND");
            }
        });

        return view;
    }
}
