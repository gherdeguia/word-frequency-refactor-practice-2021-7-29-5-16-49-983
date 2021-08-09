import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    private static final String BLANK_SPACES = "\\s+";
    private static final String NEW_LINE = "\n";

    public String getResult(String inputString) {
        if (inputString.split(BLANK_SPACES).length == 1) {
            return inputString + " 1";
        } else {
            try {
                List<WordInfo> wordInfoList = calculateWordCount(inputString);
                return generateWordCountString(wordInfoList);
            } catch (Exception e) {
                return "Calculate Error";
            }
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
        //split the input string with 1 to n pieces of spaces
        String[] wordList = inputString.split(BLANK_SPACES);

        List<WordInfo> wordInfoList = new ArrayList<>();
        for (String s : wordList) {
            WordInfo wordInfo = new WordInfo(s, 1);
            wordInfoList.add(wordInfo);
        }

        //get the map for the next step of sizing the same word
        Map<String, List<WordInfo>> map = getListMap(wordInfoList);

        List<WordInfo> list = new ArrayList<>();
        for (Map.Entry<String, List<WordInfo>> entry : map.entrySet()) {
            WordInfo wordInfo = new WordInfo(entry.getKey(), entry.getValue().size());
            list.add(wordInfo);
        }
        wordInfoList = list;

        wordInfoList.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());
        return wordInfoList;
    }

    private List<WordInfo> calculateWordCount_Temp(String inputString) {
        List<String>words = Arrays.asList(inputString.split(BLANK_SPACES));
        List<String> distinctWords = words.stream().distinct().collect(Collectors.toList());
        List<WordInfo> wordInfos = new ArrayList<>();

        for (String word : distinctWords) {
            int wordCount = Collections.frequency(words, word);
            WordInfo wordInfo = new WordInfo(word, wordCount);
            wordInfos.add(wordInfo);
        }
        wordInfos.sort((firstWord, secondWord) -> secondWord.getWordCount() - firstWord.getWordCount());
        return wordInfos;
    }

    private Map<String, List<WordInfo>> getListMap(List<WordInfo> wordInfoList) {
        Map<String, List<WordInfo>> map = new HashMap<>();
        for (WordInfo wordInfo : wordInfoList) {
//       map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
            if (!map.containsKey(wordInfo.getValue())) {
                ArrayList arr = new ArrayList<>();
                arr.add(wordInfo);
                map.put(wordInfo.getValue(), arr);
            } else {
                map.get(wordInfo.getValue()).add(wordInfo);
            }
        }
        return map;
    }
}
