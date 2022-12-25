package history.era;

import history.HistoricalEntity;
import history.Storable;
import json.JSON;

import java.io.IOException;

/**
 * Đây là lớp cho thực thể triều đại lịch sử
 * gồm các thuộc tính
 *      name: tên triều đại
 *      aliases: tên gọi khác
 *      fromYear: năm bắt đầu
 *      toYear: năm kết thúc
 *      precededEra: triều đại liền trước
 *      succeededEra: triều đại kế tiếp
 */

public class Era extends HistoricalEntity implements Storable {
    private static long nbEras = 0;
    private int fromYear;
    private int toYear;
    private Era precededEra;
    private Era succeededEra;

    public int getFromYear() {
        return fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getToYear() {
        return toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    public Era getPrecededEra() {
        return precededEra;
    }

    public void setPrecededEra(Era precededEra) {
        this.precededEra = precededEra;
    }

    public Era getSucceededEra() {
        return succeededEra;
    }

    public void setSucceededEra(Era succeededEra) {
        this.succeededEra = succeededEra;
    }

    public Era() {
        super();
        this.id = ++nbEras;
    }

    public Era(String name, int fromYear, int toYear){
        super(name);
        this.id = ++nbEras;
        this.fromYear = fromYear;
        this.toYear = toYear;
    }

    public Era(String name){
        super(name);
        this.id = ++nbEras;
    }

    public void save() throws IOException {
        String filename = "\\Era" + this.id + ".json";
        JSON.writeJSON(filename, this);
    }
}
