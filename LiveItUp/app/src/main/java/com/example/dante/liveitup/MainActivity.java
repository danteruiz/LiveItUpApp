package com.example.dante.liveitup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

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
    public int numberDay = 1;
    public int actionCount = 3;
    public int money = 50;
    public String bodySize = "Puny";
    public int bodyProgress = 95;
    public int charisma = 0;
    public int intelligence = 0;
    public String job = "unemployed";
    public ArrayList<String> inv = new ArrayList<String>();

    private static MainActivity instance = null;

    private int energy = 70;
    public int energyMax = 100;
    private int hunger = 70;
    public int hungerMax = 100;
    private int emotion = 70;
    public int emotionMax = 100;


    private String current_list;

    SharedPreferences settings;
    SharedPreferences.Editor editor;


    public static MainActivity getActivity()
    {
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
        editor = settings.edit();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mItem=menu.findItem(R.id.action_settings);

        mItem.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        timeOfDay = "Morning";
                        numberDay = 1;
                        actionCount = 3;
                        money = 50;
                        bodySize = "Puny";
                        bodyProgress = 0;
                        charisma = 0;
                        intelligence = 0;
                        job = "unemployed";
                        energy = 70;
                        energyMax = 100;
                        hunger = 70;
                        hungerMax = 100;
                        emotion = 70;
                        emotionMax = 100;
                        inv.clear();
                        editor.putString("bodySize", bodySize);
                        editor.putInt("energy", energy);
                        editor.putInt("emotion", emotion);
                        editor.putInt("hunger", hunger);
                        editor.putInt("energyMax", energyMax);
                        editor.putInt("hungerMax", hungerMax);
                        editor.putInt("emotionMax", emotionMax);
                        editor.putInt("100", money);
                        editor.putInt("1", numberDay);
                        editor.commit();
                        progressBar.setMax(energyMax);
                        progressBar.setProgress(energyMax);
                        progressBar2.setMax(hungerMax);
                        progressBar2.setProgress(hungerMax);
                        progressBar3.setMax(emotionMax);
                        progressBar3.setProgress(emotionMax);
                        init();
                        reload();

                        return false;
                    }
                });
        return true;
    }

    public void decrement_count()
    {
        actionCount--;
        if(actionCount == 0)
        {
            actionCount = 3;
            if(timeOfDay.equals("Morning"))
            {
                timeOfDay = "Afternoon";
                reload();
                return;
            }

            if(timeOfDay.equals("Afternoon"))
            {
                timeOfDay = "Evening";
                reload();
                return;
            }

            if(timeOfDay.equals("Evening"))
            {
                timeOfDay = "Morning";
                numberDay += 1;
                editor.putInt("1", numberDay);
                editor.commit();
                reload();
                return;
            }


        }
    }

    public void bodyIncrease(){
        if(bodySize.equals("Puny")) {
            bodySize = "Small build";
            energyMax = 130;
            editor.putInt("energyMax", energyMax);
            editor.putString("bodySize", bodySize);
            editor.commit();
            return;
        }
        if(bodySize.equals("Small build")) {
            bodySize = "Medium build";
            energyMax = 150;
            editor.putInt("energyMax", energyMax);
            editor.putString("bodySize", bodySize);
            editor.commit();
            return;
        }
        if(bodySize.equals("Medium build")) {
            bodySize = "Athletic build";
            energyMax = 170;
            editor.putInt("energyMax", energyMax);
            editor.putString("bodySize", bodySize);
            editor.commit();
            return;
        }
        if(bodySize.equals("Althetic build")) {
            bodySize = "Max build";
            energyMax = 200;
            editor.putInt("energyMax", energyMax);
            editor.putString("bodySize", bodySize);
            editor.commit();
        }
    }


    public void reload()
    {
        if(current_list.equals("stats"))
        {
            Stats(null);
        }

        if(current_list.equals("actions"))
        {
            Actions(null);
        }

        if(current_list.equals("store"))
        {
            Store(null);
        }
    }

    public void Stats(View v)
    {
        bodySize = settings.getString("bodySize", bodySize);
        bodyProgress =  settings.getInt("bodyProgress", bodyProgress);

        current_list = "stats";
        aa.clear();
        ListElement le = new ListElement();
        le.textLabel = "Body size: " + bodySize + " level up: " + bodyProgress + "/100";
        le.tag = "size";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Intelligence: " + intelligence;
        le2.tag = "Intelligence";
        aList.add(le2);
        ListElement le3 = new ListElement();
        le3.textLabel = "charisma: " + charisma;
        le3.tag = "charisma";
        aList.add(le3);
        ListElement le4 = new ListElement();
        le4.textLabel = "job: " + job;
        le4.tag = "jobTitle";
        aList.add(le4);
    }

    public void Inv(View v)
    {
        current_list = "inv";
        aa.clear();

        for(int i = 0; i < inv.size(); i++)
        {
            String tag = inv.get(i);
            ListElement le = new ListElement();
            le.textLabel = tag;
            le.tag = tag;
            aList.add(le);
        }

    }

    public void Actions(View v)
    {
        current_list = "actions";
        aa.clear();
        if(timeOfDay.equals("Morning"))
            morningA();
        if(timeOfDay.equals("Afternoon"))
            afternoonA();
        if(timeOfDay.equals("Evening"))
            eveningA();
    }


    public void Store(View v)
    {
        current_list = "store";
        aa.clear();
        ListElement le = new ListElement();
        le.textLabel = "TV: $50";
        le.tag = "tv";
        le.cost = 50;
        aList.add(le);

    }

    public void morningA(){
        ListElement le = new ListElement();
        le.textLabel = "Take a nap";
        le.tag = "nap1";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Eat breakfast: -$5";
        le2.tag = "breakfast1";
        le2.cost = 5;
        aList.add(le2);
        ListElement le3 =  new ListElement();
        le3.textLabel = "Morning jog";
        le3.tag = "jog1";
        aList.add(le3);
        if(job.equals("Teacher")){
            ListElement le9 = new ListElement();
            le9.textLabel = "Work at a school: +$50";
            le9.tag = "workSchool";
            aList.add(le9);
        }
        ListElement le4 =  new ListElement();
        le4.textLabel = "Work odd jobs: +$25";
        le4.tag = "work1";
        aList.add(le4);
        ListElement le5 =  new ListElement();
        le5.textLabel = "Volunteer work";
        le5.tag = "volunteer";
        aList.add(le5);
        ListElement le6 =  new ListElement();
        le6.textLabel = "Go to the movies: -$10";
        le6.tag = "movies";
        le6.cost = 10;
        aList.add(le6);

        if(inv.contains("Tv"))
        {
            ListElement le7 =  new ListElement();
            le7.textLabel = "Watch TV";
            le7.tag = "watchTV";
            aList.add(le7);
        }

        ListElement le8 =  new ListElement();
        le8.textLabel = "Go to the library: -$3";
        le8.tag = "library";
        le8.cost = 3;
        aList.add(le8);
    }

    public void afternoonA(){
        ListElement le = new ListElement();
        le.textLabel = "Take a mid day nap";
        le.tag = "nap1";
        aList.add(le);
        ListElement le2 = new ListElement();
        le2.textLabel = "Eat Lunch: -$7";
        le2.tag = "lunch1";
        le2.cost = 7;
        aList.add(le2);
        ListElement le3 =  new ListElement();
        le3.textLabel = "Afternoon jog";
        le3.tag = "jog1";
        aList.add(le3);
        ListElement le4 =  new ListElement();
        le4.textLabel = "Go to the gym: -$15";
        le4.tag = "gym";
        le4.cost = 15;
        aList.add(le4);
        if(job.equals("Fitness Trainer")){
            ListElement le9 = new ListElement();
            if(bodySize.equals("Medium build")) le9.textLabel = "Work at gym: +30";
            if(bodySize.equals("Athletic build")) le9.textLabel = "Work at gym: +60";
            if(bodySize.equals("Max build")) le9.textLabel = "Work at gym: +100";
            le9.tag = "workGym";
            aList.add(le9);
        }
        ListElement le5 =  new ListElement();
        le5.textLabel = "Work odd jobs: +$25";
        le5.tag = "work1";
        aList.add(le5);
        ListElement le6 =  new ListElement();
        le6.textLabel = "Go to the movies: -$10";
        le6.tag = "movies";
        le6.cost = 10;
        aList.add(le6);
        if(inv.contains("Tv"))
        {
            ListElement le7 =  new ListElement();
            le7.textLabel = "Watch TV";
            le7.tag = "watchTV";
            aList.add(le7);
        }

        ListElement le8 =  new ListElement();
        le8.textLabel = "Go to the library: -$3";
        le8.tag = "library";
        le8.cost = 3;
        aList.add(le8);

    }

    public void eveningA(){
        if(actionCount ==1) {
            ListElement le = new ListElement();
            le.textLabel = "Go to sleep";
            le.tag = "sleep1";
            aList.add(le);
        }else{
            ListElement le = new ListElement();
            le.textLabel = "Take a late night nap";
            le.tag = "nap1";
            aList.add(le);
        }
        ListElement le2 = new ListElement();
        le2.textLabel = "Eat Dinner: -$10";
        le2.tag = "dinner1";
        le2.cost = 10;
        aList.add(le2);
        ListElement le3 =  new ListElement();
        le3.textLabel = "Go to the gym: -$15";
        le3.tag = "gym";
        le3.cost = 15;
        aList.add(le3);
        ListElement le4 =  new ListElement();
        le4.textLabel = "Go out to drink:  -$40";
        le4.tag = "drink";
        le4.cost = 40;
        aList.add(le4);
        ListElement le8 =  new ListElement();
        le8.textLabel = "Go to the movies: -$10";
        le8.tag = "movies";
        le8.cost = 10;
        aList.add(le8);

        if(inv.contains("Tv"))
        {
            ListElement le9 =  new ListElement();
            le9.textLabel = "Watch TV";
            le9.tag = "watchTV";
            aList.add(le9);
        }

        if(job.equals("unemployed")){
            ListElement le5 = new ListElement();
            le5.textLabel = "Find a job in teaching: 100 intelligence needed";
            le5.tag = "findJobTeaching";
            aList.add(le5);
            ListElement le6 = new ListElement();
            le6.textLabel = "Find a job at the gym: medium build minimum";
            le6.tag = "findJobGym";
            aList.add(le6);
            ListElement le7 = new ListElement();
            le7.textLabel = "Find a job in bartending: 150 charisma needed";
            le7.tag = "findJobBar";
            aList.add(le7);
        }
        if(job.equals("Fitness Trainer")){
            ListElement le5 = new ListElement();
            le5.textLabel = "Work at gym";
            le5.tag = "workGym";
            aList.add(le5);
        }
        if(job.equals("Bartender")){
            ListElement le5 = new ListElement();
            le5.textLabel = "Work at bar: +$30 + " + charisma/5;
            le5.tag = "workBar";
            aList.add(le5);
        }
    }



    public void init()
    {

        timeOfDay = settings.getString("Morning", timeOfDay);
        String actionText = Integer.toString(actionCount);
        dayAndAction.setText(timeOfDay + ": " + actionText + " actions");

        numberDay = settings.getInt("1", numberDay);
        String dayText = Integer.toString(numberDay);
        countDay.setText("Day: " + dayText);

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
