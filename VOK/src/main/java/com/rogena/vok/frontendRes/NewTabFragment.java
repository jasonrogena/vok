package com.rogena.vok.frontendRes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rogena.vok.R;

/**
 * Created by jason on 6/19/13.
 */
public class NewTabFragment extends Fragment
{
    public static final String TITLE="New";
    public static final int POSITION=0;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)//called when the fragment should create its UI
    {
        super.onCreateView(layoutInflater,container,savedInstanceState);
        View view=layoutInflater.inflate(R.layout.fragment_new,container,false);
        return view;
    }

    /*
    * called after onCreateView() when the host activity is created
    * initialize all objects that require a context here
    * */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)//called after oncreateview when the host activity is created.
    {
        super.onActivityCreated(savedInstanceState);
    }

    /*called when the fragments becomes visible
    * */
    @Override
    public void onStart() {
        super.onStart();
    }
}
