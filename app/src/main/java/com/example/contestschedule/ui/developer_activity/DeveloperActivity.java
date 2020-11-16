package com.example.contestschedule.ui.developer_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.contestschedule.R;
import com.example.contestschedule.ui.browser_activity.BrowserActivity;

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
    
    public void openGmail( View v ){
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"raghavdalmia2000@gmail.com"});
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
    
    public void openLinkedIn( View v ){
        openBrowser( "https://www.linkedin.com/in/raghav0901/" );
    }
    
    public void openGitHub( View v ){
        openBrowser( "https://github.com/raghav-dalmia/" );
    }
    
    void openBrowser(String url){
        Intent intent = new Intent( this, BrowserActivity.class );
        intent.putExtra( "URL", url );
        startActivity( intent );
    }
    
}