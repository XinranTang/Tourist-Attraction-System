package Structure;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 自己实现的栈
 *
 * @param <E> 站内元素
 */
public class CarStack<E> implements Iterable<E> {
    /**
     * 构成栈的数组
     */
    private E[] stackArray;
    /**
     * 默认栈的最大元素个数，即栈数组的默认最大长度
     */
    private static final int DEFAULT_MAXIMUM_SIZE = 100;
    /**
     * 指向栈顶的指针
     */
    private int top;
    /**
     * 栈的长度
     */
    private int length;

    /**
     * 无参数构造器
     * 栈数组被设为默认最大长度
     */
    public CarStack() {
        stackArray = (E[]) new Object[DEFAULT_MAXIMUM_SIZE];
        top = 0;
        length = 0;
    }

    /**
     * 有参构造器
     *
     * @param size 栈数组的大小
     */
    public CarStack(int size) {
        stackArray = (E[]) new Object[size];
        top = 0;
        length = 0;
    }

    /**
     * 插入元素，后入先出
     *
     * @param item 待插入的元素
     */
    public void push(E item) {
        if (length <= stackArray.length) {
            stackArray[top] = item;
            top++;
            length++;
        } else {
            // 如果栈已满，不能插入元素，抛出异常
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * 移除并返回元素，后入先出
     *
     * @return 栈顶元素
     */
    public E pop() {
        if (length != 0) {
            E returnItem = stackArray[top - 1];
            top--;
            length--;
            return returnItem;
        } else {
            throw new NullPointerException();
        }
    }


    /**
     * @return 栈的深度，即栈的当前元素个数
     */
    public int depth() {
        return length;
    }

    /**
     * @return 栈数组的长度
     */
    public int length() {
        return stackArray.length;
    }

    /**
     * @return 栈的迭代器
     */
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
        private int i = top;

        /**
         * @return 是否有下一个元素
         */
        @Override
        public boolean hasNext() {
            return i > 0;
        }

        /**
         * @return 下一个元素
         */
        @Override
        public E next() {
            return stackArray[--i];
        }

    }
    // 测试语句
//    public static void main(String[] args) {
//        CarStack<Integer> carStack = new CarStack<>(2);
//        carStack.push(1);
////        carStack.push(2);
////        carStack.pop();
//        System.out.println(carStack.pop());
//    }
}