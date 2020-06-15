package com.th3h.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edThemTenBanAn;
    private Button btnDongYThemTenBanAn;
    private BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);

        edThemTenBanAn = findViewById(R.id.edtThemTenBanAn);
        btnDongYThemTenBanAn = findViewById(R.id.btnDongYThemBanAn);

        banAnDAO = new BanAnDAO(this);
        btnDongYThemTenBanAn.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View view) {
        String sTenBanAn = edThemTenBanAn.getText().toString();

        if(sTenBanAn != null || sTenBanAn.equals("") ){
            boolean check = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem",check);
            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    }
}
