import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String BLANK_SPACES = "\\s+";
    private static final String NEW_LINE = "\n";

    public String getResult(String inputString) {
        try {
            List<WordInfo> wordInfoList = calculateWordCount(inputString);
            return generateWordCountString(wordInfoList);
        } catch (Exception e) {
            return "Calculate Error";
        }
    }

    private String generateWordCountString(List<WordInfo> wordInfos) {
        StringJoiner joiner = new StringJoiner(NEW_LINE);
        for (WordInfo word : wordInfos) {
            String sentence = String.format("%s %d",word.getValue(),word.getWordCount());
            joiner.add(sentence);
        }
        return joiner.toString();
    }

    private List<WordInfo> calculateWordCount(String inputString) {
        List<String>words = Arrays.asList(inputString.split(BLANK_SPACES));
        List<String> distinctWords = words.stream().distinct().collect(Collectors.toList());
        List<WordInfo> wordInfos = new ArrayList<>();

        for (String word : distinctWords) {
            int wordCount = Collections.frequency(words, word);
            WordInfo wordInfo = new WordInfo(word, wordCount);
            wordInfos.add(wordInfo);
        }
        sortWordInfos(wordInfos);
        return wordInfos;
    }

    private void sortWordInfos(List<WordInfo> wordInfos) {
        wordInfos.sort((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount());
    }
}
