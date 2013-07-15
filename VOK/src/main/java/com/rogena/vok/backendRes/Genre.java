package com.rogena.vok.backendRes;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jason on 7/10/13.
 */
public class Genre
{
    public static final String TABLE_NAME="genre";
    public static final String ID="id";
    public static final String NAME="name";
    public static final String DATE_ADDED_M="date_added_m";

    private final int id;
    private String name;
    private long dateAddedM;
    private GenreUpdatedListener genreUpdatedListener;
    public Genre (int id, String name, long dateAddedM)
    {
        this.id=id;
        this.name=name;
        this.dateAddedM=dateAddedM;
    }
    public int getId()
    {
        return id;
    }
    public void update(String name, long dateAddedM)
    {
        this.name=name;
        this.dateAddedM=dateAddedM;
        if(this.genreUpdatedListener !=null)
        {
            genreUpdatedListener.onGenreUpdated(this);
        }
    }

    public interface GenreUpdatedListener
    {
        public void onGenreUpdated(Genre genre);
    }

    public void setGenreUpdatedListener(GenreUpdatedListener genreUpdatedListener)
    {
        this.genreUpdatedListener = genreUpdatedListener;
    }

    public void updateLocalDatabaseCopy(DatabaseHelper databaseHelper, SQLiteDatabase sqLiteDatabase)
    {
        String[][] results=databaseHelper.runSelectQuery(sqLiteDatabase, TABLE_NAME, new String[]{String.valueOf(id)}, String.valueOf(id), null, null, null, null, null);
        if(results==null)//new entry
        {
            String[] columns={ID,NAME,DATE_ADDED_M};
            String[] values={String.valueOf(id),name,String.valueOf(dateAddedM)};
            databaseHelper.runInsertQuery(TABLE_NAME,columns,values,sqLiteDatabase);
        }
        else
        {
            String[] columns={NAME,DATE_ADDED_M};
            String[] values={name,String.valueOf(dateAddedM)};
            databaseHelper.runUpdateQuery(sqLiteDatabase,TABLE_NAME,columns,values,ID+"=?",new String[]{String.valueOf(id)});
        }
    }
}
