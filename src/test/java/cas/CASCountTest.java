package cas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenIncrement() {
        CASCount cas = new CASCount();
        cas.increment();
        assertEquals(1, cas.get());
    }

    @Test
    public void whenIncrementMultipleTimes() {
        CASCount count = new CASCount();
        for (int i = 0; i < 100; i++) {
            count.increment();
        }
        assertEquals(100, count.get());
    }

    @Test
    public void whenIncrementWithTwoThreads() throws InterruptedException {
        final CASCount cas = new CASCount();
        Thread one = new Thread(
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        cas.increment();
                    }
                }
        );
        Thread two = new Thread(
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        cas.increment();
                    }
                }
        );
        one.start();
        two.start();
        one.join();
        two.join();
        assertEquals(2000, cas.get());
    }
}