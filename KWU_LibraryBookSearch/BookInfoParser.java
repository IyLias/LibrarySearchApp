package org.techtown.http.kwangwoon_library_search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BookInfoParser {

    String stringReplaceBraces(String targetString,char startChar,char endChar){

        int startIndex = targetString.indexOf(startChar);
        int endIndex = targetString.indexOf(endChar);

        String tempString = targetString.substring(startIndex,endIndex+1);
        String resultString = targetString.replace(tempString,"");

        return resultString;
    }

    String stringParse(String targetString,int startIndex,int endIndex){

        String resultString = targetString.substring(startIndex+1,endIndex);

        return resultString;
    }


    String parseBookAuthor(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[5*bookNum + 1] = results[5*bookNum + 1].replace("<span>","");
        results[5*bookNum + 1] = results[5*bookNum + 1].replace("</span>","");

        if(results[5*bookNum + 1].contains("<span style") == true){
            results[5*bookNum + 1] = stringReplaceBraces(results[5*bookNum + 1],'<','>');
        }

        return results[5*bookNum + 1];
    }

    String parseBookPublisher(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[5*bookNum + 2] = results[5*bookNum + 2].replace("<span>","");
        results[5*bookNum + 2] = results[5*bookNum + 2].replace("</span>","");

        if(results[5*bookNum + 2].contains("<span style") == true){
            results[5*bookNum + 2] = stringReplaceBraces(results[5*bookNum + 2],'<','>');
        }

        return results[5*bookNum + 2];
    }

    String parseBookIssueYear(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[5*bookNum + 3] = results[5*bookNum + 3].replace("<span>","");
        results[5*bookNum + 3] = results[5*bookNum + 3].replace("</span>","");

        return results[5*bookNum + 3];
    }

    String parseBookISBN(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[5*bookNum + 1] = results[5*bookNum + 1].replace("<span>","");
        results[5*bookNum + 1] = results[5*bookNum + 1].replace("</span>","");

        return results[5*bookNum + 1];
    }

    String parseBookDataType(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[5*bookNum + 2] = results[5*bookNum + 2].replace("<span>","");
        results[5*bookNum + 2] = results[5*bookNum + 2].replace("</span>","");

        return results[5*bookNum + 2];
    }


    String parseBookCallNumber(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[5*bookNum + 3] = results[5*bookNum + 3].replace("<span>","");
        results[5*bookNum + 3] = results[5*bookNum + 3].replace("</span>","");

        return results[5*bookNum + 3];
    }

    String parseBookWhichLibrary(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[4*bookNum + 1] = results[4*bookNum + 1].replace("<span>","");
        results[4*bookNum + 1] = results[4*bookNum + 1].replace("</span>","");

        return results[4*bookNum + 1];
    }

    String parseBookWhereIs(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[4*bookNum + 2] = results[4*bookNum + 2].replace("<span>","");
        results[4*bookNum + 2] = results[4*bookNum + 2].replace("</span>","");

        return results[4*bookNum + 2];
    }

    String parseBookCurrentCondition(String pageSource,int bookNum){

        String results[] = pageSource.split("\n");
        results[bookNum] = results[bookNum].replace("<p class=\"txt\">","");
        results[bookNum] = results[bookNum].replace("</p>","");

        return results[bookNum];
    }

    String parseBookName(String pageSource , int bookNum){

        String results[] = pageSource.split("\n");

        results[4*bookNum + 2] = stringReplaceBraces(results[4*bookNum + 2],'<','>');

        if(results[4*bookNum + 2].contains("<span ") == true){
            results[4*bookNum + 2] = stringReplaceBraces(results[4*bookNum + 2],'<','>');
            results[4*bookNum + 2] = results[4*bookNum + 2].replace("</span>","");
        }

        results[4*bookNum + 2] = results[4*bookNum + 2].replace("</a>","");

        return results[4*bookNum + 2];
    }

    String parseBookImagePath(String pageSource, int bookNum){

        String results[] = pageSource.split("\n");

        results[3*bookNum + 1]= stringReplaceBraces(results[3*bookNum + 1],'<','>');
        int startIndex = results[3*bookNum + 1].indexOf('\"');
        int quoteIndex = 0;
        for(int i=results[3*bookNum + 1].indexOf('\"')+1;results[3*bookNum + 1].charAt(i)!='\"';i++ ){
            if(results[3*bookNum + 1].charAt(i+1)=='\"'){
                quoteIndex=i+1;
            }
        }

        results[3*bookNum + 1] = stringParse(results[3*bookNum + 1],startIndex,quoteIndex);

        return results[3*bookNum + 1];
    }

}
