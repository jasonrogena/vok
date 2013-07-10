package com.rogena.vok.backendRes;

/**
 * Created by jason on 7/10/13.
 */
public class Genre
{
    public static final String TABLE_NAME="genre";
    public static final String ID="id";
    public static final String NAME="name";

    private final int id;
    private final String name;
    public Genre (int id, String name)
    {
        this.id=id;
        this.name=name;
    }
}
