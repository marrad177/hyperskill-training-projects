package readability;

public class ReadabilityScores {
    public static double calculateAriScore(int sentenceCount, int wordCount, int characterCount) {
        double score = (4.71*characterCount/wordCount) + (0.5*wordCount/sentenceCount) - 21.43;
        return score;
    }

    public static double calculateFkScore(int sentenceCount, int wordCount, int syllableCount) {
        double score = (0.39*wordCount/sentenceCount) + (11.8*syllableCount/wordCount) - 15.59;
        return score;
    }

    public static double calculateSmogScore(int sentenceCount, int polySyllableCount) {
        double score = 1.043 * Math.sqrt(polySyllableCount * 30 / sentenceCount) + 3.1291;
        return score;
    }

    public static double calculateClScore(int sentenceCount, int characterCount, int wordCount) {
        double avgChars = ((double) characterCount / wordCount) * 100;   //501,46
        double avgSentences = ((double) sentenceCount / wordCount) * 100;    //10,22
        double score = 0.0588 * avgChars - 0.296 * avgSentences - 15.8;
        return score;
    }
}
