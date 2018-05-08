package com.gateway.lead_hunter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gateway.lead_hunter.objects.pojo.Show;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowsWrapperAdapter extends RecyclerView.Adapter<ShowsWrapperAdapter.ShowViewHolder>{

    List<Show> shows;

    ShowsWrapperAdapter(List<Show> shows){
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

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv) CardView cardView;
        @BindView(R.id.show_name) TextView showName;
        @BindView(R.id.venue) TextView venue;
        @BindView(R.id.show_dates) TextView showDates;

        ShowViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv)
        public void onClick() {
            Intent intent = new Intent(itemView.getContext(), Leads.class);
            String showName = shows.get(getAdapterPosition()).getCity();
            String showId = shows.get(getAdapterPosition()).getId();
            intent.putExtra(Leads.SHOW_NAME, showName);
            intent.putExtra(Leads.SHOW_ENTRY_ID, showId);
            itemView.getContext().startActivity(intent);
        }

    }

}