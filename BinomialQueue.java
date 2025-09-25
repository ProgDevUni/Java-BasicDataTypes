public class BinomialQueue <T extends Comparable<?super T>>{
    private static class Node<T> {
        T elem;
        Node<T> left;
        Node<T> nextSib;

        public Node(T elem) {this(elem, null, null);}
        public Node(T elem, Node<T> lt, Node<T> nt) {
            this.elem = elem;
            this.left = lt;
            this.nextSib = nt;
        }
    }

    private int size;
    private Node<T>[] trees;

    public BinomialQueue() {
        trees = new Node[1];
        size = 0;
    }

    public BinomialQueue(T item) {
        trees = new Node[1];
        trees[0] = new Node<>(item);
        size = 1;
    }

    public void merge(BinomialQueue<T> rhs) {
        if (this == rhs)
            return;

        size += rhs.size;

        if (size > capacity()) {
            int maxLength = Math.max(trees.length, rhs.trees.length);
            expandTrees(maxLength+1);
        }

        Node<T> carry = null;
        for (int i=0, j=1;j<=size;i++,j*=2) {
            Node<T> t1 = trees[i];
            Node<T> t2 = i < rhs.trees.length ? rhs.trees[i] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0: 4;

            switch (whichCase) {
                case 0: //no trees
                case 1: //only this
                    break;
                case 2:
                    trees[i] = t2;
                    rhs.trees[i] = null;
                    break;
                case 4:
                    trees[i] = carry;
                    carry = null;
                    break;
                case 3:
                    carry = combineTrees(t1, t1);
                    trees[i] = rhs.trees[i] = null;
                    break;
                case 5:
                    carry = combineTrees(t1, carry);
                    trees[i] = null;
                    break;
                case 6:
                    carry = combineTrees(t2, carry);
                    rhs.trees[i] = null;
                case 7:
                    trees[i] = carry;
                    carry = combineTrees(t1, t2);
                    rhs.trees[i] = null;
                    break;
            }
        }

        for (int k=0;k<rhs.trees.length;k++)
            rhs.trees[k] = null;
        rhs.size = 0;
    }

    public void insert(T x) {merge(new BinomialQueue<>(x));}

    public T findMin() {
        int minIndex = findMinIndex();
        return minIndex == -1 ? null : trees[minIndex].elem;
    }
    public T deleteMin() {
        int minIndex = findMinIndex();
        if (minIndex == -1) return null;

        T minItem = trees[minIndex].elem;

        Node<T> deletedTree = trees[minIndex].left;
        trees[minIndex] = null;

        BinomialQueue<T> deletedQueue = new BinomialQueue<>();
        deletedQueue.expandTrees(minIndex);
        deletedQueue.size = (1 << minIndex) - 1;

        for (int i=minIndex-1; i>=0;i--) {
            deletedQueue.trees[i] = deletedTree;
            deletedTree = deletedTree.nextSib;
            deletedQueue.trees[i].nextSib = null;
        }

        size -= (1<<minIndex);
        merge(deletedQueue);

        return minItem;
    }

    public boolean isEmpty() {return size==0;}
    public void makeEmpty() {
        trees = new Node[1];
        size=0;
    }

    private void expandTrees(int newNumTrees) {
        if (trees.length >= newNumTrees+1) return;

        Node<T>[] newTrees = new Node[newNumTrees+1];
        System.arraycopy(trees, 0, newTrees, 0, trees.length);
        trees = newTrees;
    }

    private Node<T> combineTrees(Node<T> node1, Node<T> node2) {
        if (node1.elem.compareTo(node2.elem) > 0)
            return combineTrees(node2, node1);
        node2.nextSib = node1.left;
        node1.left = node2;
        return node1;
    }

    private int capacity() {return (1 << trees.length) - 1;}
    private int findMinIndex() {
        int minIndex = -1;
        for (int i=0;i<trees.length;i++)
            if (trees[i] != null)
                if (minIndex == -1 || trees[i].elem.compareTo(trees[minIndex].elem) < 0)
                    minIndex = i;
        return minIndex;
    }
}
