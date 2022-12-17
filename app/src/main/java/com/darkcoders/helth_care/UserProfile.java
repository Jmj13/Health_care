package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements ValueEventListener {
    TextInputLayout fullName,email,password,w,a,h,g;
    TextView title_name;
    Cursor c;
    String username1;
    private DatabaseReference d_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();
        fullName=findViewById(R.id.full_name_profile);
        email=findViewById(R.id.full_email);
        password=findViewById(R.id.full_Pass);
        title_name=findViewById(R.id.top_user_fullName);
        h=findViewById(R.id.p_height);
        g=findViewById(R.id.p_gender);
        w=findViewById(R.id.p_weight);
        a=findViewById(R.id.p_age);

//        total_cal=findViewById(R.id.total_cal);
//        today_cal=findViewById(R.id.today_cal);
//        total_dist=findViewById(R.id.total_dist);
//        today_dist=findViewById(R.id.today_dist);

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
        title_name.setText(snapshot.child("username").getValue(String.class).toUpperCase());
        email.getEditText().setText(snapshot.child("email").getValue(String.class));
        password.getEditText().setText(snapshot.child("password").getValue(String.class));
        fullName.getEditText().setText(snapshot.child("username").getValue(String.class));
        a.getEditText().setText(snapshot.child("age").getValue(String.class));
        h.getEditText().setText(snapshot.child("height").getValue(String.class));
        w.getEditText().setText(snapshot.child("weight").getValue(String.class));
        g.getEditText().setText(snapshot.child("gender").getValue(String.class));
       // fullName.getEditText().setText(snapshot.child("username").getValue(String.class));
       // today_dist.getEditText().setText(snapshot.child("username").getValue(String.class));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}