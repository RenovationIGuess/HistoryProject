package crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class crawlCharacter {
    // Link dong lich su
    private static ArrayList<String> timeStampLinks;

    // Link con trong trang dong lich su
    private static ArrayList<String> timeStampChildLinks;

    public static ArrayList<String> getTimeStampLinks() {
        return timeStampLinks;
    }

    public static ArrayList<String> getTimeStampChildLinks() {
        return timeStampChildLinks;
    }

    public crawlCharacter() {
        this.timeStampLinks = new ArrayList<>();
        this.timeStampChildLinks = new ArrayList<>();
        crawlData();
    }

    public void getAllTimeStampLinks() {
        try {
            Document doc = Jsoup.connect("https://nguoikesu.com").get();

            // Lay cac the li gom cac dong lich su
            Elements timeStampList = doc
                    .selectFirst("ul[class=mod-articlescategories categories-module mod-list]")
                    .children()
                    .select("li");

            // Duyet qua va lay link
            for (Element item : timeStampList) {
                String link = "https://nguoikesu.com" + item.selectFirst("a").attr("href");
                System.out.println(link);
                timeStampLinks.add(link);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllChildLinkFromTimeStamp() {
        for (String item : timeStampLinks) {
            try {
                Document doc = Jsoup.connect(item).get();

                // Lay cac the dau muc - VD: Ky Bang Hong Thi
                Elements pageHeaders = doc.select("div[class=page-header]");

                for (Element header : pageHeaders) {
                    Element headerATag = header.selectFirst("a");
                    if (headerATag != null) {
                        String link = "https://nguoikesu.com" + headerATag.attr("href");
                        System.out.println(link);
                        timeStampChildLinks.add(link);
                    }
                }

                // Kiem tra xem co pagination khong?
                Element paginationUl = doc.selectFirst("ul[class=pagination ms-0 mb-4]");
                if (paginationUl != null) {
                    Elements paginationItems = paginationUl.select("li");
                    for (int i = 3; i <= paginationItems.size() - 3; ++i) {
                        String link = "https://nguoikesu.com" + paginationItems.get(i).selectFirst("a").attr("href");
                        System.out.println(link);
                        try {
                            Document pagiDoc = Jsoup.connect(link).get();

                            // Lay cac the dau muc - VD: Ky Bang Hong Thi
                            Elements pagiPageHeaders = pagiDoc.select("div[class=page-header]");

                            for (Element header : pagiPageHeaders) {
                                Element headerATag = header.selectFirst("a");
                                if (headerATag != null) {
                                    String pagiLink = "https://nguoikesu.com" + headerATag.attr("href");
                                    System.out.println(pagiLink);
                                    timeStampChildLinks.add(pagiLink);
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void crawlData() {
        getAllTimeStampLinks();
        getAllChildLinkFromTimeStamp();
    }
}
