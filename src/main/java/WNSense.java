import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;
import util.Constants;
import util.HARD_SENSE;
import util.LINE_SENSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WNSense {
    private String input;
    private IndexWord word;
    private List<String> briefSenses = new ArrayList<String>();
    private Map<Long, String> senseMapping = new HashMap<Long, String>();

    public void initHardCorpus() throws JWNLException {
        Dictionary dictionary = Dictionary.getDefaultResourceInstance();
        String input = "hard";
        IndexWord word = dictionary.getIndexWord(POS.ADJECTIVE, input);

        setInput(input);
        setWord(word);
        setBriefSenses(Constants.ALL_HARD_SENSES);
        Map<Long, String> senseMapping = getSenseMapping();
        senseMapping.put(748528L, HARD_SENSE.DIFFICULT.name());
        senseMapping.put(1158857L, HARD_SENSE.METAPHORIC.name());
        senseMapping.put(1154418L, HARD_SENSE.PHYSICAL.name());     //TODO not sure
        senseMapping.put(2330284L, HARD_SENSE.PHYSICAL.name());
        senseMapping.put(840083L, HARD_SENSE.DIFFICULT.name());     //TODO not sure
        senseMapping.put(2294026L, HARD_SENSE.METAPHORIC.name());   //TODO not sure
        senseMapping.put(1161269L, HARD_SENSE.PHYSICAL.name());     //TODO not sure
        senseMapping.put(1160012L, HARD_SENSE.METAPHORIC.name());   //TODO not sure
        senseMapping.put(1302121L, HARD_SENSE.METAPHORIC.name());   //TODO not sure
        senseMapping.put(1162481L, HARD_SENSE.METAPHORIC.name());   //TODO not sure
        senseMapping.put(1131133L, HARD_SENSE.DIFFICULT.name());
        senseMapping.put(1073471L, HARD_SENSE.PHYSICAL.name());

    }

    public void initLineCorpus() throws JWNLException {
        Dictionary dictionary = Dictionary.getDefaultResourceInstance();
        String input = "line";
        IndexWord word = dictionary.getIndexWord(POS.NOUN, input);

        setInput(input);
        setWord(word);
        setBriefSenses(Constants.ALL_LINE_SENSES);

        Map<Long, String> senseMapping = getSenseMapping();
        senseMapping.put(8447525L, LINE_SENSE.FORMATION.name());
        senseMapping.put(6812755L, LINE_SENSE.TEXT.name());        //TODO: not sure
        senseMapping.put(8447160L, LINE_SENSE.FORMATION.name());
        senseMapping.put(13886392L, LINE_SENSE.TEXT.name());       //TODO: not sure
        senseMapping.put(7025650L, LINE_SENSE.TEXT.name());
//        senseMapping.put(11495037L, LINE_SENSE.FORMATION.name());  //TODO: not sure
        senseMapping.put(11495037L, LINE_SENSE.CORD.name());  //TODO: not sure
        senseMapping.put(8611842L, LINE_SENSE.DIVISION.name());
        senseMapping.put(5781046L, LINE_SENSE.DIVISION.name());    //TODO: not sure
        senseMapping.put(2937552L, LINE_SENSE.CORD.name());
        senseMapping.put(8393816L, LINE_SENSE.FORMATION.name());
//        senseMapping.put(8610818L, LINE_SENSE.FORMATION.name());   //TODO: not sure
        senseMapping.put(8610818L, LINE_SENSE.CORD.name());   //TODO: not sure
        senseMapping.put(13928798L, LINE_SENSE.CORD.name());       //TODO: not sure
        senseMapping.put(3952601L, LINE_SENSE.PRODUCT.name());
        senseMapping.put(3676598L, LINE_SENSE.CORD.name());        //TODO: not sure
        senseMapping.put(4409192L, LINE_SENSE.PHONE.name());
        senseMapping.put(1206784L, LINE_SENSE.DIVISION.name());    //TODO: not sure
        senseMapping.put(8118903L, LINE_SENSE.FORMATION.name());
        senseMapping.put(3676175L, LINE_SENSE.CORD.name());
        senseMapping.put(583425L, LINE_SENSE.PRODUCT.name());
        senseMapping.put(8611480L, LINE_SENSE.CORD.name());
        senseMapping.put(6270774L, LINE_SENSE.PHONE.name());
        senseMapping.put(3676994L, LINE_SENSE.PRODUCT.name());
        senseMapping.put(3676799L, LINE_SENSE.PRODUCT.name());
        senseMapping.put(13735108L, LINE_SENSE.TEXT.name());       //TODO: not sure
        senseMapping.put(13400586L, LINE_SENSE.PRODUCT.name());    //TODO: not sure
        senseMapping.put(7041860L, LINE_SENSE.FORMATION.name());   //TODO: not sure
        senseMapping.put(7026095L, LINE_SENSE.DIVISION.name());    //TODO: not sure
        senseMapping.put(6638793L, LINE_SENSE.TEXT.name());
        senseMapping.put(5756783L, LINE_SENSE.DIVISION.name());
        senseMapping.put(4014761L, LINE_SENSE.PRODUCT.name());

    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public IndexWord getWord() {
        return word;
    }

    public void setWord(IndexWord word) {
        this.word = word;
    }

    public List<String> getBriefSenses() {
        return briefSenses;
    }

    public void setBriefSenses(List<String> briefSenses) {
        this.briefSenses = briefSenses;
    }

    public Map<Long, String> getSenseMapping() {
        return senseMapping;
    }

    public void setSenseMapping(Map<Long, String> senseMapping) {
        this.senseMapping = senseMapping;
    }
}
