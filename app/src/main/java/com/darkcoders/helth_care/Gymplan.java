package com.darkcoders.helth_care;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Gymplan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym);
        getSupportActionBar().hide();
    }
}