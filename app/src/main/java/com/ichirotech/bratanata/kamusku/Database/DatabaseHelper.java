package com.ichirotech.bratanata.kamusku.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.TABLE_NAME_INDO;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.DatabaseIndoColumns.ABJADIndo;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracIndo.DatabaseIndoColumns.DESCIndo;

import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.TABLE_NAME_ENG;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.DatabaseEngColumns.ABJADEng;
import static com.ichirotech.bratanata.kamusku.Database.DatabaseContracEng.DatabaseEngColumns.DESCEng;


public class DatabaseHelper extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "dbKamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_INDO_WORD = String.format(" create table %s (" +
            "%s integer primary key autoincrement, " +
            "%s text not null," +
            "%s text not null);",
            TABLE_NAME_INDO,
            DatabaseContracIndo.DatabaseIndoColumns._ID,
            ABJADIndo,
            DESCIndo);
    public static String CREATE_TABLE_ENG_WORD = String.format(" create table %s (" +
                    "%s integer primary key autoincrement, " +
                    "%s text not null," +
                    "%s text not null);",
            TABLE_NAME_ENG,
            DatabaseContracEng.DatabaseEngColumns._ID,
            ABJADEng,
            DESCEng);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INDO_WORD);
        db.execSQL(CREATE_TABLE_ENG_WORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContracIndo.TABLE_NAME_INDO );
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContracEng.TABLE_NAME_ENG);
        onCreate(db);
    }
}
