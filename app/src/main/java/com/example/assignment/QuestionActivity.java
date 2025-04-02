package com.example.assignment;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();

    private int count = 0;
    private int position = 0;
    private int carbonScore = 0;

    public String loggedInUsername;

    ImageButton ibtips;

    TextView txttips;
    TextView txtquestion;
    TextView opt1;
    TextView opt2;
    TextView opt3;

    TextView txtquestionnum;

    Button btnexit;
    ImageButton ibback;

    boolean isTipsVisible = false;

    ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            loggedInUsername = intent.getStringExtra("loggedInUsername"); // Remove the String declaration
            // Now you have the loggedInUsername, you can use it as needed.
        }

        txtquestionnum = findViewById(R.id.txtquestionnum);


        btnexit = findViewById(R.id.btnexit);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuestionActivity.this)
                        .setTitle("Exit Quiz")
                        .setMessage("Are you sure you want to exit the quiz?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });


        ibback = findViewById(R.id.ib_back);
        ibback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuestionActivity.this)
                        .setTitle("Exit Quiz")
                        .setMessage("Are you sure you want to exit the quiz?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        // Initialize views
        ibtips = binding.ibtips;
        txttips = binding.txttips;
        txtquestion = binding.txtquestion;
        opt1 = binding.optionA;
        opt2 = binding.optionB;
        opt3 = binding.optionC;

        // Set OnClickListener to the ibtips ImageButton
        ibtips.setOnClickListener(v -> {
            // Toggle the visibility of txttips
            if (isTipsVisible) {
                // If tips is currently visible, hide it
                txttips.setVisibility(View.GONE);
                isTipsVisible = false;
            } else {
                // If tips is currently hidden, show it
                txttips.setVisibility(View.VISIBLE);
                isTipsVisible = true;
            }
        });

        // Initialize questions
        list.add(new QuestionModel("What is your daily transportation?",
                "Car", "Public Transport", "Bicycle", "Did you know Using Public Transport can greatly reduce the carbon footprint?",
                new int[]{4, 8, 10}));
        list.add(new QuestionModel("How often do you eat meat?",
                "Every day", "A few times a week", "Once a week or less",
                "Did you know that reducing your meat consumption can significantly decrease your carbon footprint? Try incorporating more plant-based meals into your diet!",
                new int[]{2, 5, 8}));
        list.add(new QuestionModel("Do you recycle?",
                "Sometimes", "Most of the time", "Always",
                "Did you know that recycling helps conserve natural resources and reduces the amount of waste sent to landfills? Make sure to recycle properly and follow your local recycling guidelines.",
                new int[]{2, 6, 10}));
        list.add(new QuestionModel("How often do you buy new clothes?",
                "Every Time", "A few times a year", "Once a year or less",
                "Did you know that the fashion industry is one of the largest polluters in the world? Consider buying second-hand clothes, or choosing sustainable and eco-friendly brands.",
                new int[]{2, 5, 9}));
        list.add(new QuestionModel("Do you turn off lights and appliances when not in use?",
                "Sometimes", "Most of the time", "Always",
                "Did you know that turning off lights and appliances when not in use can save energy and reduce your carbon footprint?",
                new int[]{2, 6, 9}));
        list.add(new QuestionModel("Do you use reusable bags and bottles?",
                "Sometimes", "Most of the time", "Always",
                "Did you know that using reusable bags and bottles can significantly reduce your plastic waste and carbon footprint?",
                new int[]{2, 6, 9}));
        list.add(new QuestionModel("Do you compost food waste?",
                "Never", "Sometimes", "Always",
                "Did you know that composting food waste can reduce methane emissions from landfills and enrich soil?",
                new int[]{2, 6, 9}));
        list.add(new QuestionModel("Do you use energy-efficient appliances? ",
                "Never", "Sometimes", "Always",
                "Did you know that using energy-efficient appliances can save energy and reduce your carbon footprint?",
                new int[]{0, 5, 9}));
        list.add(new QuestionModel("How often do you buy locally sourced or organic food?",
                "Rarely", "Sometimes", "Always",
                "Did you know that buying locally sourced or organic food can reduce your carbon footprint by reducing the amount of energy used in transportation and production?",
                new int[]{2, 6, 9}));
        list.add(new QuestionModel("Do you use renewable energy sources? Like Solar-Energy?",
                "Never", "Sometimes", "Always",
                "Did you know that using renewable energy sources can reduce your carbon footprint and help combat climate change?",
                new int[]{1, 5, 8}));

        // Set the first question
        setQuestion(position);

        // Set OnClickListener to the options
        opt1.setOnClickListener(v -> {
            carbonScore += getScore(opt1);
            nextQuestion();
        });
        opt2.setOnClickListener(v -> {
            carbonScore += getScore(opt2);
            nextQuestion();
        });
        opt3.setOnClickListener(v -> {
            carbonScore += getScore(opt3);
            nextQuestion();
        });

        // Display the question number
        txtquestionnum.setText(String.format("Question %d of %d", position + 1, list.size()));
    }

    private void setQuestion(int position) {
        QuestionModel question = list.get(position);
        txtquestion.setText(question.getQuestion());
        opt1.setText(question.getOptionA());
        opt2.setText(question.getOptionB());
        opt3.setText(question.getOptionC());
        txttips.setText(question.getTips());
    }

    private int getScore(TextView option) {
        QuestionModel question = list.get(position);
        String optionText = option.getText().toString();
        if (optionText.equals(question.getOptionA())) {
            return question.getScores()[0];
        } else if (optionText.equals(question.getOptionB())) {
            return question.getScores()[1];
        } else {
            return question.getScores()[2];
        }
    }

    private void nextQuestion() {
        position++;
        if (position < list.size()) {
            setQuestion(position);
        } else {
            // Start ResultActivity and pass the carbonScore and loggedInUsername
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            intent.putExtra("carbonScore", carbonScore);
            intent.putExtra("loggedInUsername", loggedInUsername);
            startActivity(intent);
            finish(); // Finish the QuestionActivity
        }
        // Update the question number
        txtquestionnum.setText(String.format("Question %d of %d", position + 1, list.size()));
    }
}