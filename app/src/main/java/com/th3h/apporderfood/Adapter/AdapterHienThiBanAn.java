package com.th3h.apporderfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.th3h.apporderfood.DAO.BanAnDAO;
import com.th3h.apporderfood.DAO.GoiMonDAO;
import com.th3h.apporderfood.DTO.BanAnDTO;
import com.th3h.apporderfood.DTO.GoiMonDTO;
import com.th3h.apporderfood.FragmentApp.HienThiThucDonFragment;
import com.th3h.apporderfood.R;
import com.th3h.apporderfood.ThanhToanActivity;
import com.th3h.apporderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {

    private  Context context;
    private int layout;
    private List<BanAnDTO> banAnDTOList;
    private ViewHolderBanAn viewHolderBanAn;
    private BanAnDAO banAnDAO;
    private GoiMonDAO goiMonDAO;
    private FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOList){
         this.context = context;
         this.layout = layout;
         this.banAnDTOList = banAnDTOList;
         banAnDAO = new BanAnDAO(context);
         goiMonDAO = new GoiMonDAO(context);
         fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }
    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return banAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return banAnDTOList.get(i).getMaBan();
    }



    public class ViewHolderBanAn{
        ImageView imgBanAn,imgGoiMon,imgThanhToan,imgAnButton;
        TextView txtTenBanAn;
        
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view    = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan, viewGroup, false );

            viewHolderBanAn.imgBanAn = view.findViewById(R.id.imgBanAn);
            viewHolderBanAn.imgGoiMon = view.findViewById(R.id.imgGoiMon);
            viewHolderBanAn.imgThanhToan = view.findViewById(R.id.imgThanhToan);
//            viewHolderBanAn.imgAnButton = view.findViewById(R.id.imgAnButton);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);

            view.setTag(viewHolderBanAn);

        }
        else{
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        if (banAnDTOList.get(position).isDuocChon()){
               HienThiButton();
        }
        else {
               AnButton();
        }

        BanAnDTO banAnDTO = banAnDTOList.get(position);

        String ktTinhTrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        if (ktTinhTrang.equals("true")){
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        }
        else {
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }

        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.imgBanAn.setTag(position);

        viewHolderBanAn.imgBanAn.setOnClickListener(this);
        viewHolderBanAn.imgGoiMon.setOnClickListener(this);
        viewHolderBanAn.imgThanhToan.setOnClickListener(this);
        return view;
    }

    private void HienThiButton(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.VISIBLE);
       // viewHolderBanAn.imgAnButton.setVisibility(View.VISIBLE);
    }

    private void AnButton(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);
       // viewHolderBanAn.imgAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        
          int id = view.getId();
          viewHolderBanAn = (ViewHolderBanAn) ((View)view.getParent()).getTag();
          int viTri1 = (int) viewHolderBanAn.imgBanAn.getTag();
          int maBan = banAnDTOList.get(viTri1).getMaBan();

          switch (id){
              case R.id.imgBanAn:
                  String tenBan = viewHolderBanAn.txtTenBanAn.getText().toString();
                  int viTri = (int) view.getTag();
                  banAnDTOList.get(viTri).setDuocChon(true);
                  
                  HienThiButton();
                  break;

              case R.id.imgGoiMon:
                  Intent layIntentTrangChu = ((TrangChuActivity)context).getIntent();
                  int maNV = layIntentTrangChu.getIntExtra("manhanvien",0);

                  String tinhTrang =  banAnDAO.LayTinhTrangBanTheoMa(maBan);
                   if (tinhTrang.equals("false")){
                       //Them bang goi mon va cap nhat lai tinh trang ban
                       Calendar calendar = Calendar.getInstance();
                       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                       String ngayGoi = simpleDateFormat.format(calendar.getTime());

                       GoiMonDTO goiMonDTO = new GoiMonDTO();

                       goiMonDTO.setMaBan(maBan);
                       goiMonDTO.setMaNhanVien(maNV);
                       goiMonDTO.setNgayGoi(ngayGoi);
                       goiMonDTO.setTinhTrang("false");

                       long check = goiMonDAO.ThemGoiMon(goiMonDTO) ;
                        banAnDAO.CapNhatTinhTangBan(maBan,"true");  
                        if (check == 0){
                            Toast.makeText(context,context.getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT ).show();
                        }

                   }

                  FragmentTransaction tranThucDonTransaction = fragmentManager.beginTransaction();
                  HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                  Bundle bDuLieuThucDon = new Bundle();
                  bDuLieuThucDon.putInt("maban",maBan);

                  hienThiThucDonFragment.setArguments(bDuLieuThucDon);
                  tranThucDonTransaction.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                  tranThucDonTransaction.commit();
                  break;

              case R.id.imgThanhToan:
                  Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                  iThanhToan.putExtra("maban",maBan);
                  context.startActivity(iThanhToan);
                  break;

          }
    }

}
