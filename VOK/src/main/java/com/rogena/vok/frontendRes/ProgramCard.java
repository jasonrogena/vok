package com.rogena.vok.frontendRes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;

import com.rogena.vok.backendRes.DataHandler;
import com.rogena.vok.backendRes.Program;
import com.rogena.vok.customViews.Card;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by jason on 7/10/13.
 */
public class ProgramCard extends Card
{
    private final Program program;
    private int imageQuality;
    private ImageFetcher fetcher;
    public ProgramCard(Context context, Program program)
    {
        super(context);
        this.program=program;
        setPrimaryText(program.getName());
        setSecondaryText("on "+program.getParentStation().getName());
        imageQuality=90;//TODO: get quality from shared preferences
        fetcher=new ImageFetcher();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //TODO: check when card is in focus so as to start fetching image. picture should only be fetched once
        if(isCardImageSet()==false)
        {
            fetcher.
        }
    }

    private class ImageFetcher extends AsyncTask<Integer,Integer,Bitmap>
    {
        @Override
        protected Bitmap doInBackground(Integer... integers)
        {
            try
            {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY))//external storage available to read
                {
                    File picture=new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),program.getImageURL());
                    if(picture.exists())
                    {
                        return BitmapFactory.decodeFile(picture.getAbsolutePath());//TODO: does this work
                    }
                }
                URL url=new URL(DataHandler.SERVER_BASE_URL+"/media/"+program.getImageURL());
                Bitmap imageFromServer=BitmapFactory.decodeStream(url.openConnection().getInputStream());
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))//READ AND WRITE PERMISSIONS
                {
                    String dir=getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                    FileOutputStream outputStream=new FileOutputStream(dir+"/"+program.getImageURL());
                    String[] spit=program.getImageURL().split(".");
                    if(spit.length==2)
                    {
                        String extension=spit[1];
                        extension.toUpperCase();
                        if(extension.equals("PNG"))
                        {
                            imageFromServer.compress(Bitmap.CompressFormat.PNG,imageQuality,outputStream);
                        }
                        else if(extension.equals("JPEG"))
                        {
                            imageFromServer.compress(Bitmap.CompressFormat.JPEG,imageQuality,outputStream);
                        }
                        else
                        {
                            System.err.println("unable to save image with unsupported format");
                        }
                    }
                }
                return imageFromServer;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            setImageBitmap(bitmap);
        }
    }

}
