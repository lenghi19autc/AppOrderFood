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

import com.th3h.apporderfood.DAO.MonAnDAO;

public class SuaMonAnActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDongYSua;
    private EditText edtSuaTenMonAn;
    private EditText edtGiaTien;
    private MonAnDAO monAnDAO;
    private  int maMonAn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suamonan);

        btnDongYSua = findViewById(R.id.btnDongYSuaTenMonAn);
        edtSuaTenMonAn = findViewById(R.id.edtSuaTenMonAn);
        edtGiaTien = findViewById(R.id.edtSuaGiaMonAn);
       monAnDAO = new MonAnDAO(this);
       maMonAn = getIntent().getIntExtra("mamonan",0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String tenMonAn = edtSuaTenMonAn.getText().toString();
        String giaTien = edtGiaTien.getText().toString();
        if ((!tenMonAn.trim().equals("") || tenMonAn.trim() != null)
                && (!giaTien.trim().equals("") || giaTien.trim() != null)){
            boolean check = monAnDAO.CapNhatTenMonAnGiaTien(maMonAn, tenMonAn, giaTien);
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
