package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    AlertDialog.Builder builder;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String arr[]={"Select Gender","male","female","other"};
Spinner spin;
EditText age,height,weight;
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        builder = new AlertDialog.Builder(this);
        spin=findViewById(R.id.spin);
        age=findViewById(R.id.age);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arr);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spin.setAdapter(ad);


    }
    public void loginButton(View view) {
        startActivity(new Intent(getApplicationContext(),LoginPage.class));
        finish();
    }

    public void register_User(View view) {

        if(!validateName() | !validatePassword() | !validateEmail() | !validateAge() | !validateHeight() | !validateWeight() | !validateGender() ){
            return;
        } else{
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");
            final String userfullname=((EditText)findViewById(R.id.FullName)).getText().toString().trim();
            final String email_id=((EditText)findViewById(R.id.eEmailAddress)).getText().toString().toLowerCase().trim();
            final String pass=((EditText)findViewById(R.id.r_Password)).getText().toString().trim();
            final String[] temp=email_id.split("\\.");
            final String email=temp[0];
            final String today_step="0$$$"+getcurrneDate();
            final String total_step="0";
            final String cal="0$$$"+getcurrneDate();
            final String total_cal="0";
            final String h=height.getText().toString();
            final String w=weight.getText().toString();
            final String a=age.getText().toString();
            final String g=spin.getSelectedItem().toString();
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(email).exists()){
                        ((EditText)findViewById(R.id.eEmailAddress)).setError("Email Id Already Register");
                    }
                    else{
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        final View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_d, viewGroup, false);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setTitle(R.string.app_name);
                        builder.setMessage("This information is usefull when you forget password");
                        final EditText input_color = dialogView.findViewById(R.id.amount);
                        final EditText input_friend=dialogView.findViewById(R.id.user_id);
                        input_color.setInputType(InputType.TYPE_CLASS_TEXT);
                        input_friend.setInputType(InputType.TYPE_CLASS_TEXT);

                        builder.setView(dialogView);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String color_=input_color.getText().toString().trim();
                                String friend=input_friend.getText().toString().trim();
                                if(color_.equals("") || friend.equals("")){
                                    Toast.makeText(getApplicationContext(),"Plz Enter Proper information",Toast.LENGTH_LONG).show();
                                }else{
                                    student = new Student( pass,email_id,userfullname,color_,friend,today_step,total_step,a,h,w,g,cal,total_cal);
                                    reference.child(email).setValue(student);
                                    MyHelper helper=new MyHelper(getApplicationContext());
                                    SQLiteDatabase database=helper.getWritableDatabase();
                                    ContentValues values=new ContentValues();
                                    values.put("USER_NAME",email);
                                    values.put("PASSWORD",pass);
                                    values.put("STATUS","true");
                                    database.update("USER",values,"id=?",new String[]{"1"});
                                    startActivity(new Intent(SignUp.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

        }



    }


    private Boolean validatePassword(){
        String val = ((EditText)findViewById(R.id.r_Password)).getText().toString().trim();
        String c_val=((EditText)findViewById(R.id.con_pass)).getText().toString().trim();
        // Toast.makeText(getApplicationContext(),""+c_val,Toast.LENGTH_LONG).show();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.equals("")) {
            ((EditText)findViewById(R.id.r_Password)).setError("Field cannot be empty");
            return false;
        } else if (c_val.equals("")) {
            ((EditText)findViewById(R.id.con_pass)).setError("Field cannot be empty");
            return false;
        }else if (!val.equals(c_val)) {
            ((EditText) findViewById(R.id.con_pass)).setError("Password and Confirm pass must be same");
            return false;
        } else if (!val.matches(passwordVal)) {
            ((EditText)findViewById(R.id.r_Password)).setError("Password is too weak");
            return false;
        } else {
            ((EditText)findViewById(R.id.r_Password)).setError(null);
            //((EditText)findViewById(R.id.FullName)).setErrorEnabled(false);
            return true;
        }
    }



    private Boolean validateName() {
        String val = ((EditText)findViewById(R.id.FullName)).getText().toString().trim();

        if (val.isEmpty()) {
            ((EditText)findViewById(R.id.FullName)).setError("Field cannot be empty");
            return false;
        } else {
            ((EditText)findViewById(R.id.FullName)).setError(null);

            return true;
        }
    }



    private Boolean validateAge() {
        String val =age.getText().toString().trim();

        if (val.isEmpty()) {
            age.setError("Field cannot be empty");
            return false;
        } else {
            age.setError(null);

            return true;
        }
    }
    private Boolean validateHeight() {
        String val =height.getText().toString().trim();

        if (val.isEmpty()) {
            height.setError("Field cannot be empty");
            return false;
        } else {
            height.setError(null);

            return true;
        }
    }

    private Boolean validateWeight() {
        String val =weight.getText().toString().trim();

        if (val.isEmpty()) {
            weight.setError("Field cannot be empty");
            return false;
        } else {
            weight.setError(null);

            return true;
        }
    }


    private Boolean validateGender() {
        String val =spin.getSelectedItem().toString();

        if (val.equals("Select Gender")) {
            Toast.makeText(getApplicationContext(),"Plz Select Gender",Toast.LENGTH_LONG).show();
            return false;
        } else {
            //age.setError(null);
            return true;
        }
    }


    private Boolean validateEmail() {
        String val = ((EditText)findViewById(R.id.eEmailAddress)).getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            ((EditText)findViewById(R.id.eEmailAddress)).setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            ((EditText)findViewById(R.id.eEmailAddress)).setError("Invalid email address");
            return false;
        } else {
            ((EditText)findViewById(R.id.eEmailAddress)).setError(null);
            // regEmail.setErrorEnabled(false);
            return true;
        }
    }




    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginPage.class));
        finish();
        //  super.onBackPressed();

    }
    String getcurrneDate() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }

}