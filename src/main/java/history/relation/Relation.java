package history.relation;

import crawl.festival.crawlFestival;
import crawl.timestamp.crawlTimeStamp;
import crawl.event.crawlEvent;
import crawl.site.crawlHistorySite;
import crawl.character.crawlCharacter;
import history.era.Era;
import history.era.Eras;
import history.event.Event;
import history.event.Events;
import history.festival.Festival;
import history.festival.Festivals;
import history.historicalfigure.HistoricalFigure;
import history.historicalfigure.HistoricalFigures;
import history.historicsite.HistoricSite;
import history.historicsite.HistoricSites;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Relation {
    public Relation() {
        crawlData();
        createRelation();
    }

    public void crawlData() {
        new crawlFestival();
        new crawlHistorySite();
        new crawlEvent();
        new crawlTimeStamp();
        new crawlCharacter();
    }

    // Dung de luu tat ca cung luc
    public void createRelation() {
        // Lay cac gia tri crawl duoc
        // Lay nhan vat
        HistoricalFigures crawledFigures = new HistoricalFigures();
        ObservableList<HistoricalFigure> listOfFigures = crawledFigures.collection.getData();

        // Lay di tich
        HistoricSites crawledSites = new HistoricSites();
        ObservableList<HistoricSite> listOfSites = crawledSites.collection.getData();

        // Lay su kien
        Events crawledEvents = new Events();
        ObservableList<Event> listOfEvents = crawledEvents.collection.getData();

        // Lay era
        Eras crawledEras = new Eras();
        ObservableList<Era> listOfEras = crawledEras.collection.getData();

        // Lay le hoi
        Festivals crawledFes = new Festivals();
        ObservableList<Festival> listOfFestivals = crawledFes.collection.getData();

        // Tao lien ket giua nhan vat voi le hoi
        for (Festival f : listOfFestivals) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : f.getRelatedFiguresId().entrySet()) {
                // Duyet qua cac figures
                // Neu figures nao co ten == ten nhan vat trong relate char
                // phan le hoi thi lay id cua no
                if (entry.getKey().equals("")) continue;
                boolean found = false;
                for (HistoricalFigure c : listOfFigures) {
                    Set<String> allPossibleNames = c.fetchAllPossibleNames();
                    for (String name : allPossibleNames) {
                        // Loc het chu TQ voi dau ngoac ra
                        String nameToCompare = ""; // Ten nay se dung de tim nhan vat
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
                            } else {
                                nameToCompare = name;
                            }
                        } else {
                            nameToCompare = name;
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
            f.setRelatedFigures(relatedCharList);
        }

        // Tao lien ket giua nhan vat, le hoi voi di tich
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
                        Set<String> allPossibleNames = c.fetchAllPossibleNames();
                        for (String name : allPossibleNames) {
                            // Loc het chu TQ voi dau ngoac ra
                            String nameToCompare = ""; // Ten nay se dung de tim nhan vat
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
                                } else {
                                    nameToCompare = name;
                                }
                            } else {
                                nameToCompare = name;
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
                s.setRelatedFigures(relatedCharList);
            }

            // Tao lien ket giua di tich voi le hoi
            for (Map.Entry<String, Integer> entry : s.getRelatedFestivalId().entrySet()) {
                // Duyet qua cac fes
                // Neu fes nao co ten thuoc doan text le hoi cua
                // phan site thi lay id cua no
                //
                String fesContent = entry.getKey();
                boolean found = false;
                for (Festival f : listOfFestivals) {
                    Pattern p = Pattern.compile(Pattern.quote(f.getName()), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(fesContent);

                    if (m.find()) {
                        relatedFesList.put(f.getName(), f.getId());
                        found = true;
                        break;
                    }
                }
                // Neu le hoi duoc tim thay con khong thi cu de gia tri cu
                if (!found) relatedFesList.put(fesContent, null);
            }
            s.setRelatedFestival(relatedFesList);
        }

        // Tao lien ket trong chinh nhan vat => era, father, mother, precededBy, succeededBy
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
            // Dung de thoat vong lap neu da co duoc tat ca id can thiet
            // 0 - father, 1 - mother, 2 - tien nhiem, 3 - ke nhiem
//            boolean[] escapeLoop = {false, false, false, false};

            for (HistoricalFigure c : listOfFigures) {
                Set<String> allPossibleNames = c.fetchAllPossibleNames();
                for (String name : allPossibleNames) {
                    // Loc het chu TQ voi dau ngoac ra
                    String nameToCompare = ""; // Ten nay se dung de tim nhan vat
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
                        } else {
                            nameToCompare = name;
                        }
                    } else {
                        nameToCompare = name;
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
                            if (precededName.equalsIgnoreCase(nameToCompare)) {
                                hf.setPrecededBy(precededName, c.getId());
                            }
                        }
                        if (hf.getSucceededBy().getValue() == null) {
                            if (succeededName.equalsIgnoreCase(nameToCompare)) {
                                hf.setSucceededBy(succeededName, c.getId());
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
                                hf.setPrecededBy(precededName, c.getId());
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
                                hf.setSucceededBy(succeededName, c.getId());
                            }
                        }
                    }
                }
            }

            // Lien ket trieu dai
            // Chi xet nhung nhan vat co trieu dai co ten nhung chua co id
            String eraName = hf.getEra().getKey();
            if (!eraName.equals("Chưa rõ") && hf.getEra().getValue() == null) {
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
            } else {
                // Neu chua co trieu dai => thu tim trong position
                for (Era e : listOfEras) {
                    String eraNameToSearch = "";
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
                            result.trim();
                            eraNameToSearch = result;
                        }
                    } else {
                        eraNameToSearch = e.getName();
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

        // Tao lien ket giua nhan vat voi trieu dai lich su
        // Sau khi da gan trieu dai duoc cho da so nhan vat
        for (Era e : listOfEras) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : e.getListOfKingsId().entrySet()) {
                // String kingName = entry.getKey();
                if (entry.getKey().equals("")) continue;
                boolean found = false;
                for (HistoricalFigure c : listOfFigures) {
                    // Kiem tra trieu dai => neu trieu dai cua nhan vat tim thay ten
                    // Khac trieu dai dang xet => sai
                    if (
                            !c.getEra().getKey().equals("Chưa rõ") &&
                                    !c.getEra().getKey().equals(e.getName())
                    ) {
                        continue;
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
                        String nameToCompare = ""; // Ten nay se dung de tim nhan vat
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
                            } else {
                                nameToCompare = name;
                            }
                        } else {
                            nameToCompare = name;
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

        // Tao lien ket su kien voi nhan vat
        for (Event e : listOfEvents) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : e.getRelatedFiguresId().entrySet()) {
                if (entry.getKey().equals("")) continue;
                boolean found = false;
                for (HistoricalFigure c : listOfFigures) {
                    Set<String> allPossibleNames = c.fetchAllPossibleNames();
                    for (String name : allPossibleNames) {
                        // Loc het chu TQ voi dau ngoac ra
                        String nameToCompare = ""; // Ten nay se dung de tim nhan vat
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
                            } else {
                                nameToCompare = name;
                            }
                        } else {
                            nameToCompare = name;
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
            e.setRelatedFigures(relatedCharList);
        }

        // Luu vao file JSON
        crawledFigures.collection.save();
        crawledEvents.collection.save();
        crawledFes.collection.save();
        crawledEras.collection.save();
        crawledSites.collection.save();
    }
}
