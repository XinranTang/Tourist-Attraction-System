package Structure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 自己写的HashMap类
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> implements Iterable {
    // 默认容量16
    private final int DEFALUT_CAPACITY = 16;
    // 内部存储结构
    private Node<K, V>[] table = new Node[DEFALUT_CAPACITY];
    // 长度
    private int size = 0;
    private int size0;
    // 键集
    Set<K> keySet = new HashSet<>();
    // 值集
    Set<V> values = new HashSet<>();
    // 键值对集合
    public Set<Node<K, V>> entrySet = new HashSet<>();

    /**
     * @return 长度
     */
    public int size() {
        return this.size0;
    }


    /**
     * @return 是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        int hashValue = hash(key);
        // 找该健代表的位置
        int i = indexFor(hashValue, table.length);
        for (Node<K, V> node = (Node<K, V>) table[i]; node != null; node = node.next) {
            if (node.key.equals(key) && hashValue == node.hash) {
                return node.value;
            }
        }
        return null;
    }


    /**
     * @param key   键
     * @param value 值
     * @return 旧值
     */
    public V put(K key, V value) {
        size0++;
        //通过key,求hash值
        int hashValue = hash(key);
        //通过hash,找到这个key应该放的位置
        int i = indexFor(hashValue, table.length);
        //i位置已经有数据了，往链表添加元素
        for (Node<K, V> node = (Node<K, V>) table[i]; node != null; node = node.next) {
            K k;
            //且数组中有这个key,覆盖其value
            if (node.hash == hashValue && ((k = node.key) == key || key.equals(k))) {
                V oldValue = node.value;
                node.value = value;
                values.remove(oldValue);
                values.add(value);
                Iterator<Node<K, V>> iterator = entrySet.iterator();
                Node<K, V> temp = null;
                while (iterator.hasNext()) {
                    temp = iterator.next();
                    if ((k = temp.key) == key || key.equals(k)) {
                        temp.value = value;
                    }
                }
                //返回oldValue
                return oldValue;
            }
        }
        //如果i位置没有数据，或i位置有数据，但key是新的key,新增节点
        addEntry(key, value, hashValue, i);

        return null;
    }

    /**
     * @param key 键
     * @return 是否包含该健或该健是否包含值
     */
    public boolean containsKey(K key) {
        return keySet.contains(key) && get(key) != null;
    }

    /**
     * 移除一个健的值
     *
     * @param key 键
     */
    public void remove(K key) {
        //通过key,求hash值
        int hashValue = hash(key);
        //通过hash,找到这个key应该放的位置
        int i = indexFor(hashValue, table.length);
        //i位置已经有数据了，往链表添加元素
        for (Node<K, V> node = (Node<K, V>) table[i]; node != null; node = node.next) {
            K k;
            //且数组中有这个key,覆盖其value
            if (node.hash == hashValue && ((k = node.key) == key || key.equals(k))) {
                size0--;
                V oldValue = node.value;
                node.value = null;
                values.remove(oldValue);
                Iterator<Node<K, V>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    if ((k = iterator.next().key) == key || key.equals(k)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

    }

    /**
     * 添加一条键值对
     *
     * @param key       键
     * @param value     值
     * @param hashValue 健的哈希值
     * @param i         位置
     */
    public void addEntry(K key, V value, int hashValue, int i) {
        //如果超过了原数组大小，则扩大数组
        if (++size == table.length) {
            Node<K, V>[] newTable = new Node[table.length * 2];
            System.arraycopy(table, 0, newTable, 0, table.length);
            table = newTable;
        }
        //的到i位置的数据
        Node eNode = table[i];
        //新增节点，将该节点的next指向前一个节点
        table[i] = new Node<>(hashValue, key, value, eNode);
        keySet.add(key);
        values.add(value);
        entrySet.add((Node<K, V>) table[i]);
    }

    /**
     * 获取插入的位置
     *
     * @param hashValue 哈希值
     * @param length    哈希表长度
     * @return 位置
     */
    public int indexFor(int hashValue, int length) {
        return hashValue % length;
    }

    /**
     * 获取hash值
     *
     * @param key 键
     * @return 健对应的哈希值
     */
    public int hash(Object key) {
        return key.hashCode();
    }

    /**
     * @return 返回哈希表的迭代器
     */
    @Override
    public Iterator<Node<K, V>> iterator() {
        return entrySet.iterator();
    }

    /**
     * 哈希表内的结点类
     *
     * @param <K>
     * @param <V>
     */
    public static class Node<K, V> {
        /**
         * hash值
         */
        int hash;
        /**
         * 键
         */
        K key;
        /**
         * 值
         */
        V value;
        /**
         * 指向下个节点的指针
         */
        Node<K, V> next;

        /**
         * @param hash  hash值
         * @param key   键
         * @param value 值
         * @param next  指向下个节点的指针
         */
        Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * @return 键
         */
        public K getKey() {
            return this.key;
        }


        /**
         * @return 值
         */
        public V getValue() {
            return this.value;
        }
    }
}
