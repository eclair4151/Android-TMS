package com.shemeshapps.android_tmsexampleapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shemeshapps.android_tms.Models.DrexelTerm;
import com.shemeshapps.android_tmsexampleapp.R;

import java.util.List;

/**
 * Created by Tomer on 2/16/15.
 */
public class TermSpinnerAdapter extends ArrayAdapter<DrexelTerm> {

    Context context;
    List<DrexelTerm> terms;

    public TermSpinnerAdapter(Context context,List<DrexelTerm> terms)
    {
        super(context,android.R.layout.simple_spinner_item,terms);
        this.terms = terms;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(convertView,position);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       return getItemView(convertView,position);
    }



    private View getItemView(View convertView, int position)
    {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        TextView text = (TextView)convertView.findViewById(android.R.id.text1);
        text.setText(terms.get(position).title);

        return convertView;
    }


}
