package com.rogena.vok.backendRes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jason on 7/10/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME="vok";

    public DatabaseHelper(Context context,int databaseVersion)
    {
        super(context,DB_NAME,null,databaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE "+Program.TABLE_NAME+" ("+Program.ID+" INTEGER PRIMARY KEY, "+Program.GENRE_ID+" INTEGER, "+Program.NAME+" TEXT, "+Program.START_DATE+" TEXT, "+Program.STATION_ID+" INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE "+Station.TABLE_NAME+" ("+Station.ID+" INTEGER PRIMARY KEY, "+Station.NAME+" TEXT, "+Station.DATE_ADDED_M+" INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE "+Genre.TABLE_NAME+" ("+Genre.ID+" INTEGER PRIMARY KEY, "+Genre.NAME+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Program.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Genre.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Station.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}