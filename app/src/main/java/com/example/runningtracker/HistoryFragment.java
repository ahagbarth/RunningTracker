package com.example.runningtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {

    //private RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    //private List<String> list;
    //private RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        String[] menuItems = {"do omething", "do something else", "do yet another thing"};

        ListView listView = (ListView) view.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menuItems);
        listView.setAdapter(adapter);


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
