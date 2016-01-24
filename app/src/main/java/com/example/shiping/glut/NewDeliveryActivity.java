package com.example.shiping.glut;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.Random;

public class NewDeliveryActivity extends AppCompatActivity {

    private Firebase data;

    private EditText delivererLocation;
    private EditText delivererDestination;
    private EditText delivererBudget;
    private EditText delivererCutOff;
    private EditText eta;
    private EditText foodItem1;
    private EditText foodCost1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Add New Delivery");
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        delivererLocation = (EditText) findViewById(R.id.delivererLocation);
        delivererDestination = (EditText) findViewById(R.id.delivererDestination);
        delivererBudget = (EditText) findViewById(R.id.delivererBudget);
        delivererCutOff = (EditText) findViewById(R.id.delivererCutOff);
        eta = (EditText) findViewById(R.id.eta);
        foodItem1 = (EditText) findViewById(R.id.foodItem1);
        foodCost1 = (EditText) findViewById(R.id.foodCost1);

        data = new Firebase("https://sizzling-torch-225.firebaseio.com/");

        delivererCutOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewDeliveryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        delivererCutOff.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        eta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewDeliveryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eta.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_delivery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.confirm) {
            Toast.makeText(this, "Your offer has been registered", Toast.LENGTH_SHORT).show();
            Offers offer = new Offers(MainActivity.name, delivererLocation.getText().toString(), delivererDestination.getText().toString(), delivererCutOff.getText().toString(),
                    eta.getText().toString(), delivererBudget.getText().toString(), foodItem1.getText().toString(), foodCost1.getText().toString());

            data.child("offers").child(Integer.toString(randInt(1, 1000000))).setValue(offer);

            startActivity(new Intent(this, DeliveryList.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomnum = rand.nextInt((max - min) + 1) + min;
        return randomnum;
    }
}
