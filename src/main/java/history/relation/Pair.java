package history.relation;

/**
 * Đây là lớp được tạo thủ công để lưu trữ
 * liên kết, sử dụng như một cấu trúc dữ liệu
 * theo cặp (key - value)
 * Lý do tạo :trong các thư viện được thêm vào chương trình
 * có javafx.util.Pair không thể sử dụng được
 * để phục vụ nhu cầu thay đổi dữ liệu (tạo liên kết).
 * @param <K> key
 * @param <V> value
 */
public class Pair<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + "";
    }

    public Pair(){}

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
}
