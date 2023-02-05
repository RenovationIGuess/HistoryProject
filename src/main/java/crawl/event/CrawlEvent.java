package crawl.event;

import crawl.Crawl;
import history.model.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlEvent extends Crawl {
    // Array chứa link của các event
    private static ArrayList<String> eventLinks = new ArrayList<>();

    /**
     * Lấy link của các sự kiện từ phần pagination
     */
    public static void getAllEventLinks() {
        System.out.println("Lay cac link cua phan pagination: ");

        try {
            Document doc = Jsoup.connect("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=").timeout(120000).get();

            // p tag nay cho biet co tong bnh trang trong pagination
            Element pTag = doc
                    .selectFirst("p[class=com-content-category-blog__counter counter float-end pt-3 pe-2]");
            String[] pTagContentArray = pTag.text().split(" ");
            int pTagContentArrSize = pTagContentArray.length;
            int numberOfPagination = Integer.parseInt(pTagContentArray[pTagContentArrSize - 1]);
            System.out.println("Total number of paginate page: " + numberOfPagination);

            // Lay link tu trang dau tien
            Elements pageHeader = doc.select("div[class=page-header]");

            for (Element e : pageHeader) {
                Element eATag = e.selectFirst("a");
                if (eATag != null) {
                    String link = "https://nguoikesu.com" + eATag.attr("href");

                    if (!eventLinks.contains(link)) {
                        System.out.println(e.text() + " - " + link);
                        eventLinks.add(link);
                    }
                }
            }

            // 1 trang co <= 5 su kien
            // Format link: "https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=&start=..." => query
            for (int i = 2; i <= numberOfPagination; ++i) {
                System.out.println("\nCurrent page: " + i);
                String link = "https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=&start=" + String.valueOf((i - 1) * 5);

                try {
                    Document pagiDoc = Jsoup.connect(link).timeout(120000).get();

                    // Lay tu trang thu 2 den het
                    Elements pagiPageHeaders = pagiDoc.select("div[class=page-header]");

                    for (Element pagiPageHeader : pagiPageHeaders) {
                        Element pagiPHATag = pagiPageHeader.selectFirst("a");

                        if (pagiPHATag != null) {
                            String pagiLink = "https://nguoikesu.com" + pagiPHATag.attr("href");

                            // Neu link nay chua co trong mang => tranh TH lap
                            if (!eventLinks.contains(pagiLink)) {
                                System.out.println(pagiPageHeader.text() + " - " + pagiLink);
                                eventLinks.add(pagiLink);
                            }
                        }
                    }
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crawl dữ liệu
     * @param link link của sự kiện tương ứng
     */
    public static void crawlEventData(String link) {
        System.out.println("\nDang crawl su kien o link: " + link);

        try {
            Document doc = Jsoup.connect(link).timeout(120000).get();

            String name = "Chưa rõ"; // Ten su kien
            String time = "Chưa rõ"; // Thoi gian dien ra su kien
            String location = "Chưa rõ"; // Dia diem dien ra su kien
            String reason = "Chưa rõ"; // Nguyen nhan dien ra su kien
            String description = "Chưa rõ"; // Mo ta ngan gon ve tran chien
            String result = "Chưa rõ"; // Ket qua cua su kien
            ArrayList<String> relatedChar = new ArrayList<>();

            // Lay ten cua su kien
            // Lay tu the div dau tien truoc
            Element firstPageHeader = doc.selectFirst("div[class=page-header]");
            if (firstPageHeader != null) {
                firstPageHeader.select("sup").remove();
                if (name.equals("Chưa rõ")) name = firstPageHeader.text();
            }

            // Tên từ thẻ đầu tiên trong info box
            Element eventNameTag = doc
                    .selectFirst("#content > div.com-content-article.item-page > div.com-content-article__body > div.infobox > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(1) > td > span");
            if (eventNameTag != null) {
                eventNameTag.select("sup").remove();
                if (name.equals("Chưa rõ")) name = eventNameTag.text();
            }

            // Lay ra div cua info Box -> lay nhan vat lien quan
            // Lấy ra các thẻ a có trong infobox, nếu link là /nhan-vat và không chứa nha-
            Element infoBoxDiv = doc.selectFirst("div[class^=infobox]");
            if (infoBoxDiv != null) {
                Elements aTagsInInfoBox = infoBoxDiv.select("a");
                if (aTagsInInfoBox != null) {
                    for (Element a : aTagsInInfoBox) {
//                        a.select("sup").remove();
                        String hrefValue = a.attr("href");
                        if (hrefValue.contains("/nhan-vat") && !hrefValue.contains("nha-")) {
                            String aTagValue = a.text();
                            if (!relatedChar.contains(aTagValue)) {
                                relatedChar.add(aTagValue);
                            }
                        }
                    }
                }
            }

            // Lay ra bang thong tin gom thoi gian dia diem ket qua
            Element infoTable = doc
                    .selectFirst("table[cellpadding=0]");

            if (infoTable != null) {
                Elements infoTableRows = infoTable.select("tr");
//                System.out.println("Number of tr: " + infoTableRows.size());
                for (Element tr : infoTableRows) {
//                    tr.select("sup").remove();
                    Elements tableDatas = tr.select("td");
//                    System.out.println("Number of td: " + tableDatas.size());

                    if (tableDatas != null) {
                        if (tableDatas.size() > 1) {
                            tableDatas.get(0).select("sup").remove();
                            tableDatas.get(1).select("sup").remove();
                            String title = tableDatas.get(0).text();
//                            System.out.println(title);
                            if (title.equals("Thời gian") && time.equals("Chưa rõ")) {
                                time = tableDatas.get(1).text();
                            } else if (title.equals("Địa điểm") && location.equals("Chưa rõ")) {
                                location = tableDatas.get(1).text();
                            } else if (title.equals("Kết quả") && result.equals("Chưa rõ")) {
                                result = tableDatas.get(1).text();
                            } else if (title.contains("Nguyên nhân") && reason.equals("Chưa rõ")) {
                                reason = tableDatas.get(1).text();
                            }
                        }
                    }
                }
            }

            // Loc tu text
            Element contentBody = doc.selectFirst("div[class=com-content-article__body]");
            Elements contentBodyELements = contentBody.children();
            boolean firstPFound = false;
            // Loc tu doan p dau tien, co the thieu thong tin
            for (Element item : contentBodyELements) {
                if (item.tagName().equals("p")) {
                    if (!firstPFound) {
                        firstPFound = true;
                        Element firstParagraph = item;
                        firstParagraph.select("sup").remove(); // [class~=(annotation).*]
                        String firstPContent = firstParagraph.text();

                        // The b dau tien la ten cua su kien, co the gom ca thoi gian
                        Element firstBTag = firstParagraph.selectFirst("b");
                        if (firstBTag != null) {
                            String firstBTagContent = firstBTag.text();
                            String[] splitArray = firstBTagContent.split(",");
                            if (splitArray.length == 1) {
                                if (name.equals("Chưa rõ")) name = splitArray[0].trim();
                            } else if (splitArray.length > 1) {
                                if (name.equals("Chưa rõ")) name = splitArray[0].trim();
                                if (time.equals("Chưa rõ")) time = splitArray[1].trim();
                            }
                        }

                        // Loc ra mo ta ngan gon cua tran chien
                        Pattern p = Pattern.compile("là[^.]*[.]");
                        Matcher m = p.matcher(firstPContent);

                        if (m.find()) {
                            String findResult = m.group(0);
                            if (description.equals("Chưa rõ")) {
                                description = findResult.substring(0, findResult.length() - 1);
                            }
                        }

                        // Loc ra ket qua cua su kien
                        p = Pattern.compile("(Kết quả|cuối cùng)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                            String findResult = m.group(0);
                            if (result.equals("Chưa rõ")) {
                                result = findResult.substring(0, findResult.length() - 1);
                            }
                        }

                        // Loc ra thoi gian cua su kien
                        p = Pattern.compile("(xảy ra|diễn ra) (từ|vào)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                            String findResult = m.group(0);
                            if (time.equals("Chưa rõ")) {
                                time = findResult.substring(0, findResult.length() - 1);
                            }
                        }

                        // Loc ra nguyen nhan cua tran chien
                        p = Pattern.compile("(nhằm|bắt nguồn|do)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                            String findResult = m.group(0);
                            if (reason.equals("Chưa rõ")) {
                                reason = findResult.substring(0, findResult.length() - 1);
                            }
                        }
                    }

                    // Lay ra cac thẻ a tim duoc trong p
                    Elements pATags = item.select("a");
                    for (Element a : pATags) {
                        String hrefValue = a.attr("href");
                        if (hrefValue.contains("/nhan-vat") && !hrefValue.contains("nha-")) {
                            String aTagValue = a.text();
                            if (!relatedChar.contains(aTagValue)) {
                                relatedChar.add(aTagValue);
                            }
                        }
                    }
                }
            }

            System.out.println("Name: " + name);
            System.out.println("Date: " + time);
            System.out.println("Location: " + location);
            System.out.println("Result: " + result);
            System.out.println("Reason: " + reason);
            System.out.println("Description: " + description);
            System.out.print("Related characters: [");
            if (relatedChar.size() > 0) {
                for (int i = 0; i < relatedChar.size(); ++i) {
                    if (i < relatedChar.size() - 1) {
                        System.out.print(relatedChar.get(i) + ", ");
                    } else System.out.print(relatedChar.get(i) + "]\n");
                }
            } else {
                System.out.print("]\n");
            }

            // Tao object su kien
            new Event(
                name,
                time,
                location,
                result,
                reason,
                description,
                relatedChar
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Crawling
    public static void getDataFromLink() {
        for (String link : eventLinks) {
            crawlEventData(link);
        }
    }

    // Tong hop
    public static void crawlData() {
        getAllEventLinks();
        getDataFromLink();
    }

    public CrawlEvent() {
        crawlData();
    }
}
