package com.th3h.apporderfood.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

import com.th3h.apporderfood.Adapter.AdapterHienThiDanhSachMonAn;
import com.th3h.apporderfood.DAO.MonAnDAO;
import com.th3h.apporderfood.DTO.MonAnDTO;
import com.th3h.apporderfood.R;
import com.th3h.apporderfood.SoLuongActivity;
import com.th3h.apporderfood.SuaMonAnActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {

    private GridView gridView;
    private MonAnDAO monAnDAO;
    private List<MonAnDTO> monAnDTOList;
    private AdapterHienThiDanhSachMonAn adater;
    private int maBan;
    private int maLoai ;
    public static int REQUEST_CODE_SUA = 151;
    private int maQuyen = 0;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container, false);

        gridView = view.findViewById(R.id.gvHienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());
        Bundle bundle = getArguments();

        if (bundle != null){
             maLoai = bundle.getInt("maloai");
            maBan = bundle.getInt("maban");
           
            HienThiDanhSachMonAn();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    if (maBan != 0) {
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maBan);
                        iSoLuong.putExtra("mamonan",monAnDTOList.get(position).getMaMonAn());
                        startActivity(iSoLuong);
                    }
                }
            });
            
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
               return false;
            }
        });

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen", 0);

        if (maQuyen == 1) {
            registerForContextMenu(gridView);
        }

        return view;
    }

    private void HienThiDanhSachMonAn() {
        monAnDTOList = monAnDAO.LayDanhSachMonAnTheoLoai(maLoai) ;
        adater = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.custom_layout_hienthidanhsachmonan,monAnDTOList);
        gridView.setAdapter(adater);
        adater.notifyDataSetChanged();
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
        int maMonAn = monAnDTOList.get(viTri).getMaMonAn();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaMonAnActivity.class);
                intent.putExtra("mamonan",maMonAn);
                startActivityForResult(intent, REQUEST_CODE_SUA);

                break;
            case R.id.itXoa:
                boolean check =  monAnDAO.XoaBanMonAnTheoMa(maMonAn);
                if (check){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
                    HienThiDanhSachMonAn();
                }
                else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathatbai),Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
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
                    HienThiDanhSachMonAn();

                }   else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
