package com.apurba.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateUiFromPreference();
    }


    private void updateUiFromPreference(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        setGenderTextView(sharedPreferences);
        setBackgroundColor(sharedPreferences);
        setAgeTextView(sharedPreferences);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void setAgeTextView(SharedPreferences sharedPreferences){
        TextView ageTextView = findViewById(R.id.tv_age);

        float age = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_age_key)
                , getString(R.string.pref_age_default)));

        ageTextView.setText(Float.toString(age));
    }

    private void setBackgroundColor(SharedPreferences sharedPreferences){
        LinearLayout linearLayout = findViewById(R.id.main_holder);
        String colorName = sharedPreferences.getString(getString(R.string.pref_color_key)
                , getString(R.string.pref_color_red_value));
        int colorId = R.color.red;
        if (colorName.equals(getString(R.string.pref_color_red_value))){
            colorId = R.color.red;
        }else if (colorName.equals(getString(R.string.pref_color_blue_value))){
            colorId = R.color.blue;
        }else if (colorName.equals(getString(R.string.pref_color_green_value))){
            colorId = R.color.green;
        }
        linearLayout.setBackgroundColor(getResources().getColor(colorId));
    }

    private void setGenderTextView(SharedPreferences sharedPreferences){
        TextView tvGender = findViewById(R.id.tv_gender);
        String text = (sharedPreferences.getBoolean(getString(R.string.preference_gender_key)
                , getResources().getBoolean(R.bool.preference_gander_default)))?
                getString(R.string.preference_gender_summary_on) :
                getString(R.string.preference_gender_summary_off);
        tvGender.setText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.preference_gender_key)) ){
            setGenderTextView(sharedPreferences);
        }else if (TextUtils.equals(key,getString(R.string.pref_color_key) )){
            setBackgroundColor(sharedPreferences);
        }else if (TextUtils.equals(key, getString(R.string.pref_age_key))){
            setAgeTextView(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
