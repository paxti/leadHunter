package com.gateway.lead_hunter.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gateway.lead_hunter.R;
import com.gateway.lead_hunter.objects.pojo.Lead;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeadFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SELECTED_LEAD = "SELECTED_LEAD";

    // TODO: Rename and change types of parameters
    private Lead selectedLead;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.update_button_form) Button updateLead;
    @BindView(R.id.first_name_field) EditText firstName;
    @BindView(R.id.last_name_field) EditText lastName;
    @BindView(R.id.company_field) EditText company;
    @BindView(R.id.email_field) EditText email;
    @BindView(R.id.phone_field) EditText phone;

    public LeadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param lead Lead from Activity.
     * @return A new instance of fragment LeadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeadFragment newInstance(Lead lead) {
        LeadFragment fragment = new LeadFragment();
        Bundle args = new Bundle();

        ObjectMapper mapper = new ObjectMapper();
        try {
            args.putSerializable(SELECTED_LEAD, mapper.writeValueAsString(lead));
            fragment.setArguments(args);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                ObjectReader jsonReader = mapper.readerFor(Lead.class);
                mapper.readerFor(Lead.class);
                selectedLead = (Lead) jsonReader.readValue(getArguments().getString(SELECTED_LEAD));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        ButterKnife.bind(this, view);

        firstName.setText(selectedLead.getFirstName());
        lastName.setText(selectedLead.getLastName());
        company.setText(selectedLead.getCompany());
        email.setText(selectedLead.getEmail());
        phone.setText(selectedLead.getPhone());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.update_button_form)
    public void onClick() {

        mListener.onLeadUpdate(
                firstName.getText().toString(),
                lastName.getText().toString(),
                company.getText().toString(),
                email.getText().toString(),
                phone.getText().toString()
        );
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onLeadUpdate(String firstName, String lastName, String company,
                          String email, String phone);
    }
}
