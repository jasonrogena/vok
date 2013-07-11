package com.rogena.vok.backendRes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jason on 7/10/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME="vok";

    public DatabaseHelper(Context context)
    {
        //TODO: remember to fetch the database version from the shared preferences
        super(context,DB_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE "+Program.TABLE_NAME+" ("+Program.ID+" INTEGER PRIMARY KEY, "+Program.GENRE_ID+" INTEGER, "+Program.NAME+" TEXT, "+Program.START_DATE+" TEXT, "+Program.STATION_ID+" INTEGER, "+Program.DATE_ADDED_M+" INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE "+Station.TABLE_NAME+" ("+Station.ID+" INTEGER PRIMARY KEY, "+Station.NAME+" TEXT, "+Station.DATE_ADDED_M+" INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE "+Genre.TABLE_NAME+" ("+Genre.ID+" INTEGER PRIMARY KEY, "+Genre.NAME+" TEXT, "+Genre.DATE_ADDED_M+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Program.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Genre.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Station.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public String[][] runSelectQuery(SQLiteDatabase db, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
    {
        Cursor cursor=db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if(cursor.getCount()>0)
        {
            String[][] result=new String[cursor.getCount()][columns.length];
            Log.d("runSelectQuery", "number of rows " + String.valueOf(cursor.getCount()));
            int c1=0;
            cursor.moveToFirst();
            while(c1<cursor.getCount())
            {
                int c2=0;
                while(c2<columns.length)
                {
                    result[c1][c2]=cursor.getString(c2);
                    c2++;
                }
                if(c1!=cursor.getCount()-1)//is not the last row
                {
                    cursor.moveToNext();
                }
                c1++;
            }
            cursor.close();
            return result;
        }
        else
        {
            return null;
        }
    }

    public void runDeleteQuery(SQLiteDatabase db, String table, String _id)
    {
        db.delete(table, "_id=?", new String[]{_id});
    }

    public void runInsertQuery(String table,String[] columns,String[] values,SQLiteDatabase db)
    {
        if(columns.length==values.length)
        {
            ContentValues cv=new ContentValues();
            int count=0;
            while(count<columns.length)
            {
                cv.put(columns[count], values[count]);
                count++;
            }
            db.insert(table, null, cv);
            cv.clear();
        }
    }

    public void runQuery(SQLiteDatabase db, String query)//non return queries
    {
        db.execSQL(query);
    }
}