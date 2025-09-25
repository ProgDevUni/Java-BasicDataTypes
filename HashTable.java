/*
IDEA: we have just an array.
when INSERT: trying to find a free cell by recalculating hash 0 or more times
when CONTAINS, GET, REMOVE: do the same, until array[pos] will be equal to key

isActive: when we recalculating hash it's very important not to change the pass
=> we need to save that THIS CELL WAS USED.
BUT as it's inactive, we can get a value from it.

Theoretically(as the same input have the same result for hash), we can not save key, but I want to have it.
 */
public class HashTable {
    private static class HashEntry {
        public Object key;
        public Object element;
        public boolean isActive;

        public HashEntry(Object k, Object e, boolean i) {
            this.key = k;
            this.element = e;
            this.isActive = i;
        }
    }

    private HashEntry[] array;
    private int size;

    public HashTable(int tableSize) {
        allocateArray(tableSize);
        makeEmpty();
    }

    public void makeEmpty() {
        // magic with garbage collector
        size = 0;
        for (int i=0; i<array.length;i++)
            array[i] = null;
    }

    public boolean contains(Object key) {
        int pos = findPos(key);
        return isActive(pos);
    }

    public void insert(Object key, Object data) {
        int pos = findPos(key);
        if (isActive(pos)) //means the cell is already full
            return;

        array[pos] = new HashEntry(key, data, true);

        // see section 5.5 (JUST NEED)
        if (++size > array.length / 2)
            rehash();
    }

    public void remove(Object key) {
        int pos = findPos(key);
        if (isActive(pos))
            array[pos].isActive = false;
    }

    public Object get(Object key) {
        int pos = findPos(key);
        if (isActive(pos))
            return array[pos].element;
        return null;
    }

    public void print() {
        for (int i=0;i<array.length;i++)
            if (isActive(i))
                System.out.println(array[i].key + " " + array[i].element);
    }

    private void allocateArray(int size) {
        array = new HashEntry[nextPrime(size)];
    }

    private int myHash(Object x) {
        int hashVal = x.hashCode();
        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;

        return hashVal;
    }

    private boolean isActive(int position) {return array[position]!=null && array[position].isActive;}

    private int findPos(Object key) {
        int offset = 1;
        int pos = myHash(key);
        while (array[pos] != null && !array[pos].key.equals(key)) {
            pos += offset; // compute ith probe
            offset += 2;
            if (pos >= array.length)
                pos -= array.length;
        }

        return pos;
    }
    private void rehash() {
        HashEntry[] old = array;

        allocateArray(nextPrime(array.length*2));
        size = 0;

        // copy all elements back
        for (int i=0;i< old.length;i++)
            if (old[i] != null && old[i].isActive)
                insert(old[i].key, old[i].element);
    }

    private int nextPrime(int n) {
        int i = n;
        while (!isPrime(i))
            i++;
        return i;
    }

    private boolean isPrime(int n) {
        for (int i=2;i<=(int)Math.sqrt(n);i++) {
            if (n%i ==0 )
                return false;
        }
        return true;
    }
}
/*
HashTable table = new HashTable(37);
table.insert("Hello", 1);
table.insert("World", 2);
table.insert("My", 3);
table.insert("Love", 4);
table.print();
System.out.println();
System.out.println(table.get("Hello"));
System.out.println(table.get("hello"));
table.remove("World");
System.out.println();
table.print();
 */