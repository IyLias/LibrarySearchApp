package org.techtown.samplelibrarybooksearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView bookInfoView[];

    ImageView ImageView[];

    Button button;

    Spinner librarySpinner;

    static final String seoul_lib[]={"서울시교육청 강남도서관", "서울시교육청 강동도서관" ,"서울시교육청 강서도서관","서울시교육청 개포도서관", "서울시교육청 고덕평생학습관", "서울시교육청 고척도서관", "서울시교육청 구로도서관", "서울시교육청 남산도서관", "서울시교육청 노원평생학습관", "서울시교육청 도봉도서관", "서울시교육청 동대문도서관", "서울시교육청 동작도서관", "서울시교육청 마포평생아현분관", "서울시교육청 마포평생학습관", "서울시교육청 서대문도서관", "서울시교육청 송파도서관", "서울시교육청 양천도서관", "서울시교육청 어린이도서관", "서울시교육청 영등포평생학습관", "서울시교육청 용산도서관", "서울시교육청 정독도서관", "서울시교육청 종로도서관"};
    final String lib_id[]={"111003","111004","111005","111006","111007","111008","111009","111010","111022","111011","111012","111013","111031","111014","111016","111030","111015","111017","111018","111019","111020","111021"};
    final String lib_initial[]={"gnlib","gdlib","gslib","gplib","gdllc","gclib","grlib","nslib","nwllc","dblib","ddmlib","djlib","ahyon","sdmlib","splib","yclib","childlib","ydpllc","yslib","jdlib","jnlib"};

    Book books[];
    BookInfoParser bookParser;

    StringBuilder searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLibrarySearch();
    }

    public void initLibrarySearch(){

        books = new Book[10];
        bookParser = new BookInfoParser();

        ImageView = new ImageView[10];
        bookInfoView = new TextView[10];

        for(int i=0;i<10;i++) {
            books[i] = new Book(i + 1);
            String bookImageView = "bookImageView" + (i+1);
            String bookTextView = "bookTextView" + (i+1);

            int bookImageID = getResources().getIdentifier(bookImageView,"id",getPackageName());
            ImageView[i] = (ImageView)findViewById(bookImageID);

            int bookTextID = getResources().getIdentifier(bookTextView,"id",getPackageName());
        //    books[i].bookTextView = (TextView)findViewById(bookTextID);
            bookInfoView[i] = (TextView)findViewById(bookTextID);
        }

        searchResult = new StringBuilder();

        librarySpinner = (Spinner)findViewById(R.id.spinnerWhichLibrary);
        ArrayAdapter libraryAdapter = ArrayAdapter.createFromResource(this,R.array.which_library,android.R.layout.simple_spinner_item);
        librarySpinner.setAdapter(libraryAdapter);

        textView = (TextView)findViewById(R.id.textView);
        Typeface myfont = Typeface.createFromAsset(getAssets(),"fonts/Sunflower-Light.ttf");
        textView.setTypeface(myfont);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText editText = (EditText)findViewById(R.id.editText);
                String searchText = editText.getText().toString();
                Toast.makeText(getApplicationContext(),searchText+"을 검색하셨습니다..",Toast.LENGTH_LONG).show();

                int listIdx = librarySpinner.getSelectedItemPosition();

                String url = "http://"+ lib_initial[listIdx] +".sen.go.kr/" + lib_initial[listIdx] + "/intro/search/index.do?menu_idx=4&locExquery=" + lib_id[listIdx] + "&editMode=normal&officeNm=" + seoul_lib[listIdx] + "&lectureNm=&mainSearchType=on&search_text=";

                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute(url+searchText);

                try {
                    Thread.sleep(5000);

                    for(int i=0;i<10;i++) {
                        bookInfoView[i].setText(books[i].getBookWholeInfo());
                    }

                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }

        });


    }


    private class JsoupAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... htmlPage) {

            try{

                Document htmlSource = Jsoup.connect(htmlPage[0]).get();

                Elements contents1 = htmlSource.select(".author");
                Elements contents2 = htmlSource.select(".data");
                Elements contents3 = htmlSource.select(".site");
                Elements contents4 = htmlSource.select(".txt");
                Elements contents5 = htmlSource.select(".tit2");
                Elements contents6 = htmlSource.select(".thumb");

                String pageSource1 = contents1.toString(); // author publisher publishYear parsing source
                String pageSource2 = contents2.toString(); // ISBN bookDataType bookCallNumber parsing source
                String pageSource3 = contents3.toString(); // bookWhichLibrary bookWhereIs parsing source
                String pageSource4 = contents4.toString(); // bookCurrentCondition parsing source
                String pageSource5 = contents5.toString(); // bookName parsing source
                String pageSource6 = contents6.toString(); // bookImagePath parsing source

                for(int i=0;i<10;i++){

                    searchResult.setLength(0);

                    books[i].setBookName(bookParser.parseBookName(pageSource5,i));
                    searchResult.append(books[i].getBookName()+"\n");

                    books[i].setBookAuthor(bookParser.parseBookAuthor(pageSource1, i));
                    searchResult.append(books[i].getBookAuthor()+"\n");

                    books[i].setBookPublisher(bookParser.parseBookPublisher(pageSource1, i));
                    searchResult.append(books[i].getBookPublisher()+"\n");

                    books[i].setBookIssueYear(bookParser.parseBookIssueYear(pageSource1, i));
                    searchResult.append(books[i].getBookIssueYear()+"\n");

                    books[i].setBookISBN(bookParser.parseBookISBN(pageSource2,i));
                    searchResult.append(books[i].getBookISBN()+"\n");

                    books[i].setBookDataType(bookParser.parseBookDataType(pageSource2,i));
                    searchResult.append(books[i].getBookDataType()+"\n");

                    books[i].setBookCallNumber(bookParser.parseBookCallNumber(pageSource2,i));
                    searchResult.append(books[i].getBookCallNumber()+"\n");

                    books[i].setBookWhichLibrary(bookParser.parseBookWhichLibrary(pageSource3,i));
                    searchResult.append(books[i].getBookWhichLibrary()+"\n");

                    books[i].setBookWhereIs(bookParser.parseBookWhereIs(pageSource3,i));
                    searchResult.append(books[i].getBookWhereIs()+"\n");

                    books[i].setBookCurrentCondition(bookParser.parseBookCurrentCondition(pageSource4,i));
                    searchResult.append(books[i].getBookCurrentCondition()+"\n");

                    books[i].setBookImagePath(bookParser.parseBookImagePath(pageSource6,i));

                    books[i].setBookWholeInfo(searchResult.toString());

                 }

            }catch(IOException ex){
                ex.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer value) {
            super.onPostExecute(value);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }



}
