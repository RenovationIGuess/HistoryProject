package history.relation;

import crawl.festival.CrawlFestival;
import crawl.era.CrawlEra;
import crawl.event.CrawlEvent;
import crawl.historicsite.CrawlHistoricSite;
import crawl.historicalfigure.CrawlHistoricalFigure;
import history.model.Era;
import history.collection.Eras;
import history.model.Event;
import history.collection.Events;
import history.model.Festival;
import history.collection.Festivals;
import history.model.HistoricalFigure;
import history.collection.HistoricalFigures;
import history.model.HistoricSite;
import history.collection.HistoricSites;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Relation {
    public Relation() {
        crawlData();
        createRelation();
    }

    public static void crawlData() {
        CrawlFestival.crawlData();
        CrawlHistoricSite.crawlData();
        CrawlEvent.crawlData();
        CrawlEra.crawlData();
        CrawlHistoricalFigure.crawlData();
    }

    // Dùng dể kiểm tra xem có phải hoàng đế?
    public static boolean checkKing(String s) {
        String lowerCase = s.toLowerCase();
        return lowerCase.contains("đế") ||
                lowerCase.contains("vương") ||
                lowerCase.contains("vua");
//                lowerCase.contains("chúa");
    }

    // Kiem tra hoang hau
    public static boolean checkQueen(String s) {
        String lowerCase = s.toLowerCase();
        return lowerCase.contains("hoàng hậu");
    }

    // Loai bo nhung tu khoa lien quan den gia toc, trieu dai
    public static boolean eraFilter(String s) {
        return s.equalsIgnoreCase("vương") ||
                s.equalsIgnoreCase("nhà") ||
                s.equalsIgnoreCase("gia") ||
                s.equalsIgnoreCase("tộc");
    }

    /**
     * Tao lien ket giua nhan vat voi le hoi
     */
    public static void addCharAndFesRelation() {
        // Lay nhan vat
        ObservableList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getData();
        if (listOfFigures.size() == 0) {
            CrawlHistoricalFigure.crawlData();
        }
        // Lay le hoi
        ObservableList<Festival> listOfFestivals = Festivals.collection.getData();
        if (listOfFestivals.size() == 0) {
            CrawlFestival.crawlData();
        }

        for (Festival f : listOfFestivals) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : f.getRelatedFiguresId().entrySet()) {
                // Duyet qua cac figures
                // Neu figures nao co ten == ten nhan vat trong relate char
                // phan le hoi thi lay id cua no
                if (entry.getKey().equals("")) continue;
                boolean found = false;

                // Tim truoc = ten that de tranh trung lap
                // Khi tim = ten khac
                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                        found = true;
                        relatedCharList.put(entry.getKey(), c.getId());
                        break;
                    }
                }

                // Neu ten that k co thi tim theo cac ten khac
                if (!found) {
                    for (HistoricalFigure c : listOfFigures) {
                        Set<String> allPossibleNames = c.fetchAllPossibleNames();
                        for (String name : allPossibleNames) {
                            // Loc het chu TQ voi dau ngoac ra
                            String nameToCompare = name; // Ten nay se dung de tim nhan vat
                            Pattern p = Pattern.compile("[^\\p{IsHan}]*[\\p{IsHan}]", Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(name);

                            // Neu ten co chu TQ thi loc khong thi thoi
                            // Neu gap th chu TQ o dau tien thi kiem tra xem xau
                            // voi do dai xau trc - 1 co == "" khong => Muc dich bo ky tu TQ
                            // Neu co thi cho no = ten cu
                            if (m.find()) {
                                String result = m.group();
                                if (!result.substring(0, result.length() - 1).equals("")) {
                                    // Loc ra cac dau ngoac hoac chu Han neu co
                                    if (result.contains("(")) {
                                        nameToCompare = result.substring(0, result.lastIndexOf("(")).trim();
                                    } else {
                                        nameToCompare = result.substring(0, result.length() - 1).trim();
                                    }
                                }
                            }

                            // Loc ra cac dau ngoac - trong truong hop ten k co chu Han
                            // K tinh truong hop co ngoac o dau VD - (abc) abc...
                            if (
                                    nameToCompare.equals(name) &&
                                            nameToCompare.contains("(") &&
                                            nameToCompare.lastIndexOf("(") > 0
                            ) {
                                nameToCompare = name.substring(0, name.lastIndexOf("(")).trim();
                            }

                            // Neu ten de so sanh = ten tu tieu de crawl duoc
                            // thi chi viec so sanh giong nhau
                            // con neu la ten khac cua nhan vat thi kiem tra chua xau
                            if (nameToCompare.equals(c.getName())) {
                                if (entry.getKey().equalsIgnoreCase(nameToCompare)) {
                                    found = true;
                                    relatedCharList.put(entry.getKey(), c.getId());
                                    break;
                                }
                            } else {
                                if (entry.getKey().length() > nameToCompare.length()) {
                                    p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                    m = p.matcher(entry.getKey());
                                } else {
                                    p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
                                    m = p.matcher(nameToCompare);
                                }
                                if (m.find()) {
                                    found = true;
                                    relatedCharList.put(entry.getKey(), c.getId());
                                    break;
                                }
                            }
                        }
                        if (found) break;
                    }
                    if (!found) relatedCharList.put(entry.getKey(), null);
                }
            }
            f.setRelatedFigures(relatedCharList);
        }
    }

    /**
     * Tao lien ket giua nhan vat, le hoi voi di tich
     */
    public static void addCharFesSiteRelation() {
        // Lay nhan vat
        ObservableList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getData();
        if (listOfFigures.size() == 0) {
            CrawlHistoricalFigure.crawlData();
        }
        // Lay di tich
        ObservableList<HistoricSite> listOfSites = HistoricSites.collection.getData();
        if (listOfSites.size() == 0) {
            CrawlHistoricSite.crawlData();
        }
        // Lay le hoi
        ObservableList<Festival> listOfFestivals = Festivals.collection.getData();
        if (listOfFestivals.size() == 0) {
            CrawlFestival.crawlData();
        }

        for (HistoricSite s : listOfSites) {
            // Luu nhan vat voi le hoi lien quan de dung setter
            Map<String, Integer> relatedCharList = new HashMap<>();
            Map<String, Integer> relatedFesList = new HashMap<>();

            if (s.getRelatedFiguresId().size() > 0) {
                for (Map.Entry<String, Integer> entry : s.getRelatedFiguresId().entrySet()) {
                    // Duyet qua cac char
                    // Neu char nao co ten == ten nhan vat trong relate char
                    // phan site thi lay id cua no

                    if (entry.getKey().equals("")) continue;
                    boolean found = false;

                    for (HistoricalFigure c : listOfFigures) {
                        if (c.getName().equalsIgnoreCase(entry.getKey())) {
                            found = true;
                            relatedCharList.put(entry.getKey(), c.getId());
                            break;
                        }
                    }

                    if (!found) {
                        for (HistoricalFigure c : listOfFigures) {
                            Set<String> allPossibleNames = c.fetchAllPossibleNames();
                            for (String name : allPossibleNames) {
                                // Loc het chu TQ voi dau ngoac ra
                                String nameToCompare = name; // Ten nay se dung de tim nhan vat
                                Pattern p = Pattern.compile("[^\\p{IsHan}]*[\\p{IsHan}]", Pattern.CASE_INSENSITIVE);
                                Matcher m = p.matcher(name);

                                // Neu ten co chu TQ thi loc khong thi thoi
                                if (m.find()) {
                                    String result = m.group();
                                    if (!result.substring(0, result.length() - 1).equals("")) {
                                        if (result.contains("(")) {
                                            nameToCompare = result.substring(0, result.lastIndexOf("(")).trim();
                                        } else {
                                            nameToCompare = result.substring(0, result.length() - 1).trim();
                                        }
                                    }
                                }

                                // Loc ra cac dau ngoac - trong truong hop ten k co chu Han
                                // K tinh truong hop co ngoac o dau VD - (abc) abc...
                                if (
                                        nameToCompare.equals(name) &&
                                                nameToCompare.contains("(") &&
                                                nameToCompare.lastIndexOf("(") > 0
                                ) {
                                    nameToCompare = name.substring(0, name.lastIndexOf("(")).trim();
                                }

                                // Neu ten de so sanh = ten tu tieu de crawl duoc
                                // thi chi viec so sanh giong nhau
                                // con neu la ten khac cua nhan vat thi kiem tra chua xau
                                if (nameToCompare.equals(c.getName())) {
                                    if (entry.getKey().equalsIgnoreCase(nameToCompare)) {
                                        found = true;
                                        relatedCharList.put(entry.getKey(), c.getId());
                                        break;
                                    }
                                } else {
                                    if (entry.getKey().length() > nameToCompare.length()) {
                                        p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                        m = p.matcher(entry.getKey());
                                    } else {
                                        p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
                                        m = p.matcher(nameToCompare);
                                    }
                                    if (m.find()) {
                                        found = true;
                                        relatedCharList.put(entry.getKey(), c.getId());
                                        break;
                                    }
                                }
                            }
                            if (found) break;
                        }
                        // if (!found) relatedCharList.put(entry.getKey(), null);
                    }
                }
                s.setRelatedFigures(relatedCharList);
            }

            // Tao lien ket giua di tich voi le hoi
            for (Map.Entry<String, Integer> entry : s.getRelatedFestivalId().entrySet()) {
                // Duyet qua cac fes
                // Neu fes nao co ten thuoc doan text le hoi cua
                // phan site thi lay id cua no
                //s
                String fesContent = entry.getKey();
                boolean found = false;

                if (!fesContent.equals("Chưa rõ")) {
                    // Dau tien la tim theo ten goc
                    // Neu tim theo ten goc khong thay thi bat dau xu li xau va tim o note, dia chi,...
                    for (Festival f : listOfFestivals) {
                        Pattern p = Pattern.compile(Pattern.quote(f.getName()), Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(fesContent);

                        if (m.find()) {
                            relatedFesList.put(fesContent, f.getId());
                            found = true;
                            break;
                        } else {
                            // Xu li xau
                            String fesName = f.getName();
                            String fesLocation = f.getLocation();
                            String fesNote = f.getNote();

                            // Mot so truong hop loc chu le hoi thi ra duoc dia diem
                            if (fesName.toLowerCase().contains("lễ hội")) {
                                int startIndex = fesName.toLowerCase().indexOf("lễ hội");
                                String possiblyLocation = fesName.substring(startIndex + 6).trim();

                                if (possiblyLocation.equalsIgnoreCase(s.getName())) {
                                    relatedFesList.put(fesContent, f.getId());
                                    found = true;
                                    break;
                                }
                            } else {
                                // Ten khong ra thi check location va note
                                p = Pattern.compile(Pattern.quote(s.getName()), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(fesNote);

                                if (m.find()) {
                                    relatedFesList.put(fesContent, f.getId());
                                    found = true;
                                    break;
                                } else {
                                    // Thu xem le hoi dang xet co cung dia chi voi di tich khong?
                                    // Neu co le hoi => dia chi giong nhau co the thuoc
//                                if (!fesLocation.equals("")) {
//                                    String[] splitLocations = fesLocation.split(",");
//                                    for (String splitLocation : splitLocations) {
//                                        p = Pattern.compile(Pattern.quote(splitLocation.trim()), Pattern.CASE_INSENSITIVE);
//                                        m = p.matcher(s.getLocation());
//
//                                        if (m.find()) {
//                                            relatedFesList.put(fesContent, f.getId());
//                                            found = true;
//                                            break;
//                                        }
//                                    }
//                                }

                                    // Neu tim theo note cua fes va dia chi cua fes khong co thi thu tim
                                    // ten fes trong overview cua di tich
                                    if (!found) {
                                        p = Pattern.compile(Pattern.quote(fesName), Pattern.CASE_INSENSITIVE);
                                        m = p.matcher(s.getOverview());
                                        if (m.find()) {
                                            relatedFesList.put(fesContent, f.getId());
                                            found = true;
                                            break;
                                        }
                                    } else break;
                                }
                            }
                        }
                    }
                } else {
                    // Neu chua ro thi ta thu tim tu le hoi sang xem co le hoi nao
                    // Relate den di tich nay hay khong
                    for (Festival f : listOfFestivals) {
                        // Xu li xau
                        String fesName = f.getName();
                        String fesNote = f.getNote();

                        // Mot so truong hop loc chu le hoi thi ra duoc dia diem
                        if (fesName.toLowerCase().contains("lễ hội")) {
                            int startIndex = fesName.toLowerCase().indexOf("lễ hội");
                            String possiblyLocation = fesName.substring(startIndex + 6).trim();

                            if (possiblyLocation.equalsIgnoreCase(s.getName())) {
                                relatedFesList.put(fesName, f.getId());
                                found = true;
                                break;
                            }
                        } else {
                            // Ten khong ra thi check note
                            Pattern p = Pattern.compile(Pattern.quote(s.getName()), Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(fesNote);

                            if (m.find()) {
                                relatedFesList.put(fesName, f.getId());
                                found = true;
                                break;
                            } else {
                                // Neu tim theo note cua fes khong co thi thu tim
                                // ten fes trong overview cua di tich
                                if (!found) {
                                    p = Pattern.compile(Pattern.quote(fesName), Pattern.CASE_INSENSITIVE);
                                    m = p.matcher(s.getOverview());
                                    if (m.find()) {
                                        relatedFesList.put(fesName, f.getId());
                                        found = true;
                                        break;
                                    }
                                } else break;
                            }
                        }
                    }
                }
                // Neu le hoi duoc tim thay con khong thi cu de gia tri cu
                if (!found) relatedFesList.put(fesContent, null);
            }
            s.setRelatedFestival(relatedFesList);
        }
    }

    /**
     * Tao lien ket trong chinh nhan vat => era, father, mother, precededBy, succeededBy
     */
    public static void addCharRelation() {
        // Lay nhan vat
        ObservableList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getData();
        if (listOfFigures.size() == 0) {
            CrawlHistoricalFigure.crawlData();
        }
        // Lay era
        ObservableList<Era> listOfEras = Eras.collection.getData();
        if (listOfEras.size() == 0) {
            CrawlEra.crawlData();
        }

        for (HistoricalFigure hf : listOfFigures) {
            // Lien ket voi cha, me, tien / ke nhiem
            String fatherName = hf.getFather().getKey();
            if (
                    fatherName.contains("(") &&
                            fatherName.lastIndexOf("(") > 0
            ) {
                fatherName = fatherName.substring(0, fatherName.lastIndexOf("(")).trim();
            }

            String motherName = hf.getMother().getKey();
            if (
                    motherName.contains("(") &&
                            motherName.lastIndexOf("(") > 0
            ) {
                motherName = motherName.substring(0, motherName.lastIndexOf("(")).trim();
            }

            String precededName = hf.getPrecededBy().getKey();
            if (
                    precededName.contains("(") &&
                            precededName.lastIndexOf("(") > 0
            ) {
                precededName = precededName.substring(0, precededName.lastIndexOf("(")).trim();
            }

            String succeededName = hf.getSucceededBy().getKey();
            if (
                    succeededName.contains("(") &&
                            succeededName.lastIndexOf("(") > 0
            ) {
                succeededName = succeededName.substring(0, succeededName.lastIndexOf("(")).trim();
            }

            // Kiểm tra xem chức vụ của nhân vật hiện tại là hoàng đế hay hoàng hậu?
            // Để có liên kết phần preceded với succeeded đúng hơn
            // Vẫn có thể sót trường hợp
            boolean isHFKing = checkKing(hf.getOverview());
            boolean isHFQueen = checkQueen(hf.getOverview());
            if (!isHFKing || !isHFQueen) {
                for (String alterName : hf.fetchAllPossibleNames()) {
                    if (checkKing(alterName)) {
                        if (!isHFKing) isHFKing = true;
                    }
                    if (checkQueen(alterName)) {
                        if (!isHFQueen) isHFQueen = true;
                    }
                }
            }

            // Lặp 2 lần để cho ra đúng nhân vật
            for (HistoricalFigure c : listOfFigures) {
                boolean isQueen = checkQueen(c.getOverview());
                boolean isKing = checkKing(c.getOverview());
                if (!isQueen || !isKing) {
                    for (String alterName : c.fetchAllPossibleNames()) {
                        if (checkQueen(alterName)) {
                            if (!isQueen) isQueen = true;
                        }
                        if (checkKing(alterName)) {
                            if (!isKing) isKing = true;
                        }
                    }
                }

                if (hf.getFather().getValue() == null) {
                    if (c.getName().equalsIgnoreCase(fatherName)) {
                        hf.setFather(fatherName, c.getId());
                    }
                }
                if (hf.getMother().getValue() == null) {
                    if (c.getName().equalsIgnoreCase(motherName)) {
                        hf.setMother(motherName, c.getId());
                    }
                }
                if (hf.getPrecededBy().getValue() == null) {
                    if (isHFKing) {
                        if (isKing) {
                            if (c.getName().equalsIgnoreCase(precededName)) {
                                hf.setPrecededBy(precededName, c.getId());
                            }
                        }
                    } else {
                        if (isHFQueen) {
                            if (isQueen) {
                                if (c.getName().equalsIgnoreCase(precededName)) {
                                    hf.setPrecededBy(precededName, c.getId());
                                }
                            }
                        } else {
                            if (c.getName().equalsIgnoreCase(precededName)) {
                                hf.setPrecededBy(precededName, c.getId());
                            }
                        }
                    }
                }
                if (hf.getSucceededBy().getValue() == null) {
                    if (isHFKing) {
                        if (isKing) {
                            if (c.getName().equalsIgnoreCase(succeededName)) {
                                hf.setSucceededBy(succeededName, c.getId());
                            }
                        }
                    } else {
                        if (isHFQueen) {
                            if (isQueen) {
                                if (c.getName().equalsIgnoreCase(succeededName)) {
                                    hf.setSucceededBy(succeededName, c.getId());
                                }
                            }
                        } else {
                            if (c.getName().equalsIgnoreCase(succeededName)) {
                                hf.setSucceededBy(succeededName, c.getId());
                            }
                        }
                    }
                }
            }

            for (HistoricalFigure c : listOfFigures) {
                Set<String> allPossibleNames = c.fetchAllPossibleNames();

                boolean isQueen = checkQueen(c.getOverview());
                boolean isKing = checkKing(c.getOverview());
                if (!isQueen || !isKing) {
                    for (String alterName : allPossibleNames) {
                        if (checkQueen(alterName)) {
                            if (!isQueen) isQueen = true;
                        }
                        if (checkKing(alterName)) {
                            if (!isKing) isKing = true;
                        }
                    }
                }

                for (String name : allPossibleNames) {
                    // Loc het chu TQ voi dau ngoac ra
                    String nameToCompare = name; // Ten nay se dung de tim nhan vat
                    Pattern p = Pattern.compile("[^\\p{IsHan}]*[\\p{IsHan}]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(name);

                    // Neu ten co chu TQ thi loc khong thi thoi
                    if (m.find()) {
                        String result = m.group();
                        if (!result.substring(0, result.length() - 1).equals("")) {
                            if (result.contains("(")) {
                                nameToCompare = result.substring(0, result.lastIndexOf("(")).trim();
                            } else {
                                nameToCompare = result.substring(0, result.length() - 1).trim();
                            }
                        }
                    }

                    // Loc ra cac dau ngoac - trong truong hop ten k co chu Han
                    // K tinh truong hop co ngoac o dau VD - (abc) abc...
                    if (
                            nameToCompare.equals(name) &&
                                    nameToCompare.contains("(") &&
                                    nameToCompare.lastIndexOf("(") > 0
                    ) {
                        nameToCompare = name.substring(0, name.lastIndexOf("(")).trim();
                    }

                    // Neu ten de so sanh = ten tu tieu de crawl duoc
                    // thi chi viec so sanh giong nhau
                    // con neu la ten khac cua nhan vat thi kiem tra chua xau
                    if (nameToCompare.equals(c.getName())) {
                        if (hf.getFather().getValue() == null) {
                            if (fatherName.equalsIgnoreCase(nameToCompare)) {
                                hf.setFather(fatherName, c.getId());
                            }
                        }
                        if (hf.getMother().getValue() == null) {
                            if (motherName.equalsIgnoreCase(nameToCompare)) {
                                hf.setMother(motherName, c.getId());
                            }
                        }
                        if (hf.getPrecededBy().getValue() == null) {
                            // Kiểm tra tên đang xét có chữ hoàng hậu không
                            // Trường hợp cụ thể -> Quang Trung
                            if (isHFKing) {
                                if (isKing) {
                                    if (precededName.equalsIgnoreCase(nameToCompare)) {
                                        hf.setPrecededBy(precededName, c.getId());
                                    }
                                }
                            } else {
                                if (isHFQueen) {
                                    if (isQueen) {
                                        if (precededName.equalsIgnoreCase(nameToCompare)) {
                                            hf.setPrecededBy(precededName, c.getId());
                                        }
                                    }
                                } else {
                                    if (precededName.equalsIgnoreCase(nameToCompare)) {
                                        hf.setPrecededBy(precededName, c.getId());
                                    }
                                }
                            }
                        }
                        if (hf.getSucceededBy().getValue() == null) {
                            if (isHFKing) {
                                if (isKing) {
                                    if (succeededName.equalsIgnoreCase(nameToCompare)) {
                                        hf.setSucceededBy(succeededName, c.getId());
                                    }
                                }
                            } else {
                                if (isHFQueen) {
                                    if (isQueen) {
                                        if (succeededName.equalsIgnoreCase(nameToCompare)) {
                                            hf.setSucceededBy(succeededName, c.getId());
                                        }
                                    }
                                } else {
                                    if (succeededName.equalsIgnoreCase(nameToCompare)) {
                                        hf.setSucceededBy(succeededName, c.getId());
                                    }
                                }
                            }
                        }
                    } else {
                        if (hf.getFather().getValue() == null) {
                            if (fatherName.length() > nameToCompare.length()) {
                                p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(fatherName);
                            } else {
                                p = Pattern.compile(Pattern.quote(fatherName), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(nameToCompare);
                            }

                            if (m.find()) {
                                hf.setFather(fatherName, c.getId());
                            }
                        }
                        if (hf.getMother().getValue() == null) {
                            if (motherName.length() > nameToCompare.length()) {
                                p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(motherName);
                            } else {
                                p = Pattern.compile(Pattern.quote(motherName), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(nameToCompare);
                            }

                            if (m.find()) {
                                hf.setMother(motherName, c.getId());
                            }
                        }
                        if (hf.getPrecededBy().getValue() == null) {
                            if (precededName.length() > nameToCompare.length()) {
                                p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(precededName);
                            } else {
                                p = Pattern.compile(Pattern.quote(precededName), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(nameToCompare);
                            }

                            if (m.find()) {
                                if (isHFKing) {
                                    if (isKing) hf.setPrecededBy(precededName, c.getId());
                                } else {
                                    if (isHFQueen) {
                                        if (isQueen) hf.setPrecededBy(precededName, c.getId());
                                    } else hf.setPrecededBy(precededName, c.getId());
                                }
                            }
                        }
                        if (hf.getSucceededBy().getValue() == null) {
                            if (succeededName.length() > nameToCompare.length()) {
                                p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(succeededName);
                            } else {
                                p = Pattern.compile(Pattern.quote(succeededName), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(nameToCompare);
                            }

                            if (m.find()) {
                                if (isHFKing) {
                                    if (isKing) hf.setSucceededBy(succeededName, c.getId());
                                } else {
                                    if (isHFQueen) {
                                        if (isQueen) hf.setSucceededBy(succeededName, c.getId());
                                    } else hf.setSucceededBy(succeededName, c.getId());
                                }
                            }
                        }
                    }
                }
            }

            // Lien ket trieu dai
            // Chi xet nhung nhan vat co trieu dai co ten nhung chua co id
            String eraName = hf.getEra().getKey();
            if (!eraName.equals("Chưa rõ")) {
                if (hf.getEra().getValue() == null) {
                    // Tim qua trong tat ca trieu dai truoc xem co trieu dai
                    // nao giong ten y het khong?
                    // Neu co thi lay khong thi loc xau
                    boolean isValidName = false;
                    for (Era e : listOfEras) {
                        if (e.getName().equalsIgnoreCase(eraName)) {
                            isValidName = true;
                            hf.setEra(eraName, e.getId());
                            break;
                        }
                    }

                    // Neu tu mang tren ma khong tim thay ten trung nhau
                    if (!isValidName) {
                        if (eraName.toLowerCase().contains("nhà")) {
                            // new => ten lowercase
                            String lowerCaseEraName = eraName.toLowerCase();
                            int startIndex = lowerCaseEraName.indexOf("nhà");
                            String shortenEraName = eraName.substring(startIndex + 3);

                            for (Era e : listOfEras) {
                                if (e.getName().toLowerCase().contains("nhà")) {
                                    String lowerCaseCurEraName = e.getName().toLowerCase();
                                    startIndex = lowerCaseCurEraName.indexOf("nhà");
                                    String shortenCurEraName = e.getName().substring(startIndex + 3);

                                    if (shortenCurEraName.length() > shortenEraName.length()) {
                                        Pattern p = Pattern.compile(Pattern.quote(shortenEraName), Pattern.CASE_INSENSITIVE);
                                        Matcher m = p.matcher(shortenCurEraName);

                                        // Neu tim trong trieu dai ma khong thay
                                        // Thi thu tim trong nghe nghiep - position cua may o do :v
                                        if (m.find()) {
                                            // Neu ten 2 trieu dai k giong nhau thi cho vao alias
                                            e.addAlias(eraName);
                                            hf.setEra(e.getName(), e.getId());
                                            break;
                                        }
                                    } else {
                                        Pattern p = Pattern.compile(Pattern.quote(shortenCurEraName), Pattern.CASE_INSENSITIVE);
                                        Matcher m = p.matcher(shortenEraName);

                                        if (m.find()) {
                                            if (!eraName.equalsIgnoreCase(e.getName())) {
                                                e.addAlias(eraName);
                                            }
                                            hf.setEra(e.getName(), e.getId());
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            for (Era e : listOfEras) {
                                if (eraName.length() > e.getName().length()) {
                                    Pattern p = Pattern.compile(Pattern.quote(e.getName()), Pattern.CASE_INSENSITIVE);
                                    Matcher m = p.matcher(eraName);

                                    if (m.find()) {
                                        e.addAlias(eraName);
                                        hf.setEra(e.getName(), e.getId());
                                        break;
                                    }
                                } else {
                                    Pattern p = Pattern.compile(Pattern.quote(eraName), Pattern.CASE_INSENSITIVE);
                                    Matcher m = p.matcher(e.getName());

                                    if (m.find()) {
                                        if (!eraName.equalsIgnoreCase(e.getName())) {
                                            e.addAlias(eraName);
                                        }
                                        hf.setEra(e.getName(), e.getId());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Neu chua co trieu dai => thu tim trong position
                for (Era e : listOfEras) {
                    String eraNameToSearch = e.getName();
                    Pattern p;
                    Matcher m;

                    if (e.getName().contains("nhà")) {
                        p = Pattern.compile("(nhà)[^-–]*([-–]|$)", Pattern.CASE_INSENSITIVE);
                        m = p.matcher(e.getName());

                        if (m.find()) {
                            String result = m.group(0);
                            if (result.contains("-") || result.contains("–")) {
                                // Neu co dau - thi chac chan no se o cuoi xau boi gi regex no the :v
                                // Bo qua ca chu nha
                                result = result.substring(3, result.length() - 1);
                            }

                            eraNameToSearch = result.trim();
                        }
                    }

                    // Trong truong hop ten trieu dai k chua "nha"
                    // Va chua dau - / –
                    if (eraNameToSearch.equals(e.getName())) {
                        boolean endLoop = false; // Dung de thoat vong lap lon
                        String[] splitNames = {eraNameToSearch};
                        if (eraNameToSearch.contains("–")) {
                            splitNames = eraNameToSearch.split("–");
                        } else if (eraNameToSearch.contains("-")) {
                            splitNames = eraNameToSearch.split("-");
                        }
                        // Dung de xu li cac truong hop Dang Trong -/– Chua Nguyen,...
                        for (String splitName : splitNames) {
                            p = Pattern.compile(Pattern.quote(splitName.trim()), Pattern.CASE_INSENSITIVE);
                            m = p.matcher(hf.getOverview());

                            if (m.find()) {
                                endLoop = true;
                                hf.setEra(e.getName(), e.getId());
                                break;
                            }
                        }
                        if (endLoop) break;
                    } else {
                        p = Pattern.compile(Pattern.quote(eraNameToSearch), Pattern.CASE_INSENSITIVE);
                        m = p.matcher(hf.getOverview());

                        if (m.find()) {
                            hf.setEra(e.getName(), e.getId());
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Tao lien ket giua nhan vat voi trieu dai lich su
     * Sau khi da gan trieu dai duoc cho da so nhan vat
     */
    public static void addCharEraRelation() {
        // Lay nhan vat
        ObservableList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getData();
        if (listOfFigures.size() == 0) {
            CrawlHistoricalFigure.crawlData();
        }
        // Lay era
        ObservableList<Era> listOfEras = Eras.collection.getData();
        if (listOfEras.size() == 0) {
            CrawlEra.crawlData();
        }

        for (Era e : listOfEras) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : e.getListOfKingsId().entrySet()) {
                if (entry.getKey().equals("")) continue;
                boolean found = false;
                for (HistoricalFigure c : listOfFigures) {
                    // Kiem tra trieu dai => neu trieu dai cua nhan vat tim thay ten
                    // Khac trieu dai dang xet => sai
                    if (!c.getEra().getKey().equals("Chưa rõ")) {
                        // Neu ten trieu dai khong giong voi ten trieu dai dang xet
                        // Thu kiem tra xem ten cua no co chu in hoa la gi
                        if (!c.getEra().getKey().equalsIgnoreCase(e.getName())) {
                            boolean foundEra = false;
                            String[] splitNames = c.getEra().getKey().split(" ");
                            // Chi can tim thay chu (in hoa ky tu dau tien) lien quan la tinh
                            // K tinh truong hop chu nha,...
                            for (String splitName : splitNames) {
                                if (
                                        Character.isUpperCase(splitName.charAt(0)) &&
                                                !eraFilter(splitName)
                                ) {
                                    Pattern p = Pattern.compile(Pattern.quote(splitName), Pattern.CASE_INSENSITIVE);
                                    Matcher m = p.matcher(e.getName());

                                    if (m.find()) {
                                        foundEra = true;
                                        // relatedCharList.put(entry.getKey(), c.getId());
//                                        if (c.getEra().getValue() == null) {
//                                            c.setEra(e.getName(), e.getId());
//                                        }
                                        break;
                                    }
                                }
                            }
                            // Khong dung trieu dai
                            if (!foundEra) continue;
                        }
                    }

                    // Dung de loai bo truong hop la hoang hau,...
                    // Vi list nvat lien quan la vua
                    boolean isQueen = false;

                    Set<String> allPossibleNames = c.fetchAllPossibleNames();
                    for (String name : allPossibleNames) {
                        // Nhan vat dang xet la hoang hau
                        if (name.toLowerCase().contains("hoàng hậu")) {
                            isQueen = true;
                            break;
                        }
                    }
                    // Neu la hoang hau thi xet th khac
                    if (isQueen) continue;

                    for (String name : allPossibleNames) {
                        // Loc het chu TQ voi dau ngoac ra
                        String nameToCompare = name; // Ten nay se dung de tim nhan vat
                        Pattern p = Pattern.compile("[^\\p{IsHan}]*[\\p{IsHan}]", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(name);

                        // Neu ten co chu TQ thi loc khong thi thoi
                        if (m.find()) {
                            String result = m.group();
                            // Truong hop chu Trung Quoc o dau thi tra ve chuoi rong - ""
                            // Trong truong hop do thi cu de no la ten bthg
                            if (!result.substring(0, result.length() - 1).equals("")) {
                                if (result.contains("(")) {
                                    nameToCompare = result.substring(0, result.lastIndexOf("(")).trim();
                                } else {
                                    nameToCompare = result.substring(0, result.length() - 1).trim();
                                }
                            }
                        }

                        // Loc ra cac dau ngoac - trong truong hop ten k co chu Han
                        // K tinh truong hop co ngoac o dau VD - (abc) abc...
                        if (
                                nameToCompare.equals(name) &&
                                        nameToCompare.contains("(") &&
                                        nameToCompare.lastIndexOf("(") > 0
                        ) {
                            nameToCompare = name.substring(0, name.lastIndexOf("(")).trim();
                        }

                        // Neu ten de so sanh = ten tu tieu de crawl duoc
                        // thi chi viec so sanh giong nhau
                        // con neu la ten khac cua nhan vat thi kiem tra chua xau
                        if (nameToCompare.equals(c.getName())) {
                            if (entry.getKey().equalsIgnoreCase(nameToCompare)) {
                                found = true;
                                relatedCharList.put(entry.getKey(), c.getId());
                                if (c.getEra().getKey().equals("Chưa rõ")) {
                                    c.setEra(e.getName(), e.getId());
                                } else if (c.getEra().getValue() == null) {
                                    if (!e.getAliases().contains(c.getEra().getKey())) {
                                        e.addAlias(c.getEra().getKey());
                                    }
                                    c.setEra(e.getName(), e.getId());
                                }
                                break;
                            }
                        } else {
                            if (entry.getKey().length() > nameToCompare.length()) {
                                p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(entry.getKey());
                            } else {
                                p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
                                m = p.matcher(nameToCompare);
                            }
                            if (m.find()) {
                                found = true;
                                relatedCharList.put(entry.getKey(), c.getId());
                                if (c.getEra().getKey().equals("Chưa rõ")) {
                                    c.setEra(e.getName(), e.getId());
                                } else if (c.getEra().getValue() == null) {
                                    if (!e.getAliases().contains(c.getEra().getKey())) {
                                        e.addAlias(c.getEra().getKey());
                                    }
                                    c.setEra(e.getName(), e.getId());
                                }
                                break;
                            }
                        }
                    }
                    // Neu tim thay thi thoat khoi vong lap
                    if (found) break;
                }
                if (!found) relatedCharList.put(entry.getKey(), null);
            }
            e.setListOfKingsId(relatedCharList);
        }
    }

    /**
     * Tao lien ket su kien voi nhan vat
     */
    public static void addCharEventRelation() {
        // Lay nhan vat
        ObservableList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getData();
        if (listOfFigures.size() == 0) {
            CrawlHistoricalFigure.crawlData();
        }
        // Lay su kien
        ObservableList<Event> listOfEvents = Events.collection.getData();
        if (listOfEvents.size() == 0) {
            CrawlEvent.crawlData();
        }

        for (Event e : listOfEvents) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : e.getRelatedFiguresId().entrySet()) {
                if (entry.getKey().equals("")) continue;
                boolean found = false;

                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                        found = true;
                        relatedCharList.put(entry.getKey(), c.getId());
                        break;
                    }
                }

                if (!found) {
                    for (HistoricalFigure c : listOfFigures) {
                        Set<String> allPossibleNames = c.fetchAllPossibleNames();
                        for (String name : allPossibleNames) {
                            // Loc het chu TQ voi dau ngoac ra
                            String nameToCompare = name; // Ten nay se dung de tim nhan vat
                            Pattern p = Pattern.compile("[^\\p{IsHan}]*[\\p{IsHan}]", Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(name);

                            // Neu ten co chu TQ thi loc khong thi thoi
                            if (m.find()) {
                                String result = m.group();
                                if (!result.substring(0, result.length() - 1).equals("")) {
                                    if (result.contains("(")) {
                                        nameToCompare = result.substring(0, result.lastIndexOf("(")).trim();
                                    } else {
                                        nameToCompare = result.substring(0, result.length() - 1).trim();
                                    }
                                }
                            }

                            // Loc ra cac dau ngoac - trong truong hop ten k co chu Han
                            // K tinh truong hop co ngoac o dau VD - (abc) abc...
                            if (
                                    nameToCompare.equals(name) &&
                                            nameToCompare.contains("(") &&
                                            nameToCompare.lastIndexOf("(") > 0
                            ) {
                                nameToCompare = name.substring(0, name.lastIndexOf("(")).trim();
                            }

                            // Neu ten de so sanh = ten tu tieu de crawl duoc
                            // thi chi viec so sanh giong nhau
                            // con neu la ten khac cua nhan vat thi kiem tra chua xau
                            if (nameToCompare.equals(c.getName())) {
                                if (entry.getKey().equalsIgnoreCase(nameToCompare)) {
                                    found = true;
                                    relatedCharList.put(entry.getKey(), c.getId());
                                    break;
                                }
                            } else {
                                if (entry.getKey().length() > nameToCompare.length()) {
                                    p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
                                    m = p.matcher(entry.getKey());
                                } else {
                                    p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
                                    m = p.matcher(nameToCompare);
                                }
                                if (m.find()) {
                                    found = true;
                                    relatedCharList.put(entry.getKey(), c.getId());
                                    break;
                                }
                            }
                        }
                        if (found) break;
                    }
                    if (!found) relatedCharList.put(entry.getKey(), null);
                }
            }
            e.setRelatedFigures(relatedCharList);
        }
    }

    // Dung de luu tat ca cung luc
    public static void createRelation() {
        // Lay cac gia tri crawl duoc
        // Lay nhan vat
//        ObservableList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getData();
        // Lay di tich
//        ObservableList<HistoricSite> listOfSites = HistoricSites.collection.getData();
        // Lay su kien
//        ObservableList<Event> listOfEvents = Events.collection.getData();
        // Lay era
//        ObservableList<Era> listOfEras = Eras.collection.getData();
        // Lay le hoi
//        ObservableList<Festival> listOfFestivals = Festivals.collection.getData();

//        crawlData();
        addCharAndFesRelation();
        addCharFesSiteRelation();
        addCharRelation();
        addCharEraRelation();
        addCharEventRelation();

        // Luu vao file JSON
        HistoricalFigures.save();
        Events.save();
        Festivals.save();
        Eras.save();
        HistoricSites.save();
    }
}
