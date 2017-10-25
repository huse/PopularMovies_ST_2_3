package com.hpr.hus.popularmovies.settings_general;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.hpr.hus.popularmovies.R;

/**
 * Activity in charge of inflate the {@link SettingsFragment} class which store and update the application Settings.
 */
public class SettingsActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      Log.v("nnnn","SettingsActivity - start  onCreate");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_settings);
      ActionBar actionBar = this.getSupportActionBar();
      Log.i("nnnn","SettingsActivity - onCreate");
      if (actionBar != null) {
         Log.i("nnnn","actionBar != null"+actionBar);
         actionBar.setDisplayHomeAsUpEnabled(true);
      }
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
         Log.i("nnnnn","id == android.R.id.home" + id);
         //onBackPressed();
          NavUtils.navigateUpFromSameTask(this);
      }
      return super.onOptionsItemSelected(item);
   }
}