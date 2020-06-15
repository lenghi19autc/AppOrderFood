package com.th3h.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.DAO.LoaiMonAnDAO;

public class SuaLoaiMonAnActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDongYSua;
    private EditText edtSuaTenLoai;
    private LoaiMonAnDAO loaiMonAnDAO;
    private  int maLoai;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sualoaimonan);

        btnDongYSua = findViewById(R.id.btnDongYSuaTenLoaiMonAn);
        edtSuaTenLoai = findViewById(R.id.edtSuaTenLoaiMonAn);
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        maLoai = getIntent().getIntExtra("maloai",0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String tenLoai = edtSuaTenLoai.getText().toString();
        if (!tenLoai.trim().equals("") || tenLoai.trim() != null){
            boolean check = loaiMonAnDAO.CapNhatTenLoaiMonAn(maLoai, tenLoai);
            Intent intent = new Intent();
            intent.putExtra("check",check);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu),Toast.LENGTH_SHORT).show();
        }
    }

}
