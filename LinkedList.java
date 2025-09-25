import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T extends Comparable<?super T>> implements Iterable<T>{
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;

    public void addElement(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {head = newNode;}
        else {
            Node<T> tempNode = head;
            while (tempNode.next != null) {
                tempNode = tempNode.next;
            }
            tempNode.next = newNode;
        }
        size++;
    }

    public void sortedAdd(T data) {
        if (!contains(data)) {
            Node<T> temp = head;
            if (head == null)
                head = new Node<>(data);
            else {
                while (temp.next != null && temp.next.data.compareTo(data) < 0) {
                    temp = temp.next;
                }
                if (temp.next == null)
                    temp.next = new Node<>(data);
                else {
                    Node<T> newNode = new Node<>(data);
                    newNode.next = temp.next;
                    temp.next = newNode;
                }
            }
            this.size++;
        }
    }

    public void insertElement(int index, T value) {
        int start = 0;
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        }
        else {
            Node<T> temp = head;
            while (start < index-1) {
                temp = temp.next;
                start++;
            }

            newNode.next = temp.next;
            temp.next = newNode;
        }
        size++;
    }

    public void deleteElement(int index) {
        int start = 0;
        if (index == 0) {head = head.next;}
        else {
            Node<T> tempNode = head;
            while (start < index-1) {
                tempNode = tempNode.next;
                start++;
            }
            tempNode.next = tempNode.next.next;
        }
        size--;
    }

    public T get(int index) {
        int start = 0;
        Node<T> tempNode = head;
        while (start != index) {
            tempNode = tempNode.next;
            start++;
        }
        return tempNode.data;
    }

    public void set(int index, T value) {
        int start = 0;
        Node<T> tempNode = head;
        while (start != index) {
            tempNode = tempNode.next;
            start++;
        }
        tempNode.data = value;
    }

    public boolean contains(T value) {
        for (T item : this)
            if (item.equals(value))
                return true;
        return false;
    }

    public int size() {return size;}

    public boolean isEmpty() {return size == 0;}

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T>{
        private Node<T> current = head;

        @Override
        public boolean hasNext() {return current != null;}

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }

    public void printList() {
        for (T item: this) {
            System.out.print(item + " ");
        }
    }
}
