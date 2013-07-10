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

    private final int id;
    private final String name;
    private final Station parentStation;
    private final Date startDate;
    private final Genre programGenre;
    public Program(int id, String name, Station parentStation, Date startDate, Genre programGenre)
    {
        this.id=id;
        this.name=name;
        this.parentStation=parentStation;
        this.startDate = startDate;
        this.programGenre=programGenre;
    }
}
