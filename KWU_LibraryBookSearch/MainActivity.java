package org.techtown.http.kwangwoon_library_search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    Book[] books;
    String[] bookParsingInfo = {"searchTitle","bookline",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Jacks on the beach");

        final EditText bookSearchText = (EditText)findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {

                textView.setText("button clicked");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final StringBuilder builder = new StringBuilder();
                        try {
                            String bookSearch = bookSearchText.getText().toString();
                            Document doc = Jsoup.connect("https://kupis.kw.ac.kr/search/tot/result?st=KWRD&si=TOTAL&q="+bookSearch +"#").get();
                            String title = doc.title();

                            // probability to occur exception in index i
                            // parse bookDatas except bookname
                             Elements bookData = doc.select("div#dataInfo"+0).select("dl.briefDetail").select("dd.bookline");
                             String bookInfo = bookData.toString();
                             bookInfo = bookInfo.replace("<dd class=\"bookline\">","");
                             bookInfo = bookInfo.replace("</dd>","");
                             //bookInfo = bookInfo.replace("\n","");

                            String tempString;
                            for(int i=0;i<bookInfo.length();i++)
                                if(bookInfo.charAt(i) == '<'){
                                    tempString = bookInfo.substring(i,bookInfo.indexOf('>',i));
                                    bookInfo = bookInfo.replace(tempString,"");
                                }

                             String bookNames="";
                            for(int j=0;j<10;j++) {
                                // parse BookName
                                Elements bookNameInfo = doc.select("div#dataInfo" + j).select("dl.briefDetail").select("dd.searchTitle");
                                String bookName = bookNameInfo.toString();
                                for (int i = 0; i < bookName.length(); i++)
                                    if (bookName.charAt(i) == '<') {
                                        tempString = bookName.substring(i, bookName.indexOf('>', i) + 1);
                                        bookName = bookName.replace(tempString, "");
                                    }

                                bookName = bookName.replace("&nbsp;", "");
                               // books[j].setBookName(bookName);
                                bookNames += bookName;
                                bookNames += "\n";
                            }


                            // builder.append(title).append("\n");
                            textView.setText(bookNames);

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