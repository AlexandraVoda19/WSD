package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

//    public static final List<LINE_SENSE> ALL_LINE_SENSE = new ArrayList<LINE_SENSE>(Arrays.asList(LINE_SENSE.CORD,
//            LINE_SENSE.DIVISION, LINE_SENSE.FORMATION, LINE_SENSE.PHONE, LINE_SENSE.PRODUCT, LINE_SENSE.TEXT));

    public static final List<String> ALL_LINE_SENSES = new ArrayList<String>(Arrays.asList(LINE_SENSE.CORD.name(),
            LINE_SENSE.DIVISION.name(), LINE_SENSE.FORMATION.name(), LINE_SENSE.PHONE.name(), LINE_SENSE.PRODUCT.name(), LINE_SENSE.TEXT.name()));

    public static final List<String> ALL_HARD_SENSES = new ArrayList<String>(Arrays.asList(HARD_SENSE.DIFFICULT.name(),
            HARD_SENSE.METAPHORIC.name(), HARD_SENSE.PHYSICAL.name()));
}
