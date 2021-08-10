import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String CALCULATE_ERROR = "Calculate Error";
    private static final String BLANK_SPACES = "\\s+";
    private static final String NEW_LINE = "\n";

    public String getResult(String inputString) {
        try {
            List<WordInfo> wordInfoList = calculateWordCount(inputString);
            return generateWordCountString(wordInfoList);
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private String generateWordCountString(List<WordInfo> wordInfos) {
        StringJoiner joiner = new StringJoiner(NEW_LINE);
        wordInfos
                .stream()
                .map(word -> String.format("%s %d", word.getValue(), word.getWordCount()))
                .forEach(joiner::add);
        return joiner.toString();
    }

    private List<WordInfo> calculateWordCount(String inputString) {
        List<String>words = Arrays.asList(inputString.split(BLANK_SPACES));
        List<String> distinctWords = words.stream().distinct().collect(Collectors.toList());
        List<WordInfo> wordInfos = new ArrayList<>();

        distinctWords.forEach(word -> {
            int wordCount = Collections.frequency(words, word);
            wordInfos.add(new WordInfo(word, wordCount));
        });
        sortWordInfos(wordInfos);
        return wordInfos;
    }

    private void sortWordInfos(List<WordInfo> wordInfos) {
        wordInfos.sort((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount());
    }
}
