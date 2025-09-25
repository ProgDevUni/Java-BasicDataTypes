// standard implementation is made from array

public class CircleQueue {
    private Object[] arr;
    private int front, size;
    private int capacity;

    public CircleQueue(int c) {
        arr = new Object[c];
        capacity = c;
        size = 0;
        front = 0;
    }

    public Object getFront() {
        if (isEmpty())
            return null;
        return arr[front];
    }

    public Object getLast() {
        if (isEmpty())
            return null;
        int last = (front+size-1) % capacity;
        return arr[last];
    }

    public void insertElement(Object elem) {
        if (isFull())
            return;
        int last = (front+size) % capacity;
        arr[last] = elem;
        size++;
    }

    public Object retrieveElement() {
        if (isEmpty())
            return null;
        Object res = arr[front];
        front = (front+1)%capacity;
        size--;
        return res;
    }

    public boolean isEmpty() {return size==0;}

    public boolean isFull() {return size==capacity;}
}
