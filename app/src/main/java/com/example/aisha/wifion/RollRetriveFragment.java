package com.example.aisha.wifion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.aisha.wifion.R.id.retrieve_listView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RollRetriveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RollRetriveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RollRetriveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView mListView;
    EditText mRoll;
    TextView mName;

    private OnFragmentInteractionListener mListener;

    public RollRetriveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RollRetriveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RollRetriveFragment newInstance(String param1, String param2) {
        RollRetriveFragment fragment = new RollRetriveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roll_retrive, container, false);
        mRoll = (EditText) view.findViewById(R.id.rollfragment_roll_edittext);
        mName = (TextView) view.findViewById(R.id.rollfragment_name_textview);
        mListView = (ListView) view.findViewById(R.id.rollfragment_listview);

        Button okButton = (Button) view.findViewById(R.id.rollfragment_ok_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mRoll.getText().toString();
                int semester =0;
                if(StartActivity.semester !=null) {
                    semester = Integer.parseInt(StartActivity.semester);
                    searchByRoll(s, semester);
                    hideKeyboard();
                }else{
                    Toast.makeText(getContext(), "Selesct Semester Then Try again", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mRoll.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String s = mRoll.getText().toString();
                    int semester =0;
                    if(StartActivity.semester !=null) {
                        semester = Integer.parseInt(StartActivity.semester);
                        searchByRoll(s, semester);
                    }else{
                        Toast.makeText(getContext(), "Selesct Semester Then Try again", Toast.LENGTH_SHORT).show();

                    }
                    mRoll.clearFocus();
                    hideKeyboard();

                    // mRoll.onEditorAction(EditorInfo.IME_ACTION_DONE);

                    handled = true;
                }
                return false;
            }
        });


        return view;
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


    public void searchByRoll(String rollNumber, int semester) {

        ArrayList<String> attendanceRetrieve_arraylist;
        //  String roll = retrive_edittext_search.getText().toString().trim();


        MessagesDataSource messagesDataSource = new MessagesDataSource(getContext());
        messagesDataSource.open();
        //pass roll number
       // Toast.makeText(getContext(), "Sem " + semester + "  roll num  " + rollNumber, Toast.LENGTH_SHORT).show();
        attendanceRetrieve_arraylist = (ArrayList<String>) messagesDataSource.getAttendanceByRollno(semester, rollNumber);
        String name;
        if (!attendanceRetrieve_arraylist.isEmpty()) {
             name = attendanceRetrieve_arraylist.get(0);
            attendanceRetrieve_arraylist.remove(0);
            mName.setText("Name       : " + name);
        }else{
            mName.setText("Name       : " + "No Student found");
        }



        ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, attendanceRetrieve_arraylist);

        mListView.setAdapter(attendanceRetrieve_adapter);

    }


}
