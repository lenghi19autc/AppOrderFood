package com.th3h.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3h.apporderfood.DTO.BanAnDTO;
import com.th3h.apporderfood.database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    SQLiteDatabase database;

    public BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemBanAn(String tenBanAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenBanAn);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long check = database.insert(CreateDatabase.TB_BANAN, null, contentValues);

        if (check !=0){
             return true;
        }
        else {
            return false;
        }
    }

    public List<BanAnDTO> layTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();

        String query = "SELECT *FROM " +  CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }

        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maBan){
        String tinhTrang = "";
        String query =
                " SELECT *FROM " + CreateDatabase.TB_BANAN + " WHERE "
                        + CreateDatabase.TB_BANAN_MABAN + " = '" + maBan+ "'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhTrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhTrang;
    }

    public boolean CapNhatTinhTangBan(int maBan, String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG,tinhTrang);

        long check = database.update(CreateDatabase.TB_BANAN,contentValues,
                CreateDatabase.TB_BANAN_MABAN + " = '" + maBan + "'",null );
         if (check != 0){
             return true;
         }
         else {
             return false;
         }
    }


    public boolean CapNhatTenBan(int maBan, String tenBan){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN,tenBan);

        long check = database.update(CreateDatabase.TB_BANAN,contentValues,
                CreateDatabase.TB_BANAN_MABAN + " = '" + maBan + "'",null );
        if (check != 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean XoaBanAnTheoMa(int maBan){
       long check =  database.delete(CreateDatabase.TB_BANAN,
                CreateDatabase.TB_BANAN_MABAN + " = '" + maBan + "'", null);

        if (check != 0){
            return true;
        }
        else {
            return false;
        }
    }
}
