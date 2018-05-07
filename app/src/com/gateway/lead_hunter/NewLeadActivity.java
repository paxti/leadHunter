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

    @BindView(R.id.first_name_field) EditText firstName;
    @BindView(R.id.last_name_field) EditText lastName;
    @BindView(R.id.company_field) EditText company;
    @BindView(R.id.email_field) EditText email;
    @BindView(R.id.phone_field) EditText phone;
    @BindView(R.id.notes_field) EditText notes;
    @BindView(R.id.save_button_form) Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @OnClick(R.id.save_button_form)
    public void onSaveClick(View view){
        try {
            DBManager.getInstance().createLead(firstName.getText().toString(),
                    lastName.getText().toString(),
                    company.getText().toString(),
                    email.getText().toString(),
                    phone.getText().toString(),
                    notes.getText().toString());

            Intent intent = new Intent(view.getContext(), Leads.class);
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
