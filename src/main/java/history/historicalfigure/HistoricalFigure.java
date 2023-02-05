package history.historicalfigure;

import history.HistoricalEntity;
import history.relation.Pair;

import java.util.List;

/**
 * Đây là lớp cho loại thực thể Nhân vật lịch sử
 * gồm các thuộc tính:
 *      realName: tên thật
 *      born: ngày sinh và nơi sinh
 *      died: ngày mất và nơi mất
 *      overview: mô tả ngắn gọn
 *      workTime: thời gian tại chức
 *      era: thời đại sống
 *      father: bố (liên kết ID)
 *      mother: mẹ (liên kết ID)
 *      precededBy: tiền nhiệm (liên kết ID)
 *      succeededBy: kế nhiệm (liên kết ID)
 */
public class HistoricalFigure extends HistoricalEntity {
    private String realName;
    private String born;
    private String died;
    private String overview;
    private String workTime;
    private Pair<String, Integer> era = new Pair<>();
    private Pair<String, Integer> father = new Pair<>();
    private Pair<String, Integer> mother = new Pair<>();
    private Pair<String, Integer> precededBy = new Pair<>();
    private Pair<String, Integer> succeededBy = new Pair<>();

    /* Getters */

    public String getBorn() {
        return born;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getRealName() {
        return realName;
    }

    public String getDied() {
        return died;
    }

    public String getOverview() {
        return overview;
    }

    public Pair<String, Integer> getEra() {
        return era;
    }

    public Pair<String, Integer> getFather() {
        return father;
    }

    public Pair<String, Integer> getMother() {
        return mother;
    }

    public Pair<String, Integer> getPrecededBy() {
        return precededBy;
    }

    public Pair<String, Integer> getSucceededBy() {
        return succeededBy;
    }
    /* Setters */
    public void setBorn(String born) {
        this.born = born;
    }

    public void setDied(String died) {
        this.died = died;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setEra(String name, Integer id) {
        this.era.setEntry(name, id);
    }

    public void setFather(String name, Integer id) {
        this.father.setEntry(name, id);
    }

    public void setMother(String name, Integer id) {
        this.mother.setEntry(name, id);
    }

    public void setPrecededBy(String name, Integer id) {
        this.precededBy.setEntry(name, id);
    }

    public void setSucceededBy(String name, Integer id) {
        this.succeededBy.setEntry(name, id);
    }

    /* Constructors */
    public HistoricalFigure(){
        super();
        this.id = HistoricalFigures.collection.getSequenceId();
        HistoricalFigures.collection.add(this);
    }

    public HistoricalFigure(String name) {
        super(name);
        this.id = HistoricalFigures.collection.getSequenceId();
        HistoricalFigures.collection.add(this);
    }

    public HistoricalFigure(
            String name,
            String realName,
            List<String> alterName,
            String birth,
            String lost,
            String position,
            String workTime,
            String era,
            String father,
            String mother,
            String preceded,
            String succeeded
    ) {
        super(name, alterName);
        this.id = HistoricalFigures.collection.getSequenceId();
        this.realName = realName;
        this.born = birth;
        this.died = lost;
        this.overview = position;
        this.workTime = workTime;
        this.era = new Pair<>(era, null);
        this.father = new Pair<>(father, null);
        this.mother = new Pair<>(mother, null);
        this.precededBy = new Pair<>(preceded, null);
        this.succeededBy = new Pair<>(succeeded, null);
        HistoricalFigures.collection.add(this);
    }
}
