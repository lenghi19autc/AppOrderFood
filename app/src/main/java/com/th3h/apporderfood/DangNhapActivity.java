package com.th3h.apporderfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.DAO.NhanVienDAO;
import com.th3h.apporderfood.DAO.QuyenDAO;

public class DangNhapActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button btnLogin;
    private Button btnRegisterDN;
    private EditText edtTenDangNhapDN;
    private EditText edtMatKhauDN;
    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;
    

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterDN = findViewById(R.id.btnRegisterDN);
        edtTenDangNhapDN = findViewById(R.id.edtTenDangNhapDN);
        edtMatKhauDN = findViewById(R.id.edtMatKhauDN);

        btnLogin.setOnClickListener(this);
        btnRegisterDN.setOnClickListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        HienThiButtonDangKy();
        
    }
    private void HienThiButtonDangKy(){
       boolean check =  nhanVienDAO.KiemTraNhanVien();
       if (check){
           btnRegisterDN.setVisibility(View.GONE);
           btnLogin.setVisibility(View.VISIBLE);
       }
       else {
           btnLogin.setVisibility(View.GONE);
           btnRegisterDN.setVisibility(View.VISIBLE);
       }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btnLogin:
                btnDangNhap();
                break;
            case R.id.btnRegisterDN:
                 btnDangKy();
                break;
        }
    }

    private void btnDangNhap(){

        String sTenDangNhap = edtTenDangNhapDN.getText().toString();
        String sMatKhau = edtMatKhauDN.getText().toString();
        int check = nhanVienDAO.checkLogin(sTenDangNhap, sMatKhau);
        int maQuyen = nhanVienDAO.LayQuyenNhanVien(check);
        if (check != 0){
            SharedPreferences sharedPreferences =  getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maQuyen);
            editor.commit();
            
            Intent intentTrangChu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
            intentTrangChu.putExtra("tendn",edtTenDangNhapDN.getText().toString());
            intentTrangChu.putExtra("manhanvien",check);
            
            startActivity(intentTrangChu);
        }
        else {
            Toast.makeText(DangNhapActivity.this,"Dang nhap that bai !", Toast.LENGTH_LONG).show();
        }
    }

    private void btnDangKy(){
        Intent intentDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
        intentDangKy.putExtra("landautien", 1);
        startActivity(intentDangKy);
    }
}
