package com.rogena.vok;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.rogena.vok.frontendRes.KenyanTVFragment;
import com.rogena.vok.frontendRes.MyProgramsFragment;
import com.rogena.vok.frontendRes.VOKArrayAdapter;

public class LandingActivity extends FragmentActivity implements ListView.OnItemClickListener, ActionBar.OnNavigationListener
{
    private Menu menu;
    private ListView drawerList;
    private String[] parentFeatures;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String currentFragment=KenyanTVFragment.NAME;
    private SpinnerAdapter genreFilter;
    private SpinnerAdapter stationFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        if(savedInstanceState==null)//only run if this is the first time
        {
            initKenyaTVFragment(false);
        }

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        parentFeatures=getResources().getStringArray(R.array.parent_features);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer,R.string.drawer_opened_description,R.string.drawer_closed_description)
        {
            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle("");
                getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            }
            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(R.string.app_name);
                getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        drawerList=(ListView)findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item, parentFeatures));
        drawerList.setItemChecked(0, true);
        drawerList.setOnItemClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration)
    {
        super.onConfigurationChanged(newConfiguration);
        actionBarDrawerToggle.onConfigurationChanged(newConfiguration);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        //bundle.putInt("tab",getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO: check what frame is active
        getMenuInflater().inflate(R.menu.recommended_programs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //add FLAG_ACTIVITY_CLEAR_TOP flag to intent if navigating up
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyUp(int KeyCode, KeyEvent keyEvent)
    {
        if(KeyCode==KeyEvent.KEYCODE_SEARCH)
        {
            menu.getItem(R.id.action_search).expandActionView();
        }
        else if(KeyCode==KeyEvent.KEYCODE_BACK)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyUp(KeyCode,keyEvent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        if(parent==drawerList)
        {
            if(position==0)//kenyan tv
            {
                if(!currentFragment.equals(KenyanTVFragment.NAME))
                {
                    initKenyaTVFragment(true);
                }

            }
            else if(position==1)//my programs
            {
                if(!currentFragment.equals(MyProgramsFragment.NAME))
                {
                    initMyProgramsFragment(true);
                }
            }
            else if(position==2)//channel schedules
            {

            }
            drawerLayout.closeDrawers();
        }
    }

    private void initKenyaTVFragment(boolean isReplacing)
    {
        KenyanTVFragment kenyanTVFragment=new KenyanTVFragment();
        currentFragment=kenyanTVFragment.NAME;
        genreFilter=new VOKArrayAdapter(this,R.layout.vok_spinner_item,getResources().getStringArray(R.array.genres),currentFragment);
        stationFilter=new VOKArrayAdapter(this,R.layout.vok_spinner_item,getResources().getStringArray(R.array.stations),currentFragment);
        if(isReplacing)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,kenyanTVFragment).commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,kenyanTVFragment).commit();
        }
        getActionBar().setListNavigationCallbacks(genreFilter,this);
    }

    private void initMyProgramsFragment(boolean isReplacing)
    {
        MyProgramsFragment myProgramsFragment=new MyProgramsFragment();
        currentFragment=myProgramsFragment.NAME;
        genreFilter=new VOKArrayAdapter(this,R.layout.vok_spinner_item,getResources().getStringArray(R.array.genres),currentFragment);
        stationFilter=new VOKArrayAdapter(this,R.layout.vok_spinner_item,getResources().getStringArray(R.array.stations),currentFragment);

        if(isReplacing)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myProgramsFragment).commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,myProgramsFragment).commit();
        }
        getActionBar().setListNavigationCallbacks(genreFilter,this);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l)
    {
        return false;
    }
}
