package com.th3h.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.th3h.apporderfood.DTO.QuyenDTO;
import com.th3h.apporderfood.database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {
    SQLiteDatabase database;

    public QuyenDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public void ThemQuyen(String tenQuyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN, tenQuyen);

        database.insert(CreateDatabase.TB_QUYEN, null, contentValues);
    }

    public List<QuyenDTO> LayDanhSachQuyen(){
        List<QuyenDTO> quyenDTOList = new ArrayList<QuyenDTO>();
        String query = " SELECT * FROM " + CreateDatabase.TB_QUYEN;
        Cursor cursor =  database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
             QuyenDTO quyenDTO = new QuyenDTO();
             quyenDTO.setMaQuyen(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_MAQUYEN)));
            quyenDTO.setTenQuyen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_TENQUYEN)));

            quyenDTOList.add(quyenDTO);
            cursor.moveToNext();
        }
        return quyenDTOList;
    }
}
