public class CarQueue<E> {
    private E[] queueArray;
    private int first, firstFree, length;
    private static final int DEFAULT_MAXIMUM_SIZE = 100;

    public CarQueue() {
        queueArray = (E[]) new Object[DEFAULT_MAXIMUM_SIZE];
        first = firstFree = length = 0;
    }

    public CarQueue(int size) {
        queueArray = (E[]) new Object[size];
        first = firstFree = length = 0;
    }

    public void insert(E item) {
        if (length == queueArray.length) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            queueArray[firstFree] = item;
            firstFree = (firstFree++) % queueArray.length;
            length++;
        }
    }

    public void remove() {
        if (length == 0) {
            throw new NullPointerException();
        } else {
            queueArray[first]=null;
            first=(first++)%queueArray.length;
            length--;
        }
    }
    public E getFront(){
        return queueArray[first];
    }
    public int length(){
        return length;
    }
}