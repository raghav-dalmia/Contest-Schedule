// created by: raghav
// created on: 14/11/20, 3:46 PM

package com.example.contestschedule.ui.main_activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contestschedule.R;
import com.example.contestschedule.ui.browser_activity.BrowserActivity;
import com.example.contestschedule.util.Contest;
import com.example.contestschedule.util.Utils;

import java.util.List;

public class ContestListAdapter extends RecyclerView.Adapter< ContestListAdapter.ContestViewHolder > {
    
    private List< Contest > contestList;
    private Context context;
    
    public ContestListAdapter( List < Contest > contestList, Context context ) {
        this.contestList = contestList;
        this.context = context;
    }
    
    public void setContestList( List < Contest > contestList ) {
        this.contestList = contestList;
    }
    
    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        View view = inflater.inflate( R.layout.contest_item_layout, parent, false );
        return new ContestViewHolder( view );
    }
    
    @Override
    public void onBindViewHolder( @NonNull ContestViewHolder holder, int position ) {
        Contest contest = contestList.get( position );
        holder.title.setText( contest.getTitle() );
        holder.startDate.setText( Utils.formatDateTimeToDate(contest.getStartDateTime(), context) );
        holder.startTime.setText( Utils.formatDateTimeToTime(contest.getStartDateTime(), context) );
        holder.finishDate.setText( Utils.formatDateTimeToDate(contest.getFinishDateTime(), context) );
        holder.finishTime.setText( Utils.formatDateTimeToTime(contest.getFinishDateTime(), context) );
        holder.logo.setImageResource( contest.getLogo() );
        holder.viewContestButton.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View v ) {
                String url = contest.getContestUrl();
                Intent intent = new Intent( context, BrowserActivity.class );
                intent.putExtra( "URL", url );
                context.startActivity( intent );
            }
        } );
    }
    
    @Override
    public int getItemCount( ) {
        return contestList.size();
    }
    
    public class ContestViewHolder extends RecyclerView.ViewHolder{
        public TextView title, startDate, startTime, finishDate, finishTime, viewContestButton;
        public ImageView logo;
    
        public ContestViewHolder( @NonNull View itemView ) {
            super( itemView );
            logo = itemView.findViewById( R.id.logo );
            title = itemView.findViewById( R.id.contestTitle );
            startDate = itemView.findViewById( R.id.startDate );
            startTime = itemView.findViewById( R.id.startTime );
            finishDate = itemView.findViewById( R.id.finishDate );
            finishTime = itemView.findViewById( R.id.finishTime );
            viewContestButton = itemView.findViewById( R.id.viewContestButton );
        }
    
    }
    
}
