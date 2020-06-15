package com.th3h.apporderfood.FragmentApp;

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
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.th3h.apporderfood.Adapter.AdapterHienThiNhanVien;
import com.th3h.apporderfood.DAO.NhanVienDAO;
import com.th3h.apporderfood.DTO.NhanVienDTO;
import com.th3h.apporderfood.DangKyActivity;
import com.th3h.apporderfood.R;
import com.th3h.apporderfood.TrangChuActivity;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {
    private List<NhanVienDTO> nhanVienDTOList;
    private ListView listNhanVien;
    private NhanVienDAO nhanVienDAO;
    public static int REQUEST_CODE_SUA = 136;
    private int maQuyen = 0;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien, container,false);

        setHasOptionsMenu(true);
        listNhanVien = view.findViewById(R.id.listViewNhanVien);

        nhanVienDAO = new NhanVienDAO(getActivity());
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);

        HienThiDanhSachNhanVien();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen", 0);

         if (maQuyen == 1) {
             registerForContextMenu(listNhanVien);
         }
         
        return view;
    }

    private void HienThiDanhSachNhanVien() {
        nhanVienDTOList = nhanVienDAO.LayDanhSachNhanVien();
        AdapterHienThiNhanVien adapterHienThiNhanVien =
                new AdapterHienThiNhanVien(getActivity(),R.layout.custom_layout_nhanvien, nhanVienDTOList);
        listNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetInvalidated();
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
        int maNhanVien = nhanVienDTOList.get(viTri).getMaNV();
        switch (id){
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                iDangKy.putExtra("manv", maNhanVien);
                startActivity(iDangKy);
                break;

            case R.id.itXoa:
                boolean check =  nhanVienDAO.XoaNhanVienTheoMa(maNhanVien);
                if (check){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
                    HienThiDanhSachNhanVien();
                }
                else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathatbai),Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maQuyen == 1) {
            MenuItem itThemBanAn = menu.add(1, R.id.itThemNhanVien, 1, R.string.themnhanvien);
            itThemBanAn.setIcon(R.drawable.addemployee);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.itThemNhanVien:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(iDangKy);
                break;
        }
        return true;
    }
}
