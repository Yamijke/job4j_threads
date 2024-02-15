package pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RolColSumTest {
    @Test
    void whenRowAndColSum() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] data = RolColSum.sum(array);
        int[][] expected = {
                {6, 12},
                {15, 15},
                {24, 18}
        };
        for (int i = 0; i < array.length; i++) {
            assertEquals(expected[i][0], data[i].getRowSum());
            assertEquals(expected[i][1], data[i].getColSum());
        }
    }

    @Test
    void whenAsyncSumRowAndCol() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] data = RolColSum.asyncSum(array);
        int[][] expected = {
                {6, 12},
                {15, 15},
                {24, 18}
        };
        for (int i = 0; i < array.length; i++) {
            assertEquals(expected[i][0], data[i].getRowSum());
            assertEquals(expected[i][1], data[i].getColSum());
        }
    }
}