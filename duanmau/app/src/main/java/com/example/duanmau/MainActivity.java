package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.duanmau.DAO.sachDAO;
import com.example.duanmau.DAO.thuthuDAO;
import com.example.duanmau.Fragment.fragdoanhthu;
import com.example.duanmau.Fragment.fragloaisach;
import com.example.duanmau.Fragment.fragphieumuon;
import com.example.duanmau.Fragment.fragsach;
import com.example.duanmau.Fragment.fragthanhvien;
import com.example.duanmau.Fragment.fragthongketop10;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sachDAO sachDAO = new sachDAO(this);
        sachDAO.getsach();

        Toolbar toolbar = findViewById(R.id.too);
        FrameLayout frameLayout = findViewById(R.id.frame);
        NavigationView navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.dra);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch ( item.getItemId()){
                    case R.id.phieu:
                        fragment = new fragphieumuon();
                        break;
                    case R.id.loai:
                        fragment = new fragloaisach();
                        break;
                    case R.id.out:
                        Intent intent = new Intent(MainActivity.this, dangnhap.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    case R.id.doi:
                        showdoimk();
                        break;
                    case R.id.topsach:
                        fragment = new fragthongketop10();
                        break;
                    case R.id.doanhthu:
                        fragment = new fragdoanhthu();
                        break;
                    case R.id.thanhvien:
                        fragment = new fragthanhvien();
                        break;
                    case R.id.sach:
                        fragment = new fragsach();
                        break;
                    default:
                        fragment = new fragphieumuon();
                        break;
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showdoimk(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setNegativeButton("Cập nhập", null).setPositiveButton("hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.doimatkhau, null);

        EditText etmkcu = view.findViewById(R.id.mkcu);
        EditText etmkmoi = view.findViewById(R.id.mkmoi);
        EditText etxn = view.findViewById(R.id.xacnhan);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu = etmkcu.getText().toString();
                String mkmoi = etmkmoi.getText().toString();
                String xn = etxn.getText().toString();
                if (mkcu.equals("") || mkmoi.equals("") || xn.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (mkmoi.equals(xn)){
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt", "");
                        thuthuDAO thuthuDAO = new thuthuDAO(MainActivity.this);
                        int check = thuthuDAO.doimk(matt, mkcu, mkmoi);
                        if (check ==1 ){
                            Toast.makeText(MainActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, dangnhap.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else if (check == 0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Xác nhận mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}