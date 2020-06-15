package com.th3h.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3h.apporderfood.DTO.LoaiMonAnDTO;
import com.th3h.apporderfood.database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {

    SQLiteDatabase database;

    public LoaiMonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMonAn(String tenLoai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI,tenLoai);

        long check =  database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);

        if (check != 0){
            return true;
        }
        else {
            return false;
        }

    }

    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn (){
        List<LoaiMonAnDTO> loaiMonAnDTOS = new ArrayList<LoaiMonAnDTO>();

        String query = " SELECT *FROM " + CreateDatabase.TB_LOAIMONAN ;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOS.add(loaiMonAnDTO);
            
            cursor.moveToNext();
        }

        return loaiMonAnDTOS;
    }


    public String LayHinhLoaiMonAn(int maLoai){
        String hinhAnh = "";
        String query = " SELECT *FROM " + CreateDatabase.TB_MONAN
                + " WHERE " +  CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoai + "' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY "
                + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1 ";


        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            
           hinhAnh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }

        return hinhAnh;
    }

    public boolean XoaBanLoaiMonAnTheoMa(int maLoai){
        long check =  database.delete(CreateDatabase.TB_LOAIMONAN,
                CreateDatabase.TB_LOAIMONAN_MALOAI + " = '" + maLoai + "'", null);

        if (check != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean CapNhatTenLoaiMonAn(int maLoai, String tenLoai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI,tenLoai);

        long check = database.update(CreateDatabase.TB_LOAIMONAN,contentValues,
                CreateDatabase.TB_LOAIMONAN_MALOAI + " = '" + maLoai + "'",null );
        if (check != 0){
            return true;
        }
        else {
            return false;
        }
    }
}
