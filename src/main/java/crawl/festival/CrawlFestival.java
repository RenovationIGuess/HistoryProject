package crawl.festival;

import crawl.Crawl;
import history.model.Festival;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlFestival extends Crawl {
//    private static Integer nbFestival;

    public CrawlFestival(){
        crawlData();
    }

    public static void crawlData() {
        String url_lehoi = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
        // call scraping
//        HashMap<Integer, String> Name  = new HashMap<Integer,String>();
//        HashMap<Integer, String> Location  = new HashMap<Integer,String>();
//        HashMap<Integer, String> Day  = new HashMap<Integer,String>();
//        HashMap<Integer, String> FirstTime  = new HashMap<Integer,String>();
//        HashMap<Integer, String> RelatedFigure  = new HashMap<Integer,String>();
//        HashMap<Integer, String> Note  = new HashMap<Integer,String>();

        try {
            final Document document = Jsoup.connect(url_lehoi).get();
            int i = 1;
            for (Element row : document.select("table.prettytable.wikitable tr")) {
                String day = row.select("td:nth-of-type(1)").text();
                String location = row.select("td:nth-of-type(2)").text();
                String name = row.select("td:nth-of-type(3)").text();
                String firstTime = row.select("td:nth-of-type(4)").text();
                String relatedFigure = row.select("td:nth-of-type(5)").text();
                String note = row.select("td:nth-of-type(6)").text();

                // Truong hop cot dau tien
                if (name.equals("")) continue;

                String[] splittedChars = relatedFigure.split(", ");
                ArrayList<String> relateChars = new ArrayList<>();
                for (String relateChar : splittedChars) {
                    relateChars.add(relateChar);
                }

//                addHashmap(i, Name, name);
//                addHashmap(i, Location, location);
//                addHashmap(i, Day, day);
//                addHashmap(i, FirstTime, firstTime);
//                addHashmap(i, RelatedFigure, relatedFigure);
//                addHashmap(i, Note, note);
//                nbFestival = i;
//                i++;

                Element nameLink = row.select("td:nth-of-type(3)").select("a").first();
                if (nameLink != null) {
                    if (nameLink.text().equalsIgnoreCase(name)) {
                        String link = "https://vi.wikipedia.org" + nameLink.attr("href");

                        try {
                            Document doc = Jsoup.connect(link).timeout(120000).get();
                            Element detailDiv = doc.selectFirst("#mw-content-text > div.mw-parser-output");

                            if (detailDiv != null) {
                                // Lay ra doan van dau tien
                                Element infoP = detailDiv.selectFirst("p");
                                infoP.select("sup").remove();
                                String firstPContent = infoP.text();

                                // Loc thong tin tu doan van ban dau tien
                                // Lay mo ta ngan gon cua di tich
                                // Loai bo doan trong ngoac dau tien cho chac
                                int firstOpenParen = firstPContent.indexOf("(");
                                int firstCloseParen = firstPContent.indexOf(")");
                                if (firstOpenParen != -1 && firstCloseParen != -1) {
                                    String firstParen = firstPContent.substring(firstOpenParen + 1, firstCloseParen);
                                    if (firstParen.contains("là")) {
                                        firstPContent = firstPContent.substring(firstCloseParen + 1);
                                    }
                                }

                                boolean outLoop = true;
                                while (outLoop) {
                                    // kiem tra sau chu "la" la gi?
                                    int start = firstPContent.indexOf("là"); // tra ve vi tri chu l cua "la" dau tien tim thay
                                    if (start != -1 && start < firstPContent.length() - 3) {
                                        // Neu la chu in hoa || hoac la con, lang? ... => bo TH chu la nay di :v
                                        if (
                                                Character.isUpperCase(firstPContent.charAt(start + 3)) ||
                                                        firstPContent.charAt(start + 2) != ' '
                                        ) {
                                            // Con truong hop la mot
                                            // Truong hop chu lam
                                            if (firstPContent.charAt(start + 2) == 'm' && firstPContent.charAt(start + 3) == ' ') {
                                                outLoop = false;
                                            } else firstPContent = firstPContent.substring(start + 3);
                                        } else outLoop = false;
                                    } else outLoop = false;
                                }

                                Pattern p = Pattern.compile("(là|bao gồm|nơi)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                                Matcher m = p.matcher(firstPContent);

                                if (m.find()) {
                                    String result = m.group(0);
                                    if (!result.contains(":") && !result.equals("")) {
                                        note = result.substring(0, result.length() - 1);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

//                System.out.println("id: "+ nbFestival);
                System.out.println("Name: "+ name);
                System.out.println("Location: "+ location);
                System.out.println("Day: "+ day);
                System.out.println("Related Figure: " + relatedFigure);
                System.out.println("First time: " + firstTime);
                System.out.println("Note: " + note);
                System.out.println("------");

                // Tao object moi
                new Festival(
                    name,
                    location,
                    day,
                    note,
                    relateChars,
                    firstTime
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public static void addHashmap(int i,HashMap<Integer,String> list,String text) {
//        if (text.equals("")) {
//            list.put(i,"Chưa rõ");
//        } else {
//            list.put(i,text);
//        }
//    }

    public static void show(HashMap<Integer, String> hashMap) {
        Set<Integer> keySet = hashMap.keySet();
        for (Integer key : keySet) {
            System.out.println(key + " - " + hashMap.get(key));
        }
    }
}


