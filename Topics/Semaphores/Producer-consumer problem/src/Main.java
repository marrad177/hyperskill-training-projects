import java.util.concurrent.Semaphore;

class Buffer {
    // the data stored in Buffer. Do not change or remove this line.
    private char data;
    //init your attributes here

    // to get data from Buffer
    public void get() throws InterruptedException {
        //complete this method

        // consumer consuming data, do not change or remove this line
        System.out.println("Consumer consumed data : " + data);
    }

    // to put data in buffer
    public void put(char data) throws InterruptedException {
        //complete this method

        // producer producing an data, do not change or remove this lines
        this.data = data;
        System.out.println("Producer produced data : " + data);
    }
}