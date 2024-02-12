package pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    public void smallIntArray() {
        Integer[] array = {1, 2, 3, 4, 5};
        ParallelIndexSearch<Integer> work = new ParallelIndexSearch<>(array, 0, array.length, 4);
        ForkJoinPool pool = new ForkJoinPool();
        assertEquals(3, pool.invoke(work));
    }

    @Test
    public void bigIntArray() {
        Integer[] array = new Integer[50];
        for (int i = 0; i < 50; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> work = new ParallelIndexSearch<>(array, 0, array.length, 40);
        ForkJoinPool pool = new ForkJoinPool();
        assertEquals(40, pool.invoke(work));
    }

    @Test
    public void stringArray() {
        String[] array = {"One", "Two", "Free", "Four", "Five"};
        ParallelIndexSearch<String> work = new ParallelIndexSearch<>(array, 0, array.length, "Four");
        ForkJoinPool pool = new ForkJoinPool();
        assertEquals(3, pool.invoke(work));
    }

    @Test
    public void cantFindTheElement() {
        Integer[] array = {1, 2, 3, 4, 5};
        ParallelIndexSearch<Integer> work = new ParallelIndexSearch<>(array, 0, array.length, 6);
        ForkJoinPool pool = new ForkJoinPool();
        assertEquals(-1, pool.invoke(work));
    }
}
