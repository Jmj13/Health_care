package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CaloriesActivity extends AppCompatActivity implements ValueEventListener {
TextView textView,bulk_cal,cut_cal,man_cal;
String username1;
Float today_gain_cal,total_gain_cal;
boolean flag=false;
String h,w,a,g;
String s="";
Cursor c;
DatabaseReference  d_ref;
    String food;
    EditText et;
    String calories;
    TextView tv1, tv2,tv3, tv4, tv5;
    View v1, v2;
    Button eat_button;
    TextView eat_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        getSupportActionBar().hide();
        eat_button=findViewById(R.id.eatButton);
        eat_text=findViewById(R.id.eat_text);
        bulk_cal=findViewById(R.id.cal_bulk);
        man_cal=findViewById(R.id.cal_man);
        cut_cal=findViewById(R.id.cal_cut);
        textView=findViewById(R.id.info);
        et = (EditText)findViewById(R.id.editText);
        tv1 = (TextView)findViewById(R.id.textView13);
        tv2 = (TextView)findViewById(R.id.textView16);
        tv3 = (TextView)findViewById(R.id.textView17);
        tv4 = (TextView)findViewById(R.id.textView18);
        tv5 = (TextView)findViewById(R.id.textView19);

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

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        total_gain_cal=Float.parseFloat(snapshot.child("total_calories").getValue().toString());
        s="";
        h=snapshot.child("height").getValue().toString();
        w=snapshot.child("weight").getValue().toString();
        a=snapshot.child("age").getValue().toString();
        g=snapshot.child("gender").getValue().toString();
        s+="Height: "+h+" CM."+"\nWeight: "+w+" KG."+"\nAge: "+a+"\nGender: "+g.toUpperCase();
        textView.setText(s);

        String[] temp=(snapshot.child("calories_gain").getValue().toString()).split("\\$\\$\\$");
        // Toast.makeText(getApplicationContext(), ""+temp[0], Toast.LENGTH_SHORT).show();
        if (temp[1].equals(getcurrneDate())){
            today_gain_cal=Float.parseFloat(temp[0]);
            eat_text.setText("Todays Gain Calories: "+today_gain_cal);
        }else{
            today_gain_cal=0f;
            eat_text.setText("Todays Gain Calories: 0");
        }




        if (g.equals("female")){
            femaleCheck();
        }else{
            malecheck();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) { }

    private void femaleCheck() {
        double wt1 = Double.parseDouble(w);
        double ht1 = Double.parseDouble(h);
        double age1 = Double.parseDouble(a);
        double formula2, formula3, formula4, formula5,formula6, result, bulk, cut, protein, carbohydrates, fat;
        formula3 = 1.9 * ht1;
        formula5 = 4.7*age1;
        formula4 = formula3/formula5;
        formula6 = 9.6 * wt1;
        formula2 = 665.1 + formula6 + formula4;
        result = formula2 * 1.3;
        man_cal.setText("Maintain Calories: "+(int) result *1000);
        bulk = result + 400;
        bulk_cal.setText("Bulk Calorires: "+(int) bulk * 1000);
        cut = result - 400;
        cut_cal.setText("Cut Calories: "+(int) cut * 1000);
//        protein = (0.4 * result) / 4;
//        d.setText(""+(int) protein);
//        carbohydrates = (0.6 * result) / 4;
//        e.setText(""+(int) carbohydrates);
//        fat = (0.2 * result) / 9;
//        f.setText(""+(int) fat);

    }

//    @Override
//    public void onBackPressed() {
//    if (false) {
//
//    }
//        super.onBackPressed();
//    }

    private void malecheck()
    {
        double wt1 = Double.parseDouble(w);
        double ht1 = Double.parseDouble(h);
        double age1 = Double.parseDouble(a);
        double formula2, formula3, formula4, formula5,formula6, result, bulk, cut, protein, carbohydrates, fat;
        formula6 = 13.8 * wt1;
        formula4 = 6.8*age1;
        formula3 = 5*ht1;
        formula5 = formula3/formula4;
        formula2 = 66.5 + formula6 + formula5;
        result = formula2 * 1.3;
        man_cal.setText("Maintain Calories: "+(int) result * 1000);
        bulk = result + 500;
        bulk_cal.setText("Bulk Calories: "+(int) bulk * 1000);
        cut = result - 500;
        cut_cal.setText("Cut Calories: "+(int) cut * 1000);
       // protein = (0.4 * result) / 4;
//        d.setText(""+(int) protein);
//        carbohydrates = (0.6 * result) / 4;
//        e.setText(""+(int) carbohydrates);
//        fat = (0.2 * result) / 9;
//        f.setText(""+(int) fat);
    }


    public void calculateClk(View view) {
        food = et.getText().toString().trim();
        if (food.isEmpty()){
            Toast.makeText(getApplicationContext(), "plz enter food Name", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
            new CaloriesActivity.MyAsyncTask().execute();
            eat_button.setVisibility(View.VISIBLE);
            eat_text.setVisibility(View.VISIBLE);
        }
    }

    public void eat_button_click(View view) {
       // flag=true;
       today_gain_cal= today_gain_cal + Float.parseFloat(calories);
        total_gain_cal= total_gain_cal + Float.parseFloat(calories);
        d_ref.child("calories_gain").setValue("" + (today_gain_cal) + "$$$" + getcurrneDate());
        d_ref.child("total_calories").setValue(""+total_gain_cal);
        eat_text.setText("Todays Gain Calories: "+today_gain_cal);
        Toast.makeText(getApplicationContext(), "Eating Done ", Toast.LENGTH_SHORT).show();

    }
    String getcurrneDate() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }


    /* #####AsyncTask Subclass################################################################### */
    private class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String allStrings;
            try{
                URL myUrl = new URL("https://api.nutritionix.com/v1_1/search/" +
                        food +"?fields=item_name%2Citem_id%2Cnf_calories%2Cnf_total_fat" +
                        "&appId=3fe5fa47&appKey=61729b9d2d8612a629467f0cdbbd6d2c");
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setConnectTimeout(700);
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);

                String inputLine;
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                allStrings = stringBuilder.toString();
                publishProgress(allStrings);

            }catch(Exception e){}
            return "";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            try {
                JSONObject j = new  JSONObject(values[0]);

                JSONArray h= (JSONArray) j.get("hits");

                JSONObject rec = h.getJSONObject(0);

                JSONObject fields = rec.getJSONObject("fields");

                calories = fields.getString("nf_calories");
                String fat = fields.getString("nf_total_fat");
                String name = fields.getString("item_name");


                tv2.setText("Nutrition Facts");
                tv3.setText("Amount: " + name);
                tv4.setText("Calories: " +calories);
                tv5.setText("Total Fat: " + fat);
                v1 = findViewById(R.id.view);
                v1.setVisibility(View.VISIBLE);
                v2 = findViewById(R.id.view);
                v2.setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}