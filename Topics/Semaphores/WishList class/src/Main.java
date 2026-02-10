import java.util.ArrayList;import java.util.concurrent.Semaphore;class WishList {
    private ArrayList<Integer> products;  // list of product_ids
    Semaphore semaphore = new Semaphore(1);

    public WishList() {
        this.products = new ArrayList<>();
    }

    public void addProduct(int product_id) {
        try {
            semaphore.acquire();
            products.add(product_id);

        } catch (InterruptedException e) {
            e.getMessage();
        } finally {
            semaphore.release();
        }
    }

    public void removeProduct(int product_id) {
        try {
            if(!semaphore.tryAcquire()) {
                semaphore.acquire();
            }
            products.remove(Integer.valueOf(product_id));
        } catch (InterruptedException e) {
            e.getMessage();
        } finally {
            semaphore.release();
        }
    }

    public int getSize() {
        return products.size();
    }
}