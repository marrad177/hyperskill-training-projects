package search;

import java.util.*;

public class SearchNoneMethod implements SearchMethod{
    @Override
    public List<Integer> findPeople(HashMap<String, ArrayList<Integer>> invertedIndex, List<String> searchTerms) {
        List<Integer> results = new ArrayList<>(10);
        Set<Integer> allValues = new HashSet<>(10);
        for(ArrayList<Integer> listOfLines : invertedIndex.values()) {
            allValues.addAll(listOfLines);
        }
        for(String searchTerm : searchTerms) {
            if(invertedIndex.containsKey(searchTerm)) {
                for(int lineNumber : invertedIndex.get(searchTerm)) {
                    results.add(lineNumber);
                }
            }
        }
        allValues.removeAll(results);
        return new ArrayList<>(allValues);
    }
}
