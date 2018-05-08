package com.gateway.lead_hunter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gateway.lead_hunter.objects.pojo.Lead;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeadsWrapperAdapter extends RecyclerView.Adapter<LeadsWrapperAdapter.LeadViewHolder> {

    private List<Lead> leads;
    private String showEntryId;

    LeadsWrapperAdapter(List<Lead> leads, String showEntryId){
        this.leads = leads;
        this.showEntryId = showEntryId;
    }

    @Override
    public int getItemCount() {
        return leads.size();
    }

    @Override
    public LeadsWrapperAdapter.LeadViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lead_card, viewGroup, false);
        LeadsWrapperAdapter.LeadViewHolder showViewHolder = new LeadsWrapperAdapter.LeadViewHolder(v);
        return showViewHolder;
    }

    @Override
    public void onBindViewHolder(LeadsWrapperAdapter.LeadViewHolder leadViewHolder, int i) {
        leadViewHolder.firstName.setText(leads.get(i).getFirstName());
        leadViewHolder.lastName.setText(leads.get(i).getLastName());
        leadViewHolder.email.setText(leads.get(i).getCompany());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class LeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv) CardView cardView;
        @BindView(R.id.first_name) TextView firstName;
        @BindView(R.id.last_name) TextView lastName;
        @BindView(R.id.email) TextView email;

        LeadViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv)
        public void onClick() {
            Intent intent = new Intent(itemView.getContext(), EditLeadActivity.class);
            String id = leads.get(getAdapterPosition()).getId();
            intent.putExtra(EditLeadActivity.SHOW_ENTITY_ID, id);
            itemView.getContext().startActivity(intent);
        }
    }
}
