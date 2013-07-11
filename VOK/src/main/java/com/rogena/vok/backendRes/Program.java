package com.rogena.vok.backendRes;


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

    private final int id;
    private String name;
    private Station parentStation;
    private Date startDate;
    private Genre programGenre;
    private long dateAddedM;
    private ProgramUpdatedListener programUpdatedListener;
    public Program(int id, String name, Station parentStation, Date startDate, Genre programGenre, long dateAddedM)
    {
        this.id=id;
        this.name=name;
        this.parentStation=parentStation;
        this.startDate = startDate;
        this.programGenre=programGenre;
        this.dateAddedM=dateAddedM;
    }

    public int getId()
    {
        return id;
    }

    public void update(String name, Station parentStation, Date startDate, Genre programGenre, long dateAddedM)
    {
        this.name=name;
        this.parentStation=parentStation;
        this.startDate = startDate;
        this.programGenre=programGenre;
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
    //TODO: implement interface for the onUpdate listener
}
