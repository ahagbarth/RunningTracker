package com.example.runningtracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<Runs> arrayRecipes;
    private LayoutInflater layoutInflater;

    public ListAdapter(Activity activity, ArrayList<Runs> recipes) {
        this.activity = activity;
        this.arrayRecipes=recipes;
        layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayRecipes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.run_data_list, null);

        TextView distance = view.findViewById(R.id.distance);
        TextView time = view.findViewById(R.id.time);
        TextView date = view.findViewById(R.id.date);




        distance.setText(arrayRecipes.get(position).getDistance());
        time.setText(arrayRecipes.get(position).getTime());
        date.setText(arrayRecipes.get(position).getDate());




        return view;
    }
}
