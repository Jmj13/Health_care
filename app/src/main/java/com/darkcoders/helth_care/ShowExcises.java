package com.darkcoders.helth_care;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTexImage2D;

public class ShowExcises extends AppCompatActivity {
    int[] image={R.id.image1,R.id.image2,R.id.image3,R.id.image4,R.id.image5,R.id.image6,R.id.image7};
    int[] image_id={R.drawable.exersice_1,R.drawable.exersice_2,R.drawable.exersice_3,R.drawable.exersice_4,
            R.drawable.exersice_5,R.drawable.exersice_6,R.drawable.exersice_7};
    int[] image2_id={R.drawable.exersice_8,R.drawable.exersice_9,R.drawable.exersice_10,R.drawable.exersice_11,
            R.drawable.exersice_12,R.drawable.exersice_13,R.drawable.exersice_14};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_excises);
        getSupportActionBar().hide();
        if(getIntent().getStringExtra("BUTTON").equals("AFTER")){
            for(int i=0;i<image.length;i++) {
                ((GifImageView) findViewById(image[i])).setImageResource(image2_id[i]);
            }
        }else{
            for(int i=0;i<image.length;i++) {
                ((GifImageView) findViewById(image[i])).setImageResource(image_id[i]);
            }
        }
    }
}