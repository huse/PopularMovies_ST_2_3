package com.hpr.hus.popularmovies.settings_2;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            Log.v("kkkkkkkkk","SettingsActivity2 - onCreate 5");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.v("kkkkkkkkk","SettingsActivity2 - onOptionsItemSelected");
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}