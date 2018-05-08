package com.gateway.lead_hunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gateway.lead_hunter.services.ShowsSyncService;
import com.gateway.lead_hunter.utils.DBManager;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Leads extends AppCompatActivity {

    public static final String SHOW_NAME = "SHOW_NAME";
    public static final String SHOW_ENTRY_ID = "SHOW_ENTRY_ID";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.cardWrapper) RecyclerView cardWrapper;
    @BindView(R.id.fab) FloatingActionButton fab;

    private String showEntryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(SHOW_NAME));
        showEntryId = intent.getStringExtra(SHOW_ENTRY_ID);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeAdapter();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        cardWrapper.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        cardWrapper.setLayoutManager(llm);

        initializeAdapter();
    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), NewLeadActivity.class);
        intent.putExtra(NewLeadActivity.SHOW_ENTRY_ID, showEntryId);
        view.getContext().startActivity(intent);
    }

    private void initializeAdapter(){
        LeadsWrapperAdapter adapter = null;
        try {
            adapter = new LeadsWrapperAdapter(DBManager.getInstance().getAllLeads(), showEntryId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cardWrapper.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
