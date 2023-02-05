package crawl.historicalfigure;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class TestFilterCmt {

    public static void testCrawl() {
        try {
            Document doc = Jsoup.connect("https://nguoikesu.com/nhan-vat/ngo-nguyen-te").timeout(120000).get();

            Element fatherDiv = doc.selectFirst("div[class=com-content-article__body]");
            Element firstP = fatherDiv.selectFirst("p");
            System.out.println("Before filter: ");
            System.out.println(firstP.text());
            firstP.select("sup[class~=(annotation).*]").remove();
            System.out.println("\nAfter filter: ");
            System.out.println(firstP.text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        testCrawl();
    }
}
