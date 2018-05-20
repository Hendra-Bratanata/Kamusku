package com.ichirotech.bratanata.kamusku.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.ichirotech.bratanata.kamusku.Database.DatabaseHelper;
import com.ichirotech.bratanata.kamusku.POJO_Parcelable.KamusModel;

import java.util.ArrayList;

import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.DatabaseEngColumns.ABJADEng;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.DatabaseEngColumns._ID;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.DatabaseEngColumns.DESCEng;

import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.TABLE_NAME_ENG;

public class KamusEngHelper {
    private Context context;
    private DatabaseHelper engDatabaseHelper;
    private SQLiteDatabase database;

    public KamusEngHelper(Context context) {
        this.context = context;
    }

    public KamusEngHelper open() throws SQLException{
        engDatabaseHelper = new DatabaseHelper(context);
        database = engDatabaseHelper.getWritableDatabase();
        return  this;
    }

    public void close(){
        engDatabaseHelper.close();
    }
    public ArrayList<KamusModel> getDataByAbjad(String Abjad){
        String hasil =null;
        Cursor cursor = database.query(TABLE_NAME_ENG,
                null,
                ABJADEng+" LIKE ?",new String[]{Abjad},
                null,
                null,
                _ID+" ASC",
                null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;

        if(cursor.getCount() > 0){
            do{
                kamusModel = new KamusModel();
                kamusModel.setAbjad(cursor.getString(cursor.getColumnIndexOrThrow(ABJADEng)));
                kamusModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCEng)));
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            }while (cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(){
        Cursor cursor = database.query(TABLE_NAME_ENG,
                null,
                null,
                null,
                null,
                null,
                _ID+" ASC");
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;

        if(cursor.getCount() > 0){
            do{
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCEng)));
                kamusModel.setAbjad(cursor.getString(cursor.getColumnIndexOrThrow(ABJADEng)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(KamusModel kamusModel){
        String sql = String.format(" INSERT INTO %s (%s , %s ) VALUES (?, ?)", TABLE_NAME_ENG, ABJADEng, DESCEng);
        SQLiteStatement stm = database.compileStatement(sql);
        stm.bindString(1,kamusModel.getAbjad());
        stm.bindString(2,kamusModel.getDesc());
        stm.execute();
        stm.clearBindings();
    }

    public int update(KamusModel kamusModel){
        ContentValues contentValues =new ContentValues();
        contentValues.put(ABJADEng,kamusModel.getAbjad());
        contentValues.put(DESCEng,kamusModel.getDesc());
        return database.update(TABLE_NAME_ENG,contentValues,_ID+" ='"+kamusModel.getId()+"'",null);

    }

    public int delete(int id){
        return database.delete(TABLE_NAME_ENG,_ID+" ='"+id+"'",null);
    }
}
