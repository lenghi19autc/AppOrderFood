package com.th3h.apporderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.DAO.NhanVienDAO;
import com.th3h.apporderfood.DAO.QuyenDAO;
import com.th3h.apporderfood.DTO.NhanVienDTO;
import com.th3h.apporderfood.DTO.QuyenDTO;
import com.th3h.apporderfood.FragmentApp.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText edTenDangNhap;
    private EditText edMatKhau;
    private EditText edNgaySinh;
    private EditText edCMND;
    private EditText edSDT;
    private TextView txtTieuDe;
    private Button btnRegister,btnBack;
    private RadioGroup rgGioiTinh;
    private RadioButton rdNam;
    private RadioButton rdNu;
    private String sGioiTinh;
    private Spinner spinnerQuyen;
    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;
    private   int maNV = 0;
    private   int lanDau = 0;
    private List<QuyenDTO> quyenDTOList;
    private List<String> dataAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        edTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edMatKhau = findViewById(R.id.edtMatKhau);
        edNgaySinh = findViewById(R.id.edtNgaySinh);
        edCMND = findViewById(R.id.edtCMNN);
        edSDT = findViewById(R.id.edtSDT);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);
        txtTieuDe = findViewById(R.id.txtTieuDeDangKy);
        spinnerQuyen = findViewById(R.id.spinerQuyen);

        btnRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        edNgaySinh.setOnFocusChangeListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);
        dataAdapter = new ArrayList<String>();

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
         for (int i = 0 ; i< quyenDTOList.size() ; i++){
             String tenQuyen = quyenDTOList.get(i).getTenQuyen();
             dataAdapter.add(tenQuyen);
         }

        maNV = getIntent().getIntExtra("manv",0);
        lanDau = getIntent().getIntExtra("landautien",0);

        if (lanDau == 0){
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataAdapter);
            spinnerQuyen.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }
        else {
            quyenDAO.ThemQuyen("Manager");
            quyenDAO.ThemQuyen("Employee");
            spinnerQuyen.setVisibility(View.GONE);
        }
        
        if (maNV != 0 ){
             txtTieuDe.setText(getResources().getString(R.string.update));
             NhanVienDTO nhanVienDTO = nhanVienDAO.LayNhanVienTheoMa(maNV);
             
             edTenDangNhap.setText(nhanVienDTO.getTenDN());
            edMatKhau.setText(nhanVienDTO.getMatKhau());
            edNgaySinh.setText(nhanVienDTO.getNgaySinh());
            edCMND.setText(String.valueOf(nhanVienDTO.getCmmd()));
            edSDT.setText(String.valueOf(nhanVienDTO.getSDT()));

            String gioiTinh = nhanVienDTO.getGioiTinh();
            if (gioiTinh.equals("Nam")){
                rdNam.setChecked(true);
            }
            else {
                rdNu.setChecked(true);
            }
            btnRegister.setText(getResources().getString(R.string.update));
        }
        
    }

    private void DongYThemNhanVien(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nu";
                break;
        }

        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());
        String sSDT = edSDT.getText().toString();

        if (sTenDangNhap == null || sTenDangNhap.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhaptendangnhap),Toast.LENGTH_LONG).show();
        }  else if(sMatKhau == null || sMatKhau.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhapmatkhau),Toast.LENGTH_LONG).show();
        }else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTenDN(sTenDangNhap);
            nhanVienDTO.setMatKhau(sMatKhau);
            nhanVienDTO.setCmmd(sCMND);
            nhanVienDTO.setNgaySinh(sNgaySinh);
            nhanVienDTO.setGioiTinh(sGioiTinh);
            nhanVienDTO.setSDT(sSDT);
            if (lanDau != 0 ){
                //phan quyen la admin
                nhanVienDTO.setMaQuyen(1);
            }else {
                //set maquyen bang admin chon
                int viTri = spinnerQuyen.getSelectedItemPosition();
                int maQuyen = quyenDTOList.get(viTri).getMaQuyen();
                nhanVienDTO.setMaQuyen(maQuyen);
            }

            long check = nhanVienDAO.ThemNhanVien(nhanVienDTO);

            if (check !=0){
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.dangkythanhcong),Toast.LENGTH_LONG).show();
            }   else {
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.dangkythatbai),Toast.LENGTH_LONG).show();
            }
        }
    }
    private void DongYSuaNhanVien(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nu";
                break;
        }

        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());
        String sSDT = edSDT.getText().toString();

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
          nhanVienDTO.setMaNV(maNV);
          nhanVienDTO.setTenDN(sTenDangNhap);
          nhanVienDTO.setMatKhau(sMatKhau);
          nhanVienDTO.setCmmd(sCMND);
          nhanVienDTO.setNgaySinh(sNgaySinh);
          nhanVienDTO.setGioiTinh(sGioiTinh);
          nhanVienDTO.setSDT(sSDT);

         boolean check =  nhanVienDAO.SuaNhanVien(nhanVienDTO);
         if (check){
             Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathanhcong),Toast.LENGTH_LONG).show();
         }
         else {
             Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loi),Toast.LENGTH_LONG).show();
         }
    }

    @Override
    public void onClick(View view) {
         int id = view.getId();
         switch (id){
             case R.id.btnRegister:
                 if (maNV != 0 ){
                     //sua nhan vien
                     DongYSuaNhanVien();
                     finish();
                 }
                 else {
                     //them moi nhan vien
                     DongYThemNhanVien();
                     if(lanDau == 1) {
                         Intent intent = new Intent(this, DangNhapActivity.class);
                         startActivity(intent);
                     }
                     else {
                         finish();
                     }
                 }
                 break;
             case R.id.btnBack:
                finish();
                 break;
         }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        int id = view.getId();
        switch (id){
            case R.id.edtNgaySinh:
                if (hasFocus){
                    //xuat popup ngay sinh
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), "Ngay Sinh");
                }
                break;
        }
    }

}
