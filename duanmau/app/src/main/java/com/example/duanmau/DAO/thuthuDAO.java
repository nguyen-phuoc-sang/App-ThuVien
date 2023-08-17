package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Database.dbhelper;

public class thuthuDAO {
    dbhelper dbhelper;
    public thuthuDAO (Context context){
        dbhelper = new dbhelper(context);
    }

    // dăng nhập
    public boolean checkdangnhap(String matt, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{matt, matkhau});
        if(cursor.getCount() !=0){
            return true;
        }else {
            return false;
        }
    }

    public int doimk(String user, String mkcu, String mkmoi){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{user, mkcu});
        if (cursor.getCount() > 0 ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", mkmoi);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{user});
            if (check == -1){
                return -1;
            }
            return 1;
        }
        return 0;
    }
}
