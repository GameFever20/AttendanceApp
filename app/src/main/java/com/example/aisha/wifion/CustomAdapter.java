package com.example.aisha.wifion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Aisha on 1/15/2017.
 */
public class CustomAdapter extends BaseAdapter{


        Context context;
    int count;
        LayoutInflater inflter;
    ArrayList<Message> listData = new ArrayList<Message>();

    public CustomAdapter(Context applicationcontext,ArrayList<Message> fullDetails,int inteligiblity_count,LayoutInflater passinflater){
        context=applicationcontext;
       count= inteligiblity_count;
        listData= fullDetails;
       // inflter = (LayoutInflater.from(context));
         inflter=passinflater;
        //Toast.makeText(context, fullDetails.size(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.listview_items, null);
        TextView nameofstudent = (TextView) view.findViewById(R.id.custom_textview);
        Toast.makeText(context, listData.size()+"", Toast.LENGTH_SHORT).show();

        Toast.makeText(context, "value of possition"+i, Toast.LENGTH_SHORT).show();



            Message message=listData.get(i);

            Toast.makeText(context, message.getMessage(), Toast.LENGTH_SHORT).show();
            int k=(int)message.getCount();
            if (k>=count){
                nameofstudent.setText(message.getMessage()+"-"+message.getCount());
                nameofstudent.setBackgroundColor(Color.GREEN);
            }

                else{
                nameofstudent.setText(message.getMessage()+""+message.getCount());
                nameofstudent.setBackgroundColor(Color.RED);

            }


        return view;
    }
}
