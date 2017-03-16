package com.example.aisha.wifion;

/**
 * Created by Aisha on 10/11/2016.
 */
public class Details {


    private long id;
    private String message;
    private String RollNo;
    private String Sem;

    public String getSem() {
        return Sem;
    }

    public void setSem(String sem) {
        Sem = sem;
    }




    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return message;

    }
}
