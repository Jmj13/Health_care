package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepCountActivity extends AppCompatActivity implements SensorEventListener, StepListener , ValueEventListener {
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;
    private TextView TvSteps,TvCalories,TvDistance;
Cursor c;
String username1;
int total,today_total=0;
DatabaseReference d_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);
        getSupportActionBar().hide();
        TvSteps=findViewById(R.id.tv_steps);
        TvCalories=findViewById(R.id.TV_CALORIES);
        TvDistance=findViewById(R.id.TV_DISTANCE);
        TvSteps=findViewById(R.id.tv_steps);

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

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

    }

    public void startButtonClick(View view) {
        numSteps = 0;
        sensorManager.registerListener(StepCountActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stopButtonClick(View view) {
        sensorManager.unregisterListener(StepCountActivity.this);
        TvDistance.setText(getDistanceCover(numSteps));
        TvCalories.setText(get_calories(numSteps));
        d_ref.child("total_step").setValue(""+(total+numSteps));
        d_ref.child("today_step").setValue(""+(today_total+numSteps)+"$$$"+getcurrneDate());

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override

    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(""+numSteps);

    }
    String getDistanceCover(int step){
        int feet=(int)(step*2.5);
        float d=(float) (feet/3.281);

        return Float.toString(d)+ " Meter";
    }
    String get_calories(int step){
        int cal=(int)(step*0.045);
        return cal+" Calories";
    }
    String getcurrneDate() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        total=Integer.parseInt(snapshot.child("total_step").getValue().toString());
        String[] temp=(snapshot.child("today_step").getValue().toString()).split("\\$\\$\\$");
        // Toast.makeText(getApplicationContext(), ""+temp[0], Toast.LENGTH_SHORT).show();
        if (temp[1].equals(getcurrneDate())){
           today_total=Integer.parseInt(temp[0]);
        }else{
           today_total=0;
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
    }
}