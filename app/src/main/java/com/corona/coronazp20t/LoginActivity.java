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
    Button loginbutton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //visas kodas rasomas po sito komentaro

        Button loginbutton = (Button) findViewById(R.id.loginbutton);
        //cia istraukiamas elementas is vaizdo

        final EditText usernametext = (EditText) findViewById(R.id.usernametext);
        //edit text yra neapdirbtas formatas, todel isvedant reikia rasyti getText toString
        final EditText passwordtext = (EditText) findViewById(R.id.passwordtext);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cia bus vykdomas kodas po button paspausdimo

                if (!Validation.isValidUsername(usernametext.getText().toString())) {
                    usernametext.setError(getResources().getString(R.string.login_invalid_username));
                    usernametext.requestFocus();
                }
                else if (!Validation.isValidPassword(passwordtext.getText().toString())) {
                    passwordtext.setError(getResources().getString(R.string.login_invalid_username));
                    passwordtext.requestFocus();
                }
                else {
                    Intent goToSearchActivity = new Intent(LoginActivity.this,
                            SearchActivity.class);
                    startActivity(goToSearchActivity);

                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_success),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity();
            }
        });

    }

    public void RegisterActivity() {
        Intent goRegisterActivity = new Intent(LoginActivity.this,
                RegisterActivity.class);
        startActivity(goRegisterActivity);
    }

}
