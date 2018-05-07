package com.gateway.lead_hunter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gateway.lead_hunter.objects.pojo.Lead;
import com.gateway.lead_hunter.objects.pojo.Show;

import java.util.List;

public class LeadsWrapperAdapter extends RecyclerView.Adapter<LeadsWrapperAdapter.LeadViewHolder> {
    List<Lead> leads;

    LeadsWrapperAdapter(List<Lead> leads){
        this.leads = leads;
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

    public static class LeadViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView firstName;
        TextView lastName;
        TextView email;

        LeadViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv);
            firstName = (TextView)itemView.findViewById(R.id.first_name);
            lastName = (TextView)itemView.findViewById(R.id.last_name);
            email = (TextView)itemView.findViewById(R.id.email);
            /*
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Leads.class);
//                    String message = editText.getText().toString();
//                    intent.putExtra(EXTRA_MESSAGE, message);
                    itemView.getContext().startActivity(intent);
                }
            });
            */
        }
    }
}
