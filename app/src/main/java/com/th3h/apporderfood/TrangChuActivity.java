package com.th3h.apporderfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.th3h.apporderfood.FragmentApp.HienThiBanAnFragment;
import com.th3h.apporderfood.FragmentApp.HienThiNhanVienFragment;
import com.th3h.apporderfood.FragmentApp.HienThiThucDonFragment;

public class TrangChuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar    toolbar;
    private TextView txtTenNhanVienNavigation;
    private FragmentManager fragmentManager;

    

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nvTrangChu);
        toolbar = findViewById(R.id.toolBar);

        View view = navigationView.getHeaderView(0);
        txtTenNhanVienNavigation = view.findViewById(R.id.txtTenNhanVien_Navigation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.mo,  R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setItemIconTintList(null);
        
        navigationView.setNavigationItemSelectedListener(this);

        

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
       // Log.d("dulieu",tendn);
        txtTenNhanVienNavigation.setText(tendn);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
        tranHienThiBanAn.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id){
            case R.id.itTrangChu:
                FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                tranHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
                tranHienThiBanAn.commit();

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itThemThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.replace(R.id.content, hienThiThucDonFragment);
                tranHienThiThucDon.commit();

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itNhanVien:
                FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                tranNhanVien.replace(R.id.content, hienThiNhanVienFragment);
                tranNhanVien.commit();

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        
    }
}
