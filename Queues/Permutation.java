import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args[0] == null) {
            throw new IllegalArgumentException();
        }

        final int nItems = Integer.parseInt(args[0]);
        final RandomizedQueue<String> randomQueue = new RandomizedQueue<String>(nItems);
        
        while (true) {
            try {
                randomQueue.enqueue(StdIn.readString());
            } catch (java.util.NoSuchElementException e) {
                break;
            }
        }

        for (int i = 0; i < nItems; i++) {
            System.out.println(randomQueue.dequeue());
        }
    }
}