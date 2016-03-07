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
    private TextView countDay;
    private TextView dayAndAction;
    private Handler handler = new Handler();
    private ArrayList<ListElement> aList;
    private ListAdapter aa;
    public String timeOfDay = "Morning";
    public int numberDay = 3;
    public int actionCount = 3;
    public int money = 150;

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
        countDay = (TextView) findViewById(R.id.textView);
        dayAndAction = (TextView) findViewById(R.id.textView2);


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
        ListElement le = new ListElement();
        le.textLabel = "Body size:";
        le.tag = "size";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Intelligence";
        le2.tag = "Intelligence";
        aList.add(le2);
        ListElement le3 = new ListElement();
        le3.textLabel = "charisma";
        le3.tag = "charisma";
        aList.add(le3);
    }
    public void Inv(View v)
    {
        aa.clear();
    }

    public void Actions(View v)
    {
        aa.clear();
        if(timeOfDay.equals("Morning"))
            morningA();
        if(timeOfDay.equals("Afternoon"))
            afternoonA();
        if(timeOfDay.equals("Evening"))
            eveningA();
    }
    public void morningA(){
        ListElement le = new ListElement();
        le.textLabel = "Take a nap";
        le.tag = "nap1";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Eat breakfest: $5";
        le2.tag = "breakfest1";
        aList.add(le2);
        ListElement le3 =  new ListElement();
        le3.textLabel = "Morning jog";
        le3.tag = "jog1";
        aList.add(le3);
        ListElement le4 =  new ListElement();
        le4.textLabel = "Work odd jobs: +$20";
        le4.tag = "work1";
        aList.add(le4);
        ListElement le5 =  new ListElement();
        le5.textLabel = "Volunteer work";
        le5.tag = "volunteer";
        aList.add(le5);
        ListElement le6 =  new ListElement();
        le6.textLabel = "Go to the movies: -$10";
        le6.tag = "movies";
        aList.add(le6);
    }

    public void afternoonA(){
        ListElement le = new ListElement();
        le.textLabel = "Take a mid day nap";
        le.tag = "nap1";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Eat Lunch: -$7";
        le2.tag = "lunch1";
        aList.add(le2);
        ListElement le3 =  new ListElement();
        le3.textLabel = "Afternoon jog";
        le3.tag = "jog1";
        aList.add(le3);
        ListElement le4 =  new ListElement();
        le4.textLabel = "Go to the gym: -$15";
        le4.tag = "gym";
        aList.add(le4);
        ListElement le5 =  new ListElement();
        le5.textLabel = "Work odd jobs: +$20";
        le5.tag = "work1";
        aList.add(le5);
        ListElement le6 =  new ListElement();
        le6.textLabel = "Go to the movies: -$10";
        le6.tag = "movies";
        aList.add(le6);
    }

    public void eveningA(){
        ListElement le = new ListElement();
        le.textLabel = "Sleep";
        le.tag = "Sleep1";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Eat Dinner: -$10";
        le2.tag = "dinner1";
        aList.add(le2);
        ListElement le3 =  new ListElement();
        le3.textLabel = "Late night jog... risky";
        le3.tag = "jog2";
        aList.add(le3);
        ListElement le4 =  new ListElement();
        le4.textLabel = "Go to the gym: -$15";
        le4.tag = "gym";
        aList.add(le4);
        ListElement le5 =  new ListElement();
        le5.textLabel = "Go out to drink: -$15 to -$30";
        le5.tag = "drink";
        aList.add(le5);
    }



    public void init()
    {

        timeOfDay = settings.getString("Morning", timeOfDay);
        String actionText = Integer.toString(actionCount);
        dayAndAction.setText(timeOfDay + ": " + actionText + " actions");

        numberDay = settings.getInt("1", numberDay);
        String dayText = Integer.toString(numberDay);
        countDay.setText("Day:" + dayText);

        money = settings.getInt("100", money);
        TextView moneyAmount = (TextView) findViewById(R.id.textView3);
        String moneyText = Integer.toString(money);
        moneyAmount.setText("$" + moneyText);

        energy = settings.getInt("energy", energy);
        energyMax = settings.getInt("energyMax", energyMax);
        progressBar.setProgress(energy);
        progressBar.setMax(energyMax);
        String energyText = Integer.toString(energy);
        String energyMaxText = Integer.toString(energyMax);
        TextView view = (TextView) findViewById(R.id.textView5);

        view.setText(energyText + "/" + energyMaxText);

        hunger = settings.getInt("hunger", hunger);
        energyMax = settings.getInt("hungerMax", hungerMax);
        progressBar2.setProgress(hunger);
        progressBar2.setMax(hungerMax);
        String hungerText = Integer.toString(hunger);
        String hungerMaxText = Integer.toString(hungerMax);
        TextView view2 = (TextView) findViewById(R.id.textView7);

        view2.setText(hungerText + "/" + hungerMaxText);

        emotion = settings.getInt("emotion", emotion);
        emotionMax = settings.getInt("emotionMax", emotionMax);
        progressBar3.setProgress(emotion);
        progressBar3.setMax(emotionMax);
        String emotionText = Integer.toString(emotion);
        String emotionMaxText = Integer.toString(emotionMax);
        TextView view3 = (TextView) findViewById(R.id.textView9);

        view3.setText(emotionText + "/" + emotionMaxText);

    }
}
