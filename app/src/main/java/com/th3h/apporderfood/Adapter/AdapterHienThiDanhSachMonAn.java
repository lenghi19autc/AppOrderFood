package com.th3h.apporderfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.th3h.apporderfood.DTO.MonAnDTO;
import com.th3h.apporderfood.R;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MonAnDTO> monAnDTOList  ;
    private  ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;
    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> monAnDTOList){
         this.context = context;
         this.layout = layout;
         this.monAnDTOList = monAnDTOList;
    }
    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return monAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return monAnDTOList.get(i).getMaMonAn();
    }

    public class ViewHolderHienThiDanhSachMonAn{
        ImageView imgHinhMonAn;
        TextView txtTenMonAn,txtGiaTien;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);

            viewHolderHienThiDanhSachMonAn.imgHinhMonAn = view.findViewById(R.id.imgHienThiDSMonAn);
            viewHolderHienThiDanhSachMonAn.txtTenMonAn = view.findViewById(R.id.txtTenDSMonAn);
            viewHolderHienThiDanhSachMonAn.txtGiaTien = view.findViewById(R.id.txtGiaTienDSMonAn);

            view.setTag(viewHolderHienThiDanhSachMonAn);
        }
        else {
            viewHolderHienThiDanhSachMonAn = (ViewHolderHienThiDanhSachMonAn) view.getTag();
        }

        MonAnDTO monAnDTO = monAnDTOList.get(i);

        String hinhAnh = monAnDTO.getHinhAnh();
        if (hinhAnh == null || hinhAnh.equals("")){
           // Log.d("loadhinh",hinhAnh);
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageResource(R.drawable.backgound);
        }
        else {
            //viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageResource(R.drawable.backgound);
            Uri uri = Uri.parse(monAnDTO.getHinhAnh().toString());
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageURI(uri);
            context.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)  ;
        }
        viewHolderHienThiDanhSachMonAn.txtTenMonAn.setText(monAnDTO.getTenMonAn());
        viewHolderHienThiDanhSachMonAn.txtGiaTien.setText(context.getResources().getString(R.string.gia)+ monAnDTO.getGiaTien());

        return view;
    }
}
