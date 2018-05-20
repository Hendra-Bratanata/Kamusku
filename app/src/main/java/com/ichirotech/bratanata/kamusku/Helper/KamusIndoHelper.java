package com.ichirotech.bratanata.kamusku.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.ichirotech.bratanata.kamusku.Database.DatabaseHelper;
import com.ichirotech.bratanata.kamusku.POJO_Parcelable.KamusModel;

import java.util.ArrayList;


import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.DatabaseIndoColumns.ABJADIndo;
;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.DatabaseIndoColumns.DESCIndo;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.DatabaseIndoColumns._ID;

import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.TABLE_NAME_INDO;


public class KamusIndoHelper {
    private Context context;
    private DatabaseHelper indoDatabaseHelper;
    private SQLiteDatabase database;

    public KamusIndoHelper(Context context) {
        this.context = context;
    }

    public KamusIndoHelper open() throws SQLException {
        indoDatabaseHelper= new DatabaseHelper(context);
        database = indoDatabaseHelper.getWritableDatabase();
      return this;
    }

    public void close(){
        indoDatabaseHelper  .close();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ArrayList<KamusModel> getDataByAbjad(String Abjad){
        Log.d("Search","GetdataBy abjad");
        Log.d("Search","GetAbjat String"+ Abjad);

        String hasil =null;

        Cursor cursor = database.query(
                TABLE_NAME_INDO,
                null,
                ABJADIndo+ " LIKE ?",new String[]{Abjad},
                null,
                null,
                _ID+" ASC",
                null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;

        Log.d("Search","curso count"+cursor.getCount());
        if(cursor.getCount() > 0){
            do{
                kamusModel = new KamusModel();
                kamusModel.setAbjad(cursor.getString(cursor.getColumnIndexOrThrow(ABJADIndo)));
                kamusModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCIndo)));
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<KamusModel> getAllData(){
        Cursor cursor = database.query(TABLE_NAME_INDO,
                null,
                null,
                null,
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
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCIndo)));
                kamusModel.setAbjad(cursor.getString(cursor.getColumnIndexOrThrow(ABJADIndo)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            }while ( !cursor.isAfterLast());

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
        String sql = String.format("INSERT INTO %s (%s ,%s ) VALUES (?, ?)", TABLE_NAME_INDO, ABJADIndo, DESCIndo);
        SQLiteStatement stm = database.compileStatement(sql);
        stm.bindString(1,kamusModel.getAbjad());
        stm.bindString(2,kamusModel.getDesc());
        stm.execute();
        stm.clearBindings();
    }

    public int update(KamusModel kamusModel){
        ContentValues contentValues =new ContentValues();
        contentValues.put(ABJADIndo,kamusModel.getAbjad());
        contentValues.put(DESCIndo,kamusModel.getDesc());
        return database.update(TABLE_NAME_INDO,contentValues,_ID+" ='"+kamusModel.getId()+"'",null);

    }

    public int delete(int id){
        return database.delete(TABLE_NAME_INDO,_ID+" ='"+id+"'",null);
    }
}
