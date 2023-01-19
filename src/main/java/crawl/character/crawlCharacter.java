package crawl.character;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.regex.*;

public class crawlCharacter {
    // Link dong lich su
    private static ArrayList<String> timeStampLinks;

    // Link con trong trang dong lich su
    private static ArrayList<String> timeStampChildLinks;

    // Link luu trang cua nhan vat lich su
    private static ArrayList<String> charInfoLinks;

    public static ArrayList<String> getCharInfoLinks() {
        return charInfoLinks;
    }

    public static ArrayList<String> getTimeStampLinks() {
        return timeStampLinks;
    }

    public static ArrayList<String> getTimeStampChildLinks() {
        return timeStampChildLinks;
    }

    public crawlCharacter() {
        this.timeStampLinks = new ArrayList<>();
        this.timeStampChildLinks = new ArrayList<>();
        this.charInfoLinks = new ArrayList<>();
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
        System.out.println("\nCrawl link tu dong lich su: ");
        for (String item : timeStampLinks) {
            try {
                // Dong lich su
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

    public void getAllCharInfoLinks() {
        System.out.println("\nCrawl link nhan vat tu cac link crawl duoc tu dong lich su: ");
        for (String item : timeStampChildLinks) {
            try {
                // Truy cap cac link nhat dc tu dong lich su
                Document doc = Jsoup.connect(item).get();

                // Lay cac the dau muc - h3
                Elements h3Tags = doc.select("h3");
                if (h3Tags != null) {
                    for (Element h3 : h3Tags) {
                        // Neu the h3 co con la the a => co link
                        Element h3ATag = h3.selectFirst("a");
                        if (h3ATag != null) {
                            String link = "https://nguoikesu.com" + h3ATag.attr("href");
                            // Neu la link cua nhan vat lich su
                            if (link.contains("nhan-vat")) {
                                // Co the co truong hop lap lai trong qua trinh crawl
                                if (!charInfoLinks.contains(link)) {
                                    System.out.println(link);
                                    charInfoLinks.add(link);
                                }
                            }
                        }
                    }
                } else {
                    // Neu k co the h3 => tim xem co info box ko?
                    // Neu k co infoBox thi out trang day coi nhu bo =))
                    Element infoBox = doc.selectFirst("div[class=caption]");
                    if (infoBox != null) {
                        String link = "https://nguoikesu.com" + infoBox.selectFirst("h3")
                                .selectFirst("a")
                                .attr("href");
                        if (link.contains("nhan-vat")) {
                            if (!charInfoLinks.contains(link)) {
                                System.out.println(link);
                                charInfoLinks.add(link);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Lay link info cua cac nhan vat tu /nhan-vat
    public void getCharInfoPageLink() {
//        ArrayList<String> paginateLinks = new ArrayList<>();
//        paginateLinks.add("https://nguoikesu.com/nhan-vat");
        System.out.println("\nLay link nhan vat tu /nhan-vat: ");

        try {
            Document doc = Jsoup.connect("https://nguoikesu.com/nhan-vat").timeout(120000).get();

            // p tag nay cho biet co tong bnh trang trong pagination
            Element pTag = doc
                    .selectFirst("p[class=com-content-category-blog__counter counter float-end pt-3 pe-2]");
            String[] pTagContentArray = pTag.text().split(" ");
            int pTagContentArrSize = pTagContentArray.length;
            int numberOfPagination = Integer.parseInt(pTagContentArray[pTagContentArrSize - 1]);
            System.out.println("Total number of paginate page: " + numberOfPagination);

            // Lay cac link tu trang dau tien truoc => sau do lay tu trang thu 2 den het
            Elements pageHeaders = doc.select("div[class=page-header]");
//            System.out.println(pageHeaders.size());

            for (Element pageHeader : pageHeaders) {
                Element pageHeaderATag = pageHeader.selectFirst("a");
                if (pageHeaderATag != null) {
                    String link = "https://nguoikesu.com" + pageHeaderATag.attr("href");

                    // 1 so truong hop co cac trieu dai nen khong tinh vao nhan vat
                    if (!link.contains("nha-")) {
                        // Neu link nay chua co trong mang => tranh TH lap
                        if (!charInfoLinks.contains(link)) {
                            System.out.println(pageHeader.text() + " - " + link);
                            charInfoLinks.add(link);
                        }
                    }
                }
            }

            // 1 trang co <= 5 nhan vat
            // Format link: "https://nguoikesu.com/nhan-vat?start=..." => query
            for (int i = 2; i <= 291; ++i) {
                System.out.println("\nCurrent page: " + i);
                String link = "https://nguoikesu.com/nhan-vat?start=" + String.valueOf((i - 1) * 5);

                try {
                    Document pagiDoc = Jsoup.connect(link).timeout(120000).get();

                    // Lay tu trang thu 2 den het
                    Elements pagiPageHeaders = pagiDoc.select("div[class=page-header]");

                    for (Element pagiPageHeader : pagiPageHeaders) {
                        Element pagiPHATag = pagiPageHeader.selectFirst("a");

                        if (pagiPHATag != null) {
                            String pagiLink = "https://nguoikesu.com" + pagiPHATag.attr("href");

                            // 1 so truong hop co cac trieu dai nen khong tinh vao nhan vat
                            if (!pagiLink.contains("nha-")) {
                                // Neu link nay chua co trong mang => tranh TH lap
                                if (!charInfoLinks.contains(pagiLink)) {
                                    System.out.println(pagiPageHeader.text() + " - " + pagiLink);
                                    charInfoLinks.add(pagiLink);
                                }
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

    // Kiem tra chuc vu cua nhan vat
    public boolean positionCheck(String position) {
        return position.contains("Hoàng đế") ||
                position.contains("Hoàng hậu") ||
                position.contains("Vua") ||
                position.contains("Vương") ||
                position.equals("Công việc") ||
                position.equals("Nghề nghiệp") ||
                position.equals("Cấp bậc") ||
                position.equals("Đơn vị") ||
                position.equals("Chức quan cao nhất") ||
                position.equals("Chức vụ") ||
                position.equals("Vị trí");
    }

    public boolean workTimeCheck(String workTime) {
        return workTime.equals("Trị vì") ||
                workTime.equals("Tại vị") ||
                workTime.equals("Nhiệm kỳ") ||
                workTime.equals("Năm tại ngũ") ||
                workTime.equals("Hoạt động");
    }

    public boolean fatherCheck(String father) {
        return father.equals("Thân phụ") ||
                father.equals("Cha") ||
                father.equals("Bố mẹ");
    }

    public boolean motherCheck(String mother) {
        return mother.equals("Thân mẫu") ||
                mother.equals("Mẹ") ||
                mother.equals("Bố mẹ");
    }

    public boolean eraCheck(String era) {
        return era.equals("Hoàng tộc") ||
                era.equals("Triều đại") ||
                era.equals("Gia tộc") ||
                era.equals("Kỷ nguyên");
    }

    public boolean birthCheck(String birth) {
        return birth.equals("Ngày sinh") ||
                birth.equals("Sinh");
    }

    // Truy cap vao link nhan vat va crawl
    public void crawlCharInfo(String link) {
        System.out.println("\nDang crawl nhan vat o link: " + link);
        try {
            Document doc = Jsoup.connect(link).timeout(120000).get();

            String charName = "Chưa rõ"; // ten
            String charMother = "Chưa rõ"; // me
            String charFather = "Chưa rõ"; // cha
            String dateOfBirth = "Chưa rõ"; // ngay sinh
            String lostDate = "Chưa rõ"; // ngay mat
            String preceeded = "Chưa rõ"; // tien nhiem
            String succeeded = "Chưa rõ"; // ke nhiem
            String era = "Chưa rõ"; // trieu dai ?
            String workTime = "Chưa rõ"; // thoi gian tai chuc
            String position = "Chưa rõ"; // chuc vu

            // Lay ra bang thong tin (neu co) => neu k co thi loc text
            Element infoTable = doc.selectFirst("table[class^=infobox]");

            if (infoTable != null) {
                Elements infoTableRows = infoTable.select("tr");
                int numberOfTr = infoTableRows.size();
                for (int i = 0; i < numberOfTr; ++i) {
                    // Chi muc dau tien la ten
                    if (i == 0) {
                        Element tableHead = infoTableRows.get(i).selectFirst("th");
                        // Chua co cach tach xau
                        if (tableHead != null) {
                            charName = tableHead.text();
                        }
                    } else {
                        Element tableHead = infoTableRows.get(i).selectFirst("th");

                        // Trong khi crawl co 1 so nhan vat co nhieu chuc vu, tien nhiem
                        // ke nhiem,... => lay nhung cai dau crawl duoc, bo nhung cai sau
                        if (tableHead != null) {
                            String tableHeadContent = tableHead.text();

                            // Chuc vu se dung truoc thoi gian tai vi hoac nhiem ky =>
                            // Check neu sau no la nhiem ky / tgian tai chuc thi do la chuc vu
                            // 1 khi co vi tri r thi thoi khong chay if nay nua
                            if (i < numberOfTr - 1 && (position.equals("Chưa rõ") || positionCheck(position))) {
                                Element nextTableHead = infoTableRows.get(i + 1).selectFirst("th");

                                if (nextTableHead != null) {
                                    if (workTimeCheck(nextTableHead.text())) {
                                        if (!tableHeadContent.equals("Thuộc")) {
                                            position = tableHeadContent;
                                        }
                                        continue;
                                    }
                                }
                            }

                            if (positionCheck(tableHeadContent) && (position.equals("Chưa rõ") || position.equals("Thuộc"))) { // Neu trong xau co hoang de,... => chuc vu
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                if (tableData != null) {
                                    position = tableData.text();
                                } else position = tableHeadContent;
                            } else if (workTimeCheck(tableHeadContent) && workTime.equals("Chưa rõ")) { // thoi gian tai chuc
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                workTime = tableData.text();
                            } else if (tableHeadContent.equals("Tiền nhiệm") && preceeded.equals("Chưa rõ")) { // Preceeded
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                // Co cac truong hop
                                // Dau tien la co font
                                // 2 la co the a
                                // 3 chac la k co j?
                                // ...
                                preceeded = tableData.text();
                            } else if (tableHeadContent.equals("Kế nhiệm") && succeeded.equals("Chưa rõ")) { // Succeeded
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                succeeded = tableData.text();
                            } else if (eraCheck(tableHeadContent) && era.equals("Chưa rõ")) {
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                era = tableData.text();
                            } else if (fatherCheck(tableHeadContent) && charFather.equals("Chưa rõ")) { // Father
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                charFather = tableData.text();
                            } else if (motherCheck(tableHeadContent) && charMother.equals("Chưa rõ")) { // Mother
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                charMother = tableData.text();
                            } else if (birthCheck(tableHeadContent) && dateOfBirth.equals("Chưa rõ")) { // Birth
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                dateOfBirth = tableData.text();
                            } else if (tableHeadContent.equals("Mất") && lostDate.equals("Chưa rõ")) { // Lost
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                lostDate = tableData.text();
                            }
                        } else {
                            Elements numberOfTd = infoTableRows.get(i).select("td");

                            // Neu la the img
                            if (numberOfTd.size() < 2) {
                                continue;
                            }

                            // Co the co truong hop no la the td thay vi th
                            Element tableDataAlter = infoTableRows.get(i).selectFirst("td");

                            if (tableDataAlter != null) {
                                String tableDataAlterContent = tableDataAlter.text();

                                if (i < numberOfTr - 1 && (position.equals("Chưa rõ") || positionCheck(position))) {
                                    Element nextTableData = infoTableRows.get(i + 1).selectFirst("td");

                                    if (nextTableData != null) {
                                        if (workTimeCheck(nextTableData.text())) {
                                            if (!tableDataAlterContent.equals("Thuộc")) {
                                                position = tableDataAlterContent;
                                            }
                                            continue;
                                        }
                                    }
                                }

                                if (positionCheck(tableDataAlterContent) && (position.equals("Chưa rõ") || position.equals("Thuộc"))) { // Neu trong xau co hoang de,... => chuc vu
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    if (tableData != null) {
                                        position = tableData.text();
                                    } else position = tableDataAlterContent;
                                } else if (workTimeCheck(tableDataAlterContent) && workTime.equals("Chưa rõ")) { // thoi gian tai chuc
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    workTime = tableData.text();
                                } else if (tableDataAlterContent.equals("Tiền nhiệm") && preceeded.equals("Chưa rõ")) { // Preceeded
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    // Co cac truong hop
                                    // Dau tien la co font
                                    // 2 la co the a
                                    // 3 chac la k co j?
                                    // ...
                                    preceeded = tableData.text();
                                } else if (tableDataAlterContent.equals("Kế nhiệm") && succeeded.equals("Chưa rõ")) { // Succeeded
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    succeeded = tableData.text();
                                } else if (eraCheck(tableDataAlterContent) && era.equals("Chưa rõ")) {
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    era = tableData.text();
                                } else if (fatherCheck(tableDataAlterContent) && charFather.equals("Chưa rõ")) { // Father
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    charFather = tableData.text();
                                } else if (motherCheck(tableDataAlterContent) && charMother.equals("Chưa rõ")) { // Mother
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    charMother = tableData.text();
                                } else if (birthCheck(tableDataAlterContent) && dateOfBirth.equals("Chưa rõ")) { // Birth
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    dateOfBirth = tableData.text();
                                } else if (tableDataAlterContent.equals("Mất") && lostDate.equals("Chưa rõ")) { // Lost
                                    Element tableData = infoTableRows.get(i).select("td").get(1);
                                    lostDate = tableData.text();
                                }
                            }
                        }
                    }
                }
            }

                Element contentBody = doc.selectFirst("div[class=com-content-article__body]");

                // Thuong thong tin se nam o the p dau tien
                Elements contentBodyElements = contentBody.children();
//                Element firstParagraph = contentBody.selectFirst("p");

                for (Element item : contentBodyElements) {
                    if (item.tagName().equals("p")) {
                        Element firstParagraph = item;
                        // Lay cac the a la con the p
                        Elements pATags = firstParagraph.select("a");

                        // The b dau tien la ten cua nhan vat?
                        Element firstBTag = firstParagraph.selectFirst("b");
                        if (firstBTag != null) {
                            if (charName.equals("Chưa rõ")) charName = firstBTag.text();
                        }

                        // Tim ngay sinh
                        String firstPContent = firstParagraph.text();
                        Pattern birthRegex = Pattern.compile("\\(([^)]*)\\)", Pattern.UNICODE_CASE);
                        Matcher birthMatch = birthRegex.matcher(firstPContent);

                        while (birthMatch.find()) {
                            String firstResult = birthMatch.group(0);

                            // Lay ra doan xau co format (...) => lay ...
                            // Truong hop doan trong ngoac khong phai ngay sinh
                            Pattern checkValid = Pattern.compile("sinh|tháng|năm|-|–");
                            Matcher matchValid = checkValid.matcher(firstResult);

                            if (matchValid.find()) {
                                // Loai bo phan chu Han: ...,/; ... => lay phan ... sau
                                int startIndex = firstResult.lastIndexOf(',');
                                if (startIndex == -1) {
                                    startIndex = firstResult.lastIndexOf(';');
                                    if (startIndex == -1) {
                                        startIndex = firstResult.lastIndexOf('；');
                                        if (startIndex == -1) {
                                            startIndex = 1;
                                        } else startIndex++;
                                    } else startIndex++;
                                } else startIndex++;

                                String contentInParen = firstResult.substring(startIndex, firstResult.length() - 1);
                                // Chia ra nam sinh voi nam mat
                                String[] splitString = {};
                                if (contentInParen.contains("-")) {
                                    splitString = contentInParen.split("-");
                                } else {
                                    splitString = contentInParen.split("–");
                                }

                                if (splitString.length == 1) {
                                    if (dateOfBirth.equals("Chưa rõ")) dateOfBirth = splitString[0].trim();
                                } else {
                                    if (dateOfBirth.equals("Chưa rõ")) dateOfBirth = splitString[0].trim();
                                    if (lostDate.equals("Chưa rõ")) lostDate = splitString[1].trim();
                                }
                                break;
                            } else {
                                int start = firstPContent.indexOf(')');
                                firstPContent = firstPContent.substring(start + 1);
                                birthMatch = birthRegex.matcher(firstPContent);
                            }
                        }

                        // Lay chuc vu - nghe nghiep cua nhan vat
                        firstPContent = firstParagraph.text();
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

                        Pattern posiRegex = Pattern.compile("(là|làm)[^.]*[.]");
                        Matcher posiMatcher = posiRegex.matcher(firstPContent);

                        if (posiMatcher.find()) {
                            String result = posiMatcher.group(0);
                            if (!result.contains(":")) {
                                if (position.equals("Chưa rõ") || positionCheck(position) || position.equals("Thuộc")) {
                                    position = result.substring(0, result.length() - 1);
                                }
                            }
                        }

                        // Lay trieu dai, nha???
                        for (Element a : pATags) {
                            String hrefValue = a.attr("href");
                            if (hrefValue.contains("nha-")) {
                                if (era.equals("Chưa rõ")) {
                                    era = a.text();
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }

            System.out.println("Name: " + charName);
            System.out.println("Date of birth and birth place: " + dateOfBirth);
            System.out.println("Lost date and lost place: " + lostDate);
            System.out.println("Position: " + position);
            System.out.println("Work time: " + workTime);
            System.out.println("Era: " + era);
            System.out.println("Father: " + charFather);
            System.out.println("Mother: " + charMother);
            System.out.println("Preceeded: " + preceeded);
            System.out.println("Succeeded: " + succeeded);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Crawl nhan vat
    public void getCharInfo() {
        for (String link : charInfoLinks) {
            crawlCharInfo(link);
        }
    }

    public void crawlData() {
//        getAllTimeStampLinks();
//        getAllChildLinkFromTimeStamp();
//        getAllCharInfoLinks();
        getCharInfoPageLink();
        getCharInfo();
    }
}
