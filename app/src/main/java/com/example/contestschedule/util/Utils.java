// created by: raghav
// created on: 14/11/20, 12:43 AM

package com.example.contestschedule.util;

import android.content.Context;

import com.example.contestschedule.dao.sPrefrences.PrefrencesDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    
    public static int convertTimeToMinutes(String timeDuration){
        if(timeDuration.length()==5){
            timeDuration = "0:" + timeDuration;
        }
        Integer multiplier[] = {1440, 60, 1};
        int duration = 0, pos = 0;
        for(String s:timeDuration.split( ":" ))
        {
            duration += (Integer.valueOf( s ) * multiplier[pos]);
            pos++;
        }
        return duration;
    }
    
    public static Date formatCodeforcesDate( String cfDate){
        DateFormat format = new SimpleDateFormat( "MMM/dd/yyyy hh:mm" );
        format.setTimeZone( TimeZone.getTimeZone( "Europe/Moscow" ) );
        Date date;
        try {
            date = format.parse( cfDate );
        }
        catch ( Exception e ){
            date = null;
        }
        return date;
    }
    
    public static Date formatCodechefsDate(String ccDate){
        DateFormat format = new SimpleDateFormat( "dd MMM yy hh:mm:ss" );
        format.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
        Date date;
        try {
            date = format.parse( ccDate );
        }
        catch ( Exception e ){
            date = null;
        }
        return date;
    }
    
    public static Date formatAtcoderDate(String acDate){
        DateFormat format = new SimpleDateFormat( "yy-MM-dd hh:mm:SSZ" );
        format.setTimeZone( TimeZone.getTimeZone( "Asia/Tokyo" ) );
        Date date;
        try {
            date = format.parse( acDate );
        }
        catch ( Exception e ){
            date = null;
        }
        return date;
    }
    
    public static String formatDateTimeToDate(Date date, Context context)
    {
        PrefrencesDAO dao = new PrefrencesDAO( context );
        DateFormat format = new SimpleDateFormat( "dd/MMM/yyyy" );
        format.setTimeZone( TimeZone.getTimeZone( dao.getTimeZone() ) );
        return format.format( date );
    }
    
    public static String formatDateTimeToTime( Date date, Context context )
    {
        DateFormat format;
        PrefrencesDAO dao = new PrefrencesDAO( context );
        if(dao.getTimeFormat())
            format = new SimpleDateFormat( "HH:mm" );
        else
            format = new SimpleDateFormat( "hh:mm aa" );
        format.setTimeZone( TimeZone.getTimeZone( dao.getTimeZone() ) );
        return format.format( date );
    }
    
    public static Date getFinishDateTime(Date startDate, int duration){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( startDate );
        calendar.add( Calendar.MINUTE, duration );
        
        return calendar.getTime();
    }
    
}
