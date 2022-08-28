package com.android.codersroutewidgets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codersroute.flexiblewidgets.FlexibleSwitch;

public class FlexibleSwitches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_switches);
        FlexibleSwitch flexibleSwitch = new FlexibleSwitch(this);
    }
}