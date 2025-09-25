public class BST<T extends Comparable<?super T>> {
    private static class BinaryNode<T> {
        T data;
        BinaryNode<T> left;
        BinaryNode<T> right;
        int key;

        public BinaryNode(T data, int key) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.key = key;
        }

        public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right, int key) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.key = key;
        }
    }

    private BinaryNode<T> root;

    public void insert(T data) {root = insert(root, data, 1);}
    public void remove(T data) {root = remove(root, data);}
    public T findMin() {
        BinaryNode<T> res = findMin(root);
        return res != null ? res.data : null;
    }
    public T findMax() {
        BinaryNode<T> res = findMax(root);
        return res != null ? res.data : null;
    }
    public boolean contains(T x) {return contains(x, root);}

    public int find(T x) {return find(root, x);} //return key of some node
    public T retrieve(int key) {return retrieve(root, key);} //

    public BST<T> copyBST() {
        BST<T> tree = new BST<>();
        tree.setRoot(copyBST(root));
        return tree;
    }

    public void printPreOrder() {printPreOrder(root);System.out.println();}

    public void printInOrder() {printInOrder(root);System.out.println();}



    private void setRoot(BinaryNode<T> node) {this.root = node;}

    private BinaryNode<T> insert(BinaryNode<T> node, T data, int key) {
        if (node == null) //one node tree
            return new BinaryNode<>(data, key);

        int compareResult = data.compareTo(node.data);

        if (compareResult < 0)
            node.left = insert(node.left, data, key*2);
        else if (compareResult > 0)
            node.right = insert(node.right, data, key*2+1);

        // else - means duplicate
        return node;
    }

    private BinaryNode<T> remove(BinaryNode<T> node, T data) {
        if (node == null) return null; //nothing have found
        int compareResult = data.compareTo(node.data);

        if (compareResult < 0)
            node.left = remove(node.left, data);
        else if (compareResult > 0)
            node.right = remove(node.right, data);
        else {
            if (node.left == null && node.right == null)
                return null;

            else if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;
            else {
                BinaryNode<T> temp = findMin(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data);
            }
        }
        return node;
    }


    private BinaryNode<T> findMin(BinaryNode<T> node) {
        if (node == null)
            return null; // the empty tree
        else if(node.left == null) return node;
        return findMin(node.left);
    }

    private BinaryNode<T> findMax(BinaryNode<T> node) {
        if (node == null)
            return null;
        else if(node.right == null) return node;
        return findMax(node.right);
    }

    private boolean contains(T x, BinaryNode<T> node) {
        if (node == null)
            return false; // didn't find
        int compareResult = x.compareTo(node.data); //return some integer(a<b => return negative number
                                                    // use this as we don't know what we compare(the same as with .equals)
        if (compareResult < 0)
            return contains(x, node.left);
        else if (compareResult > 0)
            return contains(x, node.right);
        return true; // 0 => node is X
    }

    private int find(BinaryNode<T> node, T data) {
        if (node.data.equals(data)) {return node.key;}
        if (node.right == null && node.left == null) {return -1;}
        int res1 = find(node.left, data);
        int res2 = find(node.right, data);
        return res1 == -1 ? res2 : res1;
    }

    private T retrieve(BinaryNode<T> node, int key) {
        if (node.key == key) {return node.data;}
        if (node.key < key) {return retrieve(node.left, key);}
        return retrieve(node.right, key);
    }

    private BinaryNode<T> copyBST(BinaryNode<T> node) {
        if (node == null) return null;
        BinaryNode<T> newLeft = node.left == null ? null : copyBST(node.left);
        BinaryNode<T> newRight = node.right == null ? null : copyBST(node.right);
        return new BinaryNode<T>(node.data, newLeft, newRight, node.key);
    }

    private void printPreOrder(BinaryNode<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            printPreOrder(node.left);
            printPreOrder(node.right);
        }
    }

    private void printInOrder(BinaryNode<T> node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + " ");
            printInOrder(node.right);
        }
    }
}
