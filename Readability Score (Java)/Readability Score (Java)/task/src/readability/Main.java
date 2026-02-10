package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String path = args[0];
        File file = new File(path);

        try (Scanner scanner = new Scanner(file) ) {
            List<String> sentences = new ArrayList<>();
            StringBuffer sentence = new StringBuffer();
            List<String> polysyllables = new ArrayList<>();
            int sentenceCount = 0;
            int characterCount = 0;
            int wordCount = 0;
            int syllableCount = 0;
            int polysyllableCount = 0;

            while(scanner.hasNext()) {
                String temp = scanner.next();
                if (temp.contains(".") || temp.contains("!") || temp.contains("?") || !scanner.hasNext()) {
                    sentence.append(temp).append(" ");
                    sentences.add(sentence.toString());
                    sentence.setLength(0);
                } else {
                    sentence.append(temp).append(" ");
                }
            }
            sentenceCount = sentences.size();

            for(String item : sentences) {
                String[] words = item.replaceAll("[+.!?,\"\\(\\)]", "").trim().split("\\s+");
                wordCount += words.length;
                for(String word : words) {
                    int syllablePerWordCount = 0;
                    String pattern = "\\b[^aeyuioAEYUIO\\s]+[aeyuioAEYUIO]\\b|[aeyuioAEYUIO]*[eE](?!\\b)[aeyuioAEYUIO]*|[ayuioAYUIO]+|\\d+[,.]\\d*";
                    Matcher matcher = Pattern.compile(pattern).matcher(word);
                    while(matcher.find()) {
                        syllablePerWordCount++;
                    }
                    if (syllablePerWordCount == 0) {
                        syllablePerWordCount++;
                    } else if (syllablePerWordCount > 2) {
                        polysyllables.add(word);
                    }
                    syllableCount += syllablePerWordCount;

                }
                characterCount += item.replaceAll("[+\"]|\\s+", "").length();
            }
            polysyllableCount = polysyllables.size();
            System.out.println("Words: " + wordCount);
            System.out.println("Sentences: " + sentenceCount);
            System.out.println("Characters: " + characterCount);
            System.out.println("Syllables: " + syllableCount);
            System.out.println("Polysyllables: " + polysyllableCount);
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
            Scanner cmdScanner = new Scanner(System.in);
            String operation = cmdScanner.next();
            switch (operation) {
                case "ARI":
                    double scoreAri = ReadabilityScores.calculateAriScore(sentenceCount, wordCount, characterCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreAri) == gradeLevel.getScore()) {
                            System.out.printf("Automated Readability Index: %.2f (about %s-year-olds).\n", scoreAri, gradeLevel.getAge());
                        }
                    }
                    break;
                case "FK":
                    double scoreFk = ReadabilityScores.calculateFkScore(sentenceCount, wordCount, syllableCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreFk) == gradeLevel.getScore()) {
                            System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).\n", scoreFk, gradeLevel.getAge());
                        }
                    }
                    break;
                case "SMOG":
                    double scoreSmog = ReadabilityScores.calculateSmogScore(sentenceCount, polysyllableCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreSmog) == gradeLevel.getScore()) {
                            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", scoreSmog, gradeLevel.getAge());
                        }
                    }
                    break;
                case "CL":
                    double scoreCl = ReadabilityScores.calculateClScore(sentenceCount, characterCount, wordCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreCl) == gradeLevel.getScore()) {
                            System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).\n", scoreCl, gradeLevel.getAge());
                        }
                    }
                    break;
                case "all":
                    double sumAge = 0;
                    double scoreAriAll = ReadabilityScores.calculateAriScore(sentenceCount, wordCount, characterCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreAriAll) == gradeLevel.getScore()) {
                            sumAge += gradeLevel.getAge();
                            System.out.printf("Automated Readability Index: %.2f (about %s-year-olds).\n", scoreAriAll, gradeLevel.getAge());
                        }
                    }
                    double scoreFkAll = ReadabilityScores.calculateFkScore(sentenceCount, wordCount, syllableCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreFkAll) == gradeLevel.getScore()) {
                            sumAge += gradeLevel.getAge();
                            System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds).\n", scoreFkAll, gradeLevel.getAge());
                        }
                    }
                    double scoreSmogAll = ReadabilityScores.calculateSmogScore(sentenceCount, polysyllableCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.ceil(scoreSmogAll) == gradeLevel.getScore()) {
                            sumAge += gradeLevel.getAge();
                            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", scoreSmogAll, gradeLevel.getAge());
                        }
                    }
                    double scoreClAll = ReadabilityScores.calculateClScore(sentenceCount, characterCount, wordCount);
                    for(GradeLevel gradeLevel : GradeLevel.values()) {
                        if(Math.floor(scoreClAll) == gradeLevel.getScore()) {
                            sumAge += gradeLevel.getAge();
                            System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds).\n", scoreClAll, gradeLevel.getAge());
                        }
                    }
                    double average = sumAge/4;
                    System.out.printf("\nThis text should be understood in average by %.2f-year-olds.", average);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
