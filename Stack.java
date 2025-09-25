import java.lang.StackOverflowError;
import java.util.EmptyStackException;

public class Stack<T> {
    private class Node<T> {
        public T data;
        public Node<T> next;
    }

    private Node<T> root;
    private int capacity;
    private int size;

    public Stack(int capacity) {
        this.capacity = capacity;
        this.root = new Node<>();
    }

    public boolean isEmpty() {return size == 0;}
    public boolean isFull() {return size == capacity;}

    public void push(T data) {
        if (isFull())
            throw new StackOverflowError();
        Node<T> tmp = new Node<T>();
        tmp.data = data;
        if (size != 0) {
            tmp.next = root.next;
        }

        root.next = tmp;
        size++;
    }

    public T top() {
        if (isEmpty())
            throw new EmptyStackException();
        return root.next.data;
    }

    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        size--;
        Node<T> res = root.next;
        root.next = root.next.next;
        return res.data;
    }
}
