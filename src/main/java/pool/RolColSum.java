package pool;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(rowSumCount(matrix, i));
            sums[i].setColSum(colSumCount(matrix, i));
        }
        return sums;
    }

    public static int rowSumCount(int[][] data, int row) {
        int sum = 0;
        for (int i = 0; i < data[row].length; i++) {
            sum += data[row][i];
        }
        return sum;
    }

    public static int colSumCount(int[][] data, int col) {
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i][col];
        }
        return sum;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        Sums[] sumsRsl = new Sums[matrix.length];
        for (Integer temp : futures.keySet()) {
            sumsRsl[temp] = futures.get(temp).get();
        }
        return sumsRsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums s = new Sums();
            s.setRowSum(rowSumCount(matrix, i));
            s.setColSum(colSumCount(matrix, i));
            return s;
        });
    }
}
