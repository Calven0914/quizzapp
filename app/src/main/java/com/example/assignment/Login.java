package com.example.assignment;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView tvregister;

    EditText etname, etpassword;

    Button btnlogin;

    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvregister = findViewById(R.id.tvregister);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click logic
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        etname = (EditText) findViewById(R.id.etrename);
        etpassword = (EditText) findViewById(R.id.etrepassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etname.getText().toString();
                String pass = etpassword.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(Login.this, "All Fields Are Required.", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                    if (checkuserpass == true){
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        etname.setText("");
                        etpassword.setText("");
                        // Pass username to the Dashboard activity
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.putExtra("USERNAME", user); // Pass username using key-value pair
                        startActivity(intent);

                    } else {
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
