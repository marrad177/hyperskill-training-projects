package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\Hyperskill Jetbeans Projects\\Simple Search Engine (Java)\\Simple Search Engine (Java)\\task\\src\\search\\" + args[1];
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
                        System.out.println("\nEnter a name or email to search all suitable people.");
                        String searchTerm = consoleScanner.nextLine().toLowerCase();
                        findPerson(inputLines, searchTerm);
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

    static void findPerson(List<String> inputLines, String searchTerm) {
        HashMap<String, ArrayList<Integer>> invertedIndex = new HashMap(32);
        for(int i = 0; i < inputLines.size(); i++) {
            for(String word : inputLines.get(i).split(" ")) {
                if(invertedIndex.containsKey(word)) {
                    invertedIndex.get(word).add(i);
                } else {
                    invertedIndex.put(word, new ArrayList(Arrays.asList(i)));
                }
            }
        }
        boolean notFound = true;

        for(String word : inputLines) {
            if(word.toLowerCase().contains(searchTerm)) {
                System.out.println(word);
                notFound = false;
            }
        }
        if(notFound) {
            System.out.println("No matching people found.");
        }
    }

    static void printPeople(List<String> inputLines) {
        System.out.println("\n=== List of people ===");
        for (String line : inputLines) {
            System.out.println(line);
        }
    }
}
