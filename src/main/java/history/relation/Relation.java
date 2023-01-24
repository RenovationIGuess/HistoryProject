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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            if (f.getRelatedFiguresId().size() > 0) {
                for (Map.Entry<String, Integer> entry : f.getRelatedFiguresId().entrySet()) {
                    // Duyet qua cac figures
                    // Neu figures nao co ten == ten nhan vat trong relate char
                    // phan le hoi thi lay id cua no
                    boolean found = false;
                    for (HistoricalFigure c : listOfFigures) {
                        Pattern p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(c.getName());

                        if (m.find()) {
                            found = true;
                            relatedCharList.put(c.getName(), c.getId());
                            break;
                        }
                    }
                    if (!found) relatedCharList.put(entry.getKey(), null);
                }
                f.setRelatedFigures(relatedCharList);
            }
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
                    boolean found = false;
                    for (HistoricalFigure c : listOfFigures) {
                        Pattern p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(c.getName());

                        if (m.find()) {
                            found = true;
                            relatedCharList.put(c.getName(), c.getId());
                            break;
                        }
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

        // Tao lien ket giua nhan vat voi trieu dai lich su
        for (Era e : listOfEras) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : e.getListOfKingsId().entrySet()) {
                String kingName = entry.getKey();
                boolean found = false;
                for (HistoricalFigure c : listOfFigures) {
                    // Tim king name trong name boi vi no co the
                    // Co truong hop ten nhan vat lich su co chu Han => length >
                    Pattern p = Pattern.compile(Pattern.quote(kingName), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(c.getName());

                    if (m.find()) {
                        relatedCharList.put(c.getName(), c.getId());
                        found = true;
                        break;
                    }
                }
                if (!found) relatedCharList.put(kingName, null);
            }
            e.setListOfKingsId(relatedCharList);
        }

        // Tao lien ket trong chinh nhan vat => era, father, mother, precededBy, succeededBy
        for (HistoricalFigure hf : listOfFigures) {
            // Lien ket voi cha, me, tien/ke nhiem
            String fatherName = hf.getFather().getKey();
            String motherName = hf.getMother().getKey();
            String precededName = hf.getPrecededBy().getKey();
            String succeededName = hf.getSucceededBy().getKey();
            // Dung de thoat vong lap neu da co duoc tat ca id can thiet
            // 0 - father, 1 - mother, 2 - tien nhiem, 3 - ke nhiem
//            boolean[] escapeLoop = {false, false, false, false};

            for (HistoricalFigure c : listOfFigures) {
                if (hf.getFather().getValue() == null) {
                    Pattern p = Pattern.compile(Pattern.quote(fatherName), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(c.getName());

                    if (m.find()) {
                        hf.setFather(fatherName, c.getId());
                    }
                }
                if (hf.getMother().getValue() == null) {
                    Pattern p = Pattern.compile(Pattern.quote(motherName), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(c.getName());

                    if (m.find()) {
                        hf.setMother(motherName, c.getId());
                    }
                }
                if (hf.getPrecededBy().getValue() == null) {
                    Pattern p = Pattern.compile(Pattern.quote(precededName), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(c.getName());

                    if (m.find()) {
                        hf.setPrecededBy(precededName, c.getId());
                    }
                }
                if (hf.getSucceededBy().getValue() == null) {
                    Pattern p = Pattern.compile(Pattern.quote(succeededName), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(c.getName());

                    if (m.find()) {
                        hf.setSucceededBy(succeededName, c.getId());
                    }
                }
            }

            // Lien ket trieu dai
            String eraName = hf.getEra().getKey();
            for (Era e : listOfEras) {
                Pattern p = Pattern.compile(Pattern.quote(eraName), Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(e.getName());

                if (m.find()) {
                    hf.setEra(eraName, e.getId());
                    break;
                }
            }
        }

        // Tao lien ket su kien voi nhan vat
        for (Event e : listOfEvents) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : e.getRelatedFiguresId().entrySet()) {
                String charName = entry.getKey();
                boolean found = false;
                for (HistoricalFigure c : listOfFigures) {
                    // Tim char name trong name boi vi no co the
                    // Co truong hop ten nhan vat lich su co chu Han => length >
                    Pattern p = Pattern.compile(Pattern.quote(charName), Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(c.getName());

                    if (m.find()) {
                        found = true;
                        relatedCharList.put(c.getName(), c.getId());
                        break;
                    }
                }
                if (!found) relatedCharList.put(charName, null);
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
