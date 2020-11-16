// created by: raghav
// created on: 14/11/20, 12:37 AM

package com.example.contestschedule.dao;

import com.example.contestschedule.util.Contest;

import java.util.ArrayList;
import java.util.List;

public interface VariableManager {
    
    List< Contest > shortContestList = new ArrayList <>(  );
    List< Contest > longContestList = new ArrayList <>(  );
    
    String[] timeZones = {
            "Asia/Kolkata",
            "Asia/Katmandu",
            "Asia/Tokyo",
            "Europe/Moscow",
            "Asia/Karachi",
    };
    
}
