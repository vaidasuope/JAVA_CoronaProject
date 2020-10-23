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
               /* Toast.makeText(LoginActivity.this,
                        "Prisijungimo vardas:" + usernametext.getText().toString() + "\n" +
                             "Slaptazodis:" + passwordtext.getText().toString(),
                        Toast.LENGTH_SHORT).show();*/
               if (Validation.isValidUsername(usernametext.getText().toString())) {
                   //ketinimas pereiti i paieskos langa
                   Intent goToSearchActivity=new Intent(LoginActivity.this,
                           SearchActivity.class);
                   startActivity(goToSearchActivity);
               }
               else {//kai duomenys neatitinka sablono/reikalavimo
                   usernametext.setError(getResources().getString(R.string.login_invalid_username));//cia taip prieinam prie susikurtu savo values, kaip su xml
                   usernametext.requestFocus();//peles kursoriu sufokusuoja ties kuriuo yra klaida ir ismeta pranesima

               }
            }
        });
    }

}
