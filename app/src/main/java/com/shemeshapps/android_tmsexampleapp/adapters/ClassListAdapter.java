package com.shemeshapps.android_tmsexampleapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shemeshapps.android_tms.Models.BasicClass;
import com.shemeshapps.android_tmsexampleapp.R;

import java.util.List;

/**
 * Created by Tomer on 2/16/15.
 */
public class ClassListAdapter extends ArrayAdapter<BasicClass> {

    List<BasicClass> classes;
    Context context;
    public ClassListAdapter(Context context, List<BasicClass> classes)
    {
        super(context, R.layout.class_list_template,classes);
        this.context = context;
        this.classes = classes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.class_list_template, null);
        }

        BasicClass c = classes.get(position);

        TextView className = (TextView)convertView.findViewById(R.id.className);
        TextView classType = (TextView)convertView.findViewById(R.id.classType);
        TextView classProf = (TextView)convertView.findViewById(R.id.classProf);
        TextView classTitle = (TextView)convertView.findViewById(R.id.classTitle);
        TextView classTime = (TextView)convertView.findViewById(R.id.classTimes);
        TextView classSpots = (TextView)convertView.findViewById(R.id.classSpots);

        className.setText(c.subject + c.courseNum);
        classType.setText(c.type);
        classProf.setText(c.instructor);
        classTitle.setText(c.courseTitle);
        classTime.setText(c.days + " " + c.stringTime);
        if(c.classFull)
        {
            classSpots.setText("FULL");
        }
        else
        {
            classSpots.setText("Max enroll=" + c.maxEnroll + ", Enroll=" + c.curEnrolled);
        }



        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return classes.get(position).crn.hashCode();
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }



}
