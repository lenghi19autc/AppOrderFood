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

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnDongYThemLoaiThucDon;
    public EditText edtThenLoaiThucDon;
    public LoaiMonAnDAO loaiMonAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themloaithucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        btnDongYThemLoaiThucDon = findViewById(R.id.btnDongYThemLoaiThucDon);
        edtThenLoaiThucDon = findViewById(R.id.edtThemLoaiThucDon);

        btnDongYThemLoaiThucDon.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
       String sTenLoaiThucDon = edtThenLoaiThucDon.getText().toString();

       if (sTenLoaiThucDon != null && !sTenLoaiThucDon.equals("")){

           boolean check = loaiMonAnDAO.ThemLoaiMonAn(sTenLoaiThucDon);
           Intent iDuLieu = new Intent();
           iDuLieu.putExtra("kiemtraloaithucdon",check);
           setResult(Activity.RESULT_OK, iDuLieu);

           if (check){
               Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
               finish();
           } else {
               Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
           }

       }  else {
           Toast.makeText(this,R.string.vuilongnhapdulieu,Toast.LENGTH_SHORT).show();
       }
    }
}
