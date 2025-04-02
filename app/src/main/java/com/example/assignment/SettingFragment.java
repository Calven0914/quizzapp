package com.example.assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    private EditText etSetname, etSetpass, etConfirmPassword;
    private Button btnUpdate;
    private ImageView backButton;
    private DBHelper dbHelper;
    private String loggedInUsername;

    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initViews(view);
        setupListeners();
        displayUserData();
        return view;
    }

    private void initViews(View view) {
        etSetname = view.findViewById(R.id.etsetname);
        etSetpass = view.findViewById(R.id.etsetpass);
        etConfirmPassword = view.findViewById(R.id.etconfirmpassword);
        btnUpdate = view.findViewById(R.id.btnupdate);
        backButton = view.findViewById(R.id.img_back);
    }

    private void setupListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHomeFragment();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void displayUserData() {
        dbHelper = new DBHelper(getActivity());
        loggedInUsername = getLoggedInUsername();
        Cursor cursor = dbHelper.getdata(loggedInUsername);
        if (cursor != null) {
            cursor.moveToFirst();
            String retrievedUsername = cursor.getString(cursor.getColumnIndex("username"));
            String retrievedPassword = cursor.getString(cursor.getColumnIndex("password"));
            etSetname.setText(retrievedUsername);
            etSetpass.setText(retrievedPassword);
            cursor.close();
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to update username/password?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUserData();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void updateUserData() {
        String newUsername = etSetname.getText().toString();
        String newPassword = etSetpass.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = dbHelper.updateUserData(loggedInUsername, newUsername, newPassword);
        if (isUpdated) {
            Toast.makeText(getActivity(), "User data updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to update user data", Toast.LENGTH_SHORT).show();
        }
    }

    private String getLoggedInUsername() {
        if (getActivity() instanceof Dashboard) {
            return ((Dashboard) getActivity()).getLoggedInUsername();
        }
        return null;
    }

    private void navigateToHomeFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
        fragmentTransaction.commit();
    }

}