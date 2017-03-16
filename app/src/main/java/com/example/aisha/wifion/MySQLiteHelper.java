package com.example.aisha.wifion;

/**
 * Created by Aisha on 9/21/2016.
 */

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MESSAGES = "ATTENDANCE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ROLLNO = "Roll_No";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_MONTH = "Month";
    public static final String COLUMN_YEAR = "Year";
    public static final String COLUMN_FULL_DATE ="Full_Date";
    public static final String COLUMN_SEMESTER ="Semester";

    private static final String DATABASE_NAME = "message.db";
    private static final int DATABASE_VERSION = 17;
    Context main;

    // Database creation sql statement

   /*
    private static final String DATABASE_CREATE = "create table " + TABLE_MESSAGES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + "text not null, "
            + COLUMN_ROLLNO + " text not null);";
    */

    private static final String DATABASE_CREATE = "create table " + TABLE_MESSAGES + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME +" "+" text not null, "
            + COLUMN_ROLLNO + " text not null, "
            + COLUMN_DAY + " text not null, "
            + COLUMN_MONTH + " text not null, "
            + COLUMN_YEAR + " text not null, "
            + COLUMN_FULL_DATE + " text not null, "
            +COLUMN_SEMESTER+ " text not null ,"
            +"unique ( "+COLUMN_ROLLNO +" , "+COLUMN_FULL_DATE+ "  )"
            + ");";


   /* String DATABASE_CREATE = "CREATE TABLE " + TABLE_MESSAGES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_ROLLNO + " TEXT," + COLUMN_DAY
            + " TEXT," + COLUMN_MONTH + " TEXT,"
            + COLUMN_YEAR + " TEXT" + ")";
*/


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        main = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.d("in o create ofdatabes", "databse new create");
        Toast.makeText(main, "on create of databse", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        Log.d("in upgrade", "databse new create");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }


}
