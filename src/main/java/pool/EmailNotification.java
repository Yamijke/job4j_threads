package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        pool.submit(() -> {
            String sub = String.format("Notification %s toEmail %s", user.username(), user.email());
            String body = String.format("New event to %s", user.username());
            send(sub, body, user.email());
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }

    public void send(String subject, String body, String email) {
    }
}
