package com.darkcoders.helth_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BMI_Calculator extends AppCompatActivity {
ImageView bmiFlagImgView,advice1IMG,advice2IMG,advice3IMG;
TextView bmiLabelTV,commentTV,advice1TV,advice2TV,advice3TV,bmiValueTV,bmiheight,bmiweight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        bmiFlagImgView=findViewById(R.id.bmiFlagImgView);
        bmiheight=findViewById(R.id.bmiheight);
        bmiweight=findViewById(R.id.bmiweight);
        advice1IMG=findViewById(R.id.advice1IMG);
        advice2IMG=findViewById(R.id.advice2IMG);
        advice3IMG=findViewById(R.id.advice3IMG);
        bmiLabelTV=findViewById(R.id.bmiLabelTV);
        commentTV=findViewById(R.id.commentTV);
        advice1TV=findViewById(R.id.advice1TV);
        advice2TV=findViewById(R.id.advice2TV);
        advice3TV=findViewById(R.id.advice3TV);
        bmiValueTV=findViewById(R.id.bmiValueTV);
        bmiheight.setText(getIntent().getStringExtra("HEIGHT"));
        bmiweight.setText(getIntent().getStringExtra("WEIGHT"));
        float bmi=Float.parseFloat(getIntent().getStringExtra("BMI"));
        bmiValueTV.setText(""+bmi);
        if (bmi < 18.5) {
            bmiFlagImgView.setImageResource(R.drawable.exclamationmark);
            bmiLabelTV.setText("You have an UnderWeight!");
            commentTV.setText("Here are some advices To help you increase your weight");
            advice1IMG.setImageResource(R.drawable.nowater);
            advice1TV.setText("Don't drink water before meals");
            advice2IMG.setImageResource(R.drawable.bigmeal);
            advice2TV.setText("Use bigger plates");
            advice3TV.setText("Get quality sleep");

        } else {
            if (bmi > 25) {

                bmiFlagImgView.setImageResource(R.drawable.warning);
                bmiLabelTV.setText("You have an OverWeight!");
                commentTV.setText("Here are some advices To help you decrease your weight");
                advice1IMG.setImageResource(R.drawable.water);
                advice1TV.setText("Drink water a half hour before meals");
                advice2IMG.setImageResource(R.drawable.twoeggs);
                advice2TV.setText("Eeat only two meals per day and make sure that they contains a high protein");
                advice3IMG.setImageResource(R.drawable.nosugar);
                advice3TV.setText("Drink coffee or tea and Avoid sugary food");
            }
        }
    }
}