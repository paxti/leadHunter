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
import com.gateway.lead_hunter.R;
import com.gateway.lead_hunter.objects.pojo.Lead;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private static final String SELECTED_LEAD = "SELECTED_LEAD";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Lead selectedLead;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.update_button_form) Button updateLead;
    @BindView(R.id.notes_field) EditText notes;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param lead Lead from Activity.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(Lead lead) {
        NotesFragment fragment = new NotesFragment();
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

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);

        notes.setText(selectedLead.getNotes());

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
        mListener.onLeadNotesUpdate(notes.getText().toString());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onLeadNotesUpdate(String notes);
    }
}
