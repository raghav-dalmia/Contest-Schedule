package com.example.contestschedule.ui.developer_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.contestschedule.R;

public class DeveloperActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_developer );
        initActionBar();
    }
    
    private void initActionBar(){
        setSupportActionBar( findViewById( R.id.developerToolbar ) );
        getSupportActionBar().setHomeAsUpIndicator( R.drawable.ic_back );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }
    
    
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        switch ( item.getItemId() ){
            case android.R.id.home:
                finish( );
                break;
            default:
                break;
        }
        
        return super.onOptionsItemSelected( item );
    }
    
}