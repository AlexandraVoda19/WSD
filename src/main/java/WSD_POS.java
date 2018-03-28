import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.*;
import net.sf.extjwnl.dictionary.Dictionary;
import org.apache.commons.lang3.StringUtils;
import util.Pair;
import util.StanfordLemmatizer;
import util.Utils;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class WSD_POS {
    public final Integer WINDOW_SIZE = 3;
    public final String GLOSS_REL = "gloss";
    public Dictionary dictionary;

    String targetWord;
    String inputPhrase;
    WNSense wnSense;

    String sTargetWord;
    ArrayList<String> sInputPhrase;

    public List<String> leftWords = new ArrayList<String>();
    public List<String> rightWords = new ArrayList<String>();
    public List<String> windowWords = new ArrayList<String>();

    public List<Pair<String, String>> relpairs = new ArrayList<Pair<String, String>>();

    public WSD_POS() {
    }

    public WSD_POS(String targetWord, String inputPhrase, WNSense sense, POS pos) throws JWNLException, FileNotFoundException {
        this.targetWord = targetWord;
        this.inputPhrase = inputPhrase;
        this.wnSense = sense;
//        this.sTargetWord = Utils.stemInput(targetWord).get(0);
        this.sTargetWord = targetWord;
        this.sInputPhrase = Utils.lemmatizeInput(inputPhrase);
        dictionary = Dictionary.getDefaultResourceInstance();

        initWindowWords(pos);
        initRelpairs();
    }

    public void initRelpairs(){
        Pair<String, String> gloss_gloss = new Pair(GLOSS_REL, GLOSS_REL);
        Pair<String, String> mero_gloss = new Pair(PointerType.PART_MERONYM.getLabel(), GLOSS_REL);
        Pair<String, String> gloss_mero = new Pair(GLOSS_REL, PointerType.PART_MERONYM.getLabel());

//        Pair<String, String> hypo_mero = new Pair(PointerType.HYPONYM.getLabel(), PointerType.PART_MERONYM.getLabel());
//        Pair<String, String> mero_hypo = new Pair(PointerType.PART_MERONYM.getLabel(), PointerType.HYPONYM.getLabel());
//        Pair<String, String> hypo_hypo = new Pair(PointerType.HYPONYM.getLabel(), PointerType.HYPONYM.getLabel());

//        Pair<String, String> mero_mero = new Pair(PointerType.PART_MERONYM.getLabel(), PointerType.PART_MERONYM.getLabel());
//        Pair<String, String> hypo_gloss = new Pair(PointerType.HYPONYM.getLabel(), GLOSS_REL);
//        Pair<String, String> gloss_hypo = new Pair(GLOSS_REL, PointerType.HYPONYM.getLabel());

        relpairs.add(gloss_gloss);
//        relpairs.add(hypo_hypo);
        relpairs.add(mero_gloss);
        relpairs.add(gloss_mero);
//        relpairs.add(hypo_mero);
//        relpairs.add(mero_hypo);
//        relpairs.add(mero_mero);
//        relpairs.add(hypo_gloss);
//        relpairs.add(gloss_hypo);
    }

    public boolean isWordInWN(String word, POS pos) throws JWNLException {
        if ( dictionary.getIndexWord(pos, word) != null ) {
            return true;
        }
        return false;
    }

    public void initWindowWords(POS pos) throws JWNLException {

        Integer targetWordPosition = sInputPhrase.indexOf(sTargetWord);
        int i = 1;
        while( targetWordPosition + i < sInputPhrase.size()
                && targetWordPosition - i >= 0
                && rightWords.size() < WINDOW_SIZE
                && leftWords.size() < WINDOW_SIZE ){
            if( isWordInWN(sInputPhrase.get(targetWordPosition + i), pos) ){
               rightWords.add(sInputPhrase.get(targetWordPosition + i));
            }
            if( isWordInWN(sInputPhrase.get(targetWordPosition - i), pos) ){
                leftWords.add(sInputPhrase.get(targetWordPosition - i));
            }
            i++;
        }

        while( rightWords.size() < WINDOW_SIZE && targetWordPosition + i < sInputPhrase.size() && (rightWords.size() + leftWords.size()) < WINDOW_SIZE * 2 ){
            if( isWordInWN(sInputPhrase.get(targetWordPosition + i), pos) ){
                rightWords.add(sInputPhrase.get(targetWordPosition + i));
            }
            i++;
        }

        while( leftWords.size() < WINDOW_SIZE && targetWordPosition - i >= 0 && (rightWords.size() + leftWords.size()) < WINDOW_SIZE * 2 ){
            if( isWordInWN(sInputPhrase.get(targetWordPosition - i), pos) ){
                leftWords.add(sInputPhrase.get(targetWordPosition - i));
            }
            i++;
        }

        windowWords.addAll(rightWords);
        windowWords.addAll(leftWords);

    }

    public static ArrayList<String> longestSubstring(ArrayList<String> str1, ArrayList<String> str2) {

        ArrayList<String> result = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
            return result;

        int[][] num = new int[str1.size()][str2.size()];
        int maxlen = 0;
        int lastSubsBegin = 0;

        for (int i = 0; i < str1.size(); i++) {
            for (int j = 0; j < str2.size(); j++) {
                if( str1.get(i).equals(str2.get(j)) ){
                    if ((i == 0) || (j == 0))
                        num[i][j] = 1;
                    else
                        num[i][j] = 1 + num[i - 1][j - 1];

                    if (num[i][j] > maxlen) {
                        maxlen = num[i][j];
                        // generate substring from str1 => i
                        int thisSubsBegin = i - num[i][j] + 1;
                        if (lastSubsBegin == thisSubsBegin) {
                            //if the current LCS is the same as the last time this block ran
                            result.add(str1.get(i));
//                            sb.append(str1.charAt(i));
                        } else {
                            //this block resets the string builder if a different LCS is found

                            lastSubsBegin = thisSubsBegin;
                            result.clear();
                            result.addAll(str1.subList(lastSubsBegin, i+1));
//                            sb = new StringBuilder();
//                            sb.append(str1.substring(lastSubsBegin, i + 1));
                        }
                    }
                }
            }}

        return result;
    }

    public Integer calculateScore(ArrayList<String> s1, ArrayList<String> s2){
        Integer score = 0;

        ArrayList<String> longestMatch = longestSubstring(s1, s2);
        while( longestMatch.size() > 0 ){
            score += longestMatch.size() * longestMatch.size();
            s1.subList(Collections.indexOfSubList(s1, longestMatch), Collections.indexOfSubList(s1, longestMatch) + longestMatch.size()).clear();
            s2.subList(Collections.indexOfSubList(s2, longestMatch), Collections.indexOfSubList(s2, longestMatch) + longestMatch.size()).clear();

//            System.out.println("S1 = " + s1);
//            System.out.println("S2 = " + s2);
//            System.out.println("score = " + score);

            longestMatch = longestSubstring(s1, s2);
        }

        return score;
    }

    private ArrayList<String> getRelText(Synset sense, String relName) throws JWNLException, FileNotFoundException {

        if( relName.equals("gloss") ){
            String gloss = sense.getGloss();
//            ArrayList<String> softStemmed = Utils.stemInput(gloss);
            ArrayList<String> softStemmed = Utils.softStemming(gloss);
//            ArrayList<String> lemmatized = Utils.lemmatizeInput(StringUtils.join(softStemmed, " "));
//            return lemmatized;
            return softStemmed;
        } else {
            String relText = getContatenatedRelGlosses(sense, relName);
//            ArrayList<String> softStemmed = Utils.stemInput(relText);
            ArrayList<String> softStemmed = Utils.softStemming(relText);
//            ArrayList<String> lemmatized = Utils.lemmatizeInput(StringUtils.join(softStemmed, " "));
//            return lemmatized;
            return softStemmed;
        }
    }

    private String getContatenatedRelGlosses(Synset sense, String relName) throws JWNLException {
        String result = "";
        List<Pointer> pointers = sense.getPointers();
        if( pointers != null && pointers.size() > 0 ){
            for( Pointer pointer:pointers ){
                if( pointer.getType().equals(relName) ) {
                    result = result + "; " + pointer.getTarget().getSynset().getGloss();
                }
            }
        }
        return result;
    }

    private Integer getCurrentContextWordScore(Synset targetSense, Synset contextWordSense) throws JWNLException, FileNotFoundException {
        Integer score = 0;

        for( Pair<String, String> rel:relpairs ){
            ArrayList<String> targetRelText = getRelText(targetSense, rel.getFirst());
            ArrayList<String> contextRelText = getRelText(contextWordSense, rel.getSecond());
            Integer currentRelPairScore = calculateScore(targetRelText, contextRelText);
            score += currentRelPairScore;
        }

        return score;
    }


    private Integer calculateSenseScore(Synset sense, POS pos) throws JWNLException, FileNotFoundException {
        Integer score = 0;

        for( String windowWord:windowWords ){
            IndexWord WNwindowWord = dictionary.getIndexWord(pos, windowWord);
            if(WNwindowWord != null) {
                for( Synset contextWordSense: getSynsetsForPos(WNwindowWord.getSenses(), pos) ) {
                    Integer contextWordScore = getCurrentContextWordScore(sense, contextWordSense);
                    score += contextWordScore;
                }
            }

        }

        return score;
    }


    public List<Long> pickCorrectSense(POS pos) throws JWNLException, FileNotFoundException {
        List<Pair<Long, Pair.PairValue>> sensesScores = new ArrayList<Pair<Long, Pair.PairValue>>();
        Map<Long, Synset> synsetsByKey = new HashMap<Long, Synset>();
        IndexWord WNtarget = dictionary.getIndexWord(pos, sTargetWord);
        if ( WNtarget != null ) {
            for( Synset sense : getSynsetsForPos(WNtarget.getSenses(), pos) ) {
                Integer senseScore = calculateSenseScore(sense, pos);
                synsetsByKey.put((Long)sense.getKey(), sense);
                Pair.PairValue pairValue = new Pair.PairValue(senseScore, wnSense.getSenseMapping().get(sense.getKey()) );
                Pair<Long, Pair.PairValue> senseScorePair = new Pair(sense.getKey(), pairValue);
                sensesScores.add(senseScorePair);
            }
        }


        Collections.sort(sensesScores, new Comparator<Pair<Long, Pair.PairValue>>() {
            public int compare(Pair<Long, Pair.PairValue> p1, Pair<Long, Pair.PairValue> p2) {
                return Integer.compare((Integer)p1.getSecond().getScore(), (Integer)p2.getSecond().getScore());
            }
        });

//        int winnerPos = 0;
//        Integer max = sensesScores.get(winnerPos).getSecond();
//        for(int i = 1; i < sensesScores.size(); i++){
//            if(sensesScores.get(i).getSecond() > max){
//                max = sensesScores.get(i).getSecond();
//                winnerPos = i;
//            }
//        }

        if( !sensesScores.isEmpty() ) {
//            Long winnerKey = sensesScores.get(sensesScores.size() - 1).getFirst();
            List<Long> winnerKeys = sensesScores.subList(sensesScores.size() - 6, sensesScores.size() - 1).stream().map( p -> p.getFirst()).collect(Collectors.toList());
//            System.out.println("Result(score=" + sensesScores.get(sensesScores.size() - 1).getSecond()
//                    + "): " + winner.getWords().toString());
            return winnerKeys;

        } else {
            System.out.println("No sense found!");
            return null;
        }

    }

    protected List<Synset> getSynsetsForPos(List<Synset> synsets, POS pos){
        synsets.get(0).getPOS().equals(pos);
        return synsets.stream().filter(s -> s.getPOS().equals(pos)).collect(Collectors.toList());
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public String getInputPhrase() {
        return inputPhrase;
    }

    public void setInputPhrase(String inputPhrase) {
        this.inputPhrase = inputPhrase;
    }

    public String getsTargetWord() {
        return sTargetWord;
    }

    public void setsTargetWord(String sTargetWord) {
        this.sTargetWord = sTargetWord;
    }

    public ArrayList<String> getsInputPhrase() {
        return sInputPhrase;
    }

    public void setsInputPhrase(ArrayList<String> sInputPhrase) {
        this.sInputPhrase = sInputPhrase;
    }

    public List<String> getWindowWords() {
        return windowWords;
    }

    public void setWindowWords(List<String> windowWords) {
        this.windowWords = windowWords;
    }
}
