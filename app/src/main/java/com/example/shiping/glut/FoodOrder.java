package com.example.shiping.glut;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FoodOrder extends AppCompatActivity {

    public double total;
    public double budg;
    public double price;
    String sfood;
    String sdel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Fill Your Order");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String sloc = getIntent().getStringExtra("loc");
        sdel = getIntent().getStringExtra("del");
        sfood = getIntent().getStringExtra("food");
        double sprice = getIntent().getDoubleExtra("price", 0.0);
        double sbudget = getIntent().getDoubleExtra("budget", 0.0);
        budg = sbudget;
        price = sprice;


        TextView location = (TextView) findViewById(R.id.locView);
        location.setText(sloc);
        TextView deliverer = (TextView) findViewById(R.id.delView);
        deliverer.setText(sdel);
        TextView food = (TextView) findViewById(R.id.foodView);
        food.setText(sfood);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        EditText text = (EditText) findViewById(R.id.editText);
        final int qty = Integer.parseInt(text.getText().toString());
        total = price * qty;
        if (id == R.id.confirm) {
            if (total > budg) {
                Toast.makeText(this, "The delivery man is poor, please order less!" + total + " " + budg, Toast.LENGTH_SHORT).show();
            }
            else {

                new AlertDialog.Builder(this)
                        .setTitle("Order confirmation")
                        .setMessage("Are you sure you want to proceed? \nThe amount charged will be: $" + String.format("%.2f",total))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Toast.makeText(FoodOrder.this, "You are now saved from hunger!", Toast.LENGTH_SHORT).show();
                                MainActivity.user.child("purchases").setValue(sfood);
                                MainActivity.user.child("deliverer").setValue(sdel);
                                MainActivity.user.child("qty").setValue(qty);
                                MainActivity.user.child("total").setValue(String.format("%.2f",total));
                                startActivity(new Intent(FoodOrder.this, DeliveryList.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
