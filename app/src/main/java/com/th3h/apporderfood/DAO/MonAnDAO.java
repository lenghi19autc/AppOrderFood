package com.th3h.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3h.apporderfood.DTO.MonAnDTO;
import com.th3h.apporderfood.database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {

    SQLiteDatabase database;

    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI,monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH,monAnDTO.getHinhAnh());

        long check = database.insert(CreateDatabase.TB_MONAN,null,contentValues);
         if (check !=0){
             return true;
         }  else {
             return false;
         }

    }

    public List<MonAnDTO> LayDanhSachMonAnTheoLoai(int maLoai){
        List<MonAnDTO> monAnDTOS = new ArrayList<MonAnDTO>();
        String query = " SELECT *FROM " + CreateDatabase.TB_MONAN
                + " WHERE " +  CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoai + "' ";


        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();
            
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)));
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOS.add(monAnDTO);

            cursor.moveToNext();
        }
        return  monAnDTOS;
    }

    public boolean XoaBanMonAnTheoMa(int maMonAn){
        long check =  database.delete(CreateDatabase.TB_MONAN,
                CreateDatabase.TB_MONAN_MAMON + " = '" + maMonAn + "'", null);

        if (check != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean CapNhatTenMonAnGiaTien(int maMonAn, String tenMonAn, String giaTien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,tenMonAn);
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,giaTien);

        long check = database.update(CreateDatabase.TB_MONAN,contentValues,
                CreateDatabase.TB_MONAN_MAMON + " = '" + maMonAn + "'",null );
        if (check != 0){
            return true;
        }
        else {
            return false;
        }
    }
}
