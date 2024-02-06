package cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        while (true) {
            int cur = get();
            int next = cur + 1;
            if (count.compareAndSet(cur, next)) {
                break;
            }
        }
    }

    public int get() {
        return count.get();
    }
}
