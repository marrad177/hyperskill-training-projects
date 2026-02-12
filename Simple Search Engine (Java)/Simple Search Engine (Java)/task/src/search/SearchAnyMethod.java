package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SearchAnyMethod implements SearchMethod{
    @Override
    public List<Integer> findPeople(HashMap<String, ArrayList<Integer>> invertedIndex, List<String> searchTerms) {
        List<Integer> results = new ArrayList<>(10);
        for(String searchTerm : searchTerms) {
            if(invertedIndex.containsKey(searchTerm)) {
                for(int lineNumber : invertedIndex.get(searchTerm)) {
                    results.add(lineNumber);
                }
            }
        }
        return results;
    }
}
