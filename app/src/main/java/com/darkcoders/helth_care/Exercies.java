package com.darkcoders.helth_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class Exercies extends AppCompatActivity {
    GifImageView image1,image2;
    Intent intent;
    int i=0,j=0;
    int[] image_id={R.drawable.exersice_1,R.drawable.exersice_2,R.drawable.exersice_3,R.drawable.exersice_4,
            R.drawable.exersice_5,R.drawable.exersice_6,R.drawable.exersice_7};
    int[] image2_id={R.drawable.exersice_8,R.drawable.exersice_9,R.drawable.exersice_10,R.drawable.exersice_11,
            R.drawable.exersice_12,R.drawable.exersice_13,R.drawable.exersice_14};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercies);
        getSupportActionBar().hide();
        image1=findViewById(R.id.first_image);
        image2=findViewById(R.id.second_image);
    }

    public void beforeage18(View view) {
        intent=new Intent(getApplicationContext(),ShowExcises.class);
        intent.putExtra("BUTTON","BEFORE");
        startActivity(intent);
    }

    public void afterage18(View view) {
        intent=new Intent(getApplicationContext(),ShowExcises.class);
        intent.putExtra("BUTTON","AFTER");
        startActivity(intent);
    }

    public void change_image(View view) {
        i++;
        if(i>=image_id.length){ i=0;}
        image1.setImageResource(image_id[i]);
    }

    public void change_image2(View view) {
        j++;
        if(j>=image_id.length){ j=0;}
        image2.setImageResource(image2_id[j]);
    }
}