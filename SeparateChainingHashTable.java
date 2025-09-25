public class SeparateChainingHashTable {
    private class Node {
        Object data;
        Object key;
        Node next;

        public Node(Object key, Object data) {
            this.key = key;
            this.data = data;
            this.next = null;
        }
    }

    private Node[] list;

    public SeparateChainingHashTable(int tableSize) {
        this.list = new Node[tableSize];
    }

    public void insert(Object key, Object value) {
        Node temp = new Node(key, value);
        if (list[myHash(key)] == null)
            list[myHash(key)] = temp;
        else {
            Node current = list[myHash(key)];
            while (current.next != null)
                current = current.next;
            current.next = temp;
        }
    }

    public void remove(Object key) {
        Node current = list[myHash(key)];
        if (current != null) {
            if (current.key == key)
                list[myHash(key)] = current.next;
            else {
                while (current != null) {
                    if (current.next.key == key) {
                        current.next = current.next.next;
                        return;
                    }
                    if (current.next.next == null) {
                        current.next = null;
                    }
                    current = current.next;
                }
            }
        }
    }

    public boolean contains(Object key) {
        Node current = list[myHash(key)];
        if (current != null) {
            while (current != null) {
                if (current.key == key)
                    return true;
                current = current.next;
            }
        }
        return false;
    }

    public Object find(Object key) {
        Node current = list[myHash(key)];
        if (current != null) {
            while (current != null) {
                if (current.key == key)
                    return current.data;
                current = current.next;
            }
        }
        return null;
    }

    public void print() {
        for (int i=0;i<list.length;i++) {
            if (list[i] != null) {
                Node current = list[i];
                while(current != null) {
                    System.out.print("{"+current.key+":"+current.data +"}"+ "-");
                    current = current.next;
                }
                System.out.println();
            }
        }
    }

    private int myHash(Object x) {
        int hashVal = x.hashCode();
        hashVal %= list.length;
        if (hashVal < 0)
            hashVal += list.length;

        return hashVal;
    }
}

/* EXAMPLE
    SeparateChainingHashTable table = new SeparateChainingHashTable(20);
    table.insert("Hello", 1);
    table.insert("My", 2);
    table.insert("World", 3);
    table.insert("Russia", 4);
    System.out.println(table.find("Hello"));
    System.out.println(table.find("World"));
    table.remove("World");
    System.out.println(table.find("World"));
* */