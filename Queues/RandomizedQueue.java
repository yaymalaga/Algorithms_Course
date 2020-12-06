import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // Resize to double size if full
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        randomizeQueue();

        // Return last item from the randomized queue
        final Item item = items[size - 1];
        items[size - 1] = null;

        size--;

        // Resize half size if just using a quarter
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    private void resize(int newSize) {
        // Just copy the items to an array with new size
        final Item[] newItems = (Item[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        randomizeQueue();

        // Return last item from the randomized queue
        return items[size - 1];
    }

    private void randomizeQueue() {
        // Generate random index
        final int randomIndex = StdRandom.uniform(size);

        // Swap item at random index with the last item
        final Item randomItem = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = randomItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int counter = 0;
        private final Item[] iterItems;

        public ListIterator() {
            final Item[] newItems = (Item[]) new Object[size];

            for (int i = 0; i < size; i++) {
                newItems[i] = items[i];
            }

            StdRandom.shuffle(newItems);

            iterItems = newItems;
        }

        public boolean hasNext() {
            return counter != size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            final Item item = iterItems[counter];
            counter++;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();

        System.out.println(randomQueue.isEmpty());
        System.out.println(randomQueue.size());
        System.out.println("");

        System.out.println("Enqueue 5");
        randomQueue.enqueue(5);
        System.out.println("Enqueue 1");
        randomQueue.enqueue(1);
        System.out.println("Enqueue 8");
        randomQueue.enqueue(8);
        System.out.println("Enqueue 10");
        randomQueue.enqueue(10);
        System.out.println("Enqueue 15");
        randomQueue.enqueue(15);
        System.out.println("");

        System.out.println(randomQueue.isEmpty());
        System.out.println(randomQueue.size());
        System.out.println("");

        System.out.println("Dequeue: " + randomQueue.dequeue());
        System.out.println("Dequeue: " + randomQueue.dequeue());
        System.out.println("Sample: " + randomQueue.sample());
        System.out.println("Sample: " + randomQueue.sample());
        System.out.println("");

        System.out.println(randomQueue.isEmpty());
        System.out.println(randomQueue.size());
        System.out.println("");

        for (Integer item : randomQueue) {
            System.out.println(item);
        }
    }

}
