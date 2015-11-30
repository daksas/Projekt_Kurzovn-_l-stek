package com.example.dak.kurzovni_listek;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dak on 29.11.2015.
 */
public class JsonParser {
    public class AsyncTaskParseJson2 extends AsyncTask<String, String, String> {

        //Zatím funguje přes kurzovni_listek.java, snad bude funkčnost přesunuta sem.
        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... params) {

            Log.e("TAG", "1000");
            HttpClient client = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(kurzovni_listek.url);
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

}
