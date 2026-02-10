class ThreadUtil {
    static Thread createThreadInNewState() {
        //implement the method
    }
}

class Main {
    public static void main(String[] args) {
        Thread thread = ThreadUtil.createThreadInNewState();
        System.out.println(thread.getState());
    }
}