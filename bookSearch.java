import java.net.URL;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BookSearch {
	
	static final String seoul_lib[]={"서울시교육청 강남도서관", "서울시교육청 강동도서관" ,"서울시교육청 강서도서관","서울시교육청 개포도서관", "서울시교육청 고덕평생학습관", "서울시교육청 고척도서관", "서울시교육청 구로도서관", "서울시교육청 남산도서관", "서울시교육청 노원평생학습관", "서울시교육청 도봉도서관", "서울시교육청 동대문도서관", "서울시교육청 동작도서관", "서울시교육청 마포평생아현분관", "서울시교육청 마포평생학습관", "서울시교육청 서대문도서관", "서울시교육청 송파도서관", "서울시교육청 양천도서관", "서울시교육청 어린이도서관", "서울시교육청 영등포평생학습관", "서울시교육청 용산도서관", "서울시교육청 정독도서관", "서울시교육청 종로도서관"};
	final String lib_id[]={"111003","111004","111005","111006","111007","111008","111009","111010","111022","111011","111012","111013","111031","111014","111016","111030","111015","111017","111018","111019","111020","111021"};
	final String lib_initial[]={"gnlib","gdlib","gslib","gplib","gdllc","gclib","grlib","nslib","nwllc","dblib","ddmlib","djlib","ahyon","sdmlib","splib","yclib","childlib","ydpllc","yslib","jdlib","jnlib"};
		
	String srch_sce;
	Book books[];
	BookInfoParser bookParser;
	
	public BookSearch(){
		
			books = new Book[10];
			bookParser = new BookInfoParser();
			
			searchButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
					try{
						
						if(e.getSource() == searchButton ){
					    	
							int listIdx=List.getSelectedIndex();
							String url = "http://"+ lib_initial[listIdx] +".sen.go.kr/" + lib_initial[listIdx] + "/intro/search/index.do?menu_idx=4&locExquery=" + lib_id[listIdx] + "&editMode=normal&officeNm=" + seoul_lib[listIdx] + "&lectureNm=&mainSearchType=on&search_text=";
						
							String query = textFieldForSearch.getText();
							Document qry_doc = Jsoup.connect(url+query).get();
							srch_sce = qry_doc.text().toString();
					    
							if(srch_sce.contains("찾으시는 자료가 없습니다") == false){
					    	
								Elements contents1 = qry_doc.select(".author");
								Elements contents2 = qry_doc.select(".data");
								Elements contents3 = qry_doc.select(".site");
								Elements contents4 = qry_doc.select(".txt");
								Elements contents5 = qry_doc.select(".tit2");
								Elements contents6 = qry_doc.select(".thumb");
					    
								String pageSource1 = contents1.toString(); //저자 발행처 발행연도 관련 파싱소스
								String pageSource2 = contents2.toString(); // ISBN 자료형태 청구기호 관련 파싱소스
								String pageSource3 = contents3.toString(); // 도서관위치 자료실위치 관련 파싱소스
								String pageSource4 = contents4.toString(); // 자료상태 관련 파싱소스
								String pageSource5 = contents5.toString(); // 책 이름 관련 파싱소스
								String pageSource6 = contents6.toString(); // 책 사진 이미지 경로 관련 파싱소스
								
								for(int i=0;i<10;i++){
									books[i] = new Book(i+1);
								}
					    
								for(int i=0;i<10;i++){
									books[i].setBookAuthor(bookParser.parseBookAuthor(pageSource1, i));
									books[i].setBookPublisher(bookParser.parseBookPublisher(pageSource1, i));
									books[i].setBookIssueYear(bookParser.parseBookIssueYear(pageSource1, i));
									books[i].setBookISBN(bookParser.parseBookISBN(pageSource2,i));
									books[i].setBookDataType(bookParser.parseBookDataType(pageSource2,i));
									books[i].setBookCallNumber(bookParser.parseBookCallNumber(pageSource2,i));
									books[i].setBookWhichLibrary(bookParser.parseBookWhichLibrary(pageSource3,i));
									books[i].setBookWhereIs(bookParser.parseBookWhereIs(pageSource3,i));
									books[i].setBookCurrentCondition(bookParser.parseBookCurrentCondition(pageSource4,i));
									books[i].setBookName(bookParser.parseBookName(pageSource5,i));
									books[i].setBookImagePath(bookParser.parseBookImagePath(pageSource6,i));
									
									books[i].setLabelTexts();
									addBookDatas(books[i]);
									
								}	
					   
								for(int i=0;i<10;i++){
									System.out.println(books[i].getBookAuthor());
									System.out.println(books[i].getBookPublisher());
									System.out.println(books[i].getBookIssueYear());
									System.out.println(books[i].getBookISBN());
									System.out.println(books[i].getBookDataType());
									System.out.println(books[i].getBookCallNumber());
									System.out.println(books[i].getBookWhichLibrary());
									System.out.println(books[i].getBookWhereIs());
									System.out.println(books[i].getBookCurrentCondition());
									System.out.println(books[i].getBookName());
									System.out.println(books[i].getBookImagePath());
									
									System.out.println("\n");
								}
					   
								searchResultFrame.setSize(800,1000);
								searchResultFrame.setLayout(null);
								searchResultFrame.setVisible(true);
								
							} // if contains statements
							else{
								
								// 찾는 자료가 없을 때 화면에 찾는 자료가 없다고 출력
								System.out.println("찾는 자료가 없어 다시 검색해봐");
							
							}
							 
					    	
						} // if searchButton statements
						
					}catch(Exception ex){
						ex.getStackTrace();
						System.out.println("Error while you search");
					}
				
				} //actionPerformed
			});
		
	}
		
	public static void main(String[] args) {
		
		new BookSearch();
		
	}

}