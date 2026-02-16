package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface SearchMethod {
    List<Integer> findPeople(HashMap<String, ArrayList<Integer>> invertedIndex, List<String> searchTerm);
}
