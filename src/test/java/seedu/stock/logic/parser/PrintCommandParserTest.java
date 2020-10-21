package seedu.stock.logic.parser;

import static seedu.stock.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.stock.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static seedu.stock.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.stock.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.stock.logic.commands.PrintCommand;

public class PrintCommandParserTest {
    private PrintCommandParser parser = new PrintCommandParser();

    @Test
    public void parse_extraArgument_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_NAME_BANANA, expectedMessage);
    }

    @Test
    public void parse_noFields_success() {
        assertParseSuccess(parser, "", new PrintCommand());
    }
}
