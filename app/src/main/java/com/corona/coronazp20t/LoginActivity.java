package com.corona.coronazp20t;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernametext, passwordtext;
    Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText this.usernametext = (EditText) findViewById(R.id.usernametext);
        EditText this.passwordtext = (EditText) findViewById(R.id.passwordtext);

        Button this.loginbutton = (Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Prisijungimo vardas:" + usernametext.getText().toString() + "\n" +
                             "Slaptazodis:" + passwordtext.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                Intent goToSearchActivity=new Intent(LoginActivity.this,
                        SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        });


    }
}
