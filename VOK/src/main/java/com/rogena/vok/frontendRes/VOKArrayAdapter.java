package com.rogena.vok.frontendRes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rogena.vok.R;

/**
 * Created by jason on 6/23/13.
 */
public class VOKArrayAdapter extends ArrayAdapter<String>
{
    private String[] objects;
    private String activityTitle;
    public VOKArrayAdapter(Context context, int textViewResourceId, String[] objects, String activityTitle)
    {
        super(context, textViewResourceId, objects);
        this.objects=objects;
        this.activityTitle=activityTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view=convertView;
        if(view==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.vok_spinner_selected_item,null);
            String name=objects[position];
            TextView activityTitleTV=(TextView)view.findViewById(R.id.activity_title);
            activityTitleTV.setText(activityTitle);
            if(name!=null)
            {
                TextView text1=(TextView)view.findViewById(R.id.text1);
                text1.setText(name);
            }
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View view=convertView;
        //parent.setBackgroundColor(getContext().getResources().getColor(R.color.theme_green_color));
        if(view==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.vok_spinner_item,null);
            String name=objects[position];
            if(name!=null)
            {
                TextView text1=(TextView)view.findViewById(R.id.text1);
                text1.setText(name);
            }
        }
        return view;
    }
}
