package com.hpr.hus.popularmovies.settings_general;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.hpr.hus.popularmovies.R;

/**
 * Fragment in charge of store and update the application Settings.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.setting_pref);
      final SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
      Log.i("gggg25","SettingFragment - onCreate");
      PreferenceCategory category = (PreferenceCategory) findPreference(getString(R.string.category_general_settings_key));
      for (int i = 0; i < category.getPreferenceCount(); i++) {
         Preference p = category.getPreference(i);
         // You don't need to set up preference summaries for checkbox preferences because
         // they are already set up in xml using summaryOff and summary On
         if (!(p instanceof CheckBoxPreference)) {
            String value = sharedPreferences.getString(p.getKey(), "");
            Log.i("gggg25","SettingFragment - CheckBoxPreference");
            p.setSummary(value);
         }
      }
      Preference preference = findPreference(getString(R.string.preference_sorting_key));
      preference.setOnPreferenceChangeListener(this);
   }
 /*  @Override
   public void onCreatePreferences(Bundle bundle, String s) {

      // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
      addPreferencesFromResource(R.xml.pref_visualizer);

      // COMPLETED (3) Get the preference screen, get the number of preferences and iterate through
      // all of the preferences if it is not a checkbox preference, call the setSummary method
      // passing in a preference and the value of the preference

      SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
      PreferenceScreen prefScreen = getPreferenceScreen();
      int count = prefScreen.getPreferenceCount();

      // Go through all of the preferences, and set up their preference summary.
      for (int i = 0; i < count; i++) {
         Preference p = prefScreen.getPreference(i);
         // You don't need to set up preference summaries for checkbox preferences because
         // they are already set up in xml using summaryOff and summary On
         if (!(p instanceof CheckBoxPreference)) {
            String value = sharedPreferences.getString(p.getKey(), "");
            setPreferenceSummary(p, value);
         }
      }
   }*/
   @Override
   public void onDestroy() {
      super.onDestroy();
   }


   @Override
   public boolean onPreferenceChange(Preference preference, Object newValue) {
      // Figure out which preference was changed
      if (null != preference) {
         // Updates the summary for the preference
         preference.setSummary(newValue.toString());
         Log.i("gggg25","SettingFragment - onPreferenceChange");
         return true;
      }
      return false;
   }
}