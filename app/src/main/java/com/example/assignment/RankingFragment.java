package com.example.assignment;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class RankingFragment extends Fragment {
    private ListView userlist;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        userlist = view.findViewById(R.id.userlist);
        db = new DBHelper(getActivity());

        Cursor cursor = db.readAllData();

        // Create a new MatrixCursor object and add the _id, rank, username, and marks columns to it
        String[] columnNames = {"_id", "rank", "username", "marks"};
        MatrixCursor newCursor = new MatrixCursor(columnNames);
        int rank = 1;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex("_id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                int marks = cursor.getInt(cursor.getColumnIndex("marks"));
                Object[] columnValues = {id, rank, username, marks};
                newCursor.addRow(columnValues);
                rank++;
            } while (cursor.moveToNext());
        }

        // Use a SimpleCursorAdapter to bind the data to the list
        String[] fromColumns = {"rank", "username", "marks"};
        int[] toViews = {R.id.txtrank, R.id.txtusername, R.id.txtscore};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.user_row,
                newCursor,
                fromColumns,
                toViews,
                0
        );

        userlist.setAdapter(adapter);

        return view;
    }
}