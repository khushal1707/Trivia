package com.example.trivia.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int curscor){
        int currentScore = curscor;
        int lastScore = preferences.getInt("high_score",0);

        if(currentScore>lastScore)
        {
            preferences.edit().putInt("high_score",currentScore).apply();
        }
    }

    public int getHighScore(){
        return preferences.getInt("high_score",0);
    }
}
