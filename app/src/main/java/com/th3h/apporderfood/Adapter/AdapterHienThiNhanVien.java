package com.th3h.apporderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.th3h.apporderfood.DTO.NhanVienDTO;
import com.th3h.apporderfood.R;

import java.util.List;

public class AdapterHienThiNhanVien extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NhanVienDTO> nhanVienDTOList;
    private  ViewHolderNhanVien viewHolderNhanVien;

    public AdapterHienThiNhanVien(Context context, int layout, List<NhanVienDTO> nhanVienDTOList){
        this.context = context;
        this.layout = layout;
        this.nhanVienDTOList = nhanVienDTOList;
    }
    @Override
    public int getCount() {
        return nhanVienDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return nhanVienDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nhanVienDTOList.get(i).getMaNV();
    }

    public class ViewHolderNhanVien{
        ImageView imgHinhNhanVien;
        TextView txtTenNhanVien;
        TextView txtSDT;

        
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);

            viewHolderNhanVien.imgHinhNhanVien = view.findViewById(R.id.imgHinhNhanVien);
            viewHolderNhanVien.txtTenNhanVien = view.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtSDT = view.findViewById(R.id.txtSoDienThoai);

            view.setTag(viewHolderNhanVien);
        }
        else {
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }

        NhanVienDTO nhanVienDTO = nhanVienDTOList.get(i);
        viewHolderNhanVien.txtTenNhanVien.setText(nhanVienDTO.getTenDN());
        viewHolderNhanVien.txtSDT.setText("0"+ String.valueOf(nhanVienDTO.getSDT()));
        
        return view;
    }
}
