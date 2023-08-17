package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.phieumuonDAO;
import com.example.duanmau.Model.phieumuon;
import com.example.duanmau.R;

import java.util.ArrayList;

public class phieumuonadapter extends RecyclerView.Adapter<phieumuonadapter.ViewHolder>{

    private ArrayList<phieumuon> list;
    private Context context;

    public phieumuonadapter(ArrayList<phieumuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    // quản lí layout từng item
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_re_phieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.maphieu.setText("Mã Phiếu Mượn: " + list.get(position).getMaphieu());
        holder.matv.setText("Mã thành viên: " + list.get(position).getMatv());
        holder.tentv.setText("Tên thành viên: " + list.get(position).getTentv());
        holder.matt.setText("Mã thủ thư: " + list.get(position).getMatt());
        holder.tentt.setText("Tên thủ thư: " + list.get(position).getTentt());
        holder.masach.setText("Mã sách: " + list.get(position).getMasach());
        holder.tensach.setText("Tên sách: " + list.get(position).getTensach());
        holder.ngay.setText("Ngày: " + list.get(position).getNgay());
        String trangthai = "";
        if (list.get(position).getTrasach() == 1){
            trangthai = "Đã trả sách";
            holder.bt.setVisibility(View.GONE);
        }else {
            trangthai = "Chưa trả sách";
            holder.bt.setVisibility(View.VISIBLE);
        }
        holder.trangthai.setText("Trạng thái: " + trangthai);
        holder.tien.setText("Tiền thuê sách: " + list.get(position).getTienthue());

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieumuonDAO phieumuonDAO = new phieumuonDAO(context);
                boolean kiemtra = phieumuonDAO.thaytrangthai(list.get(holder.getAdapterPosition()).getMaphieu());
                if (kiemtra){
                    list.clear();
                    list = phieumuonDAO.getdsphieu();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView maphieu, matv, tentv, matt, tentt, masach, tensach, ngay, trangthai, tien;
        Button bt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maphieu = itemView.findViewById(R.id.maphieu);
            matv = itemView.findViewById(R.id.matv);
            tentv = itemView.findViewById(R.id.tentv);
            matt = itemView.findViewById(R.id.matt);
            tentt = itemView.findViewById(R.id.tentt);
            masach = itemView.findViewById(R.id.masach);
            tensach = itemView.findViewById(R.id.tensach);
            ngay = itemView.findViewById(R.id.ngay);
            trangthai = itemView.findViewById(R.id.trangthai);
            tien = itemView.findViewById(R.id.tien);
            bt = itemView.findViewById(R.id.nut);
        }
    }
}
