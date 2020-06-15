package com.th3h.apporderfood;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.th3h.apporderfood.Adapter.AdapterHienThiThanhToan;
import com.th3h.apporderfood.DAO.BanAnDAO;
import com.th3h.apporderfood.DAO.GoiMonDAO;
import com.th3h.apporderfood.DTO.ThanhToanDTO;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private Button btnThanhToan;
    private Button btnThoat;
    private TextView txtTongTien;
    private GoiMonDAO goiMonDAO;
    private List<ThanhToanDTO> thanhToanDTOList;
    private AdapterHienThiThanhToan adapter;
    private long tongTien;
    private BanAnDAO banAnDAO;
    private int maBan;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gridView = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoat     = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);
        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);
        fragmentManager = getSupportFragmentManager();

         maBan = getIntent().getIntExtra("maban",0);
        if (maBan != 0){
            
            HienThiThanhToan();

            for (int i = 0 ; i<thanhToanDTOList.size() ; i++){
                int soLuong = thanhToanDTOList.get(i).getSoLuong();
                int giaTien = thanhToanDTOList.get(i).getGiaTien();

                tongTien += (soLuong * giaTien);
            }
            txtTongTien.setText(getResources().getString(R.string.tongtien) + tongTien);

            
        }

        btnThanhToan.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
    }

    private void HienThiThanhToan(){
        int maGoiMon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maBan,"false");
        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(maGoiMon);
        adapter = new AdapterHienThiThanhToan(this,R.layout.custom_layout_thanhtoan, thanhToanDTOList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
         int id = view.getId();
         switch (id){
             case R.id.btnThanhToan:
                 boolean checkBanAn = banAnDAO.CapNhatTinhTangBan(maBan,"false");
                 boolean checkGoiMon = goiMonDAO.CapNhatTrangThaiMonTheoMaBan(maBan,"true");

                 if (checkBanAn && checkGoiMon){
                     Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhtoanthanhcong),Toast.LENGTH_SHORT).show();
                     HienThiThanhToan();
                     txtTongTien.setText(getResources().getString(R.string.tongtien));
                 }else {
                     Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhtoanthatbai),Toast.LENGTH_SHORT).show();
                 }
                 break;
             case R.id.btnThoatThanhToan:
                 finish();
                 break;
         }
    }
}
