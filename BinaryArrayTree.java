public class BinaryArrayTree {
    /*
    Since we use ARRAY to store we easily can find indexes of left and siblings and parent
    (NI*2, NI*2+1, NI//2 respectively(NI - node index))
    */
    private Object[] tree;

    public BinaryArrayTree(int maxsize) {
        tree = new Object[maxsize];
    }

    public void addLeftChild(int parentIndex, Object data) {
        int leftChildIndex = parentIndex*2;
        if (tree[leftChildIndex] != null)
            throw new IllegalStateException("LeftChild is already exist");
        tree[leftChildIndex] = data;
    }

    public void addRightChild(int parentIndex, Object data) {
        int rightChildIndex = parentIndex*2+1;
        if (tree[rightChildIndex] != null)
            throw new IllegalStateException("RightChild is already exist");
        tree[rightChildIndex] = data;
    }

    public Object removeChild(int index) {
        if (tree[index] == null)
            throw new IllegalStateException("Node doesn't exist");
        Object res = tree[index];
        tree[index] = null;
        return res;
    }

    public void setChild(int index, Object data) {
        if (tree[index] == null)
            throw new IllegalStateException("RightChild is already exist");
        tree[index] = data;
    }

    public Object getLeftChild(int parentIndex) {
        if (tree[parentIndex] == null)
            throw new IllegalStateException("Parent doesn't exist");
        return tree[parentIndex*2];
    }

    public Object getRightChild(int parentIndex) {
        if (tree[parentIndex] == null)
            throw new IllegalStateException("Parent doesn't exist");
        return tree[parentIndex*2+1];
    }

    public Object getParent(int childIndex) {
        return tree[childIndex / 2];
    }

    public Object getNode(int index) {
        return tree[index];
    }
}
