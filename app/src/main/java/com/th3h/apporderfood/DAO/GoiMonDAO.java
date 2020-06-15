package com.th3h.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3h.apporderfood.DTO.ChiTietGoiMonDTO;
import com.th3h.apporderfood.DTO.GoiMonDTO;
import com.th3h.apporderfood.DTO.ThanhToanDTO;
import com.th3h.apporderfood.database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class GoiMonDAO {

    SQLiteDatabase database;

    public GoiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV ,goiMonDTO.getMaNhanVien());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMonDTO.getTinhTrang());

        long magoimon = database.insert(CreateDatabase.TB_GOIMON, null, contentValues);

        return magoimon;
    }

    public long LayMaGoiMonTheoMaBan(int maBan, String tinhTrang) {
        String query  = " SELECT *FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN +
                " = '"+ maBan + "'" + " AND " + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhTrang + "'";

        long maGoiMon = 0;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            maGoiMon = cursor.getLong(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));
            cursor.moveToNext() ;
        }

        return maGoiMon;
    }

    public boolean KiemTraMonAnDaTonTai(int maGoiMon, int maMonAn){
        String query = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON
                + " = '" + maGoiMon + "' AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = '" + maMonAn +"'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int LaySoLuongMonAnTheoMaGoiMon(int maGoiMon, int maMonAn){
        int soLuong = 0;
        String query = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON
                + " = '" + maGoiMon + "' AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = '" + maMonAn + "'";
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            soLuong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));
            cursor.moveToNext() ;
        }

        return soLuong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());

       long check =  database.update(CreateDatabase.TB_CHITIETGOIMON, contentValues,
                CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon() + " AND "
                        + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMaMonAn(),null ) ;
        
        if (check != 0 ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean ThemChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMonDTO.getMaMonAn());

        long check = database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);
        if (check != 0 ){
            return true;
        }
        else {
            return false;
        }
    }

    public List<ThanhToanDTO> LayDanhSachMonAnTheoMaGoiMon(int maGoiMon){
        String query = " SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct , " + CreateDatabase.TB_MONAN
                + " ma WHERE " +  "ct." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma." + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '" + maGoiMon + "'";

        List<ThanhToanDTO> toanDTOList = new ArrayList<ThanhToanDTO>();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            toanDTOList.add(thanhToanDTO);
            cursor.moveToNext();
        }

        return toanDTOList;
    }

    public boolean CapNhatTrangThaiMonTheoMaBan(int maBan, String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, tinhTrang);

        long check = database.update(CreateDatabase.TB_GOIMON,contentValues,CreateDatabase.TB_GOIMON_MABAN +
                " = '" + maBan + "'", null) ;
         if (check != 0 ){
             return true;
         }
         else {
             return false;
         }
    }
}
