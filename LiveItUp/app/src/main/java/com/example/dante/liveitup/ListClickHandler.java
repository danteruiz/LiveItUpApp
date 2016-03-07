package com.example.dante.liveitup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.preference.PreferenceManager;
/**
 * Created by Dante on 3/3/16.
 */


public class ListClickHandler implements OnItemClickListener {

    private int energy;
    private int hunger;
    private int emotion;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private MainActivity Activity;

    ListClickHandler(int energy, int hunger, int emotion, SharedPreferences settings, MainActivity Activity)
    {
        this.energy = energy;
        this.hunger = hunger;
        this.emotion = emotion;
        this.settings = settings;
        editor = settings.edit();
        this.Activity = Activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
        ListElement item = (ListElement) adapter.getItemAtPosition(position);
        String action = item.tag;
        energy = settings.getInt("energy", energy);
        Activity.money = settings.getInt("money", Activity.money);
        hunger = settings.getInt("hunger", hunger);
        emotion = settings.getInt("emotion", emotion);

        if(action.equals("nap1")) // sleep level one
        {
            energy += 20;
            if(energy > Activity.energyMax){
                energy = Activity.energyMax;
            }
            editor.putInt("energy", energy);
            editor.commit();
        }
        if(action.equals("movies")){
            if(Activity.money >= 10 && emotion != Activity.emotionMax){
                emotion += 40;
                if(emotion > Activity.emotionMax)
                    emotion = Activity.emotionMax;
                Activity.money -= 10;
                editor.putInt("emotion", emotion);
                editor.commit();
            }
        }



        Activity.init();
    }


}