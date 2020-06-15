package com.th3h.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.Adapter.AdapterHienThiLoaiMonAn;
import com.th3h.apporderfood.DAO.LoaiMonAnDAO;
import com.th3h.apporderfood.DAO.MonAnDAO;
import com.th3h.apporderfood.DTO.LoaiMonAnDTO;
import com.th3h.apporderfood.DTO.MonAnDTO;

import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageButton imgThemLoaiThucDon;
    public Spinner spinerLoaiThucDon;
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    public static int REQUEST_CODE_LOADHINH = 124;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    ImageView   imgHinhThucDon;
    Button btnAddThemMonAn,btnBackThemMonAn;
    private String sImageLink;
    EditText edtTenMonAn,edtGiaTien;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);
        
        imgThemLoaiThucDon = findViewById(R.id.imgThemLoaiThucDon);
        spinerLoaiThucDon = findViewById(R.id.spinerLoaiMonAn);
        imgHinhThucDon = findViewById(R.id.imgHinhThucDon);
        btnAddThemMonAn = findViewById(R.id.btnAddThemMonAn);
        btnBackThemMonAn = findViewById(R.id.btnBackThemMonAn);
        edtTenMonAn = findViewById(R.id.edtTenLoaiThucDon);
        edtGiaTien = findViewById(R.id.edtThemGiaTien);
        
        imgThemLoaiThucDon.setOnClickListener(this);
        imgHinhThucDon.setOnClickListener(this);
        btnAddThemMonAn.setOnClickListener(this);
        btnBackThemMonAn.setOnClickListener(this);

        HienThiSpinerLoaiMonAn();
    }


    public void HienThiSpinerLoaiMonAn(){
           loaiMonAnDTOs = loaiMonAnDAO.LayDanhSachLoaiMonAn();
           adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this,R.layout.custom_layout_spinerloaithucdon,loaiMonAnDTOs);
           spinerLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
           adapterHienThiLoaiMonAn.notifyDataSetChanged();
          
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.imgThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn, REQUEST_CODE_THEMLOAITHUCDON);
            break;

            case R.id.imgHinhThucDon:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_OPEN_DOCUMENT);
                iMoHinh.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                iMoHinh.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) ;
                startActivityForResult(Intent.createChooser(iMoHinh,"Chọn hình thực đơn"),REQUEST_CODE_MOHINH);
                
                break;

            case R.id.btnAddThemMonAn:
                int viTri = spinerLoaiThucDon.getSelectedItemPosition();
                int maLoai = loaiMonAnDTOs.get(viTri).getMaLoai();
                String tenMonAn = edtTenMonAn.getText().toString();
                String giaTien = edtGiaTien.getText().toString();

                if (tenMonAn !=null && giaTien !=null && !tenMonAn.equals("") && !giaTien.equals(""))  {
                    MonAnDTO   monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giaTien);
                    monAnDTO.setHinhAnh(sImageLink);
                    monAnDTO.setMaLoai(maLoai);
                    monAnDTO.setTenMonAn(tenMonAn);

                    boolean check = monAnDAO.ThemMonAn(monAnDTO);

                    if (check){
                        Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this,getResources().getString(R.string.loithemmonan),Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnBackThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if (resultCode == Activity.RESULT_OK){
                Intent duLieu = data;
                boolean check = duLieu.getBooleanExtra("kiemtraloaithucdon",false);

                if (check){
                    HienThiSpinerLoaiMonAn();
                }
            }
        }
        else {
            if (REQUEST_CODE_MOHINH == requestCode){
                if (resultCode == Activity.RESULT_OK && data != null){
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
//                        imgHinhThucDon.setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    sImageLink = data.getData().toString();
                    imgHinhThucDon.setImageURI(data.getData());
                    getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION)  ;
                    
                }
            }
        }
    }
//    @Override
//    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case :
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //reload my activity with permission granted or use the features that required the permission
//
//                } else {
//                    Toast.makeText(this, "Loi",Toast.LENGTH_LONG);
//                }
//                break;
//        }
//    }
}
