package com.example.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;

public class dbhelper extends SQLiteOpenHelper {

    public dbhelper (Context context){
        super(context, "DANGKYMONHOC", null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbthuthu = "CREATE TABLE THUTHU(matt text primary key, hoten text, matkhau text)";
        sqLiteDatabase.execSQL(dbthuthu);

        String dbthanhvien = "CREATE TABLE THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
        sqLiteDatabase.execSQL(dbthanhvien);

        String dbloaisach = "CREATE TABLE LOAI(maloai integer primary key autoincrement, tenloai text)";
        sqLiteDatabase.execSQL(dbloaisach);

        String dbsach = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAI(maloai))";
        sqLiteDatabase.execSQL(dbsach);

        String dbphieumuon = "CREATE TABLE PHIEUMUON(maphieumuon integer primary key autoincrement, matv integer references THANHVIEN(matv),matt text references " + "THUTHU(matt),masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        sqLiteDatabase.execSQL(dbphieumuon);

        //data mẫu
        sqLiteDatabase.execSQL("INSERT INTO LOAI VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        sqLiteDatabase.execSQL("INSERT INTO SACH VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO THUTHU VALUES ('thuthu01','Nguyễn Văn Anh','abc123'),('thuthu02','Trần Văn Hùng','123abc')");
        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN VALUES (1,'Cao Thu Trang','2000'),(2,'Trần Mỹ Kim','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        sqLiteDatabase.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01', 1, '19/03/2022', 1, 2500),(2,1,'thuthu01', 3, '19/03/2022', 0, 2000),(3,2,'thuthu02', 1, '19/03/2022', 1, 2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if ( i != i1 ){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAI");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(sqLiteDatabase);
        }
    }
}
