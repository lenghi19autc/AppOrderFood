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

import com.th3h.apporderfood.DAO.LoaiMonAnDAO;
import com.th3h.apporderfood.DTO.LoaiMonAnDTO;
import com.th3h.apporderfood.R;

import java.util.List;

public class AdapterHienThiLoaiMonAnThucDon extends BaseAdapter {
    public Context context;
    public int layout;
    public List<LoaiMonAnDTO> loaiMonAnDTOList;
    public ViewHolderHienThiLoaiThucDon viewHolderHienThiLoaiThucDon;
    public LoaiMonAnDAO loaiMonAnDAO;
    public static int REQUEST_CODE_LOADHINH = 124;


    public AdapterHienThiLoaiMonAnThucDon(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
         this.context = context;
         this.layout = layout;
         this.loaiMonAnDTOList = loaiMonAnDTOList;
         loaiMonAnDAO = new LoaiMonAnDAO(context);
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

    public class ViewHolderHienThiLoaiThucDon{
        ImageView imgHinhLoaiThucDon;
        TextView txtTenLoaiThucDon;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
       View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiLoaiThucDon = new ViewHolderHienThiLoaiThucDon();
            view = inflater.inflate(layout, viewGroup,false);

            viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon = view.findViewById(R.id.imgHienThiMonAn);
            viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon = view.findViewById(R.id.txtTenLoaiThucDon);

            view.setTag(viewHolderHienThiLoaiThucDon);
        }

        else {
            viewHolderHienThiLoaiThucDon = (ViewHolderHienThiLoaiThucDon) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);

        int maLoai = loaiMonAnDTO.getMaLoai();
        String hinhAnh =   loaiMonAnDAO.LayHinhLoaiMonAn(maLoai);

        viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon.setText(loaiMonAnDTO.getTenLoai());

        if (hinhAnh==null || hinhAnh.equals("")){

            viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon.setImageResource(R.drawable.backgound);
        } else {

            Uri uri = Uri.parse(hinhAnh);
            context.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)  ;
            viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon.setImageURI(uri);
        }
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//           viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return view;
    }

}
