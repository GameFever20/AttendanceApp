package layout;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aisha.wifion.Message;
import com.example.aisha.wifion.MessagesDataSource;
import com.example.aisha.wifion.R;
import com.example.aisha.wifion.RetrieveActivity;
import com.example.aisha.wifion.StartActivity;

import java.util.ArrayList;

import static com.example.aisha.wifion.R.id.datePicker;
import static com.example.aisha.wifion.R.id.retrieve_listView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateRetrieveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateRetrieveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateRetrieveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public TextView mDateTextview;
    public ListView mListview;
    Dialog ddialog;
    DatePicker datePicker;


    private OnFragmentInteractionListener mListener;

    public DateRetrieveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateRetrieveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateRetrieveFragment newInstance(String param1, String param2) {
        DateRetrieveFragment fragment = new DateRetrieveFragment();
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
        View view = inflater.inflate(R.layout.fragment_date_retrieve, container, false);

        mDateTextview = (TextView) view.findViewById(R.id.datefragment_date_textview);
        mListview = (ListView) view.findViewById(R.id.datefragment_listview);


        mDateTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        return view;
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


    public void selectDate() {
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

                String fulldate = Message.convertToFullDate(new_year ,new_month ,new_date);

                mDateTextview.setText(fulldate);
                retrieveByDate(fulldate);
            }
        });


    }

    private void retrieveByDate(String date) {

        ArrayList<Message> attendanceRetrieve_arraylist;
        MessagesDataSource messagesDataSource = new MessagesDataSource(getContext());
        messagesDataSource.open();
        int semester =0;
        if(StartActivity.semester !=null) {
            semester = Integer.parseInt(StartActivity.semester);

        }else{
            Toast.makeText(getContext(), "Selesct Semester Then Try again", Toast.LENGTH_SHORT).show();

        }
        attendanceRetrieve_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByDate(semester,date);


        ArrayList<String> retriveByDateList = convertToDateList(attendanceRetrieve_arraylist);

        ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(getContext(),android. R.layout.simple_list_item_1, retriveByDateList);

      //  mListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        mListview.setAdapter(attendanceRetrieve_adapter);


    }

    private ArrayList<String> convertToDateList(ArrayList<Message> messages ){

        //changes made on listview(to display only name )
        ArrayList<String> finalStorageList = new ArrayList<>();
        int tempSize = messages.size();
        for (int i = 0; i < tempSize; i++) {
            /*Message message = messages.get(i);
            finalStorageList.add(message.getFull_date() + " - " + message.getMessage());
            */
            finalStorageList.add(messages.get(i).getByDateInfo());

        }

        return finalStorageList;

    }


}
