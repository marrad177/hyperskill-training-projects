package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String path = args[1];
        File file = new File(path);
        try(Scanner fileScanner = new Scanner(file)) {
            List<String> inputLines = new ArrayList<>(20);
            while(fileScanner.hasNext()) {
                inputLines.add(fileScanner.nextLine());
            }

            boolean inUse = true;
            Scanner consoleScanner = new Scanner(System.in);
            while(inUse) {
                System.out.println("\n=== Menu ===");
                System.out.println("1. Find a person");
                System.out.println("2. Print all people");
                System.out.println("0. Exit");
                int menuOption = Integer.parseInt(consoleScanner.nextLine());

                switch (menuOption) {
                    case 0:
                        System.out.println("\nBye!");
                        inUse = false;
                        break;
                    case 1:
                        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
                        String searchStrategy = consoleScanner.nextLine();
                        System.out.println("\nEnter a name or email to search all suitable people.");
                        List<String> searchTerms = List.of(consoleScanner.nextLine().toLowerCase().split(" "));
                        // invoke context for search strategy, set strategy
                        SearchMethodContext searchMethodContext = new SearchMethodContext();
                        searchMethodContext.setSearchMethod(searchStrategy);
                        List<Integer> results = searchMethodContext.findPeople(inputLines, searchTerms);
                        if(results.size() > 0) {
                            System.out.printf("%d persons found:\n", results.size());
                            for(int index : results) {
                                System.out.println(inputLines.get(index));
                            }
                        } else {
                            System.out.println("No matching people found.");
                        }
                        break;
                    case 2:
                        printPeople(inputLines);
                        break;
                    default:
                        System.out.println("\nIncorrect option! Try again.");
                        break;
                }

            }
       } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        }
    }

    static void printPeople(List<String> inputLines) {
        System.out.println("\n=== List of people ===");
        for (String line : inputLines) {
            System.out.println(line);
        }
    }
}
