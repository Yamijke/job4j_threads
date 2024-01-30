package wait;

import org.junit.jupiter.api.Test;

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
}