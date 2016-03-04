package com.example.dante.liveitup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 100;
    private TextView textView;
    private Handler handler = new Handler();
    private ArrayList<ListElement> aList;
    private ListAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        aList = new ArrayList<ListElement>();
        aa = new ListAdapter(this, R.layout.list_view, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        myListView.setOnItemClickListener(new ListClickHandler());
        // Read from appInfo the list of friends, and sets it.
        for (int i = 0; i < 6; i++) {
            ListElement le = new ListElement();
            le.textLabel = "hello";
            le.buttonLabel = "Choose";
            aList.add(le);
        }
        aa.notifyDataSetChanged();

    }

    public void Stats(View v)
    {
        aa.clear();
        progressStatus += 5;
        progressBar.setProgress(progressStatus);
    }
    public void Inv(View v)
    {
        aa.clear();
        progressStatus -= 5;
        progressBar.setProgress(progressStatus);
    }

    public void Actions(View v)
    {
        aa.clear();
    }
}
