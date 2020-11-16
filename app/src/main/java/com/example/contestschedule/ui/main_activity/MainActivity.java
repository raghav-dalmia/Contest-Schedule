package com.example.contestschedule.ui.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.example.contestschedule.R;
import com.example.contestschedule.dao.VariableManager;
import com.example.contestschedule.dao.sPrefrences.PrefrencesDAO;
import com.example.contestschedule.ui.base.BasicActivity;
import com.example.contestschedule.ui.developer_activity.DeveloperActivity;
import com.example.contestschedule.ui.main_activity.adapter.ContestPagerAdapter;
import com.example.contestschedule.ui.main_activity.fragments.ContestFragment;
import com.example.contestschedule.ui.setting_activity.SettingActivity;
import com.example.contestschedule.util.AppConstants;
import com.example.contestschedule.util.Contest;
import com.example.contestschedule.util.FetchContestUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends BasicActivity {
    
    private DrawerLayout drawerLayout;
    private ContestPagerAdapter contestPagerAdapter;
    
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    
        Log.d( TAG, "onCreate: ---------------------" );
        
        initDrawer();
        initPager();
        refreshActivity();
    }
    
    @Override
    public void refreshActivity( ) {
        refreshLayout = findViewById( R.id.swipeRefreshMain );
        refreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener( ) {
            @Override
            public void onRefresh( ) {
                refreshLayout.setRefreshing( false );
                new BackgroundTask().execute(  );
            }
        } );
    }
    
    private void initDrawer(){
        
        Toolbar toolbar = findViewById( R.id.homeToolbar );
        NavigationView navigationView = findViewById( R.id.homeNavView );
        drawerLayout = findViewById( R.id.navDrawer );
        
        setSupportActionBar( toolbar );
    
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.open, R.string.close );
        drawerLayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener( ) {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem item ) {
                switch ( item.getItemId() ){
                    case R.id.drawer_settings:
                        drawerLayout.closeDrawer( GravityCompat.START );
                        navigateToSeting();
                        break;
                    case R.id.drawer_developers:
                        drawerLayout.closeDrawer( GravityCompat.START );
                        navigateToDeveloper();
                        break;
                    case R.id.drawer_shareUs:
                        drawerLayout.closeDrawer( GravityCompat.START );
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Contest Schedule App\n" + AppConstants.appUrl );
                        startActivity(Intent.createChooser(shareIntent, "Share..."));
                        break;
                    case R.id.drawer_refresh:
                        drawerLayout.closeDrawer( GravityCompat.START );
                        new BackgroundTask().execute(  );
                        break;
                    case R.id.drawer_suggestUs:
                        drawerLayout.closeDrawer( GravityCompat.START );
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"raghavdalmia2000@gmail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[Contest Schedule App] Suggestions");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        break;
                    default:
                        break;
                }
                return true;
            }
        } );
        
    }
    
    void navigateToSeting(){
        Intent intent1 = new Intent( this, SettingActivity.class );
        startActivity( intent1 );
    }
    
    void navigateToDeveloper(){
        Intent intent2 = new Intent( this, DeveloperActivity.class );
        startActivity( intent2 );
    }
    
    
    void initPager(){
    
        ArrayList<String> tabNameList = new ArrayList <>(  );
        tabNameList.add( "Short" );
        tabNameList.add( "Long" );
        
        TabLayout tabLayout = findViewById( R.id.tab_layout );
        ViewPager viewPager = findViewById( R.id.view_pager );
        contestPagerAdapter = new ContestPagerAdapter( getSupportFragmentManager() );
        
        for(int i=0;i<tabNameList.size();i++){
            Bundle bundle = new Bundle(  );
            ContestFragment fragment = ContestFragment.newInstance( i );
            contestPagerAdapter.addFragment( fragment, tabNameList.get( i ) );
        }
        
        viewPager.setAdapter( contestPagerAdapter );
        tabLayout.setupWithViewPager( viewPager );
        
        if(shortContestList.size()==0 && longContestList.size()==0)
            new BackgroundTask().execute(  );
    }
    
    public class BackgroundTask extends AsyncTask<Void, Void, Void>{
    
        @Override
        protected void onPreExecute( ) {
            super.onPreExecute( );
            showProgressDialog("Updating constest list...");
            shortContestList.clear();
            longContestList.clear();
        }
        
        @Override
        protected Void doInBackground( Void... voids ) {
            
            FetchContestUtil fetchContestUtil = new FetchContestUtil( dao );
            if(dao.getCodeforcesStatus())
                fetchContestUtil.fetchCodeforcesContest();
            if(dao.getCodechefStatus())
                fetchContestUtil.fetchCodechefContest();
            if(dao.getAtcoderStatus())
                fetchContestUtil.fetchAtcoderContest();
            
            return null;
        }
    
        @Override
        protected void onPostExecute( Void aVoid ) {
            super.onPostExecute( aVoid );
    
            Collections.sort( shortContestList, new Comparator < Contest >( ) {
                @Override
                public int compare( Contest o1, Contest o2 ) {
                    return o1.getStartDateTime().compareTo( o2.getStartDateTime() );
                }
            } );
    
            Collections.sort( longContestList, new Comparator < Contest >( ) {
                @Override
                public int compare( Contest o1, Contest o2 ) {
                    return o1.getStartDateTime().compareTo( o2.getStartDateTime() );
                }
            } );
            
            List<ContestFragment> fragmentList = contestPagerAdapter.getFragmentList();
            fragmentList.get( 0 ).setContestList( shortContestList );
            fragmentList.get( 1 ).setContestList( longContestList );
    
            hideProgressDialog();
        }
    
    }
    
    @Override
    public void onBackPressed( ) {
        if(drawerLayout.isDrawerOpen( GravityCompat.START ))
            drawerLayout.closeDrawer( GravityCompat.START );
        else
            super.onBackPressed( );
    }
    
    
}