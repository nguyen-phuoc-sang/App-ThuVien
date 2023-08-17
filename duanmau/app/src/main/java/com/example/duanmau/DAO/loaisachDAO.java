package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Database.dbhelper;
import com.example.duanmau.Model.loaisach;

import java.util.ArrayList;

public class loaisachDAO {
    dbhelper dbhelper;
    public loaisachDAO(Context context){
        dbhelper = new dbhelper(context);
    }

    public ArrayList<loaisach> getdsloaisach(){
        ArrayList <loaisach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAI", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new loaisach(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }


        // thêm
    public boolean themloaisach(String tenloai){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = sqLiteDatabase.insert("LOAI", null, contentValues);
        if (check == -1)
            return false;
        return true;
    }

    // xóa
    public int xoaloai(int id){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = sqLiteDatabase.delete("LOAI", "maloai = ?", new String[]{String.valueOf(id)});
        if (check == -1)
            return 0;
        return 1;
    }

    // sửa
    public boolean sualoai (loaisach loaisach){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaisach.getTenloai());
        long check = sqLiteDatabase.update("LOAI", contentValues, "maloai = ?", new String[]{String.valueOf(loaisach.getId())});
        if (check == -1)
            return false;
        return true;
    }
}
