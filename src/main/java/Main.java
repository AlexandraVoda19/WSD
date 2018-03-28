import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Pointer;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;
import org.apache.commons.lang3.StringUtils;
import util.StanfordLemmatizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void showRels(IndexWord word){
        List<Synset> senses = word.getSenses();

        for( Synset sense: senses ){
            List<Pointer> pointers = sense.getPointers();
            if( pointers != null && pointers.size() > 0 ){
                String rez = "";
                for( Pointer pointer:pointers ){
                    rez = rez + pointer.getType().getLabel() + "; ";
                }
                System.out.println("REZ "+ word.getLemma() + " = " + rez);
            }
        }
    }

    public static void showSynset(IndexWord word){
        List<Synset> senses = word.getSenses();
        for(Synset sense:senses){
            System.out.println(sense.getKey() + "| " + sense);
        }
    }

    public static void testHardCorpus() throws JWNLException, FileNotFoundException {
        Dictionary dictionary = Dictionary.getDefaultResourceInstance();

        WNSense wnSense = new WNSense();
        wnSense.initHardCorpus();
        Integer exampleNumber = 0;
        Integer allExamples = 0;
        Integer clasifiedCorrectly = 0;

        File dir = new File("hard_3");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                Scanner s = new Scanner(file);
                String correctSense = file.getName().toUpperCase();
                while(s.hasNextLine()){
                    allExamples++;
                    String phrase = s.nextLine();
                    phrase = phrase.substring(phrase.indexOf(":"), phrase.length() - 1);
//                    WSD wsd = new WSD(wnSense.getInput(), phrase, wnSense);
                    WSD wsd = new WSD(wnSense.getInput(), phrase, wnSense);

//                    List<Long> winnerKeys = wsd.pickCorrectSense();
//                    List<String> correspondentSenses = new ArrayList<String>();
//                    if(winnerKeys != null && winnerKeys.size() > 0) {
//                        correspondentSenses = winnerKeys.stream()
//                                .map(wKey -> wnSense.getSenseMapping().get(wKey)).collect(Collectors.toList());
//                        if (correspondentSenses.contains(correctSense)) {
//                            clasifiedCorrectly++;
//                            System.out.println("MATCH");
//                        }
//                    }
//                    System.out.println("Got " + StringUtils.join(correspondentSenses, " "));

                    Long winnerKey = wsd.pickCorrectSense();
                    String correspondentSense = wnSense.getSenseMapping().get(winnerKey);
                    if(correspondentSense != null && correspondentSense.equals(correctSense))
                        clasifiedCorrectly++;
                    System.out.println("Got " + winnerKey + " " + correspondentSense);
                    exampleNumber++;
                }
                s.close();
            }
            System.out.println("Correct = " + clasifiedCorrectly + " from "+ exampleNumber + " from " + allExamples);
        } else {
            System.out.println("No files found in line directory");
        }

    }

    public static void testLineCorpus() throws JWNLException, FileNotFoundException {
        Dictionary dictionary = Dictionary.getDefaultResourceInstance();

        WNSense wnSense = new WNSense();
        wnSense.initLineCorpus();
        Integer exampleNumber = 0;
        Integer allExamples = 0;
        Integer clasifiedCorrectly = 0;

        File dir = new File("line_3");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                Scanner s = new Scanner(file);
                String correctSense = file.getName().toUpperCase();
                while(s.hasNextLine()){
                    allExamples++;
                    String phrase = s.nextLine();
                    phrase = phrase.substring(phrase.indexOf(":"), phrase.length() - 1);
//                    WSD wsd = new WSD(wnSense.getInput(), phrase, wnSense);
                    WSD_POS wsd = new WSD_POS(wnSense.getInput(), phrase, wnSense, POS.NOUN);
                    List<Long> winnerKeys = wsd.pickCorrectSense(POS.NOUN);
                    List<String> correspondentSenses = winnerKeys.stream()
                            .map(wKey -> wnSense.getSenseMapping().get(wKey)).collect(Collectors.toList());
                    if(correspondentSenses.contains(correctSense)){
                        clasifiedCorrectly++;
                        System.out.println("MATCH");
                    }
                    System.out.println("Got " + StringUtils.join(correspondentSenses, " "));

//                    String correspondentSense = wnSense.getSenseMapping().get(winnerKey);
//                    if(correspondentSense.equals(correctSense))
//                        clasifiedCorrectly++;
//                    System.out.println("Got " + winnerKey + " " + correspondentSense);
                    exampleNumber++;
                }
                s.close();
            }
            System.out.println("Correct = " + clasifiedCorrectly + " from "+ exampleNumber + " from " + allExamples);
        } else {
            System.out.println("No files found in line directory");
        }

    }



    public static void main(String[] args) throws JWNLException, FileNotFoundException {

        Dictionary dictionary = Dictionary.getDefaultResourceInstance();
        IndexWord word = dictionary.getIndexWord(POS.NOUN, "line");
        showSynset(word);
//        testHardCorpus();

//        testLineCorpus();


//        Scanner s = new Scanner(new File("input.txt"));
//        String targetWord = s.nextLine();
//        String inputPhrase = s.nextLine();
//        s.close();

//        WSD wsd = new WSD(targetWord, inputPhrase);
//        wsd.pickCorrectSense();

//        System.out.println(targetWord);
//        System.out.println(inputPhrase);
//        System.out.println(Utils.stemInput(inputPhrase));
//        System.out.println(wsd.getWindowWords());
//
//        String s1 = "paper that is specially prepared for use in drafting";
//        ArrayList<String> s1l = new ArrayList(Arrays.asList(s1.split(" ")));
//        String s2 = "the art of transferring designs from specially prepared paper to a wood or glass or metal surface";
//        ArrayList<String> s12 = new ArrayList(Arrays.asList(s2.split(" ")));
//        wsd.calculateScore(s1l, s12);


    }
}
