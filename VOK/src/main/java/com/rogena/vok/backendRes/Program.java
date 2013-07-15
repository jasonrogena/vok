package com.rogena.vok.backendRes;


import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;

/**
 * Created by jason on 7/10/13.
 */
public class Program
{
    public static final String TABLE_NAME="program";
    public static final String ID="id";
    public static final String NAME="name";
    public static final String STATION_ID="station_id";
    public static final String START_DATE="coming_soon";
    public static final String GENRE_ID="genre_id";
    public static final String DATE_ADDED_M="date_added_m";
    public static final String IMAGE_URL="image_url";

    private final int id;
    private String name;
    private Station parentStation;
    private Date startDate;
    private Genre programGenre;
    private String imageURL;
    private long dateAddedM;
    private ProgramUpdatedListener programUpdatedListener;
    public Program(int id, String name, Station parentStation, Date startDate, Genre programGenre, String imageURL, long dateAddedM)
    {
        this.id=id;
        this.name=name;
        this.parentStation=parentStation;
        this.startDate = startDate;
        this.programGenre=programGenre;
        this.imageURL=imageURL;
        this.dateAddedM=dateAddedM;
    }

    public int getId()
    {
        return id;
    }

    public void update(String name, Station parentStation, Date startDate, Genre programGenre, String imageURL, long dateAddedM)
    {
        this.name=name;
        this.parentStation=parentStation;
        this.startDate = startDate;
        this.programGenre=programGenre;
        this.imageURL=imageURL;
        this.dateAddedM=dateAddedM;
        if(this.programUpdatedListener!=null)
        {
            programUpdatedListener.onProgramUpdated(this);
        }
    }

    public interface ProgramUpdatedListener
    {
        public void onProgramUpdated(Program program);
    }

    public void setProgramUpdatedListener(ProgramUpdatedListener programUpdatedListener)
    {
        this.programUpdatedListener=programUpdatedListener;
    }

    public void updateLocalDatabaseCopy(DatabaseHelper databaseHelper, SQLiteDatabase sqLiteDatabase)
    {
        String[][] results=databaseHelper.runSelectQuery(sqLiteDatabase, TABLE_NAME, new String[]{String.valueOf(id)}, String.valueOf(id), null, null, null, null, null);
        if(results==null)//new entry
        {
            String[] columns={ID,NAME,STATION_ID,START_DATE,GENRE_ID,IMAGE_URL,DATE_ADDED_M};
            String[] values={String.valueOf(id),name,String.valueOf(parentStation.getId()),startDate.toString(),String.valueOf(programGenre.getId()),imageURL,String.valueOf(dateAddedM)};
            databaseHelper.runInsertQuery(TABLE_NAME,columns,values,sqLiteDatabase);
        }
        else
        {
            String[] columns={NAME,STATION_ID,START_DATE,GENRE_ID,IMAGE_URL,DATE_ADDED_M};
            String[] values={name,String.valueOf(parentStation.getId()),startDate.toString(),String.valueOf(programGenre.getId()),imageURL,String.valueOf(dateAddedM)};
            databaseHelper.runUpdateQuery(sqLiteDatabase,TABLE_NAME,columns,values,ID+"=?",new String[]{String.valueOf(id)});
        }
    }

    public String getName() {
        return name;
    }

    public Station getParentStation() {
        return parentStation;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Genre getProgramGenre() {
        return programGenre;
    }

    public long getDateAddedM() {
        return dateAddedM;
    }

    public ProgramUpdatedListener getProgramUpdatedListener() {
        return programUpdatedListener;
    }
}
