package crawl.era;

public class TestLoop {
    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            if (i == 1) {
                while (i < 3) {
                    i++;
                }
            }
            System.out.println(i);
        }
    }
}
