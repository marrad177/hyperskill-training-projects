import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Scanner;

class Worker implements Runnable {
    private final ReentrantLock lock;
    private final SharedData data;

    public Worker(ReentrantLock lock, SharedData data) {
        this.lock = lock;
        this.data = data;
    }

    @Override
    public void run() {
        // Protect code below...
        data.multiply(2);
    }
}

// Do not change code below
class SharedData {
    private int counter = 1;

    public void multiply(int mult) {
        counter = mult * counter;
    }

    public int getValue() {
        return counter;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int threadsCount = scanner.nextInt();

        ReentrantLock lock = new ReentrantLock();
        SharedData data = new SharedData();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new Worker(lock, data));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(data.getValue());
    }
}