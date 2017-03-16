package com.example.aisha.wifion;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    boolean connected = false;
    private String serverIpAddress = "";
    EditText name, roll;


    //server
    String serverIP = "10.0.2.15";
    final int SERVERPORT = 5432;
    private ServerSocket serverSocket;
    android.os.Handler handler = new android.os.Handler();
    TextView serverStatus;
    String sender_name, sender_roll, sender_sem = "";

    public ArrayList<String> namearraylist;
    public ArrayList<String> rollarraylist;
    public ArrayList<String> semarraylist;
    public ArrayList<Details> arraylistData;
    InsertQuerySource insertQuery;

    Context context;

    ArrayAdapter registeredStudentAdapter;
    ListView resigerStudentList;
    ArrayList<Details> registeredStudentArrayList;

    Button storeRegisteredbtn;
    EditText roll_editText, name_editText, sem_editText;

    ActionBar ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar mainAcivityToolbar = (Toolbar) findViewById(R.id.mainactivity2_toolbar);
        setSupportActionBar(mainAcivityToolbar);

        resigerStudentList = (ListView) findViewById(R.id.list2);
        storeRegisteredbtn = (Button) findViewById(R.id.storeRegistered2);
        roll_editText = (EditText) findViewById(R.id.roll_editText);
        name_editText = (EditText) findViewById(R.id.name_editText);
        sem_editText = (EditText) findViewById(R.id.sem_editText);
        context = this;

        namearraylist = new ArrayList<String>();
        rollarraylist = new ArrayList<String>();
        semarraylist = new ArrayList<String>();


        ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Attendance");
            displayStatus("turn switch ON to connect");
        }



    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);


        MenuItem connectionSwitch = menu.findItem(R.id.mainactivity_toolbar_switch);
        SwitchCompat actionBarSwitch = (SwitchCompat) connectionSwitch.getActionView().findViewById(R.id.mainactivity_toolbar_switch_layout);

        actionBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //start port connection

                    startConnection();
                    displayStatus("connection Started");
                } else {
                    stopConnection();
                    displayStatus("Connection Stopped");
                }
            }
        });


        return true;
    }


    private void displayStatus(String message) {
        if (ab != null) {
            ab.setSubtitle(message);
        }

    }

    private void stopConnection() {

        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void startConnection() {

        Thread fst = new Thread(new ServerThread());
        fst.start();

    }


    public void registerInDatabase(Context p) {
       /* if (!connected) {
            serverIpAddress = "192.168.43.1";
            // serverIpAddress = servertext.getText().toString();
            Thread cThread = new Thread(new ClientThread());
            cThread.start();
             }
             */
        context = p;

        Thread fst = new Thread(new ServerThread());
        fst.start();

        if (fst.isAlive()) {

        }
        Toast.makeText(Main2Activity.this, "In Method", Toast.LENGTH_SHORT).show();


    }

    public void storeRegisteredStudent2(View view) {
        storedata();
    }

    public void callingRegisterServer(View view) {
        registerInDatabase(Main2Activity.this);

    }


    public void saveEditTextData(View view) {

        String sname = name_editText.getText().toString();
        String sroll = roll_editText.getText().toString();
        String ssem = sem_editText.getText().toString();

        if (sname.isEmpty() || sroll.isEmpty() || ssem.isEmpty()) {
            Toast.makeText(Main2Activity.this, "Empty Nothing To Add", Toast.LENGTH_SHORT).show();
        } else if (sroll.length() > 5) {

            Toast.makeText(this, "Roll Number is not in correct format", Toast.LENGTH_SHORT).show();
        } else if (ssem.length() != 1) {
            Toast.makeText(context, "Wrong semester ", Toast.LENGTH_SHORT).show();

        } else {

            insertQuery = new InsertQuerySource(Main2Activity.this);
            insertQuery.open();
            //Toast.makeText(Main2Activity.this, "after opening of databse", Toast.LENGTH_SHORT).show();
            Details detail = null;

            // detail = insertQuery.createMessage(sname, sroll, ssem);

           boolean b= insertQuery.createMessage(sname, sroll, ssem);

            if (b){
                displayStatus(name + " registered");
            }else{


            }
            roll_editText.setText("");
            sem_editText.setText("");
            name_editText.setText("");


            //  Toast.makeText(Main2Activity.this, detail.toString(), Toast.LENGTH_SHORT).show();


            // namearraylist.add(sname);
            //registeredStudentAdapter = new ArrayAdapter(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, namearraylist);
            //resigerStudentList.setAdapter(registeredStudentAdapter);

        }

    }

    //client thread------333333

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
                        // out.println(name.getText().toString());
                        // out.println(roll.getText().toString());
                        out.println("checking");
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


    //recive thread
    public class ServerThread implements Runnable {

        public void run() {
            try {
                if (serverIP != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //serverStatus.setText("Listening on IP: " + serverIP);
                            Log.d("server activt", "Listening on IP: " + serverIP);

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
                                //   serverStatus.setText("Connectend.");
                                Log.d("connextedd", "Listening on IP: " + serverIP);

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
                                    String s = insertDetailsInData(sender_name, sender_roll, sender_sem);
                                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
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
                                    // serverStatus.setText("Oops. Connection interrupted. Please reconnect your phones.");
                                    Log.d("oops", "Connection interupt");

                                }
                            });
                            e.printStackTrace();
                        }


                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("oops", "Couldn't detect internet connection.");

                            // serverStatus.setText("Couldn't detect internet connection.");
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // serverStatus.setText("Error");
                    }
                });
                e.printStackTrace();
            }
        }
    }


    public String insertDetailsInData(String name, String roll, String sem) {
        if (roll.isEmpty() && name.isEmpty() && sem.isEmpty()) {
            return "Empty String";

        } else {
            boolean b = checkroll(name, roll, sem);
            if (b) {
                displayStatus("Registered "+name);

                return "REGISTERED";
            } else {
                return "Already Registered";
            }
        }
    }

    public boolean checkroll(String name, String roll, String sem) {

        if (rollarraylist.contains(roll)) {
            return false;
        } else {
            rollarraylist.add(roll);
            namearraylist.add(name);
            semarraylist.add(sem);

            registeredStudentAdapter = new ArrayAdapter(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, namearraylist);
            resigerStudentList.setAdapter(registeredStudentAdapter);

            return true;
        }


    }


    public void storedata() {

        int size = namearraylist.size();
        if (size == 0) {
            Toast.makeText(this, "List is Empty ", Toast.LENGTH_SHORT).show();
            return;
        }
        insertQuery = new InsertQuerySource(context);
        insertQuery.open();
        Toast.makeText(context, "after created databse", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < size; i++) {
            String sname = namearraylist.get(i);
            String sroll = rollarraylist.get(i);
            String ssem = semarraylist.get(i);


            // Details detail = null;
            //  detail = insertQuery.createMessage(sname, sroll, ssem);
            boolean b = insertQuery.createMessage(sname, sroll, ssem);
            if (b) {
            } else {

            }

            //  Toast.makeText(context, detail.toString(), Toast.LENGTH_SHORT).show();
        }

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
