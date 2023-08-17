package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Database.dbhelper;
import com.example.duanmau.Model.sach;

import java.util.ArrayList;

public class sachDAO {
    dbhelper dbhelper;
    public sachDAO(Context context){
        dbhelper = new dbhelper(context);
    }

    // lấy toàn bộ sách trong thư viện
    public ArrayList <sach> getsach(){
        ArrayList <sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, lo.tenloai FROM SACH sc, LOAI lo WHERE sc.maloai = lo.maloai", null);
        if ( cursor.getCount() !=0){
            cursor.moveToFirst();
            do{
                list.add(new sach(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<sach> gettop10(){
        ArrayList<sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) FROM PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10", null);
        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getdoanhthu(String batdau, String ketthuc){
        batdau = batdau.replace("/", "");
        ketthuc = ketthuc.replace("/", "");
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON WHERE substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) between ? AND ?", new String[]{batdau, ketthuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public boolean themsach(String tensach, int tienthue, int maloai){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", tienthue);
        contentValues.put("maloai", maloai);
        long check = sqLiteDatabase.insert("SACH", null, contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }


    public boolean suasach (int masach, String tensach, int tienthue, int maloai){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", tienthue);
        contentValues.put("maloai", maloai);

        long check = sqLiteDatabase.update("SACH", contentValues, "masach = ?", new String[]{String.valueOf(masach)});
        if (check == -1)
            return false;
        return true;
    }

    public int xoasach(int masach){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = sqLiteDatabase.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
        if (check == -1)
            return 0;
        return 1;
    }

}
