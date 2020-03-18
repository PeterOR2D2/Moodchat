package com.example.mentalhealthlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FoodLogAdapter extends ArrayAdapter<FoodInfo> {

    private Context foodContext;
    private int foodResources;

    public FoodLogAdapter(Context context, int resource, List<FoodInfo> objects) {
        super(context, resource, objects);
        this.foodContext = context;
        this.foodResources = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String food_name_val = getItem(position).getFood_name();
        String food_timestamp_val = getItem(position).getAte_at();
        LayoutInflater layoutInflater = LayoutInflater.from(foodContext);
        convertView = layoutInflater.inflate(foodResources, parent, false);
        FoodInfo foodInfo = new FoodInfo(food_timestamp_val, food_name_val);
        TextView foodnamevalue = (TextView) convertView.findViewById(R.id.food_val);
        TextView food_timestampvalue = (TextView) convertView.findViewById(R.id.food_timestamp_val);
        foodnamevalue.setText(food_name_val);
        food_timestampvalue.setText(food_timestamp_val);
        return convertView;
    }
}
