package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Database.dbhelper;
import com.example.duanmau.Model.phieumuon;

import java.util.ArrayList;

public class phieumuonDAO {
    dbhelper dbhelper;
    public phieumuonDAO (Context context){
        dbhelper = new dbhelper(context);
    }

    public ArrayList <phieumuon> getdsphieu(){
        ArrayList <phieumuon> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.maphieumuon, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.maphieumuon DESC", null);
        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new phieumuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean thaytrangthai (int maphieumuon){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach",1);
        long check = sqLiteDatabase.update("PHIEUMUON", contentValues, "maphieumuon = ?", new String[]{String.valueOf(maphieumuon)});
        if (check == -1){
            return false;
        }
        return true;
    }

    public boolean themphieumuon(phieumuon phieumuon){
       SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
        contentValues.put("matv", phieumuon.getMatv());
        contentValues.put("matt", phieumuon.getMatt());
        contentValues.put("masach", phieumuon.getMasach());
        contentValues.put("ngay", phieumuon.getNgay());
        contentValues.put("trasach", phieumuon.getTrasach());
        contentValues.put("tienthue", phieumuon.getTienthue());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }
}
