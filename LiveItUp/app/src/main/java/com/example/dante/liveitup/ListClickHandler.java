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
            hunger -=10;
            if(hunger < 0){
                hunger +=10;
                return;
            }

            energy += 20 + Activity.energyMax/10;
            if(energy > Activity.energyMax){
                energy = Activity.energyMax;
            }

            editor.putInt("energy", energy);
            editor.putInt("hunger", hunger);
            editor.commit();
            Activity.decrement_count();
            Activity.reload();
        }

        if(action.equals("sleep1")) // sleep level one
        {

            hunger -=20;
            if(hunger < 0){
                hunger +=20;
                return;
            }

            energy += 60 + Activity.energyMax/10;
            if(energy > Activity.energyMax){
                energy = Activity.energyMax;
            }

            editor.putInt("energy", energy);
            editor.putInt("hunger", hunger);
            editor.commit();
            Activity.decrement_count();
        }

        if(action.equals("breakfast1")){

            if(Activity.money < item.cost) return;
            Activity.money -= item.cost;

            energy -=10;
            if(energy < 0){
                energy +=10;
                return;
            }

            hunger += 30 + Activity.hungerMax/10;
            if(hunger > Activity.hungerMax) hunger = Activity.hungerMax;

            editor.putInt("energy", energy);
            editor.putInt("hunger", hunger);
            editor.commit();
            Activity.decrement_count();
        }

        if(action.equals("jog1")){

            energy -= 30;
            if(energy < 0){
                energy +=30;
                return;
            }
            
            emotion += 10 + Activity.emotionMax/10;
            if(emotion > Activity.emotionMax) emotion = Activity.emotionMax;

            Activity.bodyProgress += 50;
            if(Activity.bodyProgress == 100){
                Activity.bodyIncrease();
                Activity.bodyProgress = 0;
                Activity.energyMax += 50;
            }

            editor.putInt("energy", energy);
            editor.commit();
            Activity.decrement_count();

        }

        if(action.equals("tv"))
        {
            if (Activity.money > item.cost && Activity.inv.contains("Tv") == false) {
                Activity.decrement_count();
                Activity.inv.add("Tv");
                Activity.money -= item.cost;
            }
        }

        if(action.equals("Watch TV"))
        {
            emotion += 20;
            if (emotion > Activity.emotionMax)
                emotion = Activity.emotionMax;
            editor.putInt("emotion", emotion);
            editor.commit();
        }

        Activity.init();
    }


}