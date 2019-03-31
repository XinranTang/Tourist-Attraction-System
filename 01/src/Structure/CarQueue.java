package Structure;

import java.util.Iterator;

/**
 * 自己实现的一个队列
 *
 * @param <E>
 */
public class CarQueue<E> implements Iterable{
    /**
     * 存放队列元素的数组
     */
    private E[] queueArray;
    /**
     * 队列的控制指针
     */
    private int first, firstFree;
    /**
     * 队列长度
     */
    private int length;
    /**
     * 队列默认最大值
     */
    private static final int DEFAULT_MAXIMUM_SIZE = 100;

    /**
     * 无参数构造器
     * 队列数组被设为默认最大长度
     */
    public CarQueue() {
        queueArray = (E[]) new Object[DEFAULT_MAXIMUM_SIZE];
        first = firstFree = length = 0;
    }

    /**
     * 有参构造器
     *
     * @param size 队列数组的大小
     */
    public CarQueue(int size) {
        queueArray = (E[]) new Object[size];
        first = firstFree = length = 0;
    }

    /**
     * @param item 插入的元素
     */
    public void insert(E item) {
        if (length == queueArray.length) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            // 队尾指针若达到数组末尾，将重新指向数组开头
            queueArray[firstFree] = item;
            firstFree = (firstFree + 1) % queueArray.length;
            length++;
        }
    }

    /**
     * 移除元素
     */
    public void remove() {
        if (length == 0) {
            // 无元素的队列不能移除元素
            throw new NullPointerException();
        } else {
            // 队尾指针若达到数组末尾，将重新指向数组开头
            queueArray[first] = null;
            first = (first + 1) % queueArray.length;
            length--;
        }
    }

    /**
     * @return 队列的头元素
     */
    public E getFront() {
        return queueArray[first];
    }

    /**
     * @return 队列的当前长度
     */
    public int length() {
        return length;
    }

    /**
     * @return 队列的头指针位置
     */
    public int getFirst() {
        return first;
    }

    /**
     * @return 队列的尾部指针位置
     */
    public int getFirstFree() {
        return firstFree;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }
    /**
     * 实现了一个自己的迭代器
     */
    private class MyIterator implements Iterator<E> {

        /**
         * 迭代器指针
         */
        private int i = firstFree;

        /**
         * @return 是否有下一个元素
         */
        @Override
        public boolean hasNext() {
            return i > first;
        }

        /**
         * @return 下一个元素
         */
        @Override
        public E next() {
            return (E)queueArray[--i];
        }

    }
    // 测试语句
//    public static void main(String[] args) {
//        CarQueue carQueue = new CarQueue();
//        carQueue.insert(1);
//        carQueue.insert(2);
//        carQueue.remove();
//        carQueue.remove();
//    }
}