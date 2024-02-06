package wait;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleBlockingQueueTest {

    @Test
    void whenSizeIsEqualsToAddedElements() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread master = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread slave = new Thread(() -> {
            try {
                assertEquals(Integer.valueOf(1), queue.poll());
                assertEquals(Integer.valueOf(2), queue.poll());
                assertEquals(Integer.valueOf(3), queue.poll());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        master.start();
        slave.start();
        master.join();
        slave.join();
    }

    @Test
    void whenSizeIsNotEqualsToAddedElements() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread master = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        master.start();
        Thread.sleep(1000);
        assertEquals(2, queue.size());
        Thread slave = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        slave.start();
        master.join();
        slave.join();
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            x -> {
                                try {
                                    queue.offer(x);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
    }
}