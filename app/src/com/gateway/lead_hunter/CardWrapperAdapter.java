package com.gateway.lead_hunter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gateway.lead_hunter.objects.pojo.Show;

import java.util.List;

public class CardWrapperAdapter extends RecyclerView.Adapter<CardWrapperAdapter.ShowViewHolder>{

    List<Show> shows;

    CardWrapperAdapter(List<Show> shows){
        this.shows = shows;
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        ShowViewHolder showViewHolder = new ShowViewHolder(v);
        return showViewHolder;
    }

    @Override
    public void onBindViewHolder(ShowViewHolder showViewHolder, int i) {
        showViewHolder.showName.setText(shows.get(i).getCity());
        showViewHolder.showDates.setText(shows.get(i).getShowDates());
        showViewHolder.venue.setText(shows.get(i).getConventionCenter().getName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ShowViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView showName;
        TextView showDates;
        TextView venue;

        ShowViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            showName = (TextView)itemView.findViewById(R.id.show_name);
            venue = (TextView)itemView.findViewById(R.id.venue);
            showDates = (TextView)itemView.findViewById(R.id.show_dates);
        }
    }

}