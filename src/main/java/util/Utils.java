package util;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Utils {

    public static boolean isStopWord(String word) throws FileNotFoundException {
//        CharArraySet luceneStopWords = org.apache.lucene.analysis.en.EnglishAnalyzer.getDefaultStopSet();
        Scanner s = new Scanner(new File("stop_words.txt"));
        Set<String> stopWords = new HashSet<String>();

        while (s.hasNext()){
            stopWords.add(s.next());
        }
        s.close();

        if(stopWords.contains(word))
            return true;
        return false;
    }

    public static ArrayList<String> softStemming(String s) throws FileNotFoundException {
        s = s.toLowerCase();
        s = s.replaceAll("[\\.\\,\\:\\?\\<\\>]", "");
        s = s.replaceAll("[^a-zA-Z0-9'\\s]", "").replaceAll("\\s+", " ");
        String[] tmp = s.split(" ");
//        ArrayList<String> res = new ArrayList<String>(Arrays.asList(tmp));

        ArrayList<String> res = new ArrayList<String>();

        for (String word: tmp) {
            word = word.toLowerCase();
            if (isStopWord(word) || word.length() == 1 || word.matches("-?\\d+(\\.\\d+)?")) {
                continue;
            }
            res.add(word);

        }

        return res;
    }

    public static ArrayList<String> stemInput(String s) throws FileNotFoundException {
        SnowballStemmer stemmer = (SnowballStemmer) new englishStemmer();
        s = s.replaceAll("[\\.\\,\\:\\?\\<\\>]", "");
        s = s.replaceAll("[^a-zA-Z0-9'\\s]", "").replaceAll("\\s+", " ");
        String[] tmp = s.split(" ");
        ArrayList<String> res = new ArrayList<String>();

        for (String word: tmp) {
            word = word.toLowerCase();
            if (isStopWord(word) || word.length() == 1 || word.matches("-?\\d+(\\.\\d+)?")) {
                continue;
            }

            stemmer.setCurrent(word);
            stemmer.stem();

            res.add(stemmer.getCurrent());
        }
        return res;
    }

    public static ArrayList<String> lemmatizeInput(String s) throws FileNotFoundException {
        SnowballStemmer stemmer = (SnowballStemmer) new englishStemmer();
        s = s.replaceAll("[\\.\\,\\:\\?\\<\\>]", "");
        s = s.replaceAll("[^a-zA-Z0-9'\\s]", "").replaceAll("\\s+", " ");
        String[] tmp = s.split(" ");
        ArrayList<String> res = new ArrayList<String>();

        for (String word: tmp) {
            word = word.toLowerCase();
            if (isStopWord(word) || word.length() == 1 || word.matches("-?\\d+(\\.\\d+)?")) {
                continue;
            }

            StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
            List<String> result = lemmatizer.lemmatize(word);
            if(!result.isEmpty())
                res.add(result.get(0));
        }
        return res;
    }
}
