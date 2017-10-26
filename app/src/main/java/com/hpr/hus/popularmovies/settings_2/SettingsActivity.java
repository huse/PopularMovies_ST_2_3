package com.hpr.hus.popularmovies.settings_2;

// checking vcs

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.hpr.hus.popularmovies.R;
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("kkkkkkkkk","SettingsActivity2 - start  onCreate");
        super.onCreate(savedInstanceState);
        Log.v("kkkkkkkkk","SettingsActivity2 - onCreate 2");
        setContentView(R.layout.activity_settings);
        Log.v("kkkkkkkkk","SettingsActivity2 - onCreate 3");
        ActionBar actionBar = this.getSupportActionBar();
        Log.v("kkkkkkkkk","SettingsActivity2 - onCreate 4");

        if (actionBar != null) {
            Log.v("kkkkkkkkk","SettingsActivity2 - onCreate 5");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.v("kkkkkkkkk","SettingsActivity2 - onOptionsItemSelected");
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}