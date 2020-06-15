package com.th3h.apporderfood.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.th3h.apporderfood.Adapter.AdapterHienThiLoaiMonAnThucDon;
import com.th3h.apporderfood.DAO.LoaiMonAnDAO;
import com.th3h.apporderfood.DTO.LoaiMonAnDTO;
import com.th3h.apporderfood.R;
import com.th3h.apporderfood.SuaLoaiMonAnActivity;
import com.th3h.apporderfood.ThemThucDonActivity;
import com.th3h.apporderfood.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {
    private GridView gridView;
    private List<LoaiMonAnDTO> loaiMonAnDTOList;
    private LoaiMonAnDAO loaiMonAnDAO;
    private FragmentManager fragmentManager;
    public static int REQUEST_CODE_THEMLOAITHUCDON = 122;
    public static int REQUEST_CODE_SUA = 125;
    private  int maBan;
    private int maQuyen = 0;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container, false);

        setHasOptionsMenu(true);

        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        gridView = view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity())  ;

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen", 0);
         
        HienThiDanhSachLoaiThucDon();


        Bundle bDuLieuThucDon = getArguments();
        if (bDuLieuThucDon != null) {
           maBan = bDuLieuThucDon.getInt("maban");
        }

         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                 int maLoai = loaiMonAnDTOList.get(position).getMaLoai();

                 HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();

                 Bundle bundle = new Bundle();
                 bundle.putInt("maloai",maLoai);
                 bundle.putInt("maban",maBan);

                 hienThiDanhSachMonAnFragment.setArguments(bundle);

                 FragmentTransaction transaction = fragmentManager.beginTransaction();
                 transaction.replace(R.id.content,hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");

                 transaction.commit();
             }
         });
         if (maQuyen == 1) {
             registerForContextMenu(gridView);
         }

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int viTri = menuInfo.position;
        int maLoai = loaiMonAnDTOList.get(viTri).getMaLoai();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaLoaiMonAnActivity.class);
                intent.putExtra("maloai",maLoai);
                startActivityForResult(intent, REQUEST_CODE_SUA);
                break;
            case R.id.itXoa:
                boolean check =  loaiMonAnDAO.XoaBanLoaiMonAnTheoMa(maLoai);
                if (check){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
                   HienThiDanhSachLoaiThucDon();
                }
                else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathatbai),Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maQuyen == 1) {
            MenuItem itThemBanAn = menu.add(1, R.id.itThemThucDon, 1, R.string.themthucdon);
            itThemBanAn.setIcon(R.drawable.addmenu);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                break;
        }
        return true;
    }
    private void HienThiDanhSachLoaiThucDon(){
        loaiMonAnDTOList = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        AdapterHienThiLoaiMonAnThucDon adapter =
                new AdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hienthiloaimonan,loaiMonAnDTOList);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = data;
        if (requestCode == REQUEST_CODE_SUA){
            if (resultCode == Activity.RESULT_OK){
                boolean check = intent.getBooleanExtra("check",false);
                if (check){
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
                    HienThiDanhSachLoaiThucDon();

                }   else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
