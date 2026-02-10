class CounterThread extends Thread {

    @Override
    public void run() {
        long counter = 0;

        while (true) {
            if (Thread.interrupted()) {
                System.out.println("It was interrupted");
                break;
            } else {
                counter++;
            }
        }
    }
}