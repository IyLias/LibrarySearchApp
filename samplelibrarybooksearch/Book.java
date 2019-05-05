package org.techtown.samplelibrarybooksearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Book {

	int bookNum;
	
	String bookName; 
	
	String bookAuthor; 
	
	String bookPublisher; 
	
	String bookISBN; 
	
	String bookImagePath; 
	
	String bookIssueYear; 
	
	String bookWhichLibrary; 
	
	String bookDataType; 
	
	String bookCallNumber; 
	
	String bookWhereIs; 
	
	String bookCurrentCondition;

	String bookWholeInfo; // which stores whole datas above

	ImageView bookImageView; // which shows bookImage
	
	TextView bookTextView; // which shows book Info
	
	Book(int bookNum){
		this.bookNum = bookNum;
	}
	
	void setBookName(String bookName){
		this.bookName = bookName;
	}
	
	String getBookName(){
		return this.bookName;
	}
	
	void setBookAuthor(String bookAuthor){
		this.bookAuthor = bookAuthor;
	}
	
	String getBookAuthor(){
		return this.bookAuthor;
	}
	
	void setBookPublisher(String bookPublisher){
		this.bookPublisher = bookPublisher;
	}
	
	String getBookPublisher(){
		return this.bookPublisher;
	}
	
	void setBookISBN(String bookISBN){
		this.bookISBN = bookISBN;
	}
	
	String getBookISBN(){
		return this.bookISBN;
	}
	
	void setBookImagePath(String bookImagePath){
		this.bookImagePath = bookImagePath;
	}
	
	String getBookImagePath(){
		return this.bookImagePath;
	}
	
	void setBookIssueYear(String bookIssueYear){
		this.bookIssueYear = bookIssueYear;
	}
	
	String getBookIssueYear(){
		return this.bookIssueYear;
	}
	
	void setBookWhichLibrary(String bookWhichLibrary){
		this.bookWhichLibrary = bookWhichLibrary;
	}
	
	String getBookWhichLibrary(){
		return this.bookWhichLibrary;
	}
	
	void setBookDataType(String bookDataType){
		this.bookDataType = bookDataType;
	}
	
	String getBookDataType(){
		return this.bookDataType;
	}
	
	void setBookCallNumber(String bookCallNumber){
		this.bookCallNumber = bookCallNumber;
	}
	
	String getBookCallNumber(){
		return this.bookCallNumber;
	}
	
	void setBookWhereIs(String bookWhereIs){
		this.bookWhereIs = bookWhereIs;
	}
	
	String getBookWhereIs(){
		return this.bookWhereIs;
	}
	
	void setBookCurrentCondition(String bookCurrentCondition){
		this.bookCurrentCondition = bookCurrentCondition;
	}
	
	String getBookCurrentCondition(){
		return this.bookCurrentCondition;
	}

	void setBookWholeInfo(String bookData){
		bookWholeInfo = bookData;
	}

	String getBookWholeInfo(){
		return bookWholeInfo;
	}

	void showBookImage(){

		try {
			InputStream in = new java.net.URL(bookImagePath).openStream();
			Bitmap imageBitMap = BitmapFactory.decodeStream(in);
			bookImageView.setImageBitmap(imageBitMap);

		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

	void showBookInfo(){
		bookTextView.setText(bookWholeInfo);
	}
	
}
