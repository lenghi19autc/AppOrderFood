package com.th3h.apporderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.th3h.apporderfood.DTO.ThanhToanDTO;
import com.th3h.apporderfood.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ThanhToanDTO> thanhToanDTOList ;
    private ViewHolDerThanhToan viewHolDerThanhToan;

    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOList){
           this.context = context;
           this.layout = layout;
           this.thanhToanDTOList = thanhToanDTOList;
    }

    @Override
    public int getCount() {
        return thanhToanDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return thanhToanDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
     public class ViewHolDerThanhToan{
        TextView txtTenMonAn;
         TextView txtSoLuong;
         TextView txtGiaTien;
     }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            viewHolDerThanhToan = new ViewHolDerThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
             view = inflater.inflate(layout, viewGroup, false);

             viewHolDerThanhToan.txtTenMonAn = view.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolDerThanhToan.txtSoLuong = view.findViewById(R.id.txtSoLuongThanhToan);
            viewHolDerThanhToan.txtGiaTien = view.findViewById(R.id.txtGiaTienThanhToan);

            view.setTag(viewHolDerThanhToan);
        }
        else {
            viewHolDerThanhToan = (ViewHolDerThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(i);
        viewHolDerThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolDerThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolDerThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));
        
        return view;
    }
}
