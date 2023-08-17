package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.duanmau.DAO.thuthuDAO;

public class dangnhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        EditText etuser = findViewById(R.id.user);
        EditText et2pass = findViewById(R.id.pass);
        Button bt = findViewById(R.id.nut);

        thuthuDAO thuthuDAO = new thuthuDAO(this);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etuser.getText().toString();
                String pass = et2pass.getText().toString();

                if(thuthuDAO.checkdangnhap(user, pass)){
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt", user);
                    editor.commit();
                    startActivity(new Intent(dangnhap.this, MainActivity.class));
                }else {
                    Toast.makeText(dangnhap.this, "username or mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}