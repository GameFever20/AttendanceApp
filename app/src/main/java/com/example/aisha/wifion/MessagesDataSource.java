package com.example.aisha.wifion;

/**
 * Created by Aisha on 9/21/2016.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

public class MessagesDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_ROLLNO, MySQLiteHelper.COLUMN_DAY, MySQLiteHelper.COLUMN_MONTH, MySQLiteHelper.COLUMN_YEAR, MySQLiteHelper.COLUMN_FULL_DATE, MySQLiteHelper.COLUMN_SEMESTER};
    Context main;

    public MessagesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        main = context;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Message createMessage(String message, String rollno, String sem, String date, String month, String year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, message);
        values.put(MySQLiteHelper.COLUMN_ROLLNO, rollno);
        values.put(MySQLiteHelper.COLUMN_SEMESTER, sem);
        values.put(MySQLiteHelper.COLUMN_DAY, date);
        values.put(MySQLiteHelper.COLUMN_MONTH, month);
        values.put(MySQLiteHelper.COLUMN_YEAR, year);

        String set_column_full_date = Message.convertToFullDate(year ,month ,date);
        values.put(MySQLiteHelper.COLUMN_FULL_DATE, set_column_full_date);

        long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,null, null,null, null, null);
        cursor.moveToFirst();
        Message newMessage = cursorToMessage(cursor);
        cursor.close();
        return newMessage;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, null, null, null, null, null);
        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MainActivity.SEARCH_BY, null,null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);

            messages.add(message);
            Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    private Message cursorToMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getLong(0));
        message.setMessage(cursor.getString(1));
        message.setRollNo(cursor.getString(2));
        message.setcDay(cursor.getString(3));
        message.setcMonth(cursor.getString(4));
        message.setcYear(cursor.getString(5));

        message.setFull_date(cursor.getString(6));


        return message;
    }

    private Message cursorToMessage(Cursor cursor, boolean iscount) {
        Message message = new Message();
        message.setId(cursor.getLong(0));
        message.setMessage(cursor.getString(1));
        message.setRollNo(cursor.getString(2));
        message.setcDay(cursor.getString(3));
        message.setcMonth(cursor.getString(4));
        message.setcYear(cursor.getString(5));
        //changes by priyank
        message.setFull_date(cursor.getString(6));
        message.setSemester(cursor.getString(7));
        message.setCount(cursor.getLong(8));


        return message;
    }


    public List<String> getAttendanceByRollno(int value) {
        List<Message> messages = new ArrayList<Message>();

        Log.d("Logs", "before cursor call");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_ROLLNO
                + "= " + value, null, null, null, null);
        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MainActivity.SEARCH_BY, null,null, null, null);

        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);

            messages.add(message);
            // Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        //changes made on listview(to display only name )
        List<String> finalStorageList = new ArrayList<>();
        int tempSize = messages.size();
        for (int i = 0; i < tempSize; i++) {
            Message message = messages.get(i);
            finalStorageList.add(message.getFull_date() + " - " + message.getMessage());
        }
        // return messages;
        return finalStorageList;
    }

    public List<String> getAttendanceByRollno(int semester, String rollNumber) {
        List<Message> messages = new ArrayList<Message>();

        Log.d("Logs", "before cursor call");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns
                , MySQLiteHelper.COLUMN_ROLLNO + "= " + "\'" + rollNumber + "\'" + " AND " + MySQLiteHelper.COLUMN_SEMESTER + " = " + "\'" + String.valueOf(semester) + "\'"
                , null, null, null, null);
        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MainActivity.SEARCH_BY, null,null, null, null);

        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);

            //Toast.makeText(main, "message added", Toast.LENGTH_SHORT).show();
            messages.add(message);
            // Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        //changes made on listview(to display only name )
        List<String> finalStorageList = new ArrayList<>();
        if (!messages.isEmpty())
            finalStorageList.add(messages.get(0).getMessage());

        int tempSize = messages.size();
        for (int i = 0; i < tempSize; i++) {
            /*Message message = messages.get(i);
            finalStorageList.add(message.getFull_date() + " - " + message.getMessage());
            */
            finalStorageList.add(messages.get(i).getByRollInfo());

        }


        // return messages;
        return finalStorageList;
    }

    public List<String> getAttendanceByRollno(String value) {
        List<Message> messages = new ArrayList<Message>();

        Log.d("Logs", "before cursor call");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_ROLLNO
                + "= " + value, null, null, null, null);
        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MainActivity.SEARCH_BY, null,null, null, null);

        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);

            messages.add(message);
            // Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        //changes made on listview(to display only name )
        List<String> finalStorageList = new ArrayList<>();
        int tempSize = messages.size();
        for (int i = 0; i < tempSize; i++) {
            Message message = messages.get(i);
            finalStorageList.add(message.getFull_date() + " - " + message.getMessage());
        }
        // return messages;
        return finalStorageList;
    }


    public List<Message> getAttendanceByName(String value) {
        List<Message> messages = new ArrayList<Message>();

        Log.d("Logs", "before cursor call");

        //  Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MySQLiteHelper.COLUMN_NAME + "= "+value, null, null, null, null);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_NAME

                + "= " + value, null, null, null, null);

        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);

            messages.add(message);
            Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    public List<Message> getAttendanceByDate(int sem) {
        List<Message> messages = new ArrayList<Message>();

        Log.d("Logs", "before cursor call");

        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_DAY + "= " + value, null, null, null, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_SEMESTER + "= " + sem, null, null, null, null);

        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MySQLiteHelper.COLUMN_DAY+ "= "+value, null, null, null, null);

        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            //   Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    public ArrayList<Message> getAttendanceByDate(int semester, String fullDate) {
        ArrayList<Message> messages = new ArrayList<Message>();

        Log.d("Logs", "before cursor call");

        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns, MySQLiteHelper.COLUMN_DAY + "= " + value, null, null, null, null);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES, allColumns
                , MySQLiteHelper.COLUMN_SEMESTER + "= " + "\'" + semester + "\'" + " AND " + MySQLiteHelper.COLUMN_FULL_DATE + " = " + "\'" + fullDate + "\'"
                , null, null, null, null);

        // Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,allColumns,MySQLiteHelper.COLUMN_DAY+ "= "+value, null, null, null, null);

        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            //   Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(main, "message added in dabase"+message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;

        /*//changes made on listview(to display only name )
        List<String> finalStorageList = new ArrayList<>();
        int tempSize = messages.size();
        for (int i = 0; i < tempSize; i++) {
            *//*Message message = messages.get(i);
            finalStorageList.add(message.getFull_date() + " - " + message.getMessage());
            *//*
            finalStorageList.add(messages.get(i).getByDateInfo());

        }

        return finalStorageList;*/

    }


    public List<Message> getAttendanceByBetweenDates(String from, String to) {
        List<Message> messages = new ArrayList<Message>();

//        String from= "2016-11-3";
        //      String to="2016-120-5";

        //  Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_MESSAGES + " where "+MySQLiteHelper.COLUMN_FULL_DATE+" BETWEEN '" + from + "' AND '" + to + "' ORDER BY "+MySQLiteHelper.COLUMN_FULL_DATE+" ASC", null);

        //new b/w date only order by
        // Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_MESSAGES + " where "+MySQLiteHelper.COLUMN_FULL_DATE+" BETWEEN '" + from + "' AND '" + to + "' ORDER BY "+MySQLiteHelper.COLUMN_FULL_DATE+" ASC", null);

        //with order by and group by and coount
        // Cursor cursor = database.rawQuery("select *, count(NAME) from " + MySQLiteHelper.TABLE_MESSAGES + " where "+MySQLiteHelper.COLUMN_FULL_DATE+" BETWEEN '" + from + "' AND '" + to + "' GROUP BY "+ MySQLiteHelper.COLUMN_NAME + " ORDER BY "+MySQLiteHelper.COLUMN_FULL_DATE+" ASC", null);

        //group by and order by
        Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_MESSAGES + " where " + MySQLiteHelper.COLUMN_FULL_DATE + " BETWEEN '" + from + "' AND '" + to + "' GROUP BY " + MySQLiteHelper.COLUMN_NAME + " ORDER BY " + MySQLiteHelper.COLUMN_FULL_DATE + " ASC", null);


        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);

            messages.add(message);
            Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    public List<Message> getAttendanceByEligiblity(String from, String to, int semester) {
        List<Message> messages = new ArrayList<Message>();


        //with order by and group by and coount
        // Cursor cursor = database.rawQuery("select " + MySQLiteHelper.COLUMN_ID + ", " + MySQLiteHelper.COLUMN_NAME + ", " + MySQLiteHelper.COLUMN_ROLLNO + ", " + MySQLiteHelper.COLUMN_DAY + ", " + MySQLiteHelper.COLUMN_MONTH + ", " + MySQLiteHelper.COLUMN_YEAR + ", " + MySQLiteHelper.COLUMN_FULL_DATE +", " + MySQLiteHelper.COLUMN_SEMESTER + ", COUNT(" + MySQLiteHelper.COLUMN_NAME + ") from " + MySQLiteHelper.TABLE_MESSAGES + " where " + MySQLiteHelper.COLUMN_FULL_DATE + " BETWEEN '" + from + "' AND '" + to + "' GROUP BY " + MySQLiteHelper.COLUMN_NAME + " ORDER BY " + MySQLiteHelper.COLUMN_FULL_DATE + " ASC", null);

        // Cursor cursor = database.rawQuery("select " + MySQLiteHelper.COLUMN_ID + ", " + MySQLiteHelper.COLUMN_NAME + ", " + MySQLiteHelper.COLUMN_ROLLNO + ", " + MySQLiteHelper.COLUMN_DAY + ", " + MySQLiteHelper.COLUMN_MONTH + ", " + MySQLiteHelper.COLUMN_YEAR + ", " + MySQLiteHelper.COLUMN_FULL_DATE +", " + MySQLiteHelper.COLUMN_SEMESTER + ", COUNT(" + MySQLiteHelper.COLUMN_ROLLNO + ") from " + MySQLiteHelper.TABLE_MESSAGES + " where " +MySQLiteHelper.COLUMN_SEMESTER +" = "+semester+" AND " + MySQLiteHelper.COLUMN_FULL_DATE + " BETWEEN '" + from + "' AND '" + to + "' GROUP BY " + MySQLiteHelper.COLUMN_ROLLNO + " ORDER BY " + MySQLiteHelper.COLUMN_FULL_DATE + " ASC", null);
        Cursor cursor = database.rawQuery("select " + MySQLiteHelper.COLUMN_ID + ", " + MySQLiteHelper.COLUMN_NAME + ", " + MySQLiteHelper.COLUMN_ROLLNO + ", " + MySQLiteHelper.COLUMN_DAY + ", " + MySQLiteHelper.COLUMN_MONTH + ", " + MySQLiteHelper.COLUMN_YEAR + ", " + MySQLiteHelper.COLUMN_FULL_DATE + ", " + MySQLiteHelper.COLUMN_SEMESTER + ", COUNT(" + MySQLiteHelper.COLUMN_ROLLNO + ") from " + MySQLiteHelper.TABLE_MESSAGES + " where " + MySQLiteHelper.COLUMN_SEMESTER + " = " + semester + " AND " + MySQLiteHelper.COLUMN_FULL_DATE + " BETWEEN '" + from + "' AND '" + to + "' GROUP BY " + MySQLiteHelper.COLUMN_ROLLNO + " ORDER BY COUNT(" + MySQLiteHelper.COLUMN_ROLLNO + ")" + " ASC", null);

        //group by and order by
        //Cursor cursor = database.rawQuery("select * from " + MySQLiteHelper.TABLE_MESSAGES + " where "+MySQLiteHelper.COLUMN_FULL_DATE+" BETWEEN '" + from + "' AND '" + to + "' GROUP BY "+ MySQLiteHelper.COLUMN_NAME + " ORDER BY "+MySQLiteHelper.COLUMN_FULL_DATE+" ASC", null);


        Log.d("Logs", "before cursor call");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor, true);
            messages.add(message);

            //  Toast.makeText(main, "message added in dabase" + message.toString(), Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
        }


        /*List<Message> getSemesterStudentAL = new ArrayList<>();
        int size = messages.size();


        for (int i = 0; i < size; i++) {
            Message message = messages.get(i);
            int s = Integer.parseInt(message.getSemester());
            if (s == semester) {
                getSemesterStudentAL.add(message);

            }

        }*/


        // make sure to close the cursor
        cursor.close();
        //return getEligibleStudent;
        //  return messages;

        return messages;


    }


}

