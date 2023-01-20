package crawl.timestamp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class crawlTimeStamp {
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

    public crawlTimeStamp() {
        this.timeStampLinks = new ArrayList<>();
        this.timeStampChildLinks = new ArrayList<>();
        crawlData();
    }

    public void getAllTimeStampLinks() {
        System.out.println("Crawl link dong lich su: ");
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
            System.out.println("\nCrawl link tu dong lich su: " + item);
            try {
                // Dong lich su
                Document doc = Jsoup.connect(item).get();

                // Lay cac the dau muc - VD: Ky Bang Hong Thi
                Elements pageHeaders = doc.select("div[class=page-header]");

                System.out.println("Cac link con crawl duoc tu link hien tai: ");
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

                // Crawl du lieu cua dong lich su dang xet
                String name = "Chưa rõ";
                String time = "Chưa rõ";
                String location = "Chưa rõ";
                ArrayList<String> relatedChar = new ArrayList<>();

                // Lay ra ten cua trieu dai
//                Element

                // Lay ra doan chua thong tin cua trieu dai dang xet
                Element infoDiv = doc.selectFirst("div[class=category-desc clearfix]");
                for (Element e : infoDiv.children()) {
                    if (e.tagName().equals("p")) {

                    }
                }

                System.out.println("Thong tin trieu dai: ");
                System.out.println("Name: " + name);
                System.out.println("Time: " + time);
                System.out.println("Location: " + location);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public void getAllCharInfoLinks() {
//        System.out.println("\nCrawl link nhan vat tu cac link crawl duoc tu dong lich su: ");
//        for (String item : timeStampChildLinks) {
//            try {
//                // Truy cap cac link nhat dc tu dong lich su
//                Document doc = Jsoup.connect(item).get();
//
//                // Lay cac the dau muc - h3
//                Elements h3Tags = doc.select("h3");
//                if (h3Tags != null) {
//                    for (Element h3 : h3Tags) {
//                        // Neu the h3 co con la the a => co link
//                        Element h3ATag = h3.selectFirst("a");
//                        if (h3ATag != null) {
//                            String link = "https://nguoikesu.com" + h3ATag.attr("href");
//                            // Neu la link cua nhan vat lich su
//                            if (link.contains("nhan-vat")) {
//                                // Co the co truong hop lap lai trong qua trinh crawl
//                                if (!charInfoLinks.contains(link)) {
//                                    System.out.println(link);
//                                    charInfoLinks.add(link);
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    // Neu k co the h3 => tim xem co info box ko?
//                    // Neu k co infoBox thi out trang day coi nhu bo =))
//                    Element infoBox = doc.selectFirst("div[class=caption]");
//                    if (infoBox != null) {
//                        String link = "https://nguoikesu.com" + infoBox.selectFirst("h3")
//                                .selectFirst("a")
//                                .attr("href");
//                        if (link.contains("nhan-vat")) {
//                            if (!charInfoLinks.contains(link)) {
//                                System.out.println(link);
//                                charInfoLinks.add(link);
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void crawlFromWiki() {
        try {
            Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Vua_Việt_Nam").timeout(120000).get();

            // Lay ra content table
            Element contentDiv = doc.selectFirst("div[class=mw-parser-output]");
            Elements contentDivChild = contentDiv.children();

            // Lay ra bang wiki
            Element wikiTable = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(91)");
            Elements tableRows = wikiTable.select("tr");
            System.out.println("Number of table row: " + tableRows.size());

            for (int i = 0; i < contentDivChild.size(); ++i) {
                Element e = contentDivChild.get(i);
                if (e.tagName().equals("h2")) {
                    // The span chua ten dong lich su
                    Element titleSpan = e.selectFirst("span[class=mw-headline]");

                    if (titleSpan != null) {
                        String titleSpanContent = titleSpan.text();

                        Pattern p = Pattern.compile("(Thời|Bắc).*", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(titleSpanContent);

                        if (m.find()) {
                            String currentTimeStamp = titleSpanContent; // Dong thoi gian dang xet
//                            System.out.println("Current timestamp: " + currentTimeStamp);
                            String currentEra = ""; // Trieu dai dang xet
                            String founder = "Chưa rõ"; // Nguoi sang lap - vi vua dau tien?
                            String location = "Chưa rõ"; // Dia chi kinh do - neu co
                            String hometown = "Chưa rõ"; // Que huong - neu co
                            String eraTime = "Chưa rõ"; // Thoi gian cua trieu dai
                            ArrayList<String> kingList = new ArrayList<>();

                            while (!contentDivChild.get(i + 1).tagName().equals("h2")) {
                                i++;
                                Element currentElement = contentDivChild.get(i);
                                if (currentElement.tagName().equals("h3")) {
                                    Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
                                    if (eraNameElement != null) {
                                        String eraNameEleContent = eraNameElement.text();
                                        // Xem xem co dau ngoac nao khong
                                        int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();
//                                        System.out.println("Number of parentheses: " + countParen);
//                                        System.out.println("Current title: " + eraNameEleContent);
                                        // Khong ro nam nao?
                                        if (countParen == 0) {
                                            currentEra = eraNameEleContent;
                                        }
                                        // Chi co 1 moc tgian
                                        else if (countParen == 1) {
                                            int startParen = eraNameEleContent.indexOf("(");
                                            int endParen = eraNameEleContent.indexOf(")");
                                            currentEra = eraNameEleContent.substring(0, startParen - 1).trim();
                                            eraTime = eraNameEleContent.substring(startParen + 1, endParen).trim();
                                        }
                                        // Co 2 moc thoi gian => phai lay 2 cai
                                        else if (countParen == 2) {
                                            int startParenFirst = eraNameEleContent.indexOf("(");
                                            int endParenFirst = eraNameEleContent.indexOf(")");
                                            int startParenSec = eraNameEleContent.lastIndexOf("(");
                                            int endParenSec = eraNameEleContent.lastIndexOf(")");
                                            currentEra = eraNameEleContent.substring(0, startParenFirst - 1);
                                            String firstParenContent = eraNameEleContent.substring(startParenFirst + 1, endParenFirst);
                                            String secondParenContent = eraNameEleContent.substring(startParenSec + 1, endParenSec);
                                            String[] firstParenSplit = firstParenContent.split("–");
                                            String[] secParenSplit = secondParenContent.split("–");
                                            if (firstParenSplit.length < 2) {
                                                firstParenSplit = firstParenContent.split("-");
                                            }
                                            if (secParenSplit.length < 2) {
                                                secParenSplit = secondParenContent.split("-");
                                            }
                                            eraTime = firstParenSplit[0].trim() + " - " + secParenSplit[1].trim();
                                        }
                                    }
                                } else if (currentElement.tagName().equals("table")) {
                                    if (
                                            currentElement.attr("cellpadding").equals("2") ||
                                            (currentElement.hasClass("wikitable") &&
                                                    !currentElement.hasAttr("cellpadding"))
                                    ) {
                                        Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
                                        if (eraNameElement != null) {
                                            String eraNameEleContent = eraNameElement.text();
                                            // Xem xem co dau ngoac nao khong
                                            int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();
//                                        System.out.println("Number of parentheses: " + countParen);
//                                            System.out.println("Current title: " + eraNameEleContent);
                                            // Khong ro nam nao?
                                            if (countParen == 0) {
                                                currentEra = eraNameEleContent;
                                            }
                                            // Chi co 1 moc tgian
                                            else if (countParen == 1) {
                                                int startParen = eraNameEleContent.indexOf("(");
                                                int endParen = eraNameEleContent.indexOf(")");
                                                currentEra = eraNameEleContent.substring(0, startParen - 1).trim();
                                                eraTime = eraNameEleContent.substring(startParen + 1, endParen).trim();
                                            }
                                            // Co 2 moc thoi gian => phai lay 2 cai
                                            else if (countParen == 2) {
                                                int startParenFirst = eraNameEleContent.indexOf("(");
                                                int endParenFirst = eraNameEleContent.indexOf(")");
                                                int startParenSec = eraNameEleContent.lastIndexOf("(");
                                                int endParenSec = eraNameEleContent.lastIndexOf(")");
                                                currentEra = eraNameEleContent.substring(0, startParenFirst - 1);
                                                String firstParenContent = eraNameEleContent.substring(startParenFirst + 1, endParenFirst);
                                                String secondParenContent = eraNameEleContent.substring(startParenSec + 1, endParenSec);
                                                String[] firstParenSplit = firstParenContent.split("–");
                                                String[] secParenSplit = secondParenContent.split("–");
                                                if (firstParenSplit.length < 2) {
                                                    firstParenSplit = firstParenContent.split("-");
                                                }
                                                if (secParenSplit.length < 2) {
                                                    secParenSplit = secondParenContent.split("-");
                                                }
                                                eraTime = firstParenSplit[0].trim() + " - " + secParenSplit[1].trim();
                                            }
                                        }
                                    } else {
                                        // #mw-content-text > div.mw-parser-output > table:nth-child(17) > tbody > tr:nth-child(2) > td:nth-child(2)
                                        // #mw-content-text > div.mw-parser-output > table:nth-child(17) > tbody > tr:nth-child(3) > td:nth-child(2)
                                        // Cac vi vua cua trieu dai dang xet
                                        Elements tableDatas = currentElement.select("tbody > tr > td:nth-child(2)");
                                        if (tableDatas != null) {
                                            for (Element td : tableDatas) {
                                                Element tdATag = td.selectFirst("a");
                                                if (tdATag != null) {
                                                    String kingName = tdATag.text();
//                                                    System.out.println("King name: " + kingName);
                                                    kingList.add(kingName);
                                                }
                                            }

                                            // Lay cac thong tin nhu nguoi sang lap - 2, que huong - 3, kinh do - 4
                                            for (int j = 1; j < tableRows.size(); ++j) {
                                                // Gia tri cua the td dau tien la ten cua trieu dai
                                                Element firstTd = tableRows.get(j).selectFirst("td");
                                                if (firstTd != null) {
                                                    String eraValue = firstTd.text();
//                                                    System.out.println("Current era: " + eraValue);
                                                    if (!currentEra.equals("")) {
                                                        if (eraValue.contains(currentEra)) {
                                                            Elements currentRowTds = tableRows.get(j).select("td");
                                                            if (currentRowTds != null) {
                                                                founder = currentRowTds.get(1).text();
                                                                hometown = currentRowTds.get(2).text();
                                                                location = currentRowTds.get(3).text();
                                                            }
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            System.out.println("\nEra name: " + currentEra);
                                            System.out.println("Belongs to timestamp: " + currentTimeStamp);
                                            System.out.println("Hometown: " + hometown);
                                            System.out.println("Founder: " + founder);
                                            System.out.println("Location: " + location);
                                            System.out.println("Time: " + eraTime);
                                            System.out.print("List of kings: [");
                                            if (kingList.size() > 0) {
                                                for (int t = 0; t < kingList.size(); ++t) {
                                                    if (t < kingList.size() - 1) {
                                                        System.out.print(kingList.get(t) + ", ");
                                                    } else System.out.print(kingList.get(t) + "]\n");
                                                }
                                            } else {
                                                System.out.print("]\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void crawlData() {
//        getAllTimeStampLinks();
//        getAllChildLinkFromTimeStamp();
//        getAllCharInfoLinks();
        crawlFromWiki();
    }
}
