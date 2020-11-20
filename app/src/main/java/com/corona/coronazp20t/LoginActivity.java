package com.corona.coronazp20t;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernametext, passwordtext;
    Button loginbutton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate - veiksmas vyksta, kai krauna langa
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //visas kodas rasomas po sito komentaro

        Button loginbutton = (Button) findViewById(R.id.loginbutton);
        //cia istraukiamas elementas is vaizdo

        final EditText usernametext = (EditText) findViewById(R.id.usernametext);//final kad jis nekinta
        //edit text yra neapdirbtas formatas, todel isvedant reikia rasyti getText toString
        final EditText passwordtext = (EditText) findViewById(R.id.passwordtext);

        final CheckBox rememberme = (CheckBox) findViewById(R.id.rememberMe);
        //bus konstruojamas vartotojo objektas perduodant context'a (langa kuriame esame)
        final User user=new User(LoginActivity.this);
        //patikriname, ar paskutini karta buvo pazymetas checkbox remember me
        rememberme.setChecked(user.isRememberedForLogin());

        //Aprasoma prisiminti mane checkbox logika
        if (rememberme.isChecked()){//jeigu checkbox buvo pazymetas-vartotojas pageidavo, kad informacija buvo issaugota
            usernametext.setText(user.getUsernameForLogin(),TextView.BufferType.EDITABLE);//setText-uzpildysime user informacija, editable - suteiksim galimybe paredaguoti duomenis
            passwordtext.setText(user.getPasswordForLogin(),TextView.BufferType.EDITABLE);
        } else {//jeigu checkbox buvo nepazymetas-vartotojas nenorejo, kad informacija buvo issaugota
            usernametext.setText("",TextView.BufferType.EDITABLE);
            passwordtext.setText("",TextView.BufferType.EDITABLE);
        }
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
                    user.setUsernameForLogin(usernametext.getText().toString());
                    user.setPasswordForLogin(passwordtext.getText().toString());
                    if (rememberme.isChecked()){
                        user.setRemembermeKeyForLogin(true);
                    } else {
                        user.setRemembermeKeyForLogin(false);
                    }

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
