package seedu.stock.logic.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_SOURCE = new Prefix("s/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_SERIAL_NUMBER = new Prefix("sn/");
    public static final Prefix PREFIX_NEW_QUANTITY = new Prefix("nq/");
    public static final Prefix PREFIX_INCREMENT_QUANTITY = new Prefix("iq/");
    public static final Prefix PREFIX_NOTE = new Prefix("nt/");
    public static final Prefix PREFIX_NOTE_INDEX = new Prefix("ni/");
    public static final Prefix PREFIX_STATISTICS_TYPE = new Prefix("st/");
    public static final Prefix PREFIX_SORT_FIELD = new Prefix("by/");
    public static final Prefix PREFIX_SORT_ORDER = new Prefix("o/");


    /* Prefix values descriptions */
    public static final String PREFIX_NAME_DESCRIPTION = "<name>";
    public static final String PREFIX_SOURCE_DESCRIPTION = "<source>";
    public static final String PREFIX_LOCATION_DESCRIPTION = "<location>";
    public static final String PREFIX_QUANTITY_DESCRIPTION = "<quantity>";
    public static final String PREFIX_SERIAL_NUMBER_DESCRIPTION = "<serial number>";
    public static final String PREFIX_NOTE_DESCRIPTION = "<note>";
    public static final String PREFIX_NOTE_INDEX_DESCRIPTION = "<note index>";
    public static final String PREFIX_STATISTICS_TYPE_DESCRIPTION = "<statistics type>";
    public static final String PREFIX_NEW_QUANTITY_DESCRIPTION = "<new quantity>";
    public static final String PREFIX_INCREMENT_QUANTITY_DESCRIPTION = "<increment value>";
    public static final String PREFIX_SORT_FIELD_DESCRIPTION = "<field>";
    public static final String PREFIX_SORT_ORDER_DESCRIPTION = "<order>";

    /**
     * Returns a list containing all possible prefixes.
     *
     * @return A list containing all possible prefixes.
     */
    public static List<Prefix> getAllPossiblePrefixes() {
        List<Prefix> allPrefixes = new ArrayList<>();
        allPrefixes.add(PREFIX_NAME);
        allPrefixes.add(PREFIX_SOURCE);
        allPrefixes.add(PREFIX_QUANTITY);
        allPrefixes.add(PREFIX_LOCATION);
        allPrefixes.add(PREFIX_SERIAL_NUMBER);
        allPrefixes.add(PREFIX_NEW_QUANTITY);
        allPrefixes.add(PREFIX_INCREMENT_QUANTITY);
        allPrefixes.add(PREFIX_NOTE);
        allPrefixes.add(PREFIX_NOTE_INDEX);
        allPrefixes.add(PREFIX_STATISTICS_TYPE);
        allPrefixes.add(PREFIX_SORT_FIELD);
        allPrefixes.add(PREFIX_SORT_ORDER);
        return allPrefixes;
    }

    /**
     * Returns an array containing all possible prefixes.
     *
     * @return An array containing all possible prefixes.
     */
    public static Prefix[] getAllPossiblePrefixesAsArray() {
        List<Prefix> allPrefixes = getAllPossiblePrefixes();
        Prefix[] allPrefixesInArray = allPrefixes.toArray(new Prefix[0]);
        return allPrefixesInArray;
    }

    /**
     * Returns the default parameter description of a certain prefix.
     *
     * @param prefix The prefix that needs parameter description.
     * @return The default parameter description of the given prefix.
     */
    public static String getDefaultDescription(Prefix prefix) {
        if (prefix.equals(PREFIX_NAME)) {
            return PREFIX_NAME_DESCRIPTION;
        } else if (prefix.equals(PREFIX_SOURCE)) {
            return PREFIX_SOURCE_DESCRIPTION;
        } else if (prefix.equals(PREFIX_LOCATION)) {
            return PREFIX_LOCATION_DESCRIPTION;
        } else if (prefix.equals(PREFIX_QUANTITY)) {
            return PREFIX_QUANTITY_DESCRIPTION;
        } else if (prefix.equals(PREFIX_SERIAL_NUMBER)) {
            return PREFIX_SERIAL_NUMBER_DESCRIPTION;
        } else if (prefix.equals(PREFIX_NOTE)) {
            return PREFIX_NOTE_DESCRIPTION;
        } else if (prefix.equals(PREFIX_NOTE_INDEX)) {
            return PREFIX_NOTE_INDEX_DESCRIPTION;
        } else if (prefix.equals(PREFIX_STATISTICS_TYPE)) {
            return PREFIX_STATISTICS_TYPE_DESCRIPTION;
        } else if (prefix.equals(PREFIX_NEW_QUANTITY)) {
            return PREFIX_NEW_QUANTITY_DESCRIPTION;
        } else if (prefix.equals(PREFIX_INCREMENT_QUANTITY)) {
            return PREFIX_INCREMENT_QUANTITY_DESCRIPTION;
        } else if (prefix.equals(PREFIX_SORT_FIELD)) {
            return PREFIX_SORT_FIELD_DESCRIPTION;
        } else if (prefix.equals(PREFIX_SORT_ORDER)) {
            return PREFIX_SORT_ORDER_DESCRIPTION;
        } else {
            return "";
        }
    }
}
