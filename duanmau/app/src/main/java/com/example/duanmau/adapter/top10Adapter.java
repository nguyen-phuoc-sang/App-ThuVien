package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Model.sach;
import com.example.duanmau.R;

import java.util.ArrayList;

public class top10Adapter extends RecyclerView.Adapter<top10Adapter.ViewHolder>{

    private Context context;
    private ArrayList <sach> list;

    public top10Adapter(Context context, ArrayList<sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ma.setText("Mã sách: " + String.valueOf(list.get(position).getMasach()));
        holder.ten.setText("Tên sách: " +list.get(position).getTensach());
        holder.soluong.setText("Số lượng: "+ String.valueOf(list.get(position).getSoluong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ma, ten, soluong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.topma);
            ten = itemView.findViewById(R.id.topten);
            soluong = itemView.findViewById(R.id.soluong);
        }
    }
}
