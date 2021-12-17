package Structure;

/**
 * 自己实现的Pair类
 *
 * @param <K>
 * @param <V>
 */
public class MyPair<K, V> {
    /**
     * 第一个元素
     */
    private K first;
    /**
     * 第二个元素
     */
    private V second;

    public MyPair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }
}
