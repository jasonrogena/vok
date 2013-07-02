package com.rogena.vok.frontendRes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rogena.vok.R;

/**
 * Created by jason on 6/23/13.
 */
public class KenyanTVFragment extends Fragment
{
    public static final String NAME="Kenyan TV";
    private ViewPager viewPager;
    private View mainView;
    /*
    * called when the fragment should create its UI
    * */
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)//called when the fragment should create its UI
    {
        super.onCreateView(layoutInflater,container,savedInstanceState);
        View view=layoutInflater.inflate(R.layout.fragment_kenyan_tv,container,false);
        mainView=view;
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
        viewPager=(ViewPager)mainView.findViewById(R.id.view_pager);
        VOKFragmentPagerAdapter pagerAdapter=new VOKFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1,true);
    }

    /*called when the fragments becomes visible
    * */
    @Override
    public void onStart() {
        super.onStart();
    }

    private class VOKFragmentPagerAdapter extends FragmentPagerAdapter
    {
        public VOKFragmentPagerAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int i)
        {
            if(i==NewTabFragment.POSITION)
            {
                return new NewTabFragment();

            }
            else if(i==RecommendedTabFragment.POSITION)
            {
                return new RecommendedTabFragment();
            }
            else if(i==TrendingTabFragment.POSITION)
            {
                return new TrendingTabFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            if(position==NewTabFragment.POSITION)
            {
                return NewTabFragment.TITLE;
            }
            else if(position==RecommendedTabFragment.POSITION)
            {
                return RecommendedTabFragment.TITLE;
            }
            else if(position==TrendingTabFragment.POSITION)
            {
                return TrendingTabFragment.TITLE;
            }
            return null;
        }
    }
}
