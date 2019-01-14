package com.example.runningtracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {

    TextView totalDistance;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        DBHelper dbHelper = new DBHelper(getContext());
        ArrayList<Runs> runList;
        totalDistance = view.findViewById(R.id.totalDistance);
        runList = dbHelper.getRecipeList();


        ListView listView = (ListView) view.findViewById(R.id.listView);

        ListAdapter adapter = new ListAdapter(getActivity(), runList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.putExtra("position",position + 1 );
                startActivity(intent);
            }
        });









        return view;



    }
}
