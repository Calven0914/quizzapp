package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    TextView tvlogin;

    EditText etrename, etrepassword, etconpassword;

    Button btnregister;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etrename = findViewById(R.id.etrename);
        etrepassword = findViewById(R.id.etrepassword);
        etconpassword = findViewById(R.id.etconpassword);
        btnregister = findViewById(R.id.btnregister);
        DB = new DBHelper(this);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etrename.getText().toString();
                String repass = etrepassword.getText().toString();
                String conpass = etconpassword.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(conpass))
                    Toast.makeText(Register.this,"All fields Required", Toast.LENGTH_SHORT).show();
                else{
                    if(repass.equals(conpass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user,repass);
                            if(insert =true){
                                Toast.makeText(Register.this,"Registered Succesfully.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Register.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "User already Exists", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Register.this, "Password are not matching!", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });









        tvlogin = findViewById(R.id.tvlogin);
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click logic
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }

        });
    }
}