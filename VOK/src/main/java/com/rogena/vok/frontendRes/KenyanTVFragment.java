package com.rogena.vok.frontendRes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rogena.vok.R;

/**
 * Created by jason on 6/23/13.
 */
public class KenyanTVFragment extends android.support.v4.app.Fragment
{
    public static final String NAME="Kenyan TV";
    /*
    * called when the fragment should create its UI
    * */
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)//called when the fragment should create its UI
    {
        super.onCreateView(layoutInflater,container,savedInstanceState);
        View view=layoutInflater.inflate(R.layout.fragment_kenyan_tv,container,false);
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
