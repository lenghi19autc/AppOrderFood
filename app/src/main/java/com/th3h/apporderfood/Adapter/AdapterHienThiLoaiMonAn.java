package com.th3h.apporderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.th3h.apporderfood.DTO.LoaiMonAnDTO;
import com.th3h.apporderfood.R;

import java.util.List;

public class AdapterHienThiLoaiMonAn extends BaseAdapter {
    public Context context;
    public int layout;
    public  List<LoaiMonAnDTO> loaiMonAnDTOList ;

    ViewHolderLoaiMonAn viewHolderLoaiMonAn;
    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
         this.context =context;
         this.layout = layout;
         this.loaiMonAnDTOList = loaiMonAnDTOList;
    }
    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiMonAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return loaiMonAnDTOList.get(i).getMaLoai();
    }

    public class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            viewHolderLoaiMonAn  = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinerloaithucdon, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }
        else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;

        if (view == null){
            viewHolderLoaiMonAn  = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             view = inflater.inflate(R.layout.custom_layout_spinerloaithucdon, viewGroup, false);

             viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

             view.setTag(viewHolderLoaiMonAn);
        }
        else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }
}
