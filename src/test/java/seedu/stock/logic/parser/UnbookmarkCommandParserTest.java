package seedu.stock.logic.parser;

import static seedu.stock.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.stock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.stock.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.stock.testutil.TypicalStocks.SERIAL_NUMBER_FIRST_STOCK;
import static seedu.stock.testutil.TypicalStocks.SERIAL_NUMBER_FOURTH_STOCK;
import static seedu.stock.testutil.TypicalStocks.SERIAL_NUMBER_SECOND_STOCK;
import static seedu.stock.testutil.TypicalStocks.SERIAL_NUMBER_THIRD_STOCK;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.stock.logic.commands.UnbookmarkCommand;
import seedu.stock.model.stock.SerialNumber;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For DeleteCommand, the only path taken is by deleting
 * through serial numbers.
 */
public class UnbookmarkCommandParserTest {

    private UnbookmarkCommandParser parser = new UnbookmarkCommandParser();

    @Test
    public void parse_oneValidArg_returnsUnbookmarkCommand() {
        Set<SerialNumber> serialNumberSet = new LinkedHashSet<>();
        serialNumberSet.add(SERIAL_NUMBER_FIRST_STOCK);
        String input = " sn/" + SERIAL_NUMBER_FIRST_STOCK;

        UnbookmarkCommand expectedUnbookmarkCommand = new UnbookmarkCommand(serialNumberSet);

        assertParseSuccess(parser, input, expectedUnbookmarkCommand);
    }

    @Test
    public void parse_multipleAndAllValidArg_returnsUnbookmarkCommand() {
        Set<SerialNumber> serialNumberSet = new LinkedHashSet<>();
        serialNumberSet.add(SERIAL_NUMBER_SECOND_STOCK);
        serialNumberSet.add(SERIAL_NUMBER_THIRD_STOCK);
        serialNumberSet.add(SERIAL_NUMBER_FOURTH_STOCK);
        String input = " sn/" + SERIAL_NUMBER_SECOND_STOCK
                + " sn/" + SERIAL_NUMBER_THIRD_STOCK
                + " sn/" + SERIAL_NUMBER_FOURTH_STOCK;

        assertParseSuccess(parser, input, new UnbookmarkCommand(serialNumberSet));
    }

    @Test
    public void parse_invalidHeaderInStartingArg_throwsParseException() {
        String input = " ssss/" + SERIAL_NUMBER_FIRST_STOCK
                + " sn/" + SERIAL_NUMBER_SECOND_STOCK;

        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnbookmarkCommand.MESSAGE_USAGE));
    }

    //for this test, it is correct to return a unbookmark command as invalid headers in between
    //is read as a continuation of string from the previous valid header.
    @Test
    public void parse_invalidHeaderNonStartingArg_returnsBookmarkCommand() {
        Set<SerialNumber> serialNumberSet = new LinkedHashSet<>();
        SerialNumber wrongSerialNumber = new SerialNumber(SERIAL_NUMBER_FIRST_STOCK.getSerialNumberAsString()
                + " sssss/" + SERIAL_NUMBER_SECOND_STOCK);
        serialNumberSet.add(wrongSerialNumber);

        String input = " sn/" + SERIAL_NUMBER_FIRST_STOCK
                + " sssss/" + SERIAL_NUMBER_SECOND_STOCK;

        assertParseSuccess(parser, input, new UnbookmarkCommand(serialNumberSet));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " sss/wrongarg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnbookmarkCommand.MESSAGE_USAGE));
    }

}
