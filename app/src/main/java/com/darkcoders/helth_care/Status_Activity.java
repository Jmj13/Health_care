package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Status_Activity extends AppCompatActivity implements ValueEventListener {
    TextInputLayout t_step,t_cal,t_dist,total_step,total_cal,total_dist,today_body_cal1,total_body_cal1,total_gain_cal1,today_gain_cal1;
    TextView userfullname;
    Cursor c;
    String username1;
    DatabaseReference d_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getSupportActionBar().hide();
        userfullname=findViewById(R.id.user_fullName);
        t_step=findViewById(R.id.todays_step_count);
        t_cal=findViewById(R.id.today_burn_calories);
        t_dist=findViewById(R.id.today_distance);
        total_cal=findViewById(R.id.total_burn_cal);
        total_step=findViewById(R.id.total_step_Count);
        total_dist=findViewById(R.id.total_dist);
        today_body_cal1=findViewById(R.id.todays_body_cal);
        total_body_cal1=findViewById(R.id.total_body_cal);
        today_gain_cal1=findViewById(R.id.todays_gain_cal);
        total_gain_cal1=findViewById(R.id.total_gain_cal);
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
    String getDistanceCover(int step){
        int feet=(int)(step*2.5);
        float d=(float) (feet/3.281);

        return Float.toString(d)+ " Meter";
    }
    String get_calories(int step){
        int cal=(int)(step*0.045);
        return cal+"";
    }
    String getcurrneDate() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        float val_today_cal;
        float val_burn_cal;
        userfullname.setText(snapshot.child("username").getValue().toString());
        String[] temp=(snapshot.child("today_step").getValue().toString()).split("\\$\\$\\$");
       // Toast.makeText(getApplicationContext(), ""+temp[0], Toast.LENGTH_SHORT).show();
        if (temp[1].equals(getcurrneDate())){
            t_step.getEditText().setText(temp[0]);
            t_cal.getEditText().setText(get_calories(Integer.parseInt(temp[0])));
            t_dist.getEditText().setText(getDistanceCover(Integer.parseInt(temp[0])));
            val_burn_cal=Float.parseFloat(get_calories(Integer.parseInt(temp[0])));
        }else{
            t_step.getEditText().setText("0");
            t_cal.getEditText().setText("0 Calories");
            t_dist.getEditText().setText("0.0 Meter");
            val_burn_cal=0.0f;
        }
        String total=snapshot.child("total_step").getValue().toString();
        total_step.getEditText().setText(total);
        total_cal.getEditText().setText(get_calories(Integer.parseInt(total)));
        total_dist.getEditText().setText(getDistanceCover(Integer.parseInt(total)));

        String[] temp1=(snapshot.child("calories_gain").getValue().toString()).split("\\$\\$\\$");
        if(temp1[1].equals(getcurrneDate())){
            today_gain_cal1.getEditText().setText(temp1[0]);
            val_today_cal=Float.parseFloat(temp1[0]);
            today_body_cal1.getEditText().setText(""+(val_today_cal -val_burn_cal ));

        }else{
            today_gain_cal1.getEditText().setText("0");
            val_today_cal=0.0f;
            today_body_cal1.getEditText().setText(""+(val_today_cal -val_burn_cal ));
        }

        total_gain_cal1.getEditText().setText(snapshot.child("total_calories").getValue().toString());
        float j=(Float.parseFloat(snapshot.child("total_calories").getValue().toString()));
        total_body_cal1.getEditText().setText(""+(j-Float.parseFloat(get_calories(Integer.parseInt(total)))));



    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) { }
}