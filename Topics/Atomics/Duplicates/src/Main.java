import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;

class MyList {
    public void removeDuplicates(AtomicReference<List<Integer>> list) {
        List<Integer> numbersList = list.get();
        Collections.sort(numbersList);
        HashSet<Integer> uniqueElementsSet = new HashSet<>(numbersList);
        List<Integer> resultList = new ArrayList<>(uniqueElementsSet);
        list.set(resultList);
    }
}