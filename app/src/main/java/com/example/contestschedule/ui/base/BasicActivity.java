// created by: raghav
// created on: 13/11/20, 9:56 PM

package com.example.contestschedule.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.contestschedule.dao.VariableManager;
import com.example.contestschedule.dao.sPrefrences.PrefrencesDAO;
import com.example.contestschedule.ui.setting_activity.SettingActivity;

public abstract class BasicActivity extends AppCompatActivity implements VariableManager {
    
    private ProgressDialog progressDialog;
    protected PrefrencesDAO dao;
    protected SwipeRefreshLayout refreshLayout;
    
    
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        progressDialog = new ProgressDialog( this );
        dao = new PrefrencesDAO( this );
    }
    
    public abstract void refreshActivity();
    
    public void showProgressDialog( ){
        hideProgressDialog();
        
        if(progressDialog.getWindow()!=null)
            progressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        progressDialog.setCancelable( false );
        progressDialog.setCanceledOnTouchOutside( false );
        
        progressDialog.show();
    }
    
    public void showProgressDialog( String message ){
        hideProgressDialog();
        
        progressDialog.setMessage( message );
        progressDialog.setCancelable( false );
        progressDialog.setCanceledOnTouchOutside( false );
        
        progressDialog.show();
    }
    
    public void hideProgressDialog( ){
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    
    public void showMessage(String message){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
    }
    
    public void showError(String error){
        Toast.makeText( this, error, Toast.LENGTH_LONG ).show();
    }
    
    
}
