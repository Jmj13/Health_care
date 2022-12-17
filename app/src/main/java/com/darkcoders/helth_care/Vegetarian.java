package com.darkcoders.helth_care;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Vegetarian extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vegetarian);
        getSupportActionBar().hide();
    }
}