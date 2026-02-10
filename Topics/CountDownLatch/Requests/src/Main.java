import java.util.concurrent.CountDownLatch;
import java.util.Random;
import java.util.Scanner;

class RequestHandler implements Runnable {
    private final int requestId;
    private final CountDownLatch latch;
    private final SharedData data;
    private final int TIME = 5000;
    private final int BIAS = 1000;

    public RequestHandler(int requestId, CountDownLatch latch, SharedData data) {
        this.requestId = requestId;
        this.latch = latch;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            data.multiply(2);
            Thread.sleep(new Random().nextInt(TIME) + BIAS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}

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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int threadsCount = scanner.nextInt();
        CountDownLatch latch = new CountDownLatch(threadsCount);

        SharedData data = new SharedData();
        for (int i = 0; i < threadsCount; i++) {
            new Thread(new RequestHandler(i, latch, data)).start();
        }

        try{
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(data.getValue());
    }
}