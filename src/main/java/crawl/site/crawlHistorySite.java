package crawl.site;

import history.historicsite.HistoricSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class crawlHistorySite {
    private static ArrayList<String> siteLinks = new ArrayList<>();

    public static boolean notCharName(String test) {
        // Xem co phai xau rong k?
        if (test.equals("")) return true;

        // Xem neu chi co 1 ky tu
        if (test.length() == 1) return true;

        // Kiem tra xem co chu so khong?
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(test);

        if (m.find()) {
            return true;
        }

        // Kiem tra neu ten qua ngan
        String[] splitted = test.split(" ");
        if (splitted.length < 2) return true;
        else {
            // Kiem tra co phai tat ca deu in hoa khong?
            for (String s : splitted) {
                if (!Character.isUpperCase(s.charAt(0))) {
                    return true;
                }
            }
        }

        // Mang chua nhung tu khong hop le doi voi ten 1 nhan vat
        String[] notValid = {"nhà", "triều", "miếu", "sông", "phủ", "đền", "biển", "thành", "di sản", "cố đô",
                "di tích", "tổ chức", "thế kỷ", "trận", "chùa", "đường", "phố", "bản", "người", "động",
                "bộ", "sửa", "xã", "kháng chiến", "chiến khu", "quốc lộ", "cách mạng", "chú thích", "nguồn", "đảo", "chiến dịch",
                "trung đoàn", "đại đoàn", "chiều", "xã", "huyện", "tỉnh", "thủy điện", "hang", "UBND", "ủy", "thời", "khảo cổ",
                "lịch sử", "đá", "thị trấn", "cực", "vĩ độ", "kinh độ", "tọa độ", "việt nam", "trống",
                "biên giới", "tiếng", "cờ", "ruộng", "biên giới", "này", "kiểm chứng", "'", "[", "]", "/", "km", "cm", "suối",
                "gỗ", "trám", "-"
        };
        String lowerCaseTest = test.toLowerCase();
        for (String s : notValid) {
            if (lowerCaseTest.contains(s)) return true;
        }
        return false;
    }

    public static void getDataFromSecondWikiLink() {
        String secondLink = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
        System.out.println("Crawl from Wiki link: " + secondLink);

        try {
            Document doc = Jsoup.connect(secondLink).timeout(120000).get();

            // Lay ra cac bang thong tin
            Elements listOfTables = doc.select("table[class^=wikitable sortable]");
            // Lap qua cac bang
            for (Element table : listOfTables) {
                // Lay ra cac the tr
                Elements tableRows = table.select("tbody > tr");
                for (int i = 1; i < tableRows.size(); ++i) {
                    Element tr = tableRows.get(i);
                    String name = "Chưa rõ";
                    String category = "Chưa rõ";
                    String location = "Chưa rõ";
                    String note = "Chưa rõ";
                    String description = "Chưa rõ";
                    String festival = "Chưa rõ";
                    String approvedYear = "Chưa rõ";
                    String builtDate = "Chưa rõ";
                    String founder = "Chưa rõ";
                    Set<String> relateChars = new HashSet<>();

                    // Lay ra cac the td co trong the tr
                    Elements tableDatas = tr.select("td");
                    String navLink = null; // link dung de truy cap va crawl nhieu hon
                    int numberOfTds = tableDatas.size();
                    if (numberOfTds == 3) {
                        // Lay ra ten di tich
                        Element nameTd = tableDatas.get(0);
                        if (nameTd != null) {
                            nameTd.select("sup").remove();
                            if (!nameTd.text().equals("")) name = nameTd.text();
                            // Tim kiem the a
                            Element aTag = nameTd.selectFirst("a");
                            if (aTag != null) {
                                // Neu ten khong chua dau ngoac
                                // Thi chi truy cap nhung link co noi dung the a == ten
                                if (!name.contains("(")) {
                                    if (name.equalsIgnoreCase(aTag.text())) {
                                        navLink = "https://vi.wikipedia.org" + aTag.attr("href");
                                    }
                                }
                            }
                        }

                        // Lay ra vi tri di tich
                        Element locationTd = tableDatas.get(1);
                        if (locationTd != null) {
                            locationTd.select("sup").remove();
                            if (!locationTd.text().equals("")) location = locationTd.text();
                        }

                        // Lay ra ghi chu / gia tri noi bat
                        Element noteTd = tableDatas.get(2);
                        if (noteTd != null) {
                            noteTd.select("sup").remove();
                            if (!noteTd.text().equals("")) note = noteTd.text();
                        }
                    } else if (numberOfTds == 4) {
                        // Lay ra ten di tich
                        Element nameTd = tableDatas.get(1);
                        if (nameTd != null) {
                            nameTd.select("sup").remove();
                            if (!nameTd.text().equals("")) name = nameTd.text();
                            Element aTag = nameTd.selectFirst("a");
                            if (aTag != null) {
                                // Neu ten khong chua dau ngoac
                                // Thi chi truy cap nhung link co noi dung the a == ten
                                if (!name.contains("(")) {
                                    if (name.equalsIgnoreCase(aTag.text())) {
                                        navLink = "https://vi.wikipedia.org" + aTag.attr("href");
                                    }
                                }
                            }
                        }

                        // Lay ra vi tri di tich
                        Element locationTd = tableDatas.get(2);
                        if (locationTd != null) {
                            locationTd.select("sup").remove();
                            if (!locationTd.text().equals("")) location = locationTd.text();
                        }

                        // Lay ra ghi chu / gia tri noi bat
                        Element noteTd = tableDatas.get(3);
                        if (noteTd != null) {
                            noteTd.select("sup").remove();
                            if (!noteTd.text().equals("")) note = noteTd.text();
                        }
                    } else if (numberOfTds == 5) {
                        // Lay ra ten di tich
                        Element nameTd = tableDatas.get(0);
                        if (nameTd != null) {
                            nameTd.select("sup").remove();
                            if (!nameTd.text().equals("")) name = nameTd.text();
                            Element aTag = nameTd.selectFirst("a");
                            if (aTag != null) {
                                // Neu ten khong chua dau ngoac
                                // Thi chi truy cap nhung link co noi dung the a == ten
                                if (!name.contains("(")) {
                                    if (name.equalsIgnoreCase(aTag.text())) {
                                        navLink = "https://vi.wikipedia.org" + aTag.attr("href");
                                    }
                                }
                            }
                        }

                        // Lay ra vi tri di tich
                        Element locationTd = tableDatas.get(1);
                        if (locationTd != null) {
                            locationTd.select("sup").remove();
                            if (!locationTd.text().equals("")) location = locationTd.text();
                        }

                        // Lay ra hang muc di tich
                        Element categoryTd = tableDatas.get(2);
                        if (categoryTd != null) {
                            categoryTd.select("sup").remove();
                            if (!categoryTd.text().equals("")) category = categoryTd.text();
                        }

                        // Lay ra nam cong nhan
                        Element approvedYearTd = tableDatas.get(3);
                        if (approvedYearTd != null) {
                            approvedYearTd.select("sup").remove();
                            if (!approvedYearTd.text().equals("")) approvedYear = approvedYearTd.text();
                        }

                        // Lay ra ghi chu
                        Element noteTd = tableDatas.get(4);
                        if (noteTd != null) {
                            noteTd.select("sup").remove();
                            if (!noteTd.text().equals("")) note = noteTd.text();
                        }
                    }

                    if (navLink != null) {
                       System.out.print("\nTruy cap vao link: " + navLink);
                       try {
                           Document detailDoc = Jsoup.connect(navLink).timeout(120000).get();
                           Element detailDiv = detailDoc.selectFirst("#mw-content-text > div.mw-parser-output");

                           // Tranh truong hop link ded
                           if (detailDiv != null) {
                               // Lay ra doan van dau tien
                               Element infoP = detailDiv.selectFirst("p");
                               infoP.select("sup").remove();
                               String firstPContent = infoP.text();

                               // Loc thong tin tu doan van ban dau tien
                               // Lay mo ta ngan gon cua di tich
                               // Loai bo doan trong ngoac dau tien cho chac
                               if (description.equals("Chưa rõ")) {
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
                                       if (!result.contains(":")) {
                                           description = result.substring(0, result.length() - 1);
                                       }
                                   }
                               }

                               // Lay ra bang thong tin (neu co)
                               Element infoTable = detailDiv.selectFirst("table[class^=infobox]");
                               if (infoTable != null) {
                                   for (Element row : infoTable.select("tbody > tr")) {
                                       Element rowHead = row.selectFirst("th");
                                       if (rowHead != null) {
                                           rowHead.select("sup").remove();
                                           String lowerCaseContent = rowHead.text().toLowerCase();
                                           Element tableData = row.selectFirst("td");
                                           if (tableData != null) {
                                               // Loai bo chu thich?
                                               tableData.select("sup").remove();
                                               if (
                                                       lowerCaseContent.contains("công nhận") ||
                                                               lowerCaseContent.contains("ngày nhận danh hiệu")
                                               ) {
                                                   if (approvedYear.equals("Chưa rõ")) approvedYear = tableData.text();
                                               } else if (
                                                       lowerCaseContent.contains("thành lập") ||
                                                               lowerCaseContent.contains("khởi lập")
                                               ) {
                                                   if (builtDate.equals("Chưa rõ")) builtDate = tableData.text();
                                               } else if (
                                                       lowerCaseContent.contains("vị trí") ||
                                                               lowerCaseContent.contains("địa chỉ")
                                               ) {
                                                   if (location.equals("Chưa rõ")) location = tableData.text();
                                               } else if (lowerCaseContent.contains("phân loại")) {
                                                   if (category.equals("Chưa rõ")) category = tableData.text();
                                                   else if (
                                                           !category.toLowerCase().contains(tableData.text().toLowerCase())
                                                   ) {
                                                       category = category + ", " + tableData.text();
                                                   }
                                               } else if (lowerCaseContent.contains("người sáng lập")) {
                                                   if (founder.equals("Chưa rõ")) founder = tableData.text();
                                               }
                                           }
                                       }
                                   }
                               }

                               // Lay ra dia chi cua di tich
                               if (location.equals("Chưa rõ")) {
                                   firstPContent = infoP.text();
                                   // Tim trong doan van dau tien truoc
                                   do {
                                       Pattern p = Pattern.compile(
                                               "(((nằm|tọa lạc|dựng|xây dựng) (tại|ở|trên|trong))|(thuộc)|(ở))[^.]*[.]",
                                               Pattern.CASE_INSENSITIVE
                                       );
                                       Matcher m = p.matcher(firstPContent);

                                       if (m.find()) {
                                           String findResult = m.group(0);
                                           if (findResult.contains("triều đại")) {
                                               // Bo qua chu thuoc hien tai?
                                               int skipIndex = firstPContent.indexOf("triều đại");
                                               firstPContent = firstPContent.substring(skipIndex + 9);
                                           } else {
                                               location = findResult.substring(0, findResult.length() - 1);
                                               break;
                                           }
                                       } else break;
                                   } while (true);

                                   // Neu tim khong thay thi xem co the vi tri khong tim tiep
                               }

                               // Lay thoi gian xay dung
                               if (builtDate.equals("Chưa rõ")) {
                                   firstPContent = infoP.text();
                                   Pattern p = Pattern.compile("(xây dựng|xây) (từ|vào|trong)[^.]*[.(]", Pattern.CASE_INSENSITIVE);
                                   Matcher m = p.matcher(firstPContent);
                                   // Tim trong doan van dau tien truoc
                                   // Neu khong thay thi xem co muc lich su khong -> co thi tim tiep
                                   if (m.find()) {
                                       String findResult = m.group(0);
                                       builtDate = findResult.substring(0, findResult.length() - 1);
                                   } else {
                                       Element builtDateH2 = detailDiv.selectFirst("h2:contains(Lịch sử)");
                                       if (builtDateH2 != null) {
                                           Element builtDateP = builtDateH2.nextElementSibling();
                                           builtDateP.select("sup").remove();
                                           if (builtDate.equals("Chưa rõ")) {
                                               m = p.matcher(builtDateP.text());
                                               if (m.find()) {
                                                   String findResult = m.group(0);
                                                   builtDate = findResult.substring(0, findResult.length() - 1);
                                               }
                                           }
                                       }
                                   }
                               }

                               // Lay ra le hoi (neu co)
                               Element festivalH2 = detailDiv.selectFirst("h2:contains(Lễ hội)");
                               if (festivalH2 != null) {
                                   Element festivalP = festivalH2.nextElementSibling();
                                   festivalP.select("sup").remove();
                                   if (festival.equals("Chưa rõ")) festival = festivalP.text();
                               }

                               // Lấy ra các thẻ link để lọc ra nhân vật liên quan
                               for (Element e : detailDiv.children()) {
                                   if (e.tagName().equals("h2")) {
                                       String eContent = e.text().toLowerCase();
                                       if (
                                               eContent.contains("liên quan") ||
                                                       eContent.contains("tham khảo") ||
                                                       eContent.contains("chú thích") ||
                                                       eContent.contains("xem thêm")
                                       )
                                           break;
                                   }
                                   Elements aTags = e.select("a");
                                   if (aTags.size() > 0) {
                                       for (Element a : aTags) {
                                           if (!notCharName(a.text())) {
                                                relateChars.add(a.text());
                                           }
                                       }
                                   }

                               }
                           }
                       } catch (IOException err) {
                           err.printStackTrace();
                       }
                    }

                    System.out.println("\nName: " + name);
                    System.out.println("Built date: " + builtDate);
                    System.out.println("Location: " + location);
                    System.out.println("Related festival: " + festival);
                    System.out.println("Description: " + description);
                    System.out.println("Approved Year: " + approvedYear);
                    System.out.println("Founder: " + founder);
                    System.out.println("Category: " + category);
                    System.out.println("Note: " + note);
                    System.out.print("Related characters: ");
                    System.out.println(relateChars);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Bo sung du lieu tu wiki
    public static void getDataFromFirstWikiLink() {
        String firstLink = "https://vi.wikipedia.org/wiki/Di_t%C3%ADch_qu%E1%BB%91c_gia_%C4%91%E1%BA%B7c_bi%E1%BB%87t_(Vi%E1%BB%87t_Nam)";
        System.out.println("Crawl from Wiki link: " + firstLink);

        try {
            Document doc = Jsoup
                    .connect("https://vi.wikipedia.org/wiki/Di_t%C3%ADch_qu%E1%BB%91c_gia_%C4%91%E1%BA%B7c_bi%E1%BB%87t_(Vi%E1%BB%87t_Nam)")
                    .timeout(120000).get();

            // Lay ra cac bang thong tin
            Elements listOfTables = doc.select("table[class^=wikitable sortable]");
            // Lap qua cac bang
            for (Element table : listOfTables) {
                // Chon ra cac the tr trong tbody cua table
                Elements tableRows = table.select("tbody > tr");
                // Moi the tr la 1 di tich
                for (int i = 1; i < tableRows.size(); ++i) {
                    Element tr = tableRows.get(i);
                    String name = "Chưa rõ";
                    String category = "Chưa rõ";
                    String location = "Chưa rõ";
                    String note = "Chưa rõ";
                    String description = "Chưa rõ";
                    String festival = "Chưa rõ";
                    String approvedYear = "Chưa rõ";
                    String builtDate = "Chưa rõ";
//                    ArrayList<String> relateChars = new ArrayList<>();

                    Elements tableDatas = tr.select("td");
                    if (tableDatas.size() > 0) {
                        // Lay ra ten cua di tich
                        Element firstTd = tableDatas.get(0);
                        // Loai bo cac the chu thich
                        firstTd.select("sup").remove();
                        // Ten cua di tích
                        if (!firstTd.text().equals("")) name = firstTd.text();

                        // Lấy ra địa chỉ của di tích
                        Element locationTd = tableDatas.get(2);
                        locationTd.select("img").remove();
                        locationTd.select("sup").remove();
                        if (!locationTd.text().equals("")) location = locationTd.text();

                        // Lấy ra hạng mục của di tích
                        Element categoryTd = tableDatas.get(3);
                        categoryTd.select("sup").remove();
                        if (!categoryTd.text().equals("")) category = categoryTd.text();

                        Element noteTd = tableDatas.get(4);
                        noteTd.select("sup").remove();
                        if (!noteTd.text().equals("")) note = noteTd.text();

                        // Lay ra link de truy cap
                        // Format link: https://vi.wikipedia.org
                        if (firstTd.selectFirst("a") != null) {
                            if (firstTd.text().equals(firstTd.selectFirst("a").text())) {
                                String link = "https://vi.wikipedia.org" + firstTd.selectFirst("a").attr("href");
                                System.out.print("\nTruy cap vao link: " + link);

                                try {
                                    Document detailDoc = Jsoup.connect(link).timeout(120000).get();
                                    Element detailDiv = detailDoc.selectFirst("#mw-content-text > div.mw-parser-output");

                                    // Truong hop trang thieu thong tin
                                    if (detailDiv != null) {
                                        // Lay ra doan van dau tien
                                        Element infoP = detailDiv.selectFirst("p");
                                        infoP.select("sup").remove();
                                        String firstPContent = infoP.text();
                                        // Loc thong tin tu doan van ban dau tien
                                        // Lay mo ta ngan gon cua di tich
                                        // Loai bo doan trong ngoac dau tien cho chac
                                        if (description.equals("Chưa rõ")) {
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
                                                if (!result.contains(":")) {
                                                    description = result.substring(0, result.length() - 1);
                                                }
                                            }
                                        }

                                        // Lay ra bang thong tin (neu co)
                                        Element infoTable = detailDiv.selectFirst("table[class^=infobox]");
                                        if (infoTable != null) {
                                            for (Element row : infoTable.select("tbody > tr")) {
                                                Element rowHead = row.selectFirst("th");
                                                if (rowHead != null) {
                                                    rowHead.select("sup").remove();
                                                    String lowerCaseContent = rowHead.text().toLowerCase();
                                                    Element tableData = row.selectFirst("td");
                                                    if (tableData != null) {
                                                        // Loai bo chu thich?
                                                        tableData.select("sup").remove();
                                                        if (
                                                                lowerCaseContent.contains("công nhận") ||
                                                                        lowerCaseContent.contains("ngày nhận danh hiệu")
                                                        ) {
                                                            if (approvedYear.equals("Chưa rõ")) approvedYear = tableData.text();
                                                        } else if (
                                                                lowerCaseContent.contains("thành lập") ||
                                                                        lowerCaseContent.contains("khởi lập")
                                                        ) {
                                                            if (builtDate.equals("Chưa rõ")) builtDate = tableData.text();
                                                        } else if (
                                                                lowerCaseContent.contains("vị trí") ||
                                                                        lowerCaseContent.contains("địa chỉ")
                                                        ) {
                                                            if (location.equals("Chưa rõ")) location = tableData.text();
                                                        } else if (lowerCaseContent.contains("phân loại")) {
                                                            if (category.equals("Chưa rõ")) category = tableData.text();
                                                            else if (
                                                                    !category.toLowerCase().contains(tableData.text().toLowerCase())
                                                            ) {
                                                                category = category + ", " + tableData.text();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        // Lay ra le hoi (neu co)
                                        Element festivalH2 = detailDiv.selectFirst("h2:contains(Lễ hội)");
                                        if (festivalH2 != null) {
                                            Element festivalP = festivalH2.nextElementSibling();
                                            festivalP.select("sup").remove();
                                            if (festival.equals("Chưa rõ")) festival = festivalP.text();
                                        }
                                    }
                                } catch (IOException err) {
                                    err.printStackTrace();
                                }
                            }
                        }
                    }

                    System.out.println("\nName: " + name);
                    System.out.println("Built date: " + builtDate);
                    System.out.println("Location: " + location);
//                    System.out.println("Founder: " + founder);
//                    System.out.println("Related Myth: " + backgroundStory);
                    System.out.println("Related festival: " + festival);
                    System.out.println("Description: " + description);
                    System.out.println("Approved Year: " + approvedYear);
                    System.out.println("Category: " + category);
                    System.out.println("Note: " + note);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lay tat ca cac link di tich de crawl
    public static void getAllSiteLinks() {
        System.out.println("Lay cac link cua phan pagination: ");

        try {
            Document doc = Jsoup.connect("https://nguoikesu.com/di-tich-lich-su?types[0]=1").timeout(120000).get();

            // p tag nay cho biet co tong bnh trang trong pagination
            int numberOfPagination = 1;
            Element pTag = doc
                    .selectFirst("p[class=counter float-end pt-3 pe-2]");
            if (pTag != null) {
                String[] pTagContentArray = pTag.text().split(" ");
                int pTagContentArrSize = pTagContentArray.length;
                numberOfPagination = Integer.parseInt(pTagContentArray[pTagContentArrSize - 1]);
                System.out.println("Total number of paginate page: " + numberOfPagination);
            }

            // Lay link tu trang dau tien
            Element siteUl = doc.selectFirst("ul[class=com-tags-tag__category category list-group]");
            Elements siteLi = siteUl.select("li");

            for (Element li : siteLi) {
                // Lay the li dau tien co duoc
                Element liATag = li.selectFirst("a");
                if (liATag != null) {
                    String link = "https://nguoikesu.com" + liATag.attr("href");

                    if (!siteLinks.contains(link)) {
                        System.out.println(liATag.text() + " - " + link);
                        siteLinks.add(link);
                    }
                }
            }

            // 1 trang co <= 10 di tich
            // Format link: "https://nguoikesu.com/di-tich-lich-su?types[0]=1&start=..." => query
            if (numberOfPagination > 1) {
                for (int i = 2; i <= numberOfPagination; ++i) {
                    System.out.println("\nCurrent page: " + i);
                    String link = "https://nguoikesu.com/di-tich-lich-su?types[0]=1&start=" + (i - 1) * 10;

                    try {
                        Document pagiDoc = Jsoup.connect(link).timeout(120000).get();

                        // Lay tu trang thu 2 den het
                        Element pagiSiteUl = pagiDoc.selectFirst("ul[class=com-tags-tag__category category list-group]");
                        Elements pagiSiteLi = pagiSiteUl.select("li");

                        for (Element li : pagiSiteLi) {
                            // Lay the li dau tien co duoc
                            Element liATag = li.selectFirst("a");
                            if (liATag != null) {
                                String pagilink = "https://nguoikesu.com" + liATag.attr("href");

                                if (!siteLinks.contains(pagilink)) {
                                    System.out.println(liATag.text() + " - " + pagilink);
                                    siteLinks.add(pagilink);
                                }
                            }
                        }
                    } catch (IOException err) {
                        throw new RuntimeException(err);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Cac truong hop cua dia diem
    public static boolean locationCheck(String location) {
        return location.equals("Khu vực") ||
                location.equals("Địa chỉ") ||
                location.equals("Địa điểm") ||
                location.equals("Vị trí");
    }

    // Cac truong hop cua thoi gian xay dung, hoan thanh
    public static boolean timeCheck(String time) {
        return time.equals("Khởi công") ||
                time.equals("Hoàn thành") ||
                time.equals("Thành lập") ||
                time.equals("Khởi lập");
    }

    public static void crawlSiteData(String link) {
        System.out.println("\nDang crawl di tich o link: " + link);

        try {
            Document doc = Jsoup.connect(link).timeout(120000).get();

            String name = "Chưa rõ"; // Ten di tich
            String time = "Chưa rõ"; // Thoi gian khoi cong / xay dung / xuat hien
            String description = "Chưa rõ"; // Mo ta ngan
            String location = "Chưa rõ"; // Dia diem
            String backgroundStory = "Chưa rõ"; // Truyen thuyet ???
            String founder = "Chưa rõ"; // Nguoi sang lap?
            String festival = "Chưa rõ"; // Le hoi (neu co)
            ArrayList<String> relatedChar = new ArrayList<>(); // Nhan vat lien quan

            // Lay bang thong tin (neu co)
            Element infoTable = doc.selectFirst("table[class=infobox]");

            if (infoTable != null) {
                Elements infoTableRows = infoTable.select("tr");
                int numberOfTr = infoTableRows.size();

                for (int i = 0; i < numberOfTr; ++i) {
                    Element tableHead = infoTableRows.get(i).selectFirst("th");

                    if (tableHead != null) {
                        tableHead.select("sup").remove();
                        String tableHeadContent = tableHead.text();
                        if (i == 0) {
                            // Ten cua dia danh
                            name = tableHeadContent;
                        } else {
                            // Neu chua co gia tri cho dia diem va th dag chi dia diem
                            if (locationCheck(tableHeadContent) && location.equals("Chưa rõ")) {
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                if (tableData != null) {
                                    tableData.select("sup").remove();
                                    location = tableData.text();
                                }
                            } else if (timeCheck(tableHeadContent)) {
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                if (tableData != null) {
                                    tableData.select("sup").remove();
                                    if (time.equals("Chưa rõ")) {
                                        // Thoi gian khoi tao
                                        time = tableData.text();
                                    } else {
                                        // Thoi gian hoan thanh (neu co)
                                        time = time + " - " + tableData.text();
                                    }
                                }
                            } else if (tableHeadContent.equals("Người sáng lập") && founder.equals("Chưa rõ")) {
                                Element tableData = infoTableRows.get(i).selectFirst("td");
                                if (tableData != null) {
                                    tableData.select("sup").remove();
                                    founder = tableData.text();
                                }
                            }
                        }
                    }
                }
            }

            // Loc text
            Element contentBody = doc.selectFirst("div[class=com-content-article__body]");
            // Lay cac the con cua contentBody => loc text tu cac the p
            Elements contentBodyElements = contentBody.children();
            boolean isFirstP = false; // Kiem tra xem co phai the p dau tien tim duoc k
            // Hau nhu lay thong tin tong quat the p dau tien

            for (Element item : contentBodyElements) {
                if (item.tagName().equals("p")) {
                    Element firstParagraph = item;
                    firstParagraph.select("sup").remove(); // [class~=(annotation).*]
                    String firstPContent = firstParagraph.text();
                    Pattern p;
                    Matcher m;

                    // The b dau tien la ten cua di tich?
                    if (name.equals("Chưa rõ")) {
                        Element firstBTag = firstParagraph.selectFirst("b");
                        if (firstBTag != null) {
                            name = firstBTag.text();
                        }
                    }

                    // Lay mo ta ngan gon cua di tich
                    // Loai bo doan trong ngoac dau tien cho chac :D
                    if (description.equals("Chưa rõ")) {
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

                        p = Pattern.compile("(là|bao gồm|nơi)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                            String result = m.group(0);
                            if (!result.contains(":")) {
                                description = result.substring(0, result.length() - 1);
                            }
                        }
                    }

                    // Lay ra dia chi cua di tich
                    if (location.equals("Chưa rõ")) {
                        firstPContent = firstParagraph.text();

                        do {
                            p = Pattern.compile("(((nằm|tọa lạc|dựng|xây dựng) (ở|trên))|(thuộc))[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (findResult.contains("triều đại")) {
                                    // Bo qua chu thuoc hien tai?
                                    int skipIndex = firstPContent.indexOf("triều đại");
                                    firstPContent = firstPContent.substring(skipIndex + 9);
                                } else {
                                    location = findResult.substring(0, findResult.length() - 1);
                                    break;
                                }
                            } else break;
                        } while (true);
                    }

                    // Lay thoi gian xay dung
                    if (time.equals("Chưa rõ")) {
                        firstPContent = firstParagraph.text();
                        p = Pattern.compile("(xây dựng) (từ|vào|trong)[^.]*[.(]", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                        String findResult = m.group(0);
                        time = findResult.substring(0, findResult.length() - 1);
                        }
                    }

                    // Lay ra le hoi (neu co)
                    if (festival.equals("Chưa rõ")) {
                        p = Pattern.compile("(Lễ hội)[^.]*[.]");
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                            String findResult = m.group(0);
                            festival = findResult.substring(0, findResult.length() - 1);
                        }
                    }

                    // Lay ra truyen thuyet
                    if (backgroundStory.equals("Chưa rõ")) {
                        p = Pattern.compile("(truyền thuyết)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(firstPContent);

                        if (m.find()) {
                            String findResult = m.group(0);
                            backgroundStory = findResult.substring(0, findResult.length() - 1);
                        }
                    }

                    // Lay ra cac the a tim duoc trong p => tim nhan vat lien quan
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
            System.out.println("Built date: " + time);
            System.out.println("Location: " + location);
            System.out.println("Founder: " + founder);
//            System.out.println("Related Myth: " + backgroundStory);
            System.out.println("Related festival: " + festival);
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

            // Tao object moi va them vao list di tich
            new HistoricSite(
                name,
                time,
                location,
                founder,
                festival,
                description,
                relatedChar
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getDataFromLink() {
        for (String link : siteLinks) {
            crawlSiteData(link);
        }
    }

    public static void crawlData() {
        getAllSiteLinks();
        getDataFromLink();
    }

    public crawlHistorySite() {
        crawlData();
    }
}
