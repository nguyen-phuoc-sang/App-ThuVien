package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.loaisachDAO;
import com.example.duanmau.Model.itemclick;
import com.example.duanmau.Model.loaisach;
import com.example.duanmau.R;

import java.util.ArrayList;

public class loaisachadapter extends RecyclerView.Adapter<loaisachadapter.ViewHolder>{

    private Context context;
    private ArrayList<loaisach> list;
    private itemclick itemclick;

    public loaisachadapter(Context context, ArrayList<loaisach> list, itemclick itemclick) {
        this.context = context;
        this.list = list;
        this.itemclick = itemclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tenloai.setText("Tên loại sách: "+list.get(position).getTenloai());
        holder.maloai.setText("Mã loại sách: "+String.valueOf(list.get(position).getId()));

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaisachDAO loaisachDAO = new loaisachDAO(context);
                int check = loaisachDAO.xoaloai(list.get(holder.getAdapterPosition()).getId());
                switch (check){
                    case 1:
                        list.clear();
                        list = loaisachDAO.getdsloaisach();
                        notifyDataSetChanged();
                        break;
                    case -1:
                        Toast.makeText(context, "Loại sách này đã có", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        holder.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaisach loaisach = list.get(holder.getAdapterPosition());
                itemclick.onClick(loaisach);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView maloai, tenloai;
        ImageView iv, iv1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maloai = itemView.findViewById(R.id.maloai);
            tenloai = itemView.findViewById(R.id.tenloai);
            iv = itemView.findViewById(R.id.xoa);
            iv1 = itemView.findViewById(R.id.sua);
        }
    }
}
