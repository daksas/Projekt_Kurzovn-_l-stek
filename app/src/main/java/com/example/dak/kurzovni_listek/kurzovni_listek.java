package com.example.dak.kurzovni_listek;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;


public class kurzovni_listek extends AppCompatActivity {
    public static int pozice2 = 0;
    public static String url = "http://api.fixer.io/latest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurzovni_listek);
        TextView text = (TextView)findViewById(R.id.textView2);
        String currency_wn[] = getResources().getStringArray(R.array.currency_whole_name);

        ListView lv = (ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, currency_wn);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id)   {
                     pozice2 = position;
                    Intent a = new Intent(kurzovni_listek.this, compare.class);
                     startActivity(a);
                }
          }
        );
        text.setText("Vybrali jste " + currency_wn[MainActivity.pozice]);
        Log.e("TAG", "2221000");
       // new AsyncTaskParseJson().execute();
        new AsyncTaskParseJson().execute();



    }
    public class AsyncTaskParseJson extends AsyncTask<String, String, String>{


        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... params) {

            Log.e("TAG", "1000");
            HttpClient client = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            try{
                HttpResponse response = client.execute(getRequest);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                Log.e("TAG", "1");
                if(statusCode != 200){
                    return null;
                }

                InputStream jsonStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
                StringBuilder builder = new StringBuilder();
                String line;
                while((line = reader.readLine())!=null){
                    builder.append(line);
                }
                String jsonData = builder.toString();
                Log.e("TAG", jsonData);

            }catch(ClientProtocolException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }




    /*public class AsyncTaskParseJson extends AsyncTask<String, String, String>{

        final String TAG = "AsyncTaskParseJson.java";

        // set your json string url here
        String url = "http://api.fixer.io/latest";

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... arg0) {

            try {

                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get json string from url
                JSONObject json = jParser.getJSON(url);

                // get the array of users
                //dataJsonArr = json.getJSONArray("Users");


                // loop through all users
                for (int i = 0; i < dataJsonArr.length(); i++) {

                    JSONObject c = dataJsonArr.getJSONObject(i);

                    // Storing each json item in variable
                    String firstname = c.getString("firstname");
                    String lastname = c.getString("lastname");
                    String username = c.getString("username");

                    // show the values in our logcat
                    Log.e(TAG, "firstname: " + firstname
                            + ", lastname: " + lastname
                            + ", username: " + username);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kurzovni_listek, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

