package com.rogena.vok.frontendRes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rogena.vok.R;
import com.rogena.vok.customViews.Card;

/**
 * Created by jason on 6/19/13.
 */
public class RecommendedTabFragment extends Fragment
{
    public static final String TITLE="Recommended";
    public static final int POSITION=1;
    private LinearLayout linearLayout;
    private View mainView;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)//called when the fragment should create its UI
    {
        super.onCreateView(layoutInflater,container,savedInstanceState);
        View view=layoutInflater.inflate(R.layout.fragment_recommended,container,false);
        mainView=view;
        linearLayout=(LinearLayout)view.findViewById(R.id.linear_layout);
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
        Card newCard=new Card(getActivity());
        //newCard.setBackgroundWithShadow(false);
        newCard.setImage(getResources().getDrawable(R.drawable.cheche));
        newCard.setPrimaryText("Cheche");
        newCard.setSecondaryText("on Citizen TV");
        newCard.setSwipedOutThreshold(0.4f);
        newCard.setResetDuration(200);
        newCard.setGoneDuration(100);
        newCard.setMoveUpDuration(200);
        newCard.setFocusable(true);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(newCard,layoutParams);
    }

    /*called when the fragments becomes visible
    * */
    @Override
    public void onStart()
    {
        super.onStart();
    }
}
