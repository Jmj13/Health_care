package com.darkcoders.helth_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        drawerLayout=findViewById(R.id.draw_layout);
        toolbar= findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_Drawebal,R.string.CLOSE_DRAWEBLE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void step_counter_click(View view) {
        startActivity(new Intent(getApplicationContext(),StepCountActivity.class));
    }

    public void CaloriesButtonClick(View view) {
    startActivity(new Intent(getApplicationContext(),CaloriesActivity.class));
    }

    public void exerciseButtonClick(View view) {
        startActivity(new Intent(getApplicationContext(),Exercies.class));
    }

    public void DietPlan_Click(View view) {
        startActivity(new Intent(getApplicationContext(),DietPlan.class));// temp chage
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                MyHelper helper=new MyHelper(getApplicationContext());
                SQLiteDatabase database=helper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("USER_NAME","null");
                values.put("PASSWORD","null");
                values.put("STATUS","false");
                database.update("USER",values,"id=?",new String[]{"1"});
                Intent i = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(i);
                finish();
                break;
            case R.id.menu_user_Profile:
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                break;
            case R.id.admin:
                final EditText editText = new EditText(getApplicationContext());
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                a.setTitle(R.string.app_name);
                //  a.setCancelable(false);
                a.setMessage("Enter Password:");
                a.setView(editText);
                a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((editText.getText().toString().equals("admin@123"))) {
                            Intent intent=new Intent(getApplicationContext(), BookAdminActivity.class);
                            intent.putExtra("ADMIN","admin");
                            startActivity(intent);


                            dialog.cancel();
                        }else{
                            Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                a.setIcon(R.mipmap.ic_launcher);
                a.show();
                // Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();

                break;

        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void Status_ButtonClick(View view) {
        startActivity(new Intent(getApplicationContext(),Status_Activity.class));
    }

    public void bookButtonClick(View view) {
        startActivity(new Intent(getApplicationContext(),BookAdminActivity.class));
    }
}