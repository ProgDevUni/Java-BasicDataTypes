import java.util.ArrayList;

public class BTree<T extends Comparable<T>> {
    private class Node {
        T[] keys;
        ArrayList<Node> children;
        boolean isLeaf;
        int numKeys;

        public Node(int t, boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = (T[]) new Comparable[2*t-1];
            this.children = new ArrayList<>();
            this.numKeys = 0;
        }
    }

    private Node root;
    private int t;

    public BTree(int t) {
        this.t = t;
        this.root = new Node(t, true);
    }

}
