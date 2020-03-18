package com.example.mentalhealthlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentalhealthlogin.Activityinfo;
import com.example.mentalhealthlogin.R;

import java.util.List;

public class AcivityListAdapter extends ArrayAdapter<Activityinfo> {
    private Context atContext;
    int mResource;

    public AcivityListAdapter(Context context, int resource,  List<Activityinfo> objects) {
        super(context, resource, objects);
        this.atContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String what_val = getItem(position).getWhat();
        String duration_val = getItem(position).getDuration();
        String act_time_val = getItem(position).getTimestamp();
        Activityinfo activityinfo = new Activityinfo(what_val, duration_val, act_time_val);
        LayoutInflater layoutInflater = LayoutInflater.from(atContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView whatvalue = (TextView) convertView.findViewById(R.id.what_val);
        TextView durationvalue = (TextView) convertView.findViewById(R.id.duration_val);
        TextView act_timevalue = (TextView) convertView.findViewById(R.id.act_time_val);
        whatvalue.setText(what_val);
        durationvalue.setText(duration_val);
        act_timevalue.setText(act_time_val);
        return convertView;
    }
}
