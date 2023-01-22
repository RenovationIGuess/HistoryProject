package crawl.festival;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class crawlFestival {
    private static Integer nbFestival;
//    private static HashMap<Integer,String> Name ;//= new ArrayList<String>();
//    private static HashMap<Integer,String> Location ;//= new ArrayList<String>();
//    private static HashMap<Integer,String> Day;
//    //private static HashMap<Integer,String> RelatedSite;
//    //private static HashMap<Integer,String> RelatedEvent;
//    private static HashMap<Integer,String> RelatedFigure;
//    private static HashMap<Integer,String> FirstTime;
//    private static HashMap<Integer,String> Note;

    public crawlFestival(){
        crawl();
    }

    public static void crawl() {
        String url_lehoi = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
        // call scraping
        HashMap<Integer, String> Name  = new HashMap<Integer,String>();
        HashMap<Integer, String> Location  = new HashMap<Integer,String>();
        HashMap<Integer, String> Day  = new HashMap<Integer,String>();
        HashMap<Integer, String> FirstTime  = new HashMap<Integer,String>();
        HashMap<Integer, String> RelatedFigure  = new HashMap<Integer,String>();
        HashMap<Integer, String> Note  = new HashMap<Integer,String>();

        try{
            final Document document = Jsoup.connect(url_lehoi).get();
            int i =1;
            for(Element row : document.select("table.prettytable.wikitable tr")) {
                final String day = row.select("td:nth-of-type(1)").text();
                final String location = row.select("td:nth-of-type(2)").text();
                final String name = row.select("td:nth-of-type(3)").text();
                final String firstTime = row.select("td:nth-of-type(4)").text();
                final String relatedFigure = row.select("td:nth-of-type(5)").text();
                final String note = row.select("td:nth-of-type(6)").text();

                addHashmap(i, Name, name);
                addHashmap(i, Location, location);
                addHashmap(i, Day, day);
                addHashmap(i, FirstTime, firstTime);
                addHashmap(i, RelatedFigure, relatedFigure);
                addHashmap(i, Note, note);
                nbFestival = i;
                i++;
                System.out.println("id: "+ nbFestival);
                System.out.println("Name: "+ Name.get(nbFestival));
                System.out.println("Location: "+ Location.get(nbFestival));
                System.out.println("Day: "+ Day.get(nbFestival));
                System.out.println("Related Figure: " + RelatedFigure.get(nbFestival));
                System.out.println("First time: "+FirstTime.get(nbFestival));
                System.out.println("Note: "+ Note.get(nbFestival));
                System.out.println("------");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void addHashmap(int i,HashMap<Integer,String> list,String text){
        if(text.equals("")){
            list.put(i,"None");
        }else{
            list.put(i,text);
        }
    }

    public static void show(HashMap<Integer, String> hashMap) {
        Set<Integer> keySet = hashMap.keySet();
        for (Integer key : keySet) {
            System.out.println(key + " - " + hashMap.get(key));
        }
    }
}


