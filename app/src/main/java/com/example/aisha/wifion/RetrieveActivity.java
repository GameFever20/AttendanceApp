package com.example.aisha.wifion;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RetrieveActivity extends AppCompatActivity {

    ListView retrieve_listView;
    EditText retrive_edittext_search;

    // after retrieve layout dialog pop up
    Dialog retrieve_bet_dates_dialog;
    EditText from_ed, to_ed, count_ed, sem_ed_dialog_retrieve;
    Button ok, from_datepicker_dialog_button, to_datepicker_dialog_button;
    String final_to_date, final_from_date = "";
    int sem_dialog;
    int eligiblity_count;

    Dialog ddialog, semPopupDialog;
    DatePicker datePicker;
    int new_date, new_month, new_year;
    EditText sem_datepic_editText;
    EditText sem_dateRetrieve_ed;
    int semester_variable_dialog_sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        retrieve_listView = (ListView) findViewById(R.id.retrieve_listView);

        if (StartActivity.semester != null) {
            semester_variable_dialog_sem = Integer.parseInt(StartActivity.semester);
        }else{
            Toast.makeText(this, "Semester Not Selected", Toast.LENGTH_SHORT).show();
        }



    }

    // review all the three method

    public void searchByEligiblityRetrieve_method(View view) {
       /* ArrayList<Message> attendanceRetrieve_arraylist;
        String name = retrive_edittext_search.getText().toString().trim();

        MessagesDataSource messagesDataSource = new MessagesDataSource(this);

        messagesDataSource.open();

        attendanceRetrieve_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByName(name);
        ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, attendanceRetrieve_arraylist);
        retrieve_listView.setAdapter(attendanceRetrieve_adapter);
*/
        retrieve_bet_dates_dialog = new Dialog(this);
        retrieve_bet_dates_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        retrieve_bet_dates_dialog.setContentView(R.layout.between_date_retrieve);
        retrieve_bet_dates_dialog.setCancelable(true);
        retrieve_bet_dates_dialog.show();

        from_ed = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.from_ed_retrieve);
        to_ed = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.to_ed_retrieve);
        count_ed = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.count_ed_eligiblity);
        sem_ed_dialog_retrieve = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.sem_retrieval_dialog_retrievalclass_editText);
        ok = (Button) retrieve_bet_dates_dialog.findViewById(R.id.ok_btn_dialog_retrieve);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_from_date = from_ed.getText().toString().trim();
                final_to_date = to_ed.getText().toString().trim();
                eligiblity_count = Integer.valueOf(count_ed.getText().toString().trim());
                sem_dialog = Integer.parseInt(sem_ed_dialog_retrieve.getText().toString().trim());

                //  ArrayList<String> attendanceRetrieveEligible_arraylist;
                ArrayList<Message> attendanceRetrieveEligible_arraylist;


                MessagesDataSource messagesDataSource = new MessagesDataSource(RetrieveActivity.this);
                messagesDataSource.open();

                //calling only eligible student in green
                // attendanceRetrieveEligible_arraylist = (ArrayList<String>) messagesDataSource.getAttendanceByEligiblity(final_from_date,final_to_date,eligiblity_count);

                attendanceRetrieveEligible_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByEligiblity(final_from_date, final_to_date, sem_dialog);
                //  Toast.makeText(RetrieveActivity.this, attendanceRetrieveEligible_arraylist.size()+" sizeof this", Toast.LENGTH_SHORT).show();

                //ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(RetrieveActivity.this, R.layout.support_simple_spinner_dropdown_item, attendanceRetrieveEligible_arraylist);
                //  retrieve_listView.setAdapter(attendanceRetrieve_adapter);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                CustomAdapter customAdapter = new CustomAdapter(RetrieveActivity.this, attendanceRetrieveEligible_arraylist, eligiblity_count, inflater);
                retrieve_listView.setAdapter(customAdapter);

                retrieve_bet_dates_dialog.cancel();

            }
        });


    }

    public void searchByRollRetrieve_method(View view) {

        semPopupDialog = new Dialog(RetrieveActivity.this);
        semPopupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        semPopupDialog.setContentView(R.layout.sem_popup);
        semPopupDialog.setCancelable(true);
        semPopupDialog.show();

        sem_dateRetrieve_ed = (EditText) semPopupDialog.findViewById(R.id.sem_editext_dialog);
        Button enterBtnSemDialog = (Button) semPopupDialog.findViewById(R.id.sem_enter_dialog_btn);


        enterBtnSemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = sem_dateRetrieve_ed.getText().toString().trim();
                semester_variable_dialog_sem = Integer.parseInt(temp);
                semPopupDialog.cancel();
                ArrayList<String> attendanceRetrieve_arraylist;
                //  String roll = retrive_edittext_search.getText().toString().trim();

                MessagesDataSource messagesDataSource = new MessagesDataSource(RetrieveActivity.this);
                messagesDataSource.open();
                attendanceRetrieve_arraylist = (ArrayList<String>) messagesDataSource.getAttendanceByRollno(semester_variable_dialog_sem);
                ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(RetrieveActivity.this, R.layout.support_simple_spinner_dropdown_item, attendanceRetrieve_arraylist);
                retrieve_listView.setAdapter(attendanceRetrieve_adapter);

            }
        });

    }

    public void searchByDATERetrieve_method(View view) {


        semPopupDialog = new Dialog(RetrieveActivity.this);
        semPopupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        semPopupDialog.setContentView(R.layout.sem_popup);
        semPopupDialog.setCancelable(true);
        semPopupDialog.show();

        sem_dateRetrieve_ed = (EditText) semPopupDialog.findViewById(R.id.sem_editext_dialog);
        Button enterBtnSemDialog = (Button) semPopupDialog.findViewById(R.id.sem_enter_dialog_btn);


        enterBtnSemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = sem_dateRetrieve_ed.getText().toString().trim();
                semester_variable_dialog_sem = Integer.parseInt(temp);
                semPopupDialog.cancel();

                //nw dialog box
                ddialog = new Dialog(RetrieveActivity.this);
                ddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                ddialog.setContentView(R.layout.calender_picker);
                ddialog.setCancelable(true);
                ddialog.show();

                datePicker = (DatePicker) ddialog.findViewById(R.id.datePicker);
                Button ok = (Button) ddialog.findViewById(R.id.next1);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new_date = Integer.parseInt(datePicker.getDayOfMonth() + "");
                        new_month = Integer.parseInt(datePicker.getMonth() + 1 + "");
                        new_year = Integer.parseInt(datePicker.getYear() + "");
                        ddialog.cancel();


                        ArrayList<Message> attendanceRetrieve_arraylist;
                        MessagesDataSource messagesDataSource = new MessagesDataSource(RetrieveActivity.this);
                        messagesDataSource.open();
                        attendanceRetrieve_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByDate(semester_variable_dialog_sem);

                        List<String> finalStorageList = new ArrayList<>();
                        int tempSize = attendanceRetrieve_arraylist.size();

                        for (int i = 0; i < tempSize; i++) {
                            Message message = attendanceRetrieve_arraylist.get(i);
                            int messdate = Integer.parseInt(message.getcDay());
                            int messmonth = Integer.parseInt(message.getcMonth());
                            int messyear = Integer.parseInt(message.getcYear());


                            if (new_date == messdate) {
                                if (new_month == messmonth) {
                                    if (new_year == messyear) {
                                        finalStorageList.add(message.getFull_date() + "                - " + message.getMessage() + " - " + message.getCount());
                                    }
                                }

                            }
                        }


                        ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(RetrieveActivity.this, R.layout.support_simple_spinner_dropdown_item, finalStorageList);
                        retrieve_listView.setAdapter(attendanceRetrieve_adapter);


                    }
                });


                //   Toast.makeText(MainActivity.this, new_date + "-" + new_month + "-" + new_year, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void searchByDate(View v) {


        //nw dialog box
        ddialog = new Dialog(RetrieveActivity.this);
        ddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ddialog.setContentView(R.layout.calender_picker);
        ddialog.setCancelable(true);
        ddialog.show();

        datePicker = (DatePicker) ddialog.findViewById(R.id.datePicker);
        Button ok = (Button) ddialog.findViewById(R.id.next1);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_date = Integer.parseInt(datePicker.getDayOfMonth() + "");
                new_month = Integer.parseInt(datePicker.getMonth() + 1 + "");
                new_year = Integer.parseInt(datePicker.getYear() + "");
                ddialog.cancel();


                String fulldate =Message.convertToFullDate(new_year ,new_month ,new_date);
                ArrayList<Message> attendanceRetrieve_arraylist;
                MessagesDataSource messagesDataSource = new MessagesDataSource(RetrieveActivity.this);
                messagesDataSource.open();
                attendanceRetrieve_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByDate(semester_variable_dialog_sem ,fulldate);

              /*  List<String> finalStorageList = new ArrayList<>();
                int tempSize = attendanceRetrieve_arraylist.size();

                for (int i = 0; i < tempSize; i++) {
                    Message message = attendanceRetrieve_arraylist.get(i);
                    int messdate = Integer.parseInt(message.getcDay());
                    int messmonth = Integer.parseInt(message.getcMonth());
                    int messyear = Integer.parseInt(message.getcYear());


                    if (new_date == messdate) {
                        if (new_month == messmonth) {
                            if (new_year == messyear) {
                                finalStorageList.add(message.getFull_date() + "                - " + message.getMessage() + " - " + message.getCount());
                            }
                        }

                    }
                }
*/
                ArrayList<String> retriveByDateList = convertToDateList(attendanceRetrieve_arraylist);

                ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(RetrieveActivity.this, R.layout.support_simple_spinner_dropdown_item, retriveByDateList);
                retrieve_listView.setAdapter(attendanceRetrieve_adapter);


            }
        });


        //   Toast.makeText(MainActivity.this, new_date + "-" + new_month + "-" + new_year, Toast.LENGTH_SHORT).show();
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

    public void searchByEligiblity(View view){


    }

    public void searchByRoll(View view){

        ArrayList<String> attendanceRetrieve_arraylist;
        //  String roll = retrive_edittext_search.getText().toString().trim();

        EditText meditText = (EditText)findViewById(R.id.roll_retrive_edittext);
        String rollNumber = meditText.getText().toString();

        MessagesDataSource messagesDataSource = new MessagesDataSource(RetrieveActivity.this);
        messagesDataSource.open();
        //pass roll number
        Toast.makeText(this, "Sem "+semester_variable_dialog_sem +"  roll num  "+rollNumber, Toast.LENGTH_SHORT).show();
        attendanceRetrieve_arraylist = (ArrayList<String>) messagesDataSource.getAttendanceByRollno(semester_variable_dialog_sem ,rollNumber);
        ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(RetrieveActivity.this, R.layout.support_simple_spinner_dropdown_item, attendanceRetrieve_arraylist);
        retrieve_listView.setAdapter(attendanceRetrieve_adapter);

    }









  /*  public void between_date_search_retrieve(View view) {
        retrieve_bet_dates_dialog = new Dialog(this);
        retrieve_bet_dates_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        retrieve_bet_dates_dialog.setContentView(R.layout.between_date_retrieve);
        retrieve_bet_dates_dialog.setCancelable(true);
        retrieve_bet_dates_dialog.show();

        from_ed = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.from_ed_retrieve);
        to_ed = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.to_ed_retrieve);
        count_ed = (EditText) retrieve_bet_dates_dialog.findViewById(R.id.count_ed_eligiblity);
        ok = (Button) retrieve_bet_dates_dialog.findViewById(R.id.ok_btn_dialog_retrieve);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_from_date=from_ed.getText().toString().trim();
                final_to_date=to_ed.getText().toString().trim();
                //eligiblity_count=count_ed.getText().toString().trim();
                ArrayList<Message> attendanceRetrieve_arraylist;


                MessagesDataSource messagesDataSource = new MessagesDataSource(RetrieveActivity.this);

                messagesDataSource.open();

                attendanceRetrieve_arraylist = (ArrayList<Message>) messagesDataSource.getAttendanceByBetweenDates(final_from_date,final_to_date);
                ArrayAdapter attendanceRetrieve_adapter = new ArrayAdapter(RetrieveActivity.this, R.layout.support_simple_spinner_dropdown_item, attendanceRetrieve_arraylist);
                retrieve_listView.setAdapter(attendanceRetrieve_adapter);



                retrieve_bet_dates_dialog.cancel();

            }
        });


    }
*/


}

