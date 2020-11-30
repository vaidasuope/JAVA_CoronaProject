package com.corona.coronazp20t;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;


public class SearchActivity extends AppCompatActivity {

    //apsirasom URL adresa
    private static final String URL = "https://covid19-api.weedmark.systems/api/v1/stats";//krepiames del API duomenu sitoj vietoj

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //sukuriama nauja gija JSON nuskaitymui
        AsyncFetch asyncFetch = new AsyncFetch();
        asyncFetch.execute();
    }

//        try {
//            readJSONFromAPI(URL);
//        } catch (IOException e) {
//            System.out.println("Įvyko įvedimo - išvedimo klaida");//i ekrana pranesima isveda
//            System.out.println("Daugiau informacijos:" + e.getMessage());//get Message isves i ekrana klaida kokia ivyko
//        } catch (JSONException e) {
//            System.out.println("Nuskaitant JSON įvyko klaida");//i ekrana pranesima isveda
//            System.out.println("Daugiau informacijos:" + e.getMessage());
//        }
//    }
//
//    public static void readJSONFromAPI(String URL) throws IOException, JSONException {
//        System.out.println(json.toString());
//    }

    private class AsyncFetch extends AsyncTask <String, String, JSONObject> {
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        //veiksmai, kurie bus atlikti pries JSON nuskaityma (doInBackground)
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        //nuskaitys JSON
        protected JSONObject doInBackground(String... strings) {
            try {
                JSONObject json = JSON.readJsonFromUrl(URL); //JSON kreipiames i klase, kur JSON apsirase turim
                return json;
            } catch (IOException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return null;
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                return null;
            }
        }

        @Override
        //metodas bus vykdomas tada, kai grazins duomenis doInBackground metodas (kai gausim JSON is API)
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            int statusCode = 0;
            try {
                statusCode = (Integer) jsonObject.get("statusCode");
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            if (statusCode == HttpURLConnection.HTTP_OK) {
                System.out.println(jsonObject.toString());//cia i terminala isspausdina JSON
                Toast.makeText(SearchActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            } else {//else cia reiskia, kad kazkas ivyko blogai(serveris negrazino http ok status kodo)
                String message = null;
                try {
                    message = jsonObject.getString("message");
                } catch (JSONException e) {
                    Toast.makeText(
                            SearchActivity.this,
                            getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + message,
                        Toast.LENGTH_LONG
                ).show();
            } //else uzdaro
        } //onPostExecute uzdaro
    }//uzdaro klase AsyncFetch
}//uzdaro pagrindine klase Search