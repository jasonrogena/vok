package com.rogena.vok.backendRes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 7/10/13.
 */
public class DataHandler
{
    private final Context context;//make sure the datahandler object is used in only one activity then destroyed
    private final SQLiteDatabase writableDatabase;
    private final DatabaseHelper databaseHelper;
    public static final String DATE_FORMAT="yyyy-MM-dd";
    private static int httpPostTimeout=20000;
    private static int httpResponseTimeout=20000;
    private final String serverBaseURL="http://10.0.2.2/~jason/vok";

    public DataHandler(Context context)
    {
        this.context=context;
        databaseHelper=new DatabaseHelper(this.context);
        writableDatabase=databaseHelper.getWritableDatabase();
    }

    public void closeDatabase()
    {
        writableDatabase.close();
    }

    public List<Program> updateProgramsFromLocal(List<Program> programs, boolean alsoRemote)//fetch from local database
    {
        //TODO: if no programs in local database, start update process immediately
        //TODO: make sure you close the database
        if(programs!=null)
        {
            if(writableDatabase.isOpen())
            {
                String[] columns={Program.ID,Program.NAME,Program.START_DATE,Program.STATION_ID,Program.GENRE_ID,Program.DATE_ADDED_M};
                String[][] result=databaseHelper.runSelectQuery(writableDatabase,Program.TABLE_NAME, columns, null, null, null, null, null, null);
                if(result!=null)//more than 0 rows fetched
                {
                    for(int i=0;i<result.length;i++)//all of the results
                    {
                        int id=Integer.valueOf(result[i][0]);
                        String name=result[i][1];
                        String startDate=result[i][2];
                        int stationId=Integer.valueOf(result[i][3]);
                        int genreId=Integer.valueOf(result[i][4]);
                        long dateAddedM=Long.valueOf(result[i][5]);
                        if(getProgramPositionInList(programs, Integer.valueOf(result[i][0]))==-1)//not in the list
                        {
                            try
                            {
                                programs.add(new Program(id,name,getStation(stationId),new Date(new SimpleDateFormat(DATE_FORMAT).parse(startDate).getTime()), getGenre(genreId), dateAddedM));
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else//update program in list
                        {
                            try
                            {
                                programs.get(getProgramPositionInList(programs, Integer.valueOf(result[i][0]))).update(name,getStation(stationId),new Date(new SimpleDateFormat(DATE_FORMAT).parse(startDate).getTime()), getGenre(genreId), dateAddedM);
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }
        if(alsoRemote||programs.size()==0)//you really don't want to display a blank screen to a user after a splash screen
        {
            return updateProgramsFromRemote(programs);
        }
        else
        {
            return  programs;
        }
    }

    public List<Program> updateProgramsFromRemote(List<Program> currentPrograms)//fetch from network
    {
        //TODO: remember to update last online update time
        //TODO: remember to use server's timestamp and not phone
        //TODO: make sure you close the database
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, httpPostTimeout);
        HttpConnectionParams.setSoTimeout(httpParameters, httpResponseTimeout);
        HttpClient httpClient=new DefaultHttpClient(httpParameters);
        HttpPost httpPost=new HttpPost(serverBaseURL+"/getLatestPrograms.php");
        try
        {
            List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("last_updated",String.valueOf(getLastProgramUpdate())));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==200)
            {
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Station getStation(int id)
    {
        if(writableDatabase.isOpen())
        {
            String selection=String.valueOf(id);
            String[] columns={Station.ID,Station.NAME,Station.DATE_ADDED_M};
            String[][] results=databaseHelper.runSelectQuery(writableDatabase, Station.TABLE_NAME, columns, selection, null, null, null, null, null);
            if(results!=null)//at least one row has been fetched
            {
                return new Station(Integer.valueOf(results[0][0]),results[0][1], Long.valueOf(results[0][2]));
            }
        }
        return getRemoteStation(id);
    }

    public Genre getGenre(int id)
    {
        if(writableDatabase.isOpen())
        {
            String selection=String.valueOf(id);
            String[] columns={Genre.ID,Genre.NAME,Genre.DATE_ADDED_M};
            String[][] results=databaseHelper.runSelectQuery(writableDatabase, Genre.TABLE_NAME, columns, selection, null, null, null, null, null);
            if(results!=null)//at least one row fetched
            {
                return new Genre(Integer.valueOf(results[0][0]), results[0][1], Long.valueOf(results[0][2]));
            }
        }
        return getRemoteGenre(id);
    }

    private Station getRemoteStation(int id)
    {
        //TODO: remember to implement this
        return null;
    }

    private Genre getRemoteGenre(int id)
    {
        //TODO: remember to implement this
        return null;
    }

    private long getLastProgramUpdate()
    {
        if(writableDatabase.isOpen())
        {
            String[] columns={Program.DATE_ADDED_M};
            String[][] results=databaseHelper.runSelectQuery(writableDatabase,Program.TABLE_NAME,columns,null,null,null,null,Program.DATE_ADDED_M+" DESC","1");
            if(results!=null)
            {
                return Long.valueOf(results[0][0]);
            }
        }
        return 0;
    }


    private int getProgramPositionInList(List<Program> programs, int id)
    {
        for (int i=0; i>programs.size(); i++)
        {
            if(programs.get(i).getId()==id)
            {
                return i;
            }
        }
        return -1;
    }

}