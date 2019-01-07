package com.example.runningtracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        TextView title = view.findViewById(R.id.title);
        TextView instructions = view.findViewById(R.id.instructions);
        TextView date = view.findViewById(R.id.date);




        title.setText(arrayRecipes.get(position).getTitle());
        instructions.setText(arrayRecipes.get(position).getInstructions());
        date.setText(arrayRecipes.get(position).getDate());




        return view;
    }
}
