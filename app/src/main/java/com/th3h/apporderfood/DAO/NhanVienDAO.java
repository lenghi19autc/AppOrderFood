package com.th3h.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3h.apporderfood.DTO.NhanVienDTO;
import com.th3h.apporderfood.database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    SQLiteDatabase database;

    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraNhanVien(){
        String query = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount() != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public long ThemNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();

        contentValues.put(CreateDatabase.TB_NHANVIEN_TEDN, nhanVienDTO.getTenDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVienDTO.getCmmd());
        contentValues.put(CreateDatabase.TB_NHANVIEN_SDT, nhanVienDTO.getSDT());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMatKhau());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNgaySinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMaQuyen());


        long check = database.insert(CreateDatabase.TB_NHANVIEN,null, contentValues);

        return check;
    }


    public int LayQuyenNhanVien(int maNV){
            int maQuyen = 0;
           String query = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN +
                   " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + maNV;
           Cursor cursor = database.rawQuery(query, null);
           cursor.moveToFirst();
           while (!cursor.isAfterLast()){
               maQuyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN));
               cursor.moveToNext();
           }
        return maQuyen;
    }

    public boolean SuaNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();

        contentValues.put(CreateDatabase.TB_NHANVIEN_TEDN, nhanVienDTO.getTenDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVienDTO.getCmmd());
        contentValues.put(CreateDatabase.TB_NHANVIEN_SDT, nhanVienDTO.getSDT());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMatKhau());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNgaySinh());

        long check = database.update(CreateDatabase.TB_NHANVIEN,contentValues,
                CreateDatabase.TB_NHANVIEN_MANV + " = " + nhanVienDTO.getMaNV() , null);

        if (check != 0 ){
            return true;
        }
        else {
            return false;
        }
    }

    public int checkLogin(String tenDN, String matKhau){
        String query = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN +  " WHERE " + CreateDatabase.TB_NHANVIEN_TEDN + "='" + tenDN
                + "' AND " + CreateDatabase.TB_NHANVIEN_MATKHAU + "='" + matKhau +  "'";

        int maNV = 0;
        Cursor cursor = database.rawQuery(query,null)   ;
         cursor.moveToFirst();
         while (!cursor.isAfterLast()){
              maNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV));
              cursor.moveToNext();
         }

         return maNV;
    }

    public List<NhanVienDTO> LayDanhSachNhanVien(){
        List<NhanVienDTO> nhanVienDTOList = new ArrayList<NhanVienDTO>();
        String query  = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN ;

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_SDT)));
            nhanVienDTO.setGioiTinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNgaySinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCmmd(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVienDTO.setMatKhau(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setTenDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TEDN)));
            nhanVienDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));

            nhanVienDTOList.add(nhanVienDTO);
            cursor.moveToNext();
        }
        
        return  nhanVienDTOList;
    }

    public boolean XoaNhanVienTheoMa(int maNV){
        long check = database.delete(CreateDatabase.TB_NHANVIEN,
                CreateDatabase.TB_NHANVIEN_MANV + " = " + maNV , null);
        if (check != 0 ){
            return true;
        }
        else {
            return false;
        }
    }


    public NhanVienDTO LayNhanVienTheoMa(int maNV){
       NhanVienDTO nhanVienDTO = new NhanVienDTO();
        String query  = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN
                + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + maNV ;

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            nhanVienDTO.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_SDT)));
            nhanVienDTO.setGioiTinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNgaySinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCmmd(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVienDTO.setMatKhau(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setTenDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TEDN)));
            nhanVienDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));

            cursor.moveToNext();
        }

        return  nhanVienDTO;
    }

}
