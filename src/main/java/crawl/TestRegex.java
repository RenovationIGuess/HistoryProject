package crawl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
    public static void main(String[] args) {

        // Lý Hùng (sinh năm 1969) là một diễn viên điện ảnh kiêm ca sĩ của Việt Nam. Anh từng nổi danh trong giới diễn viên điện ảnh Việt Nam thập niên 1990, và tham gia đóng trong một số phim hợp tác với điện ảnh Hồng Kông.
        // Lý Hồng Chương (tiếng Hán giản thể: 李鸿章; phồn thể: 李鴻章; bính âm: Lǐ Hóngzhāng; phiên âm Wade–Giles: Li Hung-chang), phiên âm tiếng Anh: Li Hongzhang) (1823 - 1901), là một đại thần triều đình nhà Thanh trong lịch sử Trung Quốc. Ông là người tỉnh An Huy, xuất thân gia đình quan lại. Trong cuộc đời quan trường của mình ông đã thành lập Hoài quân tham gia cùng với Tăng Quốc Phiên, Tả Tông Đường trấn áp phong trào Thái Bình Thiên Quốc. Vì có công lao to lớn, ông được bổ nhiệm làm tổng đốc Hồ quảng, tổng đốc Trực Lệ kiêm Bắc dương đại thần, Tổng đốc Lưỡng Quảng, Túc nghị nhất đẳng bá.
        // Lý Hằng (chữ Hán: 李恒), tự là Đức Khanh (德卿), (1236 – 1285), người Đảng Hạng, là một trong những tướng lĩnh xuất sắc của nhà Nguyên. Lý Hằng mất vì trúng tên độc khi xâm lăng Đại Việt.
        // Lê Khắc Cẩn (chữ Hán: 黎克謹) người làng Hạnh Thị (thuộc huyện An Lão, Hải Phòng ngày nay) đỗ Hoàng giáp khoa Nhâm Tuất (1862) và từng đảm nhiệm nhiều chức vụ dưới triều Tự Đức. Ông là người Hải Phòng duy nhất đỗ Tiến sĩ Nho học dưới triều nhà Nguyễn. Lê Khắc Cẩn cũng là người có văn tài, nhiều trước tác của ông hiện được lưu giữ tại Viện Hán - Nôm và một số tác phẩm đã được xuất bản trong tuyển tập Thơ văn Lê Khắc Cẩn.

        // Bùi Quốc Khái (chữ Hán: 裴國愾, 1141-1234) là người đỗ đầu khoa thi Tiến sĩ năm Trinh Phù thứ 10 (Ất Tỵ, 1185) dưới thời vua Lý Cao Tông (ở ngôi: 1176-1210), nước Đại Việt (nay là Việt Nam).

        String paragraph = "Khuông Việt (匡越, 933-1011) trước tên là Ngô Chân Lưu (吳真流), tu chùa Phật Đà, làng Cát Lợi, Thường Lạc (nay là Vệ Linh, Sóc Sơn, Hà Nội).1 Sư người Cát Lợi, hậu duệ nhà Ngô (吳), thuộc đời (hay thế hệ) thứ 4, dòng Vô Ngôn Thông. Đại sư Khuông Việt là một vị thiền sư được phong Tăng thống đầu tiên trong lịch sử Phật giáo Việt Nam.2";

        Pattern p = Pattern.compile("\\(([^)]*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(paragraph);

        while (m.find()) {
            String result = m.group(0);

            Pattern checkValid = Pattern.compile("sinh|tháng|năm|-|–");
            Matcher matchValid = checkValid.matcher(result);
            if (matchValid.find()) {
                System.out.println(result);
                break;
            } else {
                System.out.println("Not valid!");
                int start = paragraph.indexOf(')');
                paragraph = paragraph.substring(start + 1);
                m = p.matcher(paragraph);
            }
            System.out.println(result);
        }

        boolean outLoop = true;
        while (outLoop) {
            int start = paragraph.indexOf("là");
            // Neu la chu in hoa || hoac la con ... => x
            if (start != -1 && start < paragraph.length() - 3) {
                if (
                        Character.isUpperCase(paragraph.charAt(start + 3)) ||
                                paragraph.charAt(start + 2) != ' '
                ) {
                    System.out.println("Char at start + 3: \'" + paragraph.charAt(start + 3) + "\'");
                    System.out.println("Char at start + 2: \'" + paragraph.charAt(start + 2) + "\'");
                    // Neu ko phai la ten, khong la lang mac hay j
                    // Kiem tra xem co phai chu lam hay k
                    if (paragraph.charAt(start + 2) == 'm' && paragraph.charAt(start + 3) == ' ') {
                        outLoop = false;
                    } else paragraph = paragraph.substring(start + 3);
                } else outLoop = false;
            } else outLoop = false;
        }

        System.out.println(paragraph);

        p = Pattern.compile("(là|làm)[^.]*[.]");
        m = p.matcher(paragraph);

        if (m.find()) {
            String result = m.group(0);
            System.out.println(result.substring(0, result.length() - 1));
        } else {
            System.out.println("Not found!");
        }
//        else {
//            System.out.println("No matches found!");
//        }
    }
}
