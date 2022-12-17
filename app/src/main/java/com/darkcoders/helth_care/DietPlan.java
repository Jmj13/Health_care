package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class DietPlan extends AppCompatActivity implements ValueEventListener {
    int height,weight;
    DatabaseReference d_ref;
    String username1;
    Intent intent;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);
        getSupportActionBar().hide();
        MyHelper helper = new MyHelper(getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();
        c = database.rawQuery("select * from USER", new String[]{});
        if (c != null) {
            c.moveToFirst();
        }
        do {
            username1 = c.getString(1);
        } while (c.moveToNext());
        d_ref = FirebaseDatabase.getInstance().getReference("users").child(username1);
        d_ref.addValueEventListener(this);
    }

    public void health_diet_button(View view) {
        startActivity(new Intent(getApplicationContext(),Sample_Helth_Care.class));
    }
    public void w_loss_button(View view) {
        startActivity(new Intent(getApplicationContext(),Waightloss.class));
    }
    public void w_gain_button(View view) {
        startActivity(new Intent(getApplicationContext(),Waightgain.class));
    }
    public void v_button(View view) {
        startActivity(new Intent(getApplicationContext(),Vegetarian.class));
    }
    public void gym_button(View view) {
        startActivity(new Intent(getApplicationContext(),Gymplan.class));
    }

    public void BMI_Calculator(View view) {

        final DecimalFormat df=new DecimalFormat("0.00");
        double b= (float) (weight /((height*0.01) * (height*0.01)));
       // Toast.makeText(getApplicationContext(), ""+height+"   "+weight+"    "+b, Toast.LENGTH_LONG).show();
        intent=new  Intent(getApplicationContext(),BMI_Calculator.class);
        intent.putExtra("BMI",""+df.format(b));
        intent.putExtra("HEIGHT",""+height +" CM.");
        intent.putExtra("WEIGHT",""+weight+" KG.");
        startActivity(intent);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        height=Integer.parseInt(snapshot.child("height").getValue(String.class));
        weight=Integer.parseInt(snapshot.child("weight").getValue(String.class));


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
    }
}