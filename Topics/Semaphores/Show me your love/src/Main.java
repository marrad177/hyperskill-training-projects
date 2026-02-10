import java.util.concurrent.*;

class Mouth {
    //declare your attributes here
    Semaphore semaphore1;
    Semaphore semaphore2;
    Semaphore semaphore3;

    public Mouth() {
        semaphore1 = new Semaphore(1, true);
        semaphore2 = new Semaphore(0, true);
        semaphore3 = new Semaphore(0, true);
    }

    // Update the method
    public void first() throws InterruptedException {
        semaphore1.acquire();
        System.out.print("I "); // Do not change or remove this line
        semaphore2.release();
    }

    // Update the method
    public void second() throws InterruptedException {
        semaphore2.acquire();
        System.out.print("love "); // Do not change or remove this line
        semaphore3.release();
    }

    // Update the method
    public void third() throws InterruptedException {
        semaphore3.acquire();
        System.out.print("programming!"); // Do not change or remove this line
        semaphore3.release();
    }
}