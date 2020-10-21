package seedu.stock.model.stock;

import static java.util.Objects.requireNonNull;
import static seedu.stock.commons.util.AppUtil.checkArgument;

/**
 * Represents a Stock's source in the stock book.
 * Guarantees: immutable;
 */
public class Source {

    public static final String MESSAGE_CONSTRAINTS =
            "A source can take any values, and it should not be blank";

    /*
     * The first character of the source must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Source}.
     *
     * @param source A valid source.
     */
    public Source(String source) {
        requireNonNull(source);
        checkArgument(isValidSource(source), MESSAGE_CONSTRAINTS);
        value = source;
    }

    @Override
    public String toString() {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * Returns true if a given string is a valid source.
     */
    public static boolean isValidSource(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Source // instanceof handles nulls
                && value.equals(((Source) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isSameSource(Source source) {
        return this.value.equals(source.value);
    }

}
