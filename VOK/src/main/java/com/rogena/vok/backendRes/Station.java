package com.rogena.vok.backendRes;

/**
 * Created by jason on 7/10/13.
 */
public class Station
{
    public static final String TABLE_NAME="station";
    public static final String ID="id";
    public static final String NAME="name";
    public static final String DATE_ADDED_M="date_added_m";

    private final int id;
    private final String name;
    private final long dateAddedM;
    public Station(int id, String name, long dateAddedM)
    {
        this.id=id;
        this.name=name;
        this.dateAddedM=dateAddedM;
    }
}