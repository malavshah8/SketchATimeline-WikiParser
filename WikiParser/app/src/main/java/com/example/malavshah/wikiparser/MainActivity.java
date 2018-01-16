package com.example.malavshah.wikiparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Map<String, String> hm = new HashMap<String, String>();
    String price1 = "";
    Document document;
    TreeMap<String, String> sorted;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textview);

        print("running...");
        getData();
    }

    public void print(String string) {
        Log.i("My App", string);
    }

    public String dateFormatter(String string) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        Date d = fmt.parse(string);

        DateFormat outputFormatter = new SimpleDateFormat("yyyy/MM/dd");
        String output = outputFormatter.format(d);
        // System.out.println(output);
        return output;
    }

    public void sortbykey() {
        // TreeMap to store values of HashMap
        sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(hm);

        // Display the TreeMap which is naturally sorted


    }

    // New Thread to calculate and update the final UI
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Get Document object after parsing the html from given url.
                    document = Jsoup.connect("https://en.wikipedia.org/wiki/Star_Wars_(film)").get();

                    String title = document.title(); // Get title
                    print("Title: " + title); // Print title.

                    Elements price = document.select("p"); // Get price
                    for (int i = 0; i < price.size(); i++) {
                        price1 = price.get(i).text().replaceAll("\\[.*?\\]", "");
                        String REGEX = "[a-zA-Z]{3,}\\s\\d{1,2},\\s\\d{4}";
                        Pattern p = Pattern.compile(REGEX);
                        Matcher m = p.matcher(price1);
                        while (m.find()) {
                            //System.out.println(m.group(0));
                            try {
                                String date = dateFormatter(m.group(0));

                                String newInput[] = price1.split("\\.");
                                for (String w : newInput) {
                                    if (w.contains(m.group(0))) {
                                        // System.out.println(w);
                                        hm.put(date, w.trim());
                                    }
                                }
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sortbykey();
                        for (Map.Entry<String, String> entry : sorted.entrySet())
                               // Log.i("MyApp",entry.getKey() + entry.getValue());

                            tv.append(entry.getKey() + " " + entry.getValue() + "\n\n");

                    }
                });
            }
        }).start();

    }
}


