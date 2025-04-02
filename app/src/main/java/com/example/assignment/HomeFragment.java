package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

    Button btncalculate;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the button by its ID
        btncalculate = (Button) view.findViewById(R.id.btncalculate);

        // Set OnClickListener to the button
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start QuestionActivity when the button is clicked
                Intent intent = new Intent(requireActivity(), QuestionActivity.class);
                // Pass the loggedInUsername to QuestionActivity
                intent.putExtra("loggedInUsername", ((Dashboard)requireActivity()).getLoggedInUsername());
                requireActivity().startActivity(intent);
            }
        });

        return view;
    }
}