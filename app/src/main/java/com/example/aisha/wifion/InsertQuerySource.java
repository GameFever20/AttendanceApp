package com.example.aisha.wifion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aisha on 10/11/2016.
 */
public class InsertQuerySource {


    private SQLiteDatabase database;
    private NewDbHelper newDbHelper;
    private String[] allColumns = { NewDbHelper.COLUMN_ID,NewDbHelper.COLUMN_MESSAGE, NewDbHelper.COLUMN_ROLLNO,NewDbHelper.COLUMN_SEM};
    Context main;

    public InsertQuerySource(Context context) {
        newDbHelper = new NewDbHelper(context);
        main=context;
    }

    public void open() throws SQLException {
        database = newDbHelper.getWritableDatabase();
    }

    public void close() {
        newDbHelper.close();
    }

    public boolean createMessage( String message,String rollno,String sem) {
        ContentValues values = new ContentValues();
        if(checkRollNumber(rollno)){
            Toast.makeText(main, "Already a Registered student "+rollno, Toast.LENGTH_SHORT).show();
        return false;
        }
        values.put(NewDbHelper.COLUMN_MESSAGE, message);
        values.put(NewDbHelper.COLUMN_ROLLNO,rollno);
        values.put(NewDbHelper.COLUMN_SEM,sem);


        try {
            long insertId = database.insert(NewDbHelper.TABLE_MESSAGES, null, values);

            Toast.makeText(main, "Inserted! hoe done", Toast.LENGTH_SHORT).show();

            return true;
        }catch(Exception exception){
            exception.printStackTrace();
            Toast.makeText(main, "Some Unknown error", Toast.LENGTH_SHORT).show();
            return false ;

        }
      /*  Cursor cursor = database.query(NewDbHelper.TABLE_MESSAGES,allColumns, NewDbHelper.COLUMN_ID + " = " + insertId, null,null, null, null);
       //  Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,null, null,null, null, null);
        cursor.moveToFirst();
        Details newDetail = cursorToMessage(cursor);
        cursor.close();
        return newDetail;
        */
    }

    public List<Details> getAllMessages(int sem) {
        List<Details> detailsList = new ArrayList<Details>();


            Cursor cursor = database.query(NewDbHelper.TABLE_MESSAGES, allColumns, null, null, null, null, null);
            // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MainActivity.SEARCH_BY, null,null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Details details = cursorToMessage(cursor);
            detailsList.add(details);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        //to get particular sem
        List<Details> getSemOfStudent=new ArrayList<>();
        int size=detailsList.size();
        for (int i=0;i<size;i++){
            Details details=detailsList.get(i);
            String semOfStudent= details.getSem();
            //int s=Integer.parseInt(semOfStudent);
            if(semOfStudent.equals(String.valueOf(sem))){
              //  getSemOfStudent.add(details.getMessage()+" -"+details.getRollNo());
                getSemOfStudent.add(details);

            }

        }







       // return detailsList;
        return getSemOfStudent;

    }

    private Details cursorToMessage(Cursor cursor) {
        Details details = new Details();
        details.setId(cursor.getLong(0));
        details.setMessage(cursor.getString(1));
        details.setRollNo(cursor.getString(2));
        details.setSem(cursor.getString(3));
        return details;
    }


    public boolean checkRollNumber(String rollNumber) throws SQLException{
       // Cursor c = database.rawQuery("SELECT * FROM "+NewDbHelper.TABLE_MESSAGES+" WHERE "+NewDbHelper.COLUMN_ROLLNO+" = "+rollNumber, null);

        String selection = NewDbHelper.COLUMN_ROLLNO + " =?";
        String[] selectionArgs = { rollNumber };
        String limit = "1";

        Cursor cursor = database.query(NewDbHelper.TABLE_MESSAGES, allColumns, selection, selectionArgs, null, null, null, limit);


        if (cursor.getCount() > 0)
            return true; // return true if the value of _username already exists
        return false; // Return false if _username doesn't match with any value of the columns "Username"
    }

}
