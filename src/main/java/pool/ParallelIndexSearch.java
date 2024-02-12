package pool;

import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T target;

    public ParallelIndexSearch(T[] array, int from, int to, T target) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        int rsl = -1;
        if (to - from <= 10) {
            rsl = searchIndex(array, from, to, target);
        } else {
            int mid = from + (to - from) / 2;
            ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(array, from, mid, target);
            ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(array, mid, to, target);
            leftSearch.fork();
            rightSearch.fork();
            Integer left = leftSearch.join();
            Integer right = rightSearch.join();
            if (left != -1) {
                rsl = left;
            } else if (right != -1) {
                rsl = right;
            }
        }
        return rsl;
    }

    public int searchIndex(T[] array, int from, int to, T target) {
        int index = -1;
        for (int i = from; i < to; i++) {
            if (array[i].equals(target)) {
                index = i;
            }
        }
        return index;
    }
}