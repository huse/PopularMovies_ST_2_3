package com.hpr.hus.popularmovies.settings_2;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
//import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.hpr.hus.popularmovies.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Log.v("tttttttttttt","SettingFragment 2 - onCreatePreferences 1");
        addPreferencesFromResource(R.xml.setting_pref);
        Log.v("tttttttttttt","SettingFragment 2 - onCreatePreferences 2");
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        Log.v("tttttttttttt","SettingFragment 2 - onCreatePreferences 3");
        PreferenceScreen prefScreen = getPreferenceScreen();
        Log.v("tttttttttttt","SettingFragment 2 - onCreatePreferences 4");
        int count = prefScreen.getPreferenceCount();
        Log.v("ttttt_count", count+"");

        for (int i = 0; i < count; i++) {
            Log.v("tttttttttttt","SettingFragment 2 - onCreatePreferences 6");
            Preference p = prefScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                Log.v("tttttttttttt","SettingFragment 2 - onCreatePreferences 8");
                String value = sharedPreferences.getString(p.getKey(), "");
                Log.v("ttttt_value",value);
                setPreferenceSummary(p, value);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("tttttttttttt3333"," - onSharedPreferenceChanged 0    " + sharedPreferences);

        Preference preference = findPreference(key);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                Log.v("ttttt_value2",value);
                setPreferenceSummary(preference, value);
            }
        }
    }


    private void setPreferenceSummary(Preference preference, String value) {
        Log.v("tttttttttttt","setPreferenceSummary 2 - 1");
        Log.v("ttttt_value3",value);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("tttttttttttt","onCreate 2 - oncreate 0");
        Log.v("tttttttttttt"," - oncreate 0  sss  " + savedInstanceState);
        super.onCreate(savedInstanceState);
        Log.v("tttttttttttt"," - oncreate 0  eee  " + savedInstanceState);
        Log.v("tttttttttttt","setPreferenceSummary 2 - oncreate 01");
/*        addPreferencesFromResource(R.xml.pref_visualizer);
        Log.v("tttttttttttt","setPreferenceSummary 2 - oncreate 02");
        final SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();*/
        Log.v("tttttttttttt","setPreferenceSummary 2 - oncreate 1");
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        Log.v("tttttttttttt","setPreferenceSummary 2 - oncreate end");





    }

    @Override
    public void onDestroy() {
        Log.v("tttttttttttt","setPreferenceSummary 2 - onDestroy 1");
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}