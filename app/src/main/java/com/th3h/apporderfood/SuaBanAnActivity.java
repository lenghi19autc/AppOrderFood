package com.th3h.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.th3h.apporderfood.DAO.BanAnDAO;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDongYSua;
    private EditText edtSuaTenBan;
    private BanAnDAO banAnDAO;
    private  int maBan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);
        btnDongYSua = findViewById(R.id.btnDongYSuaBanAn);
        edtSuaTenBan = findViewById(R.id.edtSuaTenBanAn);
        banAnDAO = new BanAnDAO(this);

        maBan = getIntent().getIntExtra("maban",0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
          String tenBan = edtSuaTenBan.getText().toString();
          if (!tenBan.trim().equals("") || tenBan.trim() != null){
                boolean check = banAnDAO.CapNhatTenBan(maBan, tenBan);
              Intent intent = new Intent();
              intent.putExtra("check",check);
              setResult(Activity.RESULT_OK,intent);
              finish();
          }
          else {
              Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu),Toast.LENGTH_SHORT).show();
          }
    }
}
