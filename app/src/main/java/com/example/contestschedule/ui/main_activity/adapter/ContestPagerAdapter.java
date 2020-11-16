// created by: raghav
// created on: 14/11/20, 12:06 AM

package com.example.contestschedule.ui.main_activity.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.contestschedule.ui.main_activity.fragments.ContestFragment;

import java.util.ArrayList;
import java.util.List;

public class ContestPagerAdapter extends FragmentPagerAdapter {
    
    ArrayList<String> tabNameList = new ArrayList <>(  );
    List< ContestFragment > fragmentList = new ArrayList <>(  );
    
    public ContestPagerAdapter( @NonNull FragmentManager fm ) {
        super( fm );
    }
    
    public void addFragment(ContestFragment fragment, String title){
        tabNameList.add( title );
        fragmentList.add( fragment );
    }
    
    public List < ContestFragment > getFragmentList( ) {
        return fragmentList;
    }
    
    @NonNull
    @Override
    public Fragment getItem( int position ) {
        return fragmentList.get( position );
    }
    
    @Override
    public int getCount( ) {
        return fragmentList.size();
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle( int position ) {
        return tabNameList.get( position );
    }
    
}
