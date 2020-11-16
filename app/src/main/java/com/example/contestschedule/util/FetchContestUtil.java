// created by: raghav
// created on: 14/11/20, 12:49 AM

package com.example.contestschedule.util;

import android.util.Log;

import com.example.contestschedule.R;
import com.example.contestschedule.dao.VariableManager;
import com.example.contestschedule.dao.sPrefrences.PrefrencesDAO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class FetchContestUtil extends Utils implements VariableManager {
    
    private static final String TAG = "FetchContestUtil";
    private PrefrencesDAO dao;
    
    public FetchContestUtil( PrefrencesDAO dao ) {
        this.dao = dao;
    }
    
    public void fetchCodeforcesContest()
    {
        String url = "https://codeforces.com/contests";
        String website = "https://codeforces.com";
        
        try {
            Document doc = Jsoup.connect( url ).get( );
            Elements contests = doc.select( "table" ).get( 0 ).select( "tr" );
            for(int i=1;i<contests.size();i++){
                
                Elements contestElements = contests.get( i ).select( "td" );
                
                String cid = contests.get( i ).attr( "data-contestId" );
                String contestUrl = "";
                String title = contestElements.get( 0 ).text();
                if(title.length()>7 && title.substring( title.length()-7).equals("Enter »")) {
                    title = title.substring( 0, title.length( ) - 7 );
                    contestUrl = website + "/contest/" + cid;
                }
                else{
                    contestUrl = website + "/contestRegistration/" + cid;
                }
                
                Date startDateTime = formatCodeforcesDate( contestElements.get( 2 ).text() );
                int duration = convertTimeToMinutes( contestElements.get( 3 ).text() );
                Date finishDateTime = getFinishDateTime( startDateTime, duration );
                
                if(duration>dao.getShortContestDuration())
                    longContestList.add( new Contest( contestUrl, title, startDateTime, finishDateTime, R.drawable.codeforces_logo, duration ) );
                else
                    shortContestList.add( new Contest( contestUrl, title, startDateTime, finishDateTime, R.drawable.codeforces_logo, duration ) );
            }
        }
        catch ( IOException e ) {}
        return;
    }
    
    public void fetchCodechefContest(){
        String url = "https://www.codechef.com/contests";
        String website = "https://www.codechef.com";
        
        try{
            Document doc = Jsoup.connect( url ).get();
            
            Elements contests = doc.select( "table.dataTable" );
            
            for(int i=0;i<2;i++){
                Elements currContests = contests.get( i ).select( "tbody tr" );
                for(int j=0;j<currContests.size();j++){
                    Elements contestElements = currContests.get( j ).select( "td" );
                    
                    String title = contestElements.get( 1 ).text();
                    String contestUrl = website + contestElements.get( 1 ).select( "a" ).attr( "href" );
                    
                    Date startDateTime = formatCodechefsDate( contestElements.get( 2 ).text() );
                    Date finishDateTime = formatCodechefsDate( contestElements.get( 3 ).text() );
                    
                    int duration = (int)((finishDateTime.getTime() - startDateTime.getTime())/60000);
    
    
                    if(duration>dao.getShortContestDuration())
                        longContestList.add( new Contest( contestUrl, title, startDateTime, finishDateTime, R.drawable.codechef_logo, duration ) );
                    else
                        shortContestList.add( new Contest( contestUrl, title, startDateTime, finishDateTime, R.drawable.codechef_logo, duration ) );
                    
                }
            }
            
        }
        catch ( IOException e )
        {
        }
    }
    
    public void fetchAtcoderContest(){
        String url = "https://atcoder.jp/contests/";
        String website = "https://atcoder.jp";
        try {
            Document doc = Jsoup.connect( url ).get();
            Elements contests = doc.select( "#contest-table-upcoming tbody tr" );
            for(int i=0;i<contests.size();i++){
                Elements contestElements = contests.get( i ).select( "td" );
                Date startDateTime = formatAtcoderDate( contestElements.get( 0 ).text() );
                String contestUrl = website + contestElements.get( 1 ).select( "a" ).attr( "href" );
                String title = contestElements.get( 1 ).select( "a" ).text();
                int duration = convertTimeToMinutes( contestElements.get( 2 ).text() );
                Date finishDateTime = getFinishDateTime( startDateTime, duration );
    
    
                if(duration>dao.getShortContestDuration())
                    longContestList.add( new Contest( contestUrl, title, startDateTime, finishDateTime, R.drawable.atcoder_logo, duration ) );
                else
                    shortContestList.add( new Contest( contestUrl, title, startDateTime, finishDateTime, R.drawable.atcoder_logo, duration ) );
            }
        }
        catch ( IOException e ){}
    }
    
}
