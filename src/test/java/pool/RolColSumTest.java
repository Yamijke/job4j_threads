package pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    void whenRowAndColSum() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] data = RolColSum.sum(array);
        assertThat(data[0].getRowSum()).isEqualTo(6);
        assertThat(data[2].getColSum()).isEqualTo(18);
    }

    @Test
    void whenAsyncSumRowAndCol() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] data = RolColSum.asyncSum(array);
        assertThat(data[0].getRowSum()).isEqualTo(6);
        assertThat(data[2].getColSum()).isEqualTo(18);
    }
}