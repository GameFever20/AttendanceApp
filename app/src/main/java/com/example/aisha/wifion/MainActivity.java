package com.example.aisha.wifion;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {


    WifiManager wifiManager;
    ToggleButton toggle;
    ListView roll_list;
    TextView textStatus;
    Button buttonScan;
    int size = 0;
    List<ScanResult> results;
    String ITEM_KEY = "key";
    ArrayList<String> arraylist = new ArrayList<String>();
    String current_date, current_month, current_year;

    Button send, receive, date;
    ArrayAdapter adapter;


    String networkSSID = "test";
    String networkPass = "pass";

    //SERVER SOCKET
    String serverIP = "10.0.2.15";
    final int SERVERPORT = 5432;
    private ServerSocket serverSocket;
    android.os.Handler handler = new android.os.Handler();
    TextView serverStatus;
    String sender_name, sender_roll, sender_sem = "";

    //client side
    EditText name, roll;

    private Button connectPhones;
    private String serverIpAddress = "";
    private boolean connected = false;

    //database
    private MessagesDataSource datasource;
    ArrayList<Message> arraylistData = new ArrayList<Message>();
    Button retrieve__btn, store_data, search_btn;
    int g = 0;
    String today_date, today_month, today_year;
    public static String SEARCH_BY = "";
    public ArrayList<String> namearraylist;
    public ArrayList<String> rollarraylist;
    public ArrayList<String> semarraylist;
    public ArrayList<String> greenStudentarraylist;
    public ArrayList<String> greenlist;

    ArrayAdapter listShowingName;
    ListView name_list;
    ListView retrieveNameList;

    Dialog ddialog;
    DatePicker datePicker;
    String new_date, new_month, new_year = "";

    Context context;

    TextView nameofstudent, rollno;
    Main2Activity main2Activity;

    ListView registerStudentList;
    ArrayAdapter registerAdapter;
    Dialog registerdialog;
    ArrayList<Details> arrListRegister;


    ArrayList<Details> registeredlistData;
    InsertQuerySource insertQuery;
    ArrayAdapter registerStudentAdapter;
    CheckBox layout_checkBox;
    TextView layout_tv;

    Dialog semPopupDialog;
    EditText semOfStudent;
    int semester = 0;
    Thread fst;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Toolbar mainAcivityToolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(mainAcivityToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Attendance");
            displayStatus("turn switch ON to connect");
        }


        buttonScan = (Button) findViewById(R.id.buttonscan);

        retrieveNameList = (ListView) findViewById(R.id.retrievelist);


       //  receive = (Button) findViewById(R.id.receive);
        search_btn = (Button) findViewById(R.id.date_search);

        serverStatus = (TextView) findViewById(R.id.textView);

        nameofstudent = (TextView) findViewById(R.id.textView2);

        registerStudentList = (ListView) findViewById(R.id.registerStudentList);
        serverIP = "192.168.43.1";


        //current Date
        namearraylist = new ArrayList<String>();
        rollarraylist = new ArrayList<String>();
        semarraylist = new ArrayList<String>();
        greenStudentarraylist = new ArrayList<String>();
        greenlist = new ArrayList<String>();


        //databse calls to retrieve

        layout_checkBox = (CheckBox) findViewById(R.id.layout_checkBox);
        layout_tv = (TextView) findViewById(R.id.layout_tv);


        initializeSemester();

        displayRegisteredStudent();

     /*   receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   Thread fst = new Thread(new ServerThread());
                fst = new Thread(new ServerThread());
                fst.start();


            }
        });
*/
        retrieveNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/**/

                if (greenlist.get(i).equalsIgnoreCase("1")) {
                    retrieveNameList.getChildAt(i).setBackgroundColor(Color.WHITE);
                    greenlist.set(i, "0");
                    Toast.makeText(MainActivity.this, "New element whute " + i, Toast.LENGTH_SHORT).show();

                } else {
                    retrieveNameList.getChildAt(i).setBackgroundColor(Color.GREEN);
                    greenlist.set(i, "1");
                    Toast.makeText(MainActivity.this, "New Size" + greenlist.size(), Toast.LENGTH_SHORT).show();
                }


            }
        });


        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);


    }

    private void stopConnection() {

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();

        }


    }

    private void displayStatus(String message) {

        if (ab != null) {
            ab.setSubtitle(message);
        }


    }

    private void startConnection() {
        fst = new Thread(new ServerThread());
        fst.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);



        MenuItem connectionSwitch = menu.findItem(R.id.mainactivity_toolbar_switch);
        SwitchCompat actionBarSwitch = (SwitchCompat)connectionSwitch.getActionView().findViewById(R.id.mainactivity_toolbar_switch_layout);

        actionBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //start port connection
                    startConnection();
                    displayStatus("connection Started");
                    Toast.makeText(context, "In Main1", Toast.LENGTH_SHORT).show();
                }else{
                    stopConnection();
                    displayStatus("Connection Stopped");
                }
            }
        });


        return true;
    }

    private void initializeSemester() {

        if (StartActivity.semester != null) {
            semester = Integer.parseInt(StartActivity.semester);
        } else {
            Toast.makeText(this, "Semester Not Selected", Toast.LENGTH_SHORT).show();
        }


    }

    public void nameofRegisterStudent(View view) {


        ddialog = new Dialog(this);
        ddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        ddialog.setContentView(R.layout.calender_picker);
        ddialog.setCancelable(true);
        ddialog.show();

        datePicker = (DatePicker) ddialog.findViewById(R.id.datePicker);
        Button ok = (Button) ddialog.findViewById(R.id.next1);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_date = datePicker.getDayOfMonth() + "";
                new_month = datePicker.getMonth() + 1 + "";
                new_year = datePicker.getYear() + "";

                datasource = new MessagesDataSource(MainActivity.this);
                datasource.open();
                String fullDate = Message.convertToFullDate(new_year, new_month, new_date);

                ArrayList<Message> retriveByDateList = datasource.getAttendanceByDate(semester, fullDate);


                for (int i = 0; i < greenlist.size(); i++) {
                    String comp = greenlist.get(i);
                    if (comp.equalsIgnoreCase("1")) {
                        Details detailss = registeredlistData.get(i);
                        String name = detailss.getMessage();
                        String roll = detailss.getRollNo();
                        String sem = detailss.getSem();
                        Message currentStudent = new Message(name, roll, sem);
                        if (!retriveByDateList.contains(currentStudent)) {
                            datasource.createMessage(name, roll, sem, new_date, new_month, new_year);

                            //toast for totalstudent
                        }
                        greenlist.set(i, "0");

                    }
                }
                Toast.makeText(MainActivity.this, "Data Stored!", Toast.LENGTH_SHORT).show();

                displayStatus("Attendance Saved");
                ddialog.cancel();
                //   Toast.makeText(MainActivity.this, new_date + "-" + new_month + "-" + new_year, Toast.LENGTH_SHORT).show();
            }
        });

    }


    //recive thread
    public class ServerThread implements Runnable {

        public void run() {
            try {
                if (serverIP != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Listening on IP: " + serverIP);
                        }
                    });
                    serverSocket = new ServerSocket(SERVERPORT);


                    for (int i = 0; i == 0; i = 0) {
                        Log.d("server activt", "running");
                        // LISTEN FOR INCOMING CLIENTS
                        Socket client = serverSocket.accept();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serverStatus.setText("Connectend.");
                            }
                        });

                        try {
                            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            //Toast.makeText(MainActivity.this, "msg "+in.readLine(), Toast.LENGTH_SHORT).show();
                            String line = null;
                            int b = 0;
                            while ((line = in.readLine()) != null) {
                                Log.d("ServerActivity", line);
                                if (b == 0) {
                                    sender_name = line;
                                    Log.d("Servername", sender_name);


                                } else if (b == 1) {
                                    sender_roll = line;
                                    Log.d("Serverroll", sender_roll);
                                    //String s=insertNameRollNumber(sender_name,sender_roll);
                                    //Log.d("methor return",s);
                                } else if (b == 2) {
                                    sender_sem = line;
                                    Log.d("server sem", sender_sem);

                                }
                                b++;
                                // Toast.makeText(MainActivity.this, "in if server", Toast.LENGTH_SHORT).show();

                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // serverStatus.setText
                                    String s = insertNameRollNumber(sender_name, sender_roll, sender_sem);
                                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                                    // (serverStatus.getText() + finalmsg);
                                    // DO WHATEVER YOU WANT TO THE FRONT END
                                    // THIS IS WHERE YOU CAN BE CREATIVE
                                }
                            });

                            //  String server_result=insertNameRollNumber(sender_name,"56");


                            // Toast.makeText(MainActivity.this, server_result, Toast.LENGTH_SHORT).show();
                            // break;
                        } catch (Exception e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    serverStatus.setText("Oops. Connection interrupted. Please reconnect your phones.");
                                }
                            });
                            e.printStackTrace();
                        }


                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Couldn't detect internet connection.");
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        serverStatus.setText("Error");
                    }
                });
                e.printStackTrace();
            }
        }
    }


    //client thread
    public class ClientThread implements Runnable {

        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
                Log.d("ClientActivity", "C: Connecting...");
                Socket socket = new Socket(serverAddr, SERVERPORT);
                connected = true;

                while (connected) {

                    try {
                        Log.d("ClientActivity", "C: Sending command.");

                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                                .getOutputStream())), true);
                        // WHERE YOU ISSUE THE COMMANDS
                        out.println(name.getText().toString());
                        out.println(roll.getText().toString());
                        Log.d("ClientActivity", "C: Sent.");
                        connected = false;
                    } catch (Exception e) {
                        Log.e("ClientActivity", "S: Error", e);
                    }
                }
                socket.close();
                Log.d("ClientActivity", "C: Closed.");
            } catch (Exception e) {
                Log.e("ClientActivity", "C: Error", e);
                connected = false;
            }
        }
    }


    public String insertNameRollNumber(String name, String roll, String sem) {

        boolean b = checkroll(name, roll, sem);
        if (b) {
            displayStatus("Added "+name);

            return "added " +name;
        } else {
            return "Already present";
        }
    }

    public boolean checkroll(String name, String roll, String sem) {

        if (rollarraylist.contains(roll)) {
            return false;
        } else {

            namearraylist.add(name);
            rollarraylist.add(roll);
            semarraylist.add(sem);

            int size = registeredlistData.size();

            for (int l = 0; l < size; l++) {
                if ((registeredlistData.get(l).toString()).equalsIgnoreCase(name)) {
                    Toast.makeText(MainActivity.this, "equals" + l, Toast.LENGTH_SHORT).show();
                    retrieveNameList.getChildAt(l).setBackgroundColor(Color.GREEN);
                    greenlist.set(l, "1");
                    l = size;

                } else {
                    Toast.makeText(MainActivity.this, "Not REGISTERED", Toast.LENGTH_SHORT).show();


                }

            }
            //   Toast.makeText(MainActivity.this, "new size" + greenlist.size(), Toast.LENGTH_SHORT).show();

            return true;

        }

    }


    public void registerStudent(View view) {

        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        int rc = 1;
        //main2Activity = new Main2Activity();
        //  main2Activity.registerInDatabase(this);
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        startActivityForResult(i, rc);
    }


    public void displayRegisteredStudent() {

/*
        semPopupDialog = new Dialog(this);
        semPopupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        semPopupDialog.setContentView(R.layout.sem_popup);
        semPopupDialog.setCancelable(true);
        semPopupDialog.show();

         semOfStudent = (EditText) semPopupDialog.findViewById(R.id.sem_editext_dialog);
        Button enterBtnSemDialog=(Button)  semPopupDialog.findViewById(R.id.sem_enter_dialog_btn);

        enterBtnSemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 semester=Integer.parseInt(semOfStudent.getText().toString());
                semPopupDialog.cancel();



            }
        });

*/

        registeredlistData = new ArrayList<Details>();
        insertQuery = new InsertQuerySource(this);
        String d = "";
        insertQuery.open();
        // List<Message> values = datasource.getAllMessages();


        initializeSemester();
        registeredlistData = (ArrayList<Details>) insertQuery.getAllMessages(semester);
        retrieveNameList.setVisibility(View.VISIBLE);
        registerStudentAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, registeredlistData);
        retrieveNameList.setAdapter(registerStudentAdapter);


        Toast.makeText(MainActivity.this, "Data Retreived", Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, "size of REGISTERED STUDENT IS " + registeredlistData.size(), Toast.LENGTH_SHORT).show();

        greenlist.clear();
        for (int j = 0; j <= registeredlistData.size(); j++) {
            greenlist.add("0");
        }


        //  Toast.makeText(MainActivity.this, "SIZE OF GREENLIST" + greenlist.size(), Toast.LENGTH_SHORT).show();

      /*  Details details = registeredlistData.get(0);
        String name = details.getMessage();
        Toast.makeText(MainActivity.this, "NAME of REGISTERED STUDENT IS " + name, Toast.LENGTH_SHORT).show();
        */
    }


    public void retrieve_btn(View view) {
        Intent intent = new Intent(MainActivity.this, RetrieveActivity.class);
        startActivity(intent);
    }


    protected void onStop() {
        super.onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();

        }


    }




}




















