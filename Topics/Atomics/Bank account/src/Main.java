import java.util.concurrent.atomic.AtomicLong;

class BankAccount {
    private AtomicLong balance;

    public BankAccount(long initialBalance) {
        balance = new AtomicLong(initialBalance);
    }

    public void deposit(long amount) {
        balance.getAndAdd(amount);
    }

    public boolean withdraw(long amount) {
        if(balance.get() - amount >= 0) {
            balance.getAndAdd(0 - amount);
            return true;
        } else {
            return false;
        }
    }

    public long getBalance() {
        return balance.get();
    }
}