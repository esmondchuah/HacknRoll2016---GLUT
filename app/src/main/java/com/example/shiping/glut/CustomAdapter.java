package com.example.shiping.glut;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class CustomAdapter extends BaseAdapter{
    String [] result1;
    String [] result2;
    String [] result3;
    String [] result4;
    double[] result5;
    double[] result6;
    String [] result7;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context mainActivity, String[] foodList, int[] personImages, String[] foodLocation, String[] distance, String[] accountName, double[] foodprice, double[] budget, String[] eta) {
        // TODO Auto-generated constructor stub
        result1=foodList;
        result2=foodLocation;
        result3=distance;
        result4=accountName;
        result5=foodprice;
        result6=budget;
        result7=eta;
        context=mainActivity;
        imageId=personImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result2.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.listitems, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.food_name);
        holder.tv2=(TextView) rowView.findViewById(R.id.location);
        holder.tv3=(TextView) rowView.findViewById(R.id.distance);
        holder.tv4=(TextView) rowView.findViewById(R.id.account_name);
        holder.tv5=(TextView) rowView.findViewById(R.id.eta);
        holder.img=(ImageView) rowView.findViewById(R.id.person_photo);
        holder.tv1.setText(result1[position]);
        holder.tv2.setText(result2[position]);
        holder.tv3.setText(result3[position]);
        holder.tv4.setText(result4[position]);
        holder.tv5.setText("ETA: " + result7[position]);
        holder.img.setImageResource(imageId[position]);
        View orderselected;
        orderselected = (TextView) rowView.findViewById(R.id.order);
        orderselected.setOnClickListener(new OnClickListener() {
            String finalLocation;
            String finalDeliverer;
            String finalFood;
            double finalBudget;
            double finalPrice;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You are ordering "+result1[position], Toast.LENGTH_SHORT).show();
                finalLocation = result2[position];
                finalDeliverer = result4[position];
                finalFood = result1[position];
                finalPrice = result5[position];
                finalBudget = result6[position];
                Intent intent = new Intent(context, FoodOrder.class);
                intent.putExtra("loc", finalLocation);
                intent.putExtra("del", finalDeliverer);
                intent.putExtra("food", finalFood);
                intent.putExtra("price", finalPrice);
                intent.putExtra("budget", finalBudget);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

}
