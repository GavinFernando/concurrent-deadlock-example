public class DeadlockExampleFix {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 1: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2! and has released lock 1");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock1) { // Lock1 is acquired first
                System.out.println("Thread 2: Holding lock1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 2: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("Thread 2: Acquired lock2! and has released lock 2");
                }
            }
        });

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Both threads have finished.");
    }
}
