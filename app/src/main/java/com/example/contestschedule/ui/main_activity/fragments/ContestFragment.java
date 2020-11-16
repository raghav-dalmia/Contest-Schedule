package com.example.contestschedule.ui.main_activity.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contestschedule.R;
import com.example.contestschedule.dao.VariableManager;
import com.example.contestschedule.ui.base.BaseFragment;
import com.example.contestschedule.ui.main_activity.adapter.ContestListAdapter;
import com.example.contestschedule.util.AppConstants;
import com.example.contestschedule.util.Contest;

import java.util.List;

public class ContestFragment extends BaseFragment implements VariableManager {
    
    private static final String TAG = "ContestFragment";
    private ContestListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView contestListView;
    
    public ContestFragment( ) {
        // Required empty public constructor
    }
    
    public static ContestFragment newInstance( int contestKey ) {
        Bundle bundle = new Bundle(  );
        bundle.putInt( AppConstants.contestAdapterKey, contestKey);
        ContestFragment fragment = new ContestFragment( );
        fragment.setArguments( bundle );
        return fragment;
    }
    
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate: ---------------------------------" );
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
    
        Log.d( TAG, "onCreateView: ----------------------------------" );
        View view = inflater.inflate( R.layout.fragment_short_contest, container, false );
        contestListView = view.findViewById( R.id.contestList );
        layoutManager = new LinearLayoutManager( view.getContext() );
        contestListView.setLayoutManager( layoutManager );
        
        int listKey = getArguments().getInt( AppConstants.contestAdapterKey, 0 );
        adapter = new ContestListAdapter( ((listKey==0)?shortContestList:longContestList), view.getContext() );
        contestListView.setAdapter( adapter );
        
        
        return view;
    }
    
    public void setContestList( List< Contest > contestList ){
        Log.d( TAG, "setContestList: --------------------------" + contestList.size() );
        adapter.setContestList( contestList );
        adapter.notifyDataSetChanged();
    }
    
}