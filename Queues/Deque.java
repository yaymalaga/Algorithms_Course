import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    // Node object
    public Node firstNode;
    // Reference to last node object
    public Node lastNode;
    // Number of objects;
    private int size;

    private class Node {
        public Item item;
        public Node child;
        public Node parent;
    }

    // construct an empty deque
    public Deque() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // Create new node and assign item to it
        final Node newNode = new Node();
        newNode.item = item;
        newNode.child = firstNode;

        // Replace current node and increase size
        firstNode = newNode;
        size++;

        // Add child's parent if any
        if (size > 1) {
            firstNode.child.parent = newNode;
        } else {
            lastNode = firstNode;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == 0) {
            addFirst(item);
            return;
        }

        // Create new node and assign item to it
        final Node newNode = new Node();
        newNode.item = item;
        newNode.parent = lastNode;

        // Replace current node and increase size
        lastNode.child = newNode;
        lastNode = newNode;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        // Save current item and replace node with its child
        final Item currentItem = firstNode.item;

        // Assign child as current and remove parent
        if (size > 1) {
            firstNode = firstNode.child;
            firstNode.parent = null;
        } else {
            firstNode = null;
            lastNode = null;
        }

        size--;

        return currentItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }


        if (size == 1) {
            return removeFirst();
        } else {
            // Extract data if possible and move lastNode one pos back
            final Item currentItem = lastNode.item;
            lastNode = lastNode.parent;
            lastNode.child = null;

            size--;

            return currentItem;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = firstNode;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            final Item item = current.item;

            current = current.child;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void printPointers() {
        System.out.println("- First item: " + firstNode.item);
        System.out.println("- Last item: " + lastNode.item);
        System.out.println("");
    }

    // unit testing (required)
    public static void main(String[] args) {
        final Deque<Integer> deque = new Deque<Integer>();

        System.out.println(deque.isEmpty());
        System.out.println("");

        deque.addFirst(5);
        System.out.println("AddFirst 5");
        deque.printPointers();
        
        deque.addFirst(8);
        System.out.println("AddFirst 8");
        deque.printPointers();

        deque.addLast(2);
        System.out.println("AddLast 2");
        deque.printPointers();

        deque.addFirst(10);
        System.out.println("AddFirst 10");
        deque.printPointers();

        System.out.println("RemoveLast " + deque.removeLast());
        deque.printPointers();

        System.out.println(deque.size());
        System.out.println("");

        System.out.println("RemoveFirst " + deque.removeFirst());
        deque.printPointers();

        for (Integer element : deque) {
            System.out.println(element);
        }

    }

}