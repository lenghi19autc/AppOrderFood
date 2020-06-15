package com.th3h.apporderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.DAO.GoiMonDAO;
import com.th3h.apporderfood.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {
    private int maBan;
    private int maMonAn;
    private Button btnDongY;
    private EditText edtSoLuong;
    private GoiMonDAO goiMonDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);

        btnDongY = findViewById(R.id.btnDongYThemSoLuong);
        edtSoLuong = findViewById(R.id.edtThemSoLuong);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maBan = intent.getIntExtra("maban",0);
        maMonAn = intent.getIntExtra("mamonan",0);

        btnDongY.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
         int maGoiMon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maBan,"false");
         boolean check = goiMonDAO.KiemTraMonAnDaTonTai(maGoiMon, maMonAn);

         if (check){
             //cap nhat mon an da ton tai
             int soLuongCu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(maGoiMon, maMonAn);
             int soLuongMoi = Integer.parseInt(edtSoLuong.getText().toString());

             int tongSoLuong = soLuongCu + soLuongMoi;

             ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
             chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
             chiTietGoiMonDTO.setMaMonAn(maMonAn);
             chiTietGoiMonDTO.setSoLuong(tongSoLuong);
             boolean capNhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO) ;

             if (capNhat){
                 Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                 finish();
             }   else {
                 Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
             }
         }
         else {
             //Them moi mon an

             int soLuongGoi = Integer.parseInt(edtSoLuong.getText().toString());
             ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
             chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
             chiTietGoiMonDTO.setMaMonAn(maMonAn);
             chiTietGoiMonDTO.setSoLuong(soLuongGoi);
             boolean capNhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO) ;

             if (capNhat){
                 Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                 finish();
             }   else {
                 Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
             }
         }
    }
}
