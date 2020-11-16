// created by: raghav
// created on: 14/11/20, 12:38 AM

package com.example.contestschedule.util;

import java.util.Date;

public class Contest {
    
    private String contestUrl, title;
    private Date startDateTime, FinishDateTime;
    private int logo, duration;
    
    public Contest( String contestUrl, String title, Date startDateTime, Date finishDateTime, int logo, int duration ) {
        this.contestUrl = contestUrl;
        this.title = title;
        this.startDateTime = startDateTime;
        FinishDateTime = finishDateTime;
        this.logo = logo;
        this.duration = duration;
    }
    
    public int getDuration( ) {
        return duration;
    }
    
    public String getContestUrl( ) {
        return contestUrl;
    }
    
    public String getTitle( ) {
        return title;
    }
    
    public Date getStartDateTime( ) {
        return startDateTime;
    }
    
    public Date getFinishDateTime( ) {
        return FinishDateTime;
    }
    
    public int getLogo( ) {
        return logo;
    }
    
}
