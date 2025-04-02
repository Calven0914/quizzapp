package com.example.assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtResult;
    private TextView txtdesc;
    private ImageButton ibAgain;
    private ImageButton ibHome;
    private ImageButton ibShare;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        dbHelper = new DBHelper(this);

        // Initialize views
        initViews();

        // Retrieve the username and carbon score from the Intent
        Intent intent = getIntent();
        if (intent!= null) {
            String loggedInUsername = intent.getStringExtra("loggedInUsername");
            int carbonScore = intent.getIntExtra("carbonScore", 0);

            if (isValidUsername(loggedInUsername)) {
                updateUI(loggedInUsername, carbonScore);
                saveScoreToDatabase(loggedInUsername, carbonScore);
                setOnClickListeners();
            } else {
                showErrorToast("Error: Username is null or empty");
                finish();
            }
        } else {
            showErrorToast("Error: Intent does not have extras");
            finish();
        }
    }

    private void initViews() {
        txtName = findViewById(R.id.txtname);
        txtResult = findViewById(R.id.txtresult);
        ibAgain = findViewById(R.id.ibagain);
        ibHome = findViewById(R.id.ibhome);
        ibShare = findViewById(R.id.ibshare);
        txtdesc = findViewById(R.id.txtdesc);
    }

    private boolean isValidUsername(String username) {
        return username!= null &&!username.isEmpty();
    }

    private void updateUI(String username, int carbonScore) {
        txtName.setText(username);
        txtResult.setText(String.valueOf(carbonScore)+"%");
        txtdesc.setText("You've achieved a carbon score of "+carbonScore+"%, a notable step towards a greener Earth. Stay vigilant about your carbon footprint to contribute to the planet's restoration!");

    }

    private void saveScoreToDatabase(String username, int carbonScore) {
        try {
            dbHelper.updateUserScore(username, carbonScore);
        } catch (Exception e) {
            showErrorToast("Error: Unable to update user score");
        }
    }

    private void setOnClickListeners() {
        ibAgain.setOnClickListener(v -> startQuestionActivityAgain());
        ibHome.setOnClickListener(v -> startHomeActivity());
        ibShare.setOnClickListener(v -> shareScreenshot());
    }

    private void startQuestionActivityAgain() {

        finish();
    }

    private void startHomeActivity() {

        finish();
    }

    private void shareScreenshot() {
        // Get the view you want to capture (e.g., the entire activity)
        View view = getWindow().getDecorView().getRootView();

        // Create a bitmap from the view
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        // Create a file to save the screenshot
        String filename = "screenshot.png";
        File file = new File(getExternalCacheDir(), filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos!= null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Share the screenshot using an intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        Uri uri = Uri.fromFile(file);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share Screenshot"));
    }

    private void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}