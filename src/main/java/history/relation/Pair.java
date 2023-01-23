package history.relation;

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
        return this.key + " - " + this.value;
    }

    public Pair(){}

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
}
