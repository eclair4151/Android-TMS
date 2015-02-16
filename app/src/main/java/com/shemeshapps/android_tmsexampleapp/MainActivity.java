package com.shemeshapps.android_tmsexampleapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shemeshapps.android_tms.Models.DrexelTerm;
import com.shemeshapps.android_tms.Parser;
import com.shemeshapps.android_tms.RequestUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Spinner termSelecter;
    ProgressBar termProgress;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        termSelecter = (Spinner)findViewById(R.id.TermsSpinner);
        termProgress = (ProgressBar)findViewById(R.id.loadingTermsProgress);
        searchButton = (Button)findViewById(R.id.searchCourses);
        final List<String> stringTerms = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stringTerms);
        termSelecter.setAdapter(adapter);

        RequestUtil.init(this);

        RequestUtil.getHomePage(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                List<DrexelTerm> terms = Parser.ParseHomepage((String)response);

                for(DrexelTerm term:terms)
                {
                    stringTerms.add(term.title);
                    adapter.notifyDataSetChanged();
                    termProgress.setVisibility(View.GONE);
                    searchButton.setEnabled(true);
                }

            }
        });



        RequestUtil.searchClasses(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Parser.ParseClassList((String) response);
            }
        }, 1, "", "101", "");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
