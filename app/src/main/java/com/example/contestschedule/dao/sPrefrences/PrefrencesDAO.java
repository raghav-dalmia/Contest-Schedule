// created by: raghav
// created on: 13/11/20, 9:17 PM

package com.example.contestschedule.dao.sPrefrences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrencesDAO implements PrefrencesKeys {
 
    private String spName = "sp-name";
    private SharedPreferences sharedPreferences;
    
    public PrefrencesDAO( Context context ) {
        sharedPreferences = context.getSharedPreferences( spName, Context.MODE_PRIVATE );
    }
    
    public boolean getCodeforcesStatus( ) {
        return sharedPreferences.getBoolean( showCodeforcesKey, true );
    }
    
    public void setCodeforcesStatus( boolean codeforcesStatus ) {
        sharedPreferences.edit().putBoolean( showCodeforcesKey, codeforcesStatus ).apply();
    }
    
    public boolean getCodechefStatus( ) {
        return sharedPreferences.getBoolean( showCodechefKey, true );
    }
    
    public void setCodechefStatus( boolean codechefStatus ) {
        sharedPreferences.edit().putBoolean( showCodechefKey, codechefStatus ).apply();
    }
    
    public boolean getAtcoderStatus( ) {
        return sharedPreferences.getBoolean( showAtcoderKey, true );
    }
    
    public void setAtcoderStatus( boolean atcoderStatus ) {
        sharedPreferences.edit().putBoolean( showAtcoderKey, atcoderStatus ).apply();
    }
    
    public float getShortContestDuration( ){
        return sharedPreferences.getFloat( shortContestDurationKey, (float)210.0 );
    }
    
    public void setShortContestDuration( float duration ){
        sharedPreferences.edit().putFloat( shortContestDurationKey, duration ).apply();
    }
    
    public String getTimeZone( ) {
        return sharedPreferences.getString( timeZoneKey, "Asia/Kolkata" );
    }
    
    public void setTimeZone( String timeZone ) {
        sharedPreferences.edit().putString( timeZoneKey, timeZone ).apply();
    }
    
    public boolean getTimeFormat( ) {
        return sharedPreferences.getBoolean( timeFormatKey, false );
    }
    
    public void setTimeFormat( Boolean timeFormat ) {
        sharedPreferences.edit().putBoolean( timeFormatKey, timeFormat ).apply();
    }
    
    public void setDefault(){
        sharedPreferences.edit().clear().apply();
    }
    
}
