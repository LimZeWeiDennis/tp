package seedu.stock.logic.parser;

import static seedu.stock.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.stock.commons.core.LogsCenter;
import seedu.stock.logic.commands.SourceQuantityDistributionStatisticsCommand;
import seedu.stock.logic.commands.SourceStatisticsCommand;
import seedu.stock.logic.commands.StatisticsCommand;
import seedu.stock.logic.parser.exceptions.ParseException;

public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    private static final Logger logger = LogsCenter.getLogger(StatisticsCommandParser.class);

    @Override
    public StatisticsCommand parse(String userInput) throws ParseException {
        String[] trimmedStatisticsType = userInput.trim().split("st/");

        //a valid input will lead to an array of index 0 as "", index 1 as the statistics type
        if (trimmedStatisticsType.length != 2) {
            logger.log(Level.WARNING, "Multiple headers not allowed");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }

        String input = trimmedStatisticsType[1].toLowerCase();
        String type = getStatisticsType(input);

        switch (type) {

        case SourceStatisticsCommand.STATISTICS_TYPE:
            return new SourceStatisticsCommand();

        case SourceQuantityDistributionStatisticsCommand.STATISTICS_TYPE:
            return new SourceQuantityDistributionStatisticsCommand(getSourceCompany(input));

        default:
            logger.log(Level.WARNING, "Statistics type not found!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }

    }

    /**
     * To obtain the type of statistics command.
     *
     * @param input The user input.
     * @return The statistics type.
     * @throws ParseException if no such type of statistics command is found.
     */
    public String getStatisticsType(String input) throws ParseException {
        if (input.length() >= 10 && input.startsWith("source-qd-")) {
            return "source-qd-";
        } else if (input.equals("source")) {
            return "source";
        } else {
            logger.log(Level.WARNING, "Statistics type not found!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
    }

    //for extracting target company from source-qd types
    public String getSourceCompany(String input) throws ParseException {
        String targetSourceCompany = input.substring(10);

        //exception thrown here if the target source company is missing
        if (targetSourceCompany.length() == 0) {
            logger.log(Level.WARNING, "Source company not found!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        return targetSourceCompany;
    }
}
