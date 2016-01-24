package com.example.shiping.glut;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class DeliveryList extends AppCompatActivity {

    private Toolbar toolbar;
    ListView lv;
    Context context;
    private Firebase data;

    public ArrayList<Integer> personImages1;
    public ArrayList<String> foodList1;
    public ArrayList<String> foodLocation1;
    public ArrayList<String> eta1;
    public ArrayList<Integer> distance1;
    public ArrayList<String> accountName1;
    public ArrayList<Double> foodPrice1;
    public ArrayList<Double> delBudget1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.animation_enter_left, R.anim.animation_exit_right);
        setContentView(R.layout.activity_delivery_list);

        context=this;
        Firebase.setAndroidContext(this);
        data = new Firebase("https://sizzling-torch-225.firebaseio.com/offers");

        foodList1 = new ArrayList<String>();
        foodLocation1  = new ArrayList<String>();
        eta1 = new ArrayList<String>();
        accountName1 = new ArrayList<String>();
        personImages1 = new ArrayList<Integer>();
        distance1 = new ArrayList<Integer>();
        foodPrice1 = new ArrayList<Double>();
        delBudget1 = new ArrayList<Double>();

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final long numOffers = snapshot.getChildrenCount(); // total number of textviews to add

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Offers offer = postSnapshot.getValue(Offers.class);

                    int rand = NewDeliveryActivity.randInt(100, 1000);

                    foodList1.add(offer.getFood() + " ($" + offer.getPrice() + ")");
                    foodLocation1.add(offer.getSource());
                    eta1.add(offer.getEta());
                    distance1.add(rand);
                    accountName1.add(offer.getUsername());
                    personImages1.add(R.drawable.user5);
                    foodPrice1.add(Double.parseDouble(offer.getPrice()));
                    delBudget1.add(Double.parseDouble(offer.getBudget()));
                }
                String[] foodList = new String[foodList1.size()];
                foodList = foodList1.toArray(foodList);
                String[] foodLocation = new String[foodLocation1.size()];
                foodLocation = foodLocation1.toArray(foodLocation);

                Collections.sort(distance1);
                String[] distance = new String[distance1.size()];
                for(int i=0; i < distance1.size(); i++){
                    distance[i] = Integer.toString(distance1.get(i)) + "m";
                }

                String[] accountName = new String[accountName1.size()];
                accountName = accountName1.toArray(accountName);
                String[] eta = new String[eta1.size()];
                eta = eta1.toArray(eta);
                int[] personImages = new int[personImages1.size()];
                for (int i = 0; i < personImages1.size(); i++) {
                    personImages[i] = personImages1.get(i).intValue();
                }
                double[] foodPrice = new double[foodPrice1.size()];
                for (int i = 0; i < foodPrice1.size(); i++) {
                    foodPrice[i] = foodPrice1.get(i).doubleValue();
                }
                double[] delBudget = new double[delBudget1.size()];
                for (int i = 0; i < delBudget1.size(); i++) {
                    delBudget[i] = delBudget1.get(i).doubleValue();
                }

                lv = (ListView) findViewById(R.id.listView);
                lv.setAdapter(new CustomAdapter(DeliveryList.this, foodList, personImages, foodLocation, distance, accountName, foodPrice, delBudget, eta));


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Glut");
        setSupportActionBar(toolbar);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_add);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewDeliveryActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.summary) {
            startActivity(new Intent(this, SummaryActivity.class));
        }

        if (id == R.id.filter) {
            final CharSequence[] items = {"By user ratings","By ETA"};
            final ArrayList seletedItems=new ArrayList();

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Set filters")
                    .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                seletedItems.add(indexSelected);
                            } else if (seletedItems.contains(indexSelected)) {
                                // Else, if the item is already in the array, remove it
                                seletedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  Your code when user clicked on OK
                            //  You can write the code  to save the selected item here
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  Your code when user clicked on Cancel
                        }
                    }).create();
            dialog.show();

        }

        if (id == R.id.search) {
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickOrder(View view) {
        startActivity(new Intent(this,FoodOrder.class));
    }

}
