package org.techtown.http.kwangwoon_library_search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Jacks on the beach");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {

                textView.setText("button clicked");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final StringBuilder builder = new StringBuilder();
                        try {
                            Document doc = Jsoup.connect("https://www.naver.com").get();
                            String title = doc.title();
                           // Elements links = doc.select("a[href]");
                           // builder.append(title).append("\n");
                            textView.setText(title);

                        } catch (IOException e) {
                            builder.append("Error");
                        }

                        //textView.setText(builder.toString());
                    }
                }).start();

            }

        });
    }


}