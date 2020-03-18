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

public class SleepListAdapter extends ArrayAdapter<SleepInfo> {
    private Context sleepContext;
    private int sleepResources;

    public SleepListAdapter(Context context, int resource, List<SleepInfo> objects) {
        super(context, resource, objects);
        this.sleepContext = context;
        this.sleepResources = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String sleep_quality_val = getItem(position).getSleep_quality();
        String hours_of_sleep_val = getItem(position).getHours_of_sleep();
        String slept_time_val = getItem(position).getSlept_time();
        String sleep_timestamp_val = getItem(position).getSleep_timestamp();
        LayoutInflater layoutInflater = LayoutInflater.from(sleepContext);
        convertView = layoutInflater.inflate(sleepResources, parent, false);
        SleepInfo sleepInfo = new SleepInfo(sleep_quality_val, hours_of_sleep_val, slept_time_val, sleep_timestamp_val);
        TextView sleep_qualityvalue = (TextView) convertView.findViewById(R.id.sleep_quality_val);
        TextView hours_of_sleepvalue = (TextView) convertView.findViewById(R.id.sleep_hours_val);
        TextView slept_timevalue = (TextView) convertView.findViewById(R.id.time_slept_val);
        TextView sleep_timestampvalue = (TextView) convertView.findViewById(R.id.timestamp_val_for_sleep);
        sleep_qualityvalue.setText(sleep_quality_val);
        hours_of_sleepvalue.setText(hours_of_sleep_val);
        slept_timevalue.setText(slept_time_val);
        sleep_timestampvalue.setText(sleep_timestamp_val);
        return convertView;
    }
}
