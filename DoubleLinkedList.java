import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements Iterable<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;


    public boolean isEmpty() {return size == 0;}

    public int size() {return size;}


    public void insertBefore(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);

        if (node == head) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            newNode.prev = node.prev;
            newNode.next = node;

            node.prev.next = newNode;
            node.prev = newNode;
        }

        size++;
    }

    public void insertAfter(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);
        if (node == tail) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            newNode.prev = node;
            newNode.next = node.next;

            node.next.prev = newNode;
            node.next = newNode;
        }
        size++;
    }

    public void remove(Node<T> node) {
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        }

        else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        }
        else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    public boolean contains(T value) {
        Node<T> posPointer = head;
        Node<T> negPointer = tail;
        while ((posPointer != negPointer) && (posPointer.next != negPointer)) {
            if (posPointer.data == value || negPointer.data == value)
                return true;
            posPointer = posPointer.next;
            negPointer = negPointer.prev;
        }
        // if the value in the middle
        return posPointer.data == value || negPointer.data == value;
    }

    public Node<T> find(T value) {
        Node<T> posPointer = head;
        Node<T> negPointer = tail;
        while ((posPointer != negPointer) && (posPointer.next != negPointer)) {
            if (posPointer.data == value)
                return posPointer;
            if (negPointer.data == value)
                return negPointer;
            posPointer = posPointer.next;
            negPointer = negPointer.prev;
        }

        // if value in the middle
        if (posPointer.data == value)
            return posPointer;
        if (negPointer.data == value)
            return negPointer;
        return new Node<T>(null);
    }

    public T get(Node<T> node) {return node.data;}

    public T getFirst() {return head.data;}

    public T getLast() {return tail.data;}

    public void printForward() {
        for (T item : this)
            System.out.print(item + " ");
        System.out.println();
    }

    public void printBackward() {
        Node<T> current = tail;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }
        System.out.println();
    }


    @Override
    public ListIterator<T> iterator() {
        return new DoubleLinkedListIterator(head, 0);
    }

    private class DoubleLinkedListIterator implements ListIterator<T> {
        private Node<T> current;
        private Node<T> lastReturned = null;
        private int index = 0;

        public DoubleLinkedListIterator(Node<T> startNode, int startIndex) {
            this.current = startNode;
            this.index = startIndex;
        }

        @Override
        public boolean hasNext() {return current != null;}

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            current = current.next;
            return lastReturned.data;
        }

        @Override
        public boolean hasPrevious() {return current != null && current.prev != null;}

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = (current == null) ? lastReturned : current.prev;
            lastReturned = current;
            return lastReturned.data;
        }

        @Override
        public void remove() {
            if (lastReturned == null) throw new IllegalStateException("No element to remove");

            if (lastReturned.prev != null) {
                lastReturned.prev.next = lastReturned.next;
            } else {
                head = lastReturned.next;
            }

            if (lastReturned.next != null) {
                lastReturned.next.prev = lastReturned.prev;
            } else {
                tail = lastReturned.prev;
            }

            if (current == lastReturned) {
                current = current.next;
            } else {
                index--;
            }

            lastReturned = null;
        }

        @Override
        public void set(T value) {
            if (lastReturned == null) throw new IllegalStateException("No element to set");
            lastReturned.data = value;
        }

        @Override
        public void add(T value) {
            Node<T> newNode = new Node<>(value);

            if (current == null) {
                if (tail != null) {
                    tail.next = newNode;
                    newNode.prev = tail;
                    tail = newNode;
                } else {
                    head = tail = newNode; // empty list case
                }
            } else {
                newNode.next = current;
                newNode.prev = current.prev;

                if (current.prev != null) {
                    current.prev.next = newNode;
                } else {
                    head = newNode;
                }
                current.prev = newNode;
            }

            lastReturned = null;
            index++;
        }

        @Override
        public int nextIndex() {return index;}

        @Override
        public int previousIndex() {return index - 1;}
    }
}
