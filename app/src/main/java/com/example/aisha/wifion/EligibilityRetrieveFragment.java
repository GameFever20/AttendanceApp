package com.example.aisha.wifion;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EligibilityRetrieveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EligibilityRetrieveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EligibilityRetrieveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    TextView fromDateTextView, toDateTextView;
    EditText countEditText;
    ListView mListView;

    String fromDate = "", toDate = "";
    int count = -1;

    Dialog ddialog;
    DatePicker datePicker;

    private OnFragmentInteractionListener mListener;

    public EligibilityRetrieveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EligibilityRetrieveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EligibilityRetrieveFragment newInstance(String param1, String param2) {
        EligibilityRetrieveFragment fragment = new EligibilityRetrieveFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eligibility_retrieve, container, false);

        fromDateTextView = (TextView) view.findViewById(R.id.eligibilityfragment_fromdate_textview);
        toDateTextView = (TextView) view.findViewById(R.id.eligibilityfragment_todate_textview);

        countEditText = (EditText) view.findViewById(R.id.eligibilityfragment_count_edittext);
        mListView = (ListView) view.findViewById(R.id.eligibilityfragment_listview);

        countEditText.clearFocus();
        fromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                selectFromDate();
            }
        });

        toDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                selectToDate();
            }
        });

        countEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    try {
                        count = Integer.valueOf(countEditText.getText().toString().trim());
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Enter Valid Number", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                    checkForSearch();
                    countEditText.clearFocus();
                    hideKeyboard();


                }

                return false;
            }
        });

        return view;
    }

    private void selectFromDate() {

        ddialog = new Dialog(getContext());
        ddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ddialog.setContentView(R.layout.calender_picker);
        ddialog.setCancelable(true);
        ddialog.show();

        datePicker = (DatePicker) ddialog.findViewById(R.id.datePicker);
        Button okButton = (Button) ddialog.findViewById(R.id.next1);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int new_date = Integer.parseInt(datePicker.getDayOfMonth() + "");
                int new_month = Integer.parseInt(datePicker.getMonth() + 1 + "");
                int new_year = Integer.parseInt(datePicker.getYear() + "");
                ddialog.cancel();

                String fulldate = Message.convertToFullDate(new_year, new_month, new_date);

                fromDate = fulldate;
                fromDateTextView.setText(fulldate);
                checkForSearch();


            }
        });


    }

    private void checkForSearch() {

        if (StartActivity.semester != null) {

            if (!fromDate.isEmpty() && !toDate.isEmpty() && count != -1) {
                retrieveByEligibility();

            }
        } else {
            Toast.makeText(getContext(), "Selesct Semester Then Try again", Toast.LENGTH_SHORT).show();
        }

    }

    private void retrieveByEligibility() {

        int semester = Integer.parseInt(StartActivity.semester);

        ArrayList<Message> attendanceRetrieveEligible_arraylist;


        MessagesDataSource messagesDataSource = new MessagesDataSource(getContext());
        messagesDataSource.open();


        try {
            attendanceRetrieveEligible_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByEligiblity(fromDate, toDate, semester);

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (count >= 0) {
                CustomAdapter customAdapter = new CustomAdapter(getContext(), attendanceRetrieveEligible_arraylist, count, inflater);
                mListView.setAdapter(customAdapter);
            }
            messagesDataSource.close();


        } catch (NullPointerException ioException) {
            Toast.makeText(getContext(), "Internal Error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Internal Erroe", Toast.LENGTH_SHORT).show();
        }


    }


    private void selectToDate() {

        ddialog = new Dialog(getContext());
        ddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ddialog.setContentView(R.layout.calender_picker);
        ddialog.setCancelable(true);
        ddialog.show();

        datePicker = (DatePicker) ddialog.findViewById(R.id.datePicker);
        Button okButton = (Button) ddialog.findViewById(R.id.next1);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int new_date = Integer.parseInt(datePicker.getDayOfMonth() + "");

                //Toast.makeText(getContext(), "day is " + datePicker.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                int new_month = Integer.parseInt(datePicker.getMonth() + 1 + "");
                int new_year = Integer.parseInt(datePicker.getYear() + "");
                ddialog.cancel();

                String fulldate = Message.convertToFullDate(new_year, new_month, new_date);

                toDate = fulldate;
                toDateTextView.setText(fulldate);
                checkForSearch();


            }
        });


    }



    public void hideKeyboard(){

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
