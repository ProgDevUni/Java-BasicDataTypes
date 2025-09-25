public class BSTwithAVL<T extends Comparable<?super T>> {
    /*
            7                BF = Hl - Hr
           / \               BF(4) = 1 - 1 = 0
          2   8              BF(2) = 1 - 2 = -1 (height from 2 to 1 - height from 2 to 5)
         / \                 BF(7) = 3 - 1 = 2 (height from 7 to 5 - height from 7 to 8)
        1   4
           / \
           3  5
     */
    private static class BinaryNode<T> {
        T data;
        BinaryNode<T> left;
        BinaryNode<T> right;
        int height;

        public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 1;
        }
    }

    private BinaryNode<T> root;

    public void insert(T data) {root = insert(root, data);}
    public void delete(T data) {root = delete(root, data);}
    public boolean search(T data) {return contains(root, data);}
    public void print() {
        CircleQueue stack1 = new CircleQueue(100);//  MY OWN
        CircleQueue stack2 = new CircleQueue(100); // MY OWN
        BinaryNode<T> temp;
        stack1.insertElement(root);
        while (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                temp = (BinaryNode<T>) stack1.retrieveElement();
                if (temp != null) {
                    System.out.print(temp.data+" ");
                    stack2.insertElement(temp.left);
                    stack2.insertElement(temp.right);
                }
                else
                    System.out.print("_ ");
            }
            System.out.println();
            stack1 = stack2;
            stack2 = new CircleQueue(100);
        }
    }

    private int height(BinaryNode<T> node) {return node == null ? 0 : node.height;}
    private void updateHeight(BinaryNode<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }
    private int BF(BinaryNode<T> node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private BinaryNode<T> insert(BinaryNode<T> node, T data) {
        if (node == null) //one node tree
            return new BinaryNode<>(data, null, null);
        int compareResult = data.compareTo(node.data);
        if (compareResult < 0)
            node.left = insert(node.left, data);
        else if (compareResult > 0)
            node.right = insert(node.right, data);
        else
            // else - means duplicate
            return node;

        //  now we need to balance the tree
        updateHeight(node);

        int balance = BF(node);
        if (balance > 1 && data.compareTo(node.left.data) < 0) // LL case
            return rightRotate(node);
        if (balance < -1 && data.compareTo(node.right.data) > 0) //RR case
            return leftRotate(node);

        if (balance > 1 && data.compareTo(node.left.data) > 0) {  // LR case
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && data.compareTo(node.right.data) < 0) { //RL case
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private BinaryNode<T> delete(BinaryNode<T> node, T data) {
        if (node == null) return node; //nothing have found
        int compareResult = data.compareTo(node.data);

        if (compareResult < 0)
            return node.left = delete(node.left, data);
        if (compareResult > 0)
            return node.right = delete(node.right, data);
        else {
            // we found the node
            if (node.left != null && node.right != null) {
                // two children case
                node.data = findMin(node.right).data; // we can use or rightMIN or leftMAX (from theory)
                node.right = delete(node.right, node.data);
            }
            else {
                // one or zero child case
                node = (node.left != null) ? node.left : node.right;
            }
        }

        // time to BALANCE

        updateHeight(node);
        int balance = BF(node);
        if (balance > 1 && data.compareTo(node.left.data) < 0) // LL case
            return rightRotate(node);
        if (balance < -1 && data.compareTo(node.right.data) > 0) //RR case
            return leftRotate(node);

        if (balance > 1 && data.compareTo(node.left.data) > 0) {  // LR case
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && data.compareTo(node.right.data) < 0) { //RL case
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private boolean contains(BinaryNode<T> node, T data) {
        if (node == null) {return false;}
        if (node.data.equals(data)) {return true;}
        return contains(node.left, data) || contains(node.right, data);
    }

    private BinaryNode<T> rightRotate(BinaryNode<T> node) {
        BinaryNode<T> x = node.left;
        BinaryNode<T> temp = x.right;
        x.right = node;
        node.left = temp;

        updateHeight(node);
        updateHeight(x);
        return x;
    }

    private BinaryNode<T> leftRotate(BinaryNode<T> node) {
        BinaryNode<T> x = node.right;
        BinaryNode<T> temp = x.left;
        x.left = node;
        node.right = temp;

        updateHeight(node);
        updateHeight(x);
        return x;
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
}
