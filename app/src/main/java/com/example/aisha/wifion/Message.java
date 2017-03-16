package com.example.aisha.wifion;

import android.widget.Toast;

/**
 * Created by Aisha on 9/21/2016.
 */
public class Message {
    private long id;
    private String message;
    private String RollNo;
    private String cDay;
    private String cYear;
    private String cMonth;
    private String full_date;
    private String semester;
    private long count;

    public Message(String message, String rollNo, String semester) {
        this.message = message;
        RollNo = rollNo;
        this.semester = semester;
    }

    public Message() {
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


    public String getFull_date() {
        return full_date;
    }

    public void setFull_date(String full_date) {
        this.full_date = full_date;
    }

    public void setFull_date() {
        String fullDate = this.cYear + "-" + this.cMonth + "-" + this.cDay;
        this.full_date = fullDate;
    }

    public static String convertToFullDate(int year, int month, int day) {

        String fullDate;
        if (day <= 9) {

            fullDate = year + "-" + month + "-0" + day;
        } else {
            fullDate = year + "-" + month + "-" + day;
        }
        return fullDate;
    }

    public static String convertToFullDate(String year, String month, String day) {

        String fullDate="";
        try {
            int date = Integer.valueOf(day);

            if (date <= 9) {
                fullDate = year + "-" + month + "-0" + day;
            } else {
                fullDate = year + "-" + month + "-" + day;
            }
        }catch(Exception e){

            e.printStackTrace();
        }

        return fullDate;
    }

    public String getcYear() {
        return cYear;
    }

    public void setcYear(String cYear) {
        this.cYear = cYear;
    }

    public String getcMonth() {
        return cMonth;
    }

    public void setcMonth(String cMonth) {
        this.cMonth = cMonth;
    }


    public String getcDay() {
        return cDay;
    }

    public void setcDay(String cDay) {
        this.cDay = cDay;
    }


    public String getRollNo() {
        return RollNo;
    }


    public String getByDateInfo() {

        return this.message + "    -  " + this.RollNo;
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
        return message + " - " + RollNo + "- " + count;

    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getByRollInfo() {

        return this.full_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (RollNo != null ? !RollNo.equals(message1.RollNo) : message1.RollNo != null)
            return false;
        else
            return true;

    /*    if (id != message1.id) return false;
        if (count != message1.count) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null)
            return false;
        if (RollNo != null ? !RollNo.equals(message1.RollNo) : message1.RollNo != null)
            return false;
        if (cDay != null ? !cDay.equals(message1.cDay) : message1.cDay != null) return false;
        if (cYear != null ? !cYear.equals(message1.cYear) : message1.cYear != null) return false;
        if (cMonth != null ? !cMonth.equals(message1.cMonth) : message1.cMonth != null)
            return false;
        if (full_date != null ? !full_date.equals(message1.full_date) : message1.full_date != null)
            return false;
        return semester != null ? semester.equals(message1.semester) : message1.semester == null;
*/
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (RollNo != null ? RollNo.hashCode() : 0);
        result = 31 * result + (cDay != null ? cDay.hashCode() : 0);
        result = 31 * result + (cYear != null ? cYear.hashCode() : 0);
        result = 31 * result + (cMonth != null ? cMonth.hashCode() : 0);
        result = 31 * result + (full_date != null ? full_date.hashCode() : 0);
        result = 31 * result + (semester != null ? semester.hashCode() : 0);
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }
}
