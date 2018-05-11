package com.gateway.lead_hunter;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.gateway.lead_hunter.fragments.GalleryFragment;
import com.gateway.lead_hunter.fragments.LeadFragment;
import com.gateway.lead_hunter.fragments.NotesFragment;
import com.gateway.lead_hunter.objects.pojo.Lead;
import com.gateway.lead_hunter.services.LeadsSyncService;
import com.gateway.lead_hunter.utils.DBManager;
import com.gateway.lead_hunter.utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditLeadActivity extends AppCompatActivity
        implements LeadFragment.OnFragmentInteractionListener,
        GalleryFragment.OnFragmentInteractionListener, NotesFragment.OnFragmentInteractionListener {

    public static final String SHOW_ENTITY_ID = "SHOW_ENTITY_ID";
    public static final String LEAD_ENTITY_ID = "LEAD_ENTITY_ID";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private Long leadEntryId;
    private String showEntryId;
    private Lead lead;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lead);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        leadEntryId = intent.getLongExtra(LEAD_ENTITY_ID, 0);
        showEntryId = intent.getStringExtra(SHOW_ENTITY_ID);

        try {
            lead = DBManager.getInstance().getLead(leadEntryId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setToolbar();
        setTabs();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setTabs(){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFrag(LeadFragment.newInstance(lead), getString(R.string.tab_lead));
        mSectionsPagerAdapter.addFrag(NotesFragment.newInstance(lead), getString(R.string.tab_notes));
        mSectionsPagerAdapter.addFrag(GalleryFragment.newInstance("", ""), getString(R.string.tab_photos));


        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_lead, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLeadUpdate(String firstName, String lastName, String company,
                             String email, String phone) {

        lead.setFirstName(firstName);
        lead.setLastName(lastName);
        lead.setCompany(company);
        lead.setEmail(email);
        lead.setPhone(phone);

        try {
            DBManager.getInstance().updateLead(lead, leadEntryId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Utils.isInternetAvailable(this)) {
            Intent mServiceIntent = new Intent(this, LeadsSyncService.class);
            this.startService(mServiceIntent);
        }

        Intent intent = new Intent(this, Leads.class);
        intent.putExtra(Leads.SHOW_NAME, "Test");
        intent.putExtra(Leads.SHOW_ENTRY_ID, showEntryId);
        this.startActivity(intent);

    }

    @Override
    public void onLeadNotesUpdate(String notes) {
        lead.setNotes(notes);

        try {
            DBManager.getInstance().updateLead(lead, leadEntryId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, Leads.class);
        intent.putExtra(Leads.SHOW_NAME, "Test");
        intent.putExtra(Leads.SHOW_ENTRY_ID, showEntryId);
        this.startActivity(intent);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFrag(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
