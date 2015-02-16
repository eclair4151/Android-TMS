package com.shemeshapps.android_tmsexampleapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shemeshapps.android_tms.Helpers.Base64Coder;
import com.shemeshapps.android_tms.Models.BasicClass;
import com.shemeshapps.android_tms.Models.DrexelTerm;
import com.shemeshapps.android_tms.Parser;
import com.shemeshapps.android_tms.RequestUtil;
import com.shemeshapps.android_tmsexampleapp.adapters.ClassListAdapter;
import com.shemeshapps.android_tmsexampleapp.adapters.TermSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    Spinner termSelecter;
    ProgressBar termProgress;
    Button searchButton;
    EditText courseName,courseNum,courseCRN;
    SwipeRefreshLayout refreshLayout;
    ListView classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();

        final TermSpinnerAdapter termSpinnerAdapter = new TermSpinnerAdapter(this,new ArrayList<DrexelTerm>());
        termSelecter.setAdapter(termSpinnerAdapter);

        final ClassListAdapter classListAdapter = new ClassListAdapter(this,new ArrayList<BasicClass>());
        classList.setAdapter(classListAdapter);

        RequestUtil.init(this);
        

        RequestUtil.getHomePage(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                termSpinnerAdapter.clear();
                termSpinnerAdapter.addAll((List<DrexelTerm>) response);
                termProgress.setVisibility(View.GONE);
                searchButton.setEnabled(true);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener listener = new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        refreshLayout.setRefreshing(false);
                        classListAdapter.clear();
                        classListAdapter.addAll((List<BasicClass>)response);

                    }
                };
                hideSoftKeyBoard();
                DrexelTerm curTerm = (DrexelTerm)termSelecter.getSelectedItem();
                RequestUtil.searchClasses(listener,curTerm.index, courseName.getText().toString(), courseNum.getText().toString(),courseCRN.getText().toString());
                refreshLayout.setRefreshing(true);
            }
        });

    }



    private void setupView()
    {
        termSelecter = (Spinner)findViewById(R.id.TermsSpinner);
        termProgress = (ProgressBar)findViewById(R.id.loadingTermsProgress);
        searchButton = (Button)findViewById(R.id.searchCourses);
        courseName = (EditText)findViewById(R.id.courseNameBox);
        courseNum = (EditText)findViewById(R.id.courseNumBox);
        courseCRN = (EditText)findViewById(R.id.courseCRNBox);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.listRefreshLayout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        classList = (ListView)findViewById(R.id.classList);
    }


    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
