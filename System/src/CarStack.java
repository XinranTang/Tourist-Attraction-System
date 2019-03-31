public class CarStack<E> {
    private E[] stackArray;
    private static final int DEFAULT_MAXIMUM_SIZE = 100;
    private int top, length;

    public CarStack() {
        stackArray = (E[]) new Object[DEFAULT_MAXIMUM_SIZE];
        top = 0;
        length = 0;
    }

    public CarStack(int size) {
        stackArray = (E[]) new Object[size];
        top = 0;
        length = 0;
    }

    public void push(E item) {
        if (length <= stackArray.length) {
            stackArray[top] = item;
            top++;
            length++;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public E pop() {
        if (length != 0) {
            E returnItem = stackArray[top-1];
            top--;
            length--;
            return returnItem;
        } else {
            throw new NullPointerException();
        }
    }

    public int depth() {
        return length;
    }

    public int length(){
        return stackArray.length;
    }

    public static void main(String[] args) {
        CarStack<Integer> carStack = new CarStack<>(2);
        carStack.push(1);
        carStack.push(2);
        carStack.pop();
        System.out.println(carStack.pop());
    }
}