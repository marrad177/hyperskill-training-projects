import java.util.List;import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

class PrintElementsClass {

    public static void printElements(CopyOnWriteArrayList<String> onWriteArrayList, String elementToAdd) {
        CopyOnWriteArrayList<String> arrList = onWriteArrayList;
        arrList.add(elementToAdd);
        Iterator iterator = arrList.iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }
}