package com.example.shiping.glut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class SummaryActivity extends AppCompatActivity {
    String food;
    String deliverer;
    String quantity;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_exit_left);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Your Summary");
        setSupportActionBar(toolbar);

        MainActivity.user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                food = snapshot.child("purchases").getValue().toString();
                deliverer = snapshot.child("deliverer").getValue().toString();
                quantity = snapshot.child("qty").getValue().toString();
                total = snapshot.child("total").getValue().toString();
                ((TextView) findViewById(R.id.summaryFoodName)).setText(food);
                ((TextView) findViewById(R.id.summaryFoodBuyCost)).setText(total);
                ((TextView) findViewById(R.id.summaryFrom)).setText(deliverer);
                ((TextView) findViewById(R.id.summaryFoodName2)).setText("Chicken Rice");
                ((TextView) findViewById(R.id.summaryDeliveryQty)).setText("2");
                ((TextView) findViewById(R.id.summaryTotalCost)).setText("$10.0");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        ((TextView) findViewById(R.id.summaryFoodName2)).setText("You have no food to be delivered!");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void chat(View v){
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(intent);
    }
}
