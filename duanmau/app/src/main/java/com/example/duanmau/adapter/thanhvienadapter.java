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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.loaisachDAO;
import com.example.duanmau.DAO.thanhvienDAO;
import com.example.duanmau.Model.loaisach;
import com.example.duanmau.Model.thanhvien;
import com.example.duanmau.R;

import java.util.ArrayList;

public class thanhvienadapter extends RecyclerView.Adapter<thanhvienadapter.ViewHolder>{

    private Context context;
    private  ArrayList<thanhvien> list;
    private thanhvienDAO thanhvienDAO;

    public thanhvienadapter(Context context, ArrayList<thanhvien> list, thanhvienDAO thanhvienDAO) {
        this.context = context;
        this.list = list;
        this.thanhvienDAO = thanhvienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.matv.setText("Mã thành viên: " + list.get(position).getMatv());
        holder.tentv.setText("Tên thành viên: " + list.get(position).getTentv());
        holder.namsinh.setText("Năm sinh: " + list.get(position).getNamsinh());

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sua(list.get(holder.getAdapterPosition()));
            }
        });

        holder.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = thanhvienDAO.xoathanhvien(list.get(holder.getAdapterPosition()).getMatv());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        load();
                        break;
                    case -1:
                        Toast.makeText(context, "Thành viên vẫn còn phiếu mượn", Toast.LENGTH_SHORT).show();
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
        TextView matv, tentv, namsinh;
        ImageView iv, iv1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            matv = itemView.findViewById(R.id.matv);
            tentv = itemView.findViewById(R.id.tentv);
            namsinh = itemView.findViewById(R.id.namsinh);
            iv = itemView.findViewById(R.id.suatv);
            iv1 = itemView.findViewById(R.id.xoatv);

        }
    }

    private void sua(thanhvien thanhvien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.nutsuatv, null);
        builder.setView(view);

        EditText edthoten = view.findViewById(R.id.tentv);
        EditText edtnamsinh = view.findViewById(R.id.namsinh);
        TextView tvmatv = view.findViewById(R.id.matv);

        edthoten.setText(thanhvien.getTentv());
        edtnamsinh.setText(thanhvien.getNamsinh());
        tvmatv.setText("Mã thành viên: "+thanhvien.getMatv());

        builder.setNegativeButton("Cập nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tentv =  edthoten.getText().toString();
                String nam = edtnamsinh.getText().toString();
                int matv = thanhvien.getMatv();

                boolean check = thanhvienDAO.suathanhvien(matv, tentv, nam);
                if (check){
                    Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    load();
                }else {
                    Toast.makeText(context, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
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
        list = thanhvienDAO.getdsthanhvien();
        notifyDataSetChanged();
    }
}
