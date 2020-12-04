package com.corona.coronazp20t;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    //apsirasom URL adresa
    private static final String URL = "https://covid19-api.weedmark.systems/api/v1/stats";//krepiames del API duomenu sitoj vietoj

    private RecyclerView recyclerView;
    private Adapter adapter;//sujungia sita klase su xml

    private ArrayList<Corona> coronaList = new ArrayList<Corona>();//turesiu sarasa, kuriame saugosiu corona irasus

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //sukuriama nauja gija JSON nuskaitymui
        AsyncFetch asyncFetch = new AsyncFetch();
        asyncFetch.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu);

        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }

        return true;
    }

    //kodas reikalingas, kad meniu juostoje galima butu paieska daryti
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            //kokia valstybe vartotojas ives i search tokia info mes cia uzpildysime

            ArrayList<Corona> coronaListByCountry = JSON.getCoronaListByCountry(coronaList, query);
            if (coronaListByCountry.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.search_no_results) + query, Toast.LENGTH_SHORT).show();
            }

            //paruosiami ir perduodami duomenys recyclerView
            recyclerView = (RecyclerView) findViewById(R.id.corona_list);//susiradom corona list'a
            adapter = new Adapter(this, coronaListByCountry);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    private class AsyncFetch extends AsyncTask<String, String, ArrayList<Corona>> {
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
        protected ArrayList<Corona> doInBackground(String... strings) {
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(URL); //JSON kreipiames i klase, kur JSON apsirase turim
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
                    JSONArray jsonArray = null;
                    coronaList = new ArrayList<Corona>();
                    try {
                        jsonArray = JSON.getJSONArray(jsonObject);
                        coronaList = JSON.getList(jsonArray);
                    } catch (JSONException e) {
                        Toast.makeText(
                                SearchActivity.this,
                                getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                    return coronaList;

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
                return null;
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
        protected void onPostExecute(ArrayList <Corona>  coronaList) {
            progressDialog.dismiss();

            if (coronaList!=null) {//jeigu bus sekmingai nuskaitytas JSON - atspausdins vartotojui i ekrana kiek irasu gavome is API
                Toast.makeText(SearchActivity.this, getResources().getString(R.string.search_found_entries_from_api)+coronaList.size(), Toast.LENGTH_SHORT).show();
            }
        } //onPostExecute uzdaro
    }//uzdaro klase AsyncFetch
}//uzdaro pagrindine klase Search