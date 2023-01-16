package crawl.event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class crawlEvent {
    // Luu cac link de crawl
    private ArrayList<String> paginateLinks;

    // Lay link cua cac event
    private ArrayList<String> eventLinks;

    public ArrayList<String> getPaginateLinks() {
        return paginateLinks;
    }

    public ArrayList<String> getEventLinks() {
        return eventLinks;
    }

    // Lay tat ca cac link paginate de crawl
    public void getAllPaginateLinks() {
        // Trang dau tien cua phan su kien
        paginateLinks.add("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=");

        try {
            Document doc = Jsoup.connect("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=").get();

            // Lay ra cac the li trong ul cua phan paginate
            Elements paginateLi = doc.selectFirst("ul[class=pagination ms-0 mb-4]").children().select("li");

            // Neu co phan pagination
            if (paginateLi != null) {
                // Bo di dau mui ten va trang dau tien
                for (int i = 3; i <= paginateLi.size() - 3; ++i) {
                    String link = "https://nguoikesu.com" + paginateLi.get(i).select("a").attr("href");
                    System.out.println(link);
                    paginateLinks.add(link);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Lay tat ca cac link event de crawl
    public void getAllEventLinks() {
        for (int i = 0; i < paginateLinks.size(); ++i) {
            try {
                Document doc = Jsoup.connect(paginateLinks.get(i)).get();

                Elements pageHeader = doc.select("div[class=page-header]");

                for (Element e : pageHeader) {
                    String link = "https://nguoikesu.com" + e.selectFirst("a").attr("href");
                    System.out.println(link);
                    eventLinks.add(link);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Truy cap cac link event de lay du lieu
    public void getDataFromLink() {
        for (int i = 0; i < eventLinks.size(); ++i) {
            try {
                Document doc = Jsoup.connect(eventLinks.get(i)).get();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Tong hop
    public void crawlData() {
        getAllPaginateLinks();
        getAllEventLinks();
    }

    public crawlEvent() {
        this.paginateLinks = new ArrayList<>();
        this.eventLinks = new ArrayList<>();
        crawlData();
    }
}
