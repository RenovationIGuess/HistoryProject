package crawl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Crawl {
    /**
     * Kiểm tra xem phần text đang lấy có đúng là
     * liên quan đến chức vụ không?
     * @param position xâu muốn kiểm tra
     * @return true nếu đúng đang nói về chức vụ / còn lại là false
     */
    public static boolean positionCheck(String position) {
        return position.equalsIgnoreCase("Công việc") ||
                position.equalsIgnoreCase("Nghề nghiệp") ||
                position.equalsIgnoreCase("Cấp bậc") ||
                position.equalsIgnoreCase("Đơn vị") ||
                position.equalsIgnoreCase("Chức quan cao nhất") ||
                position.equalsIgnoreCase("Chức vụ") ||
                position.equalsIgnoreCase("Vị trí");
//        position.contains("Hoàng đế") ||
//                position.contains("Hoàng hậu") ||
//                position.contains("Vua") ||
//                position.contains("Vương") ||
    }

    /**
     * Dùng để kiểm tra xem đoạn xâu dùng Regex để lọc
     * có phải đoạn xâu mô tả chức vụ của nhân vật ls không?
     * Nếu chứa những xâu ở dưới thì tức là mô tả thân thế,...
     * => không đúng
     * @param text đoạn xâu muốn kiểm tra
     * @return true nếu là mô tả thân thế / false nếu ngược lại
     */
    public static boolean checkNotPosition(String text) {
        return text.contains("làng") ||
                text.contains("con của");
    }

    /**
     * Dùng để kiểm tra xem có phải phần đang xét
     * là thời gian làm việc với chức vụ A của nvat ls B không?
     * @param workTime xâu cần check
     * @return true nếu đúng những trường hợp ở dưới
     */
    public static boolean workTimeCheck(String workTime) {
        return workTime.equalsIgnoreCase("Trị vì") ||
                workTime.equalsIgnoreCase("trị vì") ||
                workTime.equalsIgnoreCase("Tại vị") ||
                workTime.equalsIgnoreCase("Nhiệm kỳ") ||
                workTime.equalsIgnoreCase("Năm tại ngũ") ||
                workTime.equalsIgnoreCase("Hoạt động");
    }

    /**
     * Kiểm tra xem phần đang xét có phải nói về
     * Bố của nvat lsu không?
     * @param father
     * @return true nếu đúng các cases
     */
    public static boolean fatherCheck(String father) {
        return father.equalsIgnoreCase("Thân phụ") ||
                father.equalsIgnoreCase("Cha") ||
                father.equalsIgnoreCase("Bố mẹ");
    }

    /**
     * Tương tự ở trên nhưng là kiểm tra mẹ
     * @param mother
     * @return true nếu đúng các cases
     */
    public static boolean motherCheck(String mother) {
        return mother.equalsIgnoreCase("Thân mẫu") ||
                mother.equalsIgnoreCase("Mẹ") ||
                mother.equalsIgnoreCase("Bố mẹ");
    }

    /**
     * Kiểm tra triều đại
     * @param era
     * @return
     */
    public static boolean eraCheck(String era) {
        return era.equalsIgnoreCase("Hoàng tộc") ||
                era.equalsIgnoreCase("Triều đại") ||
                era.equalsIgnoreCase("Gia tộc") ||
                era.equalsIgnoreCase("Kỷ nguyên");
    }

    /**
     * Kiểm tra ngày, nơi sinh
     * @param birth
     * @return
     */
    public static boolean birthCheck(String birth) {
        return birth.equalsIgnoreCase("Ngày sinh") ||
                birth.equalsIgnoreCase("Sinh");
    }

    /**
     * Kiểm tra tên thật => realName
     * @param realName
     * @return
     */
    public static boolean realNameCheck(String realName) {
        return realName.equalsIgnoreCase("Húy") ||
                realName.equalsIgnoreCase("Tên thật") ||
                realName.equalsIgnoreCase("tên thật") ||
                realName.equalsIgnoreCase("Tên đầy đủ") ||
                realName.equalsIgnoreCase("Tên húy");
    }

    /**
     * Kiểm tra tên khác => alterName
     * @param alterName
     * @return
     */
    public static boolean alterNameCheck(String alterName) {
        return alterName.equalsIgnoreCase("Thụy hiệu") ||
                alterName.equalsIgnoreCase("Niên hiệu") ||
                alterName.equalsIgnoreCase("Tên khác") ||
                alterName.equalsIgnoreCase("Tước hiệu") ||
                alterName.equalsIgnoreCase("Tước vị") ||
                alterName.equalsIgnoreCase("Hiệu") ||
                alterName.equalsIgnoreCase("Bút danh") ||
                alterName.equalsIgnoreCase("Miếu hiệu");
    }

    // Cac truong hop cua dia diem
    public static boolean locationCheck(String location) {
        return location.equalsIgnoreCase("Khu vực") ||
                location.equalsIgnoreCase("Địa chỉ") ||
                location.equalsIgnoreCase("Địa điểm") ||
                location.equalsIgnoreCase("Vị trí");
    }

    // Cac truong hop cua thoi gian xay dung, hoan thanh
    public static boolean timeCheck(String time) {
        return time.equalsIgnoreCase("Khởi công") ||
                time.equalsIgnoreCase("Hoàn thành") ||
                time.equalsIgnoreCase("Thành lập") ||
                time.equalsIgnoreCase("Khởi lập");
    }

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
}
