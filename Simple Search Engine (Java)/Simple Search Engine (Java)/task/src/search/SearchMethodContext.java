package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class SearchMethodContext {
    private SearchMethod searchMethod;

    public void setSearchMethod(String searchStrategy) {
        this.searchMethod = switch (searchStrategy) {
            case "ALL" -> new SearchAllMethod();
            case "ANY" -> new SearchAnyMethod();
            case "NONE" -> new SearchNoneMethod();
            default -> new SearchAnyMethod();
        };
    }

    public List<Integer> findPeople(List<String> inputLines, List<String> searchTerms) {
        HashMap<String, ArrayList<Integer>> invertedIndex = new HashMap(32);
        for(int i = 0; i < inputLines.size(); i++) {
            for(String word : inputLines.get(i).split(" ")) {
                if(invertedIndex.containsKey(word.toLowerCase())) {
                    invertedIndex.get(word.toLowerCase()).add(i);
                } else {
                    invertedIndex.put(word.toLowerCase(), new ArrayList(Arrays.asList(i)));
                }
            }
        }
        return this.searchMethod.findPeople(invertedIndex, searchTerms);
    }
}
