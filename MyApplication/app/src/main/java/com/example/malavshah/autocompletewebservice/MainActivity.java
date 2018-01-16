package com.example.malavshah.autocompletewebservice;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    public String data;
    public List<String> suggest;
    public AutoCompleteTextView autoComplete;
    public ArrayAdapter<String> aAdapter;
    URL url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suggest = new ArrayList<String>();
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        autoComplete.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                new DownloadTask1().execute(newText);
            }

        });

    }

    public class DownloadTask1 extends AsyncTask<String, String, String>{

        String data="";
        @Override
        protected String doInBackground(String... params) {

            String newText = params[0];
            Log.i("My App",newText);

            newText = newText.trim();
            newText = newText.replace(" ", "+");

            try {
                url = new URL("https://en.wikipedia.org/w/api.php?action=opensearch&search=" + newText + "&limit=8&namespace=0&format=json");
                Log.i("My", String.valueOf(url));

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while(line!=null){
                    line = br.readLine();
                    data = data + line;
                }

                Log.i("My", data);

                suggest = new ArrayList<String>();
                JSONArray jArray = new JSONArray(data);
                for (int i = 0; i < jArray.getJSONArray(1).length(); i++) {
                    String SuggestKey = jArray.getJSONArray(1).getString(i);
                    suggest.add(SuggestKey);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable(){
                public void run(){
                    aAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.item,suggest);
                    autoComplete.setAdapter(aAdapter);
                    aAdapter.notifyDataSetChanged();
                }
            });

            return null;
        }
    }
}
