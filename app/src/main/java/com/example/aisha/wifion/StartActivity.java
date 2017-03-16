package com.example.aisha.wifion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    public static String semester = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Spinner semSpinner = (Spinner) findViewById(R.id.startactivity_sem_spinner);
        semSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();

                if (s.equals("0")) {
                    semester = null;
                } else {
                    semester = s;
                    Toast.makeText(StartActivity.this, "Semester is " + semester, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                semester = null;
                Toast.makeText(StartActivity.this, "No Semester  ", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void openMainActivity(View view) {
        Intent i = new Intent(StartActivity.this, MainActivity.class);
        startActivityForResult(i, 1);
    }

    public void openMain2Activity(View view) {
        Intent i = new Intent(StartActivity.this, Main2Activity.class);
        startActivityForResult(i, 2);
    }

    public void openRetriveActivity(View view) {

        Intent i = new Intent(StartActivity.this, RetrieveNewActivity.class);
        startActivityForResult(i, 3);
    }

    public void openHelpActivity(View view) {


      /*  Intent i = new Intent(StartActivity.this , RetrieveNewActivity.class);
        startActivityForResult(i,1);
*/
    }
}
