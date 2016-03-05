package com.example.dante.liveitup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private TextView textView;
    private Handler handler = new Handler();
    private ArrayList<ListElement> aList;
    private ListAdapter aa;

    private static MainActivity instance = null;

    private int energy = 80;
    public int energyMax = 100;
    private int hunger = 70;
    public int hungerMax = 100;
    private int emotion = 50;
    public int emotionMax = 100;

    SharedPreferences settings;
    public static MainActivity getActivity(){
        if(instance == null){
            instance = new MainActivity();
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);

        settings = PreferenceManager.getDefaultSharedPreferences((this));

        aList = new ArrayList<ListElement>();
        aa = new ListAdapter(this, R.layout.list_view, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);

        init();

        myListView.setOnItemClickListener(new ListClickHandler(energy, hunger, emotion, settings, this));
        // Read from appInfo the list of friends, and sets it.
        aa.notifyDataSetChanged();

        instance = this;


    }

    public void Stats(View v)
    {
        aa.clear();
    }
    public void Inv(View v)
    {
        aa.clear();
    }

    public void Actions(View v)
    {
        aa.clear();
        MorningA();
    }
    public void MorningA(){
        ListElement le = new ListElement();
        le.textLabel = "Sleep";
        le.tag = "Sleep1";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Test";
        le2.tag = "Test";
        aList.add(le2);
    }


    public void init()
    {

        energy = settings.getInt("energy", energy);
        progressBar.setProgress(energy);
        progressBar.setMax(energyMax);
        String energyText = Integer.toString(energy);
        energyMax = settings.getInt("energyMax", energyMax);
        String energyMaxText = Integer.toString(energyMax);
        TextView view = (TextView) findViewById(R.id.textView5);

        view.setText(energyText + "/" + energyMaxText);

        hunger = settings.getInt("hunger", hunger);
        progressBar2.setProgress(hunger);
        String hungerText = Integer.toString(hunger);
        TextView view2 = (TextView) findViewById(R.id.textView7);

        view2.setText(hungerText + "/100");

        emotion = settings.getInt("emotion", emotion);
        progressBar3.setProgress(emotion);
        String emotionText = Integer.toString(emotion);
        TextView view3 = (TextView) findViewById(R.id.textView9);

        view3.setText(emotionText + "/100");

    }
}
