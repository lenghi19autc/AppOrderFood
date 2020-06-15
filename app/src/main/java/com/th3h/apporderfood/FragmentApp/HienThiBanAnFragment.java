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

import com.th3h.apporderfood.Adapter.AdapterHienThiBanAn;
import com.th3h.apporderfood.DAO.BanAnDAO;
import com.th3h.apporderfood.DTO.BanAnDTO;
import com.th3h.apporderfood.R;
import com.th3h.apporderfood.SuaBanAnActivity;
import com.th3h.apporderfood.ThemBanAnActivity;
import com.th3h.apporderfood.TrangChuActivity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {

    public static int REQUEST_CODE_THEM = 111;
    public static int REQUEST_CODE_SUA = 116;
    public GridView gvHienThiBanAn;
    public List<BanAnDTO> banAnDTOList;
    public BanAnDAO banAnDAO;
    public AdapterHienThiBanAn adapterHienThiBanAn;
    private int maQuyen = 0;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan,container,false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.banan);
        gvHienThiBanAn = view.findViewById(R.id.gvHienThiBanAn);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("maquyen",0);
        
        banAnDAO = new BanAnDAO(getActivity());

        HienThiDanhSachBanAn();
        if (maQuyen == 1) {
            registerForContextMenu(gvHienThiBanAn);
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
        int maBan = banAnDTOList.get(viTri).getMaBan();
        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban",maBan);
                startActivityForResult(intent, REQUEST_CODE_SUA);
                break;

            case R.id.itXoa:
              boolean check =  banAnDAO.XoaBanAnTheoMa(maBan);
              if (check){
                  Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
                  HienThiDanhSachBanAn();
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
            //quan ly
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.thembanan);
            itThemBanAn.setIcon(R.drawable.table);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn, REQUEST_CODE_THEM);
                break;
        }
        return true;
    }

    private void HienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.layTatCaBanAn();
        adapterHienThiBanAn= new AdapterHienThiBanAn(getActivity(),R.layout.custom_layout_hienthibanan, banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = data;
        if (requestCode == REQUEST_CODE_THEM){
            if (resultCode == Activity.RESULT_OK){
                boolean check = intent.getBooleanExtra("ketquathem",false);
                if (check){
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                    HienThiDanhSachBanAn();

                }   else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == REQUEST_CODE_SUA){
            if (resultCode == Activity.RESULT_OK){
                boolean check = intent.getBooleanExtra("check",false);
                if (check){
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
                    HienThiDanhSachBanAn();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
