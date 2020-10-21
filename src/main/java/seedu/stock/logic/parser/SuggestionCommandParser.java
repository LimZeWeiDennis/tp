package seedu.stock.logic.parser;

import static seedu.stock.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.stock.logic.commands.CommandWords.ADD_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.DELETE_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.FIND_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.FIND_EXACT_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.NOTE_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.NOTE_DELETE_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.SORT_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.STATISTICS_COMMAND_WORD;
import static seedu.stock.logic.commands.CommandWords.UPDATE_COMMAND_WORD;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_INCREMENT_QUANTITY;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_NEW_QUANTITY;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_NOTE_INDEX;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_SORT_FIELD;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_STATISTICS_TYPE;

import java.util.List;
import java.util.Random;

import seedu.stock.commons.util.SortUtil.Field;
import seedu.stock.commons.util.SortUtil.Order;
import seedu.stock.commons.util.SuggestionUtil;
import seedu.stock.logic.commands.AddCommand;
import seedu.stock.logic.commands.CommandWords;
import seedu.stock.logic.commands.DeleteCommand;
import seedu.stock.logic.commands.ExitCommand;
import seedu.stock.logic.commands.FindCommand;
import seedu.stock.logic.commands.FindExactCommand;
import seedu.stock.logic.commands.HelpCommand;
import seedu.stock.logic.commands.ListCommand;
import seedu.stock.logic.commands.NoteCommand;
import seedu.stock.logic.commands.NoteDeleteCommand;
import seedu.stock.logic.commands.PrintCommand;
import seedu.stock.logic.commands.SortCommand;
import seedu.stock.logic.commands.StatisticsCommand;
import seedu.stock.logic.commands.SuggestionCommand;
import seedu.stock.logic.commands.UpdateCommand;
import seedu.stock.logic.parser.exceptions.ParseException;

public class SuggestionCommandParser implements Parser<SuggestionCommand> {

    private static final String MESSAGE_SUGGESTION = "Do you mean: \n";
    private String faultyCommandWord;
    private String commandWord;
    private String headerErrorMessage;
    private String bodyErrorMessage;

    /**
     * Constructs a new suggestion command parser.
     *
     * @param faultyCommandWord The command word to be corrected.
     */
    public SuggestionCommandParser(String faultyCommandWord) {
        this.faultyCommandWord = faultyCommandWord;
        commandWord = "";
        this.headerErrorMessage = "";
        this.bodyErrorMessage = "";
    }

    /**
     * Constructs a new suggestion command parser with error message from another parser.
     *
     * @param commandWord The command word to be executed if all went well.
     * @param errorMessage The parse error thrown from another parser.
     */
    public SuggestionCommandParser(String commandWord, String errorMessage) {
        String[] splitHeaderAndBody = errorMessage.split("\n", 2);
        this.commandWord = commandWord;
        faultyCommandWord = "";
        headerErrorMessage = splitHeaderAndBody[0];
        bodyErrorMessage = splitHeaderAndBody.length < 2 ? "" : splitHeaderAndBody[1];
    }

    /**
     * Parses {@code args} into a suggestion command.
     *
     * @param args The user input to be parsed.
     * @return A new suggestion command.
     * @throws ParseException If any parsing errors occurs.
     */
    @Override
    public SuggestionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_SERIAL_NUMBER, PREFIX_INCREMENT_QUANTITY, PREFIX_NEW_QUANTITY,
                        PREFIX_NAME, PREFIX_SOURCE, PREFIX_LOCATION, PREFIX_QUANTITY, PREFIX_SORT_FIELD,
                        PREFIX_SORT_ORDER
                );
        List<String> allCommandWords = CommandWords.getAllCommandWords();
        StringBuilder toBeDisplayed = new StringBuilder();

        String suggestedCommandWord = faultyCommandWord;
        int bestEditDistanceSoFar = Integer.MAX_VALUE;
        for (String commandWord: allCommandWords) {
            int currentEditDistance = SuggestionUtil.minimumEditDistance(faultyCommandWord, commandWord);
            if (currentEditDistance < bestEditDistanceSoFar) {
                bestEditDistanceSoFar = currentEditDistance;
                suggestedCommandWord = commandWord;
            }
        }

        if (suggestedCommandWord.equals(faultyCommandWord) && commandWord.equals("")) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        if (!commandWord.equals("")) {
            suggestedCommandWord = commandWord;
        }

        if (headerErrorMessage.equals("")) {
            toBeDisplayed.append(MESSAGE_UNKNOWN_COMMAND + "\n" + MESSAGE_SUGGESTION);
        } else {
            toBeDisplayed.append(headerErrorMessage + "\n" + MESSAGE_SUGGESTION);
        }

        switch (suggestedCommandWord) {
        case ExitCommand.COMMAND_WORD:
            generateExitSuggestion(toBeDisplayed, argMultimap);
            break;

        case HelpCommand.COMMAND_WORD:
            generateHelpSuggestion(toBeDisplayed, argMultimap);
            break;

        case ListCommand.COMMAND_WORD:
            generateListSuggestion(toBeDisplayed, argMultimap);
            break;

        case AddCommand.COMMAND_WORD:
            generateAddSuggestion(toBeDisplayed, argMultimap);
            break;

        case DeleteCommand.COMMAND_WORD:
            generateDeleteSuggestion(toBeDisplayed, argMultimap);
            break;

        case StatisticsCommand.COMMAND_WORD:
            generateStatisticsSuggestion(toBeDisplayed, argMultimap);
            break;

        case FindCommand.COMMAND_WORD:
            generateFindSuggestion(toBeDisplayed, argMultimap);
            break;

        case UpdateCommand.COMMAND_WORD:
            generateUpdateSuggestion(toBeDisplayed, argMultimap);
            break;

        case FindExactCommand.COMMAND_WORD:
            generateFindExactSuggestion(toBeDisplayed, argMultimap);
            break;

        case NoteCommand.COMMAND_WORD:
            generateNoteSuggestion(toBeDisplayed, argMultimap);
            break;

        case NoteDeleteCommand.COMMAND_WORD:
            generateDeleteNoteSuggestion(toBeDisplayed, argMultimap);
            break;

        case PrintCommand.COMMAND_WORD:
            generatePrintSuggestion(toBeDisplayed, argMultimap);
            break;

        case SortCommand.COMMAND_WORD:
            generateSortSuggestion(toBeDisplayed, argMultimap);
            break;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        return new SuggestionCommand(toBeDisplayed.toString());
    }

    /**
     * Generates suggestion for faulty sort command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateSortSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        toBeDisplayed.append(SORT_COMMAND_WORD);
        Prefix fieldPrefix = PREFIX_SORT_FIELD;
        Prefix orderPrefix = PREFIX_SORT_ORDER;

        if (!argMultimap.getValue(orderPrefix).isPresent()) {
            toBeDisplayed.append(" " + orderPrefix + CliSyntax.getDefaultDescription(orderPrefix));
        } else {
            String description = argMultimap.getValue(orderPrefix).get();
            String suggestedDescription = description;
            int bestEditDistanceSoFar = Integer.MAX_VALUE;
            for (Order order : Order.values()) {
                String orderDescription = order.toString().toLowerCase();
                int currentEditDistance = SuggestionUtil.minimumEditDistance(description, orderDescription);
                if (currentEditDistance < bestEditDistanceSoFar) {
                    bestEditDistanceSoFar = currentEditDistance;
                    suggestedDescription = orderDescription;
                }
            }
            toBeDisplayed.append(" " + orderPrefix + suggestedDescription);
        }

        if (!argMultimap.getValue(fieldPrefix).isPresent()) {
            toBeDisplayed.append(" " + fieldPrefix + CliSyntax.getDefaultDescription(fieldPrefix));
        } else {
            String description = argMultimap.getValue(fieldPrefix).get();
            String suggestedDescription = description;
            int bestEditDistanceSoFar = Integer.MAX_VALUE;
            for (Field field : Field.values()) {
                String fieldDescription = field.toString().toLowerCase();
                int currentEditDistance = SuggestionUtil.minimumEditDistance(description, fieldDescription);
                if (currentEditDistance < bestEditDistanceSoFar) {
                    bestEditDistanceSoFar = currentEditDistance;
                    suggestedDescription = fieldDescription;
                }
            }
            toBeDisplayed.append(" " + fieldPrefix + suggestedDescription);
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + SortCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty find exact command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateFindExactSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(PREFIX_NAME, PREFIX_SOURCE,
                PREFIX_SERIAL_NUMBER, PREFIX_LOCATION);
        toBeDisplayed.append(FIND_EXACT_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            if (argMultimap.getValue(currentPrefix).isPresent()) {
                toBeDisplayed.append(" " + currentPrefix + argMultimap.getValue(currentPrefix).get());
            }
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + FindExactCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty update command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateUpdateSuggestion(StringBuilder toBeDisplayed,
            ArgumentMultimap argMultimap) throws ParseException {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(PREFIX_SERIAL_NUMBER,
                PREFIX_INCREMENT_QUANTITY, PREFIX_NEW_QUANTITY, PREFIX_NAME, PREFIX_SOURCE, PREFIX_LOCATION);
        toBeDisplayed.append(UPDATE_COMMAND_WORD);
        boolean isIncrementQuantityPresent = argMultimap.getValue(PREFIX_INCREMENT_QUANTITY).isPresent();
        boolean isNewQuantityPresent = argMultimap.getValue(PREFIX_NEW_QUANTITY).isPresent();

        if (isIncrementQuantityPresent == isNewQuantityPresent) {
            // both present or both not present
            int removeRng = new Random().nextInt(2);
            if (removeRng == 0) {
                allowedPrefixes.remove(PREFIX_INCREMENT_QUANTITY);
            } else {
                allowedPrefixes.remove(PREFIX_NEW_QUANTITY);
            }
        }

        if (!argMultimap.getValue(PREFIX_SERIAL_NUMBER).isPresent()) {
            toBeDisplayed.append(" " + PREFIX_SERIAL_NUMBER + CliSyntax.getDefaultDescription(PREFIX_SERIAL_NUMBER));
        }
        List<String> keywords = argMultimap.getAllValues(PREFIX_SERIAL_NUMBER);
        for (String serialNumber : keywords) {
            toBeDisplayed.append(" " + PREFIX_SERIAL_NUMBER + serialNumber);
        }

        for (int i = 1; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            boolean isPresent = argMultimap.getValue(currentPrefix).isPresent();
            String description = "";
            if (isPresent) {
                description = argMultimap.getValue(currentPrefix).get();
            }
            boolean isEmpty = description.equals("");
            if (!isEmpty) {
                toBeDisplayed.append(" " + currentPrefix + description);
            } else if (isEmpty && isPresent) {
                toBeDisplayed.append(" " + currentPrefix + CliSyntax.getDefaultDescription(currentPrefix));
            }
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + UpdateCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty find command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateFindSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(PREFIX_NAME, PREFIX_SOURCE,
                PREFIX_SERIAL_NUMBER, PREFIX_LOCATION);
        toBeDisplayed.append(FIND_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            if (argMultimap.getValue(currentPrefix).isPresent()) {
                toBeDisplayed.append(" " + currentPrefix + argMultimap.getValue(currentPrefix).get());
            }
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + FindCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty note command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateNoteSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(
                PREFIX_SERIAL_NUMBER, PREFIX_NOTE);
        toBeDisplayed.append(NOTE_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            toBeDisplayed.append(" ").append(currentPrefix).append(CliSyntax.getDefaultDescription(currentPrefix));
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + NoteCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty deletenote command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateDeleteNoteSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(
                PREFIX_SERIAL_NUMBER, PREFIX_NOTE_INDEX);
        toBeDisplayed.append(NOTE_DELETE_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            toBeDisplayed.append(" ").append(currentPrefix)
                    .append(CliSyntax.getDefaultDescription(currentPrefix));
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + NoteDeleteCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty delete command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateDeleteSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(PREFIX_SERIAL_NUMBER);
        toBeDisplayed.append(DELETE_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            if (!argMultimap.getValue(currentPrefix).isPresent()) {
                toBeDisplayed.append(" " + currentPrefix + CliSyntax.getDefaultDescription(currentPrefix));
            }
            List<String> keywords = argMultimap.getAllValues(PREFIX_SERIAL_NUMBER);
            for (String serialNumber : keywords) {
                boolean isEmpty = serialNumber.equals("");
                String description = isEmpty ? CliSyntax.getDefaultDescription(currentPrefix) : serialNumber;
                toBeDisplayed.append(" " + currentPrefix + description);
            }
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + DeleteCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty statistics command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateStatisticsSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(PREFIX_STATISTICS_TYPE);
        toBeDisplayed.append(STATISTICS_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            if (!argMultimap.getValue(currentPrefix).isPresent()) {
                toBeDisplayed.append(" " + currentPrefix + CliSyntax.getDefaultDescription(currentPrefix));
            }
            List<String> keywords = argMultimap.getAllValues(PREFIX_STATISTICS_TYPE);
            for (String statisticsType : keywords) {
                toBeDisplayed.append(" " + currentPrefix + statisticsType);
            }
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + StatisticsCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty add command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateAddSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        List<Prefix> allowedPrefixes = ParserUtil.generateListOfPrefixes(PREFIX_NAME, PREFIX_SOURCE,
                 PREFIX_QUANTITY, PREFIX_LOCATION);
        toBeDisplayed.append(ADD_COMMAND_WORD);

        for (int i = 0; i < allowedPrefixes.size(); i++) {
            Prefix currentPrefix = allowedPrefixes.get(i);
            String description = "";
            if (argMultimap.getValue(currentPrefix).isPresent()) {
                description = argMultimap.getValue(currentPrefix).get();
            }
            boolean isEmpty = description.equals("");
            if (isEmpty) {
                toBeDisplayed.append(" " + currentPrefix + CliSyntax.getDefaultDescription(currentPrefix));
            } else {
                toBeDisplayed.append(" " + currentPrefix + description);
            }
        }

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + AddCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty list command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateListSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        toBeDisplayed.append(CommandWords.LIST_COMMAND_WORD);

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + ListCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty help command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateHelpSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        toBeDisplayed.append(CommandWords.HELP_COMMAND_WORD);

        if (!bodyErrorMessage.equals("")) {
            toBeDisplayed.append("\n" + bodyErrorMessage);
        } else {
            toBeDisplayed.append("\n" + HelpCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Generates suggestion for faulty exit command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generateExitSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        toBeDisplayed.append(CommandWords.EXIT_COMMAND_WORD);
    }

    /**
     * Generates suggestion for faulty print command.
     *
     * @param toBeDisplayed The accumulated suggestion to be displayed to the user.
     * @param argMultimap The parsed user input fields.
     */
    private void generatePrintSuggestion(StringBuilder toBeDisplayed, ArgumentMultimap argMultimap) {
        toBeDisplayed.append(CommandWords.PRINT_COMMAND_WORD);
    }
}
