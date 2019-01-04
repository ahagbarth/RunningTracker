package com.example.runningtracker;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {

    //private RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    //private List<String> list;
    //private RecyclerAdapter adapter;
    //ListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        DBHelper dbHelper = new DBHelper(getContext());
        ArrayList<Runs> runList;

        runList = dbHelper.getRecipeList();

       // String[] menuItems = {"do omething", "do something else", "do yet another thing"};

        ListView listView = (ListView) view.findViewById(R.id.listView);

        ListAdapter adapter = new ListAdapter(getActivity(), runList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), RunProfileActivity.class);
                startActivity(intent);
            }
        });


/*
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<String> list= Arrays.asList(getResources().getStringArray(R.array.android_versions));

        RecyclerAdapter adapter = new RecyclerAdapter(list);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);


*/




        return view;



    }
}
