import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class Enemy {
    private static final long TIMEOUT_MS = 10L;

    private final ReentrantLock lock = new ReentrantLock();
    public int health;

    public Enemy(int health) {
        this.health = health;
    }

    public boolean protect(int damage) throws InterruptedException {
        if (lock.tryLock()) {
            // Write your code here...
        } else {
            return true;
        }
    }
}

// Do not change code below
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int totalHealth = scanner.nextInt();
        int hit1 = scanner.nextInt();
        int hit2 = scanner.nextInt();

        Enemy enemy = new Enemy(totalHealth);
        Thread thread1 = new Thread(() -> Main.activeAttack(enemy, hit1));
        Thread thread2 = new Thread(() -> Main.activeAttack(enemy, hit2));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(enemy.health);
    }

    private static void activeAttack(Enemy enemy, int damage) {
        while (true) {
            try {
                if (!enemy.protect(damage)) {
                    break;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}