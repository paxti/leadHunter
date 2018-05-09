package com.gateway.lead_hunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gateway.lead_hunter.utils.DBManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;

import static java.security.AccessController.getContext;

public class NewLeadActivity extends AppCompatActivity {

    public static final String SHOW_ENTRY_ID = "SHOW_ENTRY_ID";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.first_name_field) EditText firstName;
    @BindView(R.id.last_name_field) EditText lastName;
    @BindView(R.id.company_field) EditText company;
    @BindView(R.id.email_field) EditText email;
    @BindView(R.id.phone_field) EditText phone;
    @BindView(R.id.notes_field) EditText notes;
    @BindView(R.id.save_button_form) Button saveButton;
    @BindView(R.id.fab) FloatingActionButton fab;

    private String showEntryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead);

        ButterKnife.bind(this);

        setToolbar();

        Intent intent = getIntent();
        showEntryId = intent.getStringExtra(SHOW_ENTRY_ID);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.save_button_form)
    public void onSaveClick(View view){
        try {
            DBManager.getInstance().createLead(
                    showEntryId,
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    company.getText().toString(),
                    email.getText().toString(),
                    phone.getText().toString(),
                    notes.getText().toString());

            Intent intent = new Intent(view.getContext(), Leads.class);
            intent.putExtra(Leads.SHOW_ENTRY_ID, showEntryId);
            view.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
