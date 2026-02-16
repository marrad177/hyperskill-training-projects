package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAllMethod implements SearchMethod{
    @Override
    public List<Integer> findPeople(HashMap<String, ArrayList<Integer>> invertedIndex, List<String> searchTerms) {
        List<Integer> resultsFirstTerm = new ArrayList<>(10);
        List<Integer> resultsRest = new ArrayList<>(10);
        if(invertedIndex.containsKey(searchTerms.get(0))) {
            resultsFirstTerm = invertedIndex.get(searchTerms.get(0));
        }
        for(int i = 1; i < searchTerms.size(); i++) {
            if(invertedIndex.containsKey(searchTerms.get(i))) {
                for(int lineNumber : invertedIndex.get(searchTerms.get(i))) {
                    resultsRest.add(lineNumber);
                }
            }
        }
        resultsRest.retainAll(resultsFirstTerm);
        return resultsRest;
    }
}
