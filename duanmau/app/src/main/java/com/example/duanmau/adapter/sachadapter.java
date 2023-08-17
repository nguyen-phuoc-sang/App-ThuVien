package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.sachDAO;
import com.example.duanmau.Model.sach;
import com.example.duanmau.Model.thanhvien;
import com.example.duanmau.R;

import java.util.ArrayList;
import java.util.HashMap;

public class sachadapter extends RecyclerView.Adapter<sachadapter.ViewHolder>{

    private Context context;
    private ArrayList<sach> list;
    private ArrayList<HashMap<String, Object>> listhm;
    private sachDAO sachDAO;

    public sachadapter(Context context, ArrayList<sach> list, ArrayList<HashMap<String,Object>> listhm , sachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listhm = listhm;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.itemsach, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.masach.setText("Mã sách: " + list.get(position).getMasach());
        holder.tensach.setText("Tên sách: " + list.get(position).getTensach());
        holder.giathue.setText("Giá thuê: " + list.get(position).getGiathue());
        holder.maloai.setText("Mã loại sách: " + list.get(position).getMaloai());
        holder.tenloai.setText("Tên loại sách: " + list.get(position).getTenloai());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suasach(list.get(holder.getAdapterPosition()));
            }
        });

        holder.dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDAO.xoasach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        load();
                        break;
                    case -1:
                        Toast.makeText(context, "Sách vẫn còn trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView masach, tensach, giathue, maloai, tenloai;
        ImageView edit, dele;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            masach = itemView.findViewById(R.id.masach);
            tensach = itemView.findViewById(R.id.tensach);
            giathue = itemView.findViewById(R.id.giathue);
            maloai = itemView.findViewById(R.id.maloai);
            tenloai = itemView.findViewById(R.id.tenloai);
            edit = itemView.findViewById(R.id.suasach);
            dele = itemView.findViewById(R.id.xoasach);

        }
    }

    private void suasach(sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.nutsuasach, null);
        builder.setView(view);

        EditText ten = view.findViewById(R.id.tensach);
        EditText gia = view.findViewById(R.id.tienthue);
        Spinner spinner = view.findViewById(R.id.spsuasach);
        TextView ma = view.findViewById(R.id.masach);


        ma.setText("Mã sách: " + sach.getMasach());
        ten.setText(sach.getTensach());
        gia.setText(String.valueOf(sach.getGiathue()));
        SimpleAdapter simpleAdapter = new SimpleAdapter(context,listhm, android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spinner.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;
        for (HashMap<String, Object> item : listhm){
            if ((int)item.get("maloai") == sach.getMaloai()){
                position = index;
            }
            index++;
        }
        spinner.setSelection(position);

        builder.setNegativeButton("Cập nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach =  ten.getText().toString();
                int tien = Integer.parseInt(gia.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spinner.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                boolean check = sachDAO.suasach(sach.getMasach(),tensach, tien, maloai);
                if (check){
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    load();
                }else {
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void load(){
        list.clear();
        list = sachDAO.getsach();
        notifyDataSetChanged();
    }
}
