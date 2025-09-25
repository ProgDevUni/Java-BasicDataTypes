public class BinaryListTree<T> {
    /*
    Since we use LIST we store POINTERS to each sibling and parents
    TODO: write different way to communicate with Nodes(I want to keep NodeClass private)
        I think I need something like public interface
    */
    public static class BinaryNode<T> {
        public T data;
        public BinaryNode<T> left;
        public BinaryNode<T> right;

        public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public BinaryNode<T> root;

    public BinaryListTree(T data) {
        root = new BinaryNode<>(data, null, null);
    }

    public void insertLeft(BinaryNode<T> node, T data) {
        if (node.left != null)
            throw new IllegalStateException("Left is already exist");
        node.left = new BinaryNode<>(data, null, null);
    }

    public void insertRight(BinaryNode<T> node, T data) {
        if (node.right != null)
            throw new IllegalStateException("Right is already exist");
        node.right = new BinaryNode<>(data, null, null);
    }

    public void setNode(BinaryNode<T> node, T data) {
        node.data = data;
    }

    public void delete(BinaryNode<T> root, BinaryNode<T> toDelete) {
        if (root == null) return;

        if (root.left == toDelete) {
            root.left = null;
        }
        else if(root.right == toDelete) {
            root.right = null;
        }
        else {
            delete(root.left, toDelete);
            delete(root.right, toDelete);
        }
    }

    public BinaryNode<T> getRoot() {return root;}
}
