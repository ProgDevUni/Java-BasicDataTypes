public class Tree<T> {
    public static class TreeNode<T> {
        private T data;
        private TreeNode<T> child;
        private TreeNode<T> rightSibling;

        public TreeNode(T data) {
            this.data = data;
            this.child = null;
            this.rightSibling = null;
        }

        public void printDFS() {
            System.out.print(this.data + " ");
            if (this.child != null)
                this.child.printDFS();
            if (this.rightSibling != null)
                this.rightSibling.printDFS();
        }

        public void printPostOrder() {
            if (this.child != null)
                this.child.printPostOrder();
            System.out.print(this.data + " ");
            if (this.rightSibling != null)
                this.rightSibling.printPostOrder();
        }

        public void printBFS() {
            System.out.print(this.data + " ");
            if (this.rightSibling != null)
                this.rightSibling.printBFS();
            if (this.child != null)
                this.child.printBFS();
        }

        public void setChild(TreeNode<T> node) {this.child = node;}
        public void setRightSibling(TreeNode<T> node) {this.rightSibling = node;}

        public T getValue() {return this.data;}
        public TreeNode<T> getChild() {return this.child;}
        public TreeNode<T> getRightSibling() {return this.rightSibling;}
    }

    public TreeNode<T> root;


    // TODO: insert and remove functions
    //  also I need add key(each node will have unique key) => getPosition()
    //  ask teacher how calculate it
    public Tree(T rootData) {
        this.root = new TreeNode<>(rootData);
    }

    public TreeNode<T> getRoot() {return this.root;}

    public void addChild(TreeNode<T> parentNode, T data) {
        if (parentNode.getChild() == null) {
            parentNode.setChild(new TreeNode<T>(data));
        }
        else {
            TreeNode<T> temp = parentNode.getChild();
            while (temp.getRightSibling() != null) {
                temp = temp.getRightSibling();
            }
            temp.setRightSibling(new TreeNode<T>(data));
        }
    }

    public boolean isEmpty() {return this.root.getValue() == null && this.root.getChild() == null;}

    public void printTree(int mode) {
        if (mode == 1) {
            // preorder
            root.printDFS();
        }
        else if(mode == 2) {
            root.printPostOrder();
        } else if (mode == 3) {
            // breadth first travel
            root.printBFS();
        }
    }
}
