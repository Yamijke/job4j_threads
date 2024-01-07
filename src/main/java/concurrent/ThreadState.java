package concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName()),
                "First thread"
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()),
                "Second thread"
        );

        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName() + ": " + first.getState());
            System.out.println(second.getName() + ": " + second.getState());
        }
        System.out.println(first.getName() + ": " + first.getState());
        System.out.println(second.getName() + ": " + second.getState());
        System.out.println("Main thread: The work is complete");
    }
}
