import java.nio.BufferUnderflowException;

public class BinaryHeap<T extends Comparable<?super T>> {
    private int size;
    private T[] array;

    public BinaryHeap() {this(100);}

    public BinaryHeap(int capacity) {
        size = 0;
        array = (T[]) new Comparable[capacity];
    }

    public void insert(T data) {
        if (size == array.length - 1)
            enlargeArray(2*size+1);

        int hole = ++size;
        // array[0] - you will never use it
        // percolate up.
        for (array[0] = data; data.compareTo(array[hole/2]) < 0; hole /= 2)
            array[hole] = array[hole / 2];
        array[hole] = data;
    }

    public T findMin() {
        if (isEmpty())
            throw new BufferUnderflowException();
        return array[1];
    }

    public T deleteMin() {
        if (isEmpty())
            throw new BufferUnderflowException();
        T minItem = findMin();
        array[1] = array[size--];
        percolateDown(1);

        return minItem;
    }

    public boolean isEmpty() {return size==0;}

    public void makeEmpty() {
        size = 0;
        for (int i=0;i<array.length;i++)
            array[i] = null;
    }



    private void percolateDown(int hole) {
        int child;
        T tmp = array[hole];

        for (; hole*2 <= size; hole = child) {
            child = hole*2;
            if (child != size && array[child+1].compareTo(array[child]) < 0)
                child++;

            if (array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }

    private void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for (int i=0;i<old.length;i++)
            array[i] = old[i];
    }
}
/*
BinaryHeap<Integer> heap = new BinaryHeap<>(100);
heap.insert(10);
heap.insert(5);
heap.insert(20);
heap.insert(3);
assert heap.findMin() == 3: "Min should be 3";

assert heap.deleteMin() == 3;
assert heap.deleteMin() == 5;
assert heap.deleteMin() == 10;
assert heap.deleteMin() == 20;
assert heap.isEmpty() : "Heap should be empty";
*/