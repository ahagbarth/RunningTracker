package com.example.runningtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Declaring the Run button
        Button btnRun = getView().findViewById(R.id.btnRun);

        //Adding Functionality to button
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If pressed, start RunActivity
                Intent intent = new Intent(getActivity(), RunActivity.class);
                startActivity(intent);
            }

        });

    }
}
