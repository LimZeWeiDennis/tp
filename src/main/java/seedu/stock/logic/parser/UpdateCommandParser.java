package seedu.stock.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.stock.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.stock.logic.commands.UpdateCommand.MESSAGE_TOO_MANY_QUANTITY_PREFIXES;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_INCREMENT_QUANTITY;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_NEW_QUANTITY;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.stock.logic.parser.CliSyntax.PREFIX_SOURCE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.stock.logic.commands.UpdateCommand;
import seedu.stock.logic.commands.UpdateCommand.UpdateStockDescriptor;
import seedu.stock.logic.parser.exceptions.ParseException;
import seedu.stock.model.stock.SerialNumber;

/**
 * Parses user input and creates a new update command.
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses {@code args} into an update command.
     *
     * @param args The user input to be parsed.
     * @return A new update command.
     * @throws ParseException If a parsing error occurs.
     */
    @Override
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_SERIAL_NUMBER, PREFIX_INCREMENT_QUANTITY, PREFIX_NEW_QUANTITY,
                        PREFIX_NAME, PREFIX_SOURCE, PREFIX_LOCATION, PREFIX_QUANTITY
                );
        List<Prefix> allPrefixes = CliSyntax.getAllPossiblePrefixes();

        // If unallowed prefixes are provided
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        // If serial number is not provided
        if (!argMultimap.getValue(PREFIX_SERIAL_NUMBER).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        // Check if there are unallowed duplicate prefixes
        for (Prefix prefix: allPrefixes) {
            if (argMultimap.getAllValues(prefix).size() >= 2 && !prefix.equals(PREFIX_SERIAL_NUMBER)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
            }
        }

        // If both increment and new quantity prefix provided
        if (argMultimap.getValue(PREFIX_INCREMENT_QUANTITY).isPresent()
                && argMultimap.getValue(PREFIX_NEW_QUANTITY).isPresent()) {
            throw new ParseException(MESSAGE_TOO_MANY_QUANTITY_PREFIXES + "\n" + UpdateCommand.MESSAGE_USAGE);
        }

        UpdateStockDescriptor updateStockDescriptor = new UpdateStockDescriptor();

        // Store the serial number provided
        List<String> keywords = argMultimap.getAllValues(PREFIX_SERIAL_NUMBER);
        ArrayList<SerialNumber> serialNumbers = keywords.stream()
                .map((keyword) -> new SerialNumber(keyword.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        updateStockDescriptor.setSerialNumbers(serialNumbers);

        // Update name with new name provided
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameLowerCased = argMultimap.getValue(PREFIX_NAME).get().toLowerCase();
            updateStockDescriptor.setName(ParserUtil.parseName(nameLowerCased));
        }

        // Update source with new source provided
        if (argMultimap.getValue(PREFIX_SOURCE).isPresent()) {
            String sourceLowerCased = argMultimap.getValue(PREFIX_SOURCE).get().toLowerCase();
            updateStockDescriptor.setSource(ParserUtil.parseSource(sourceLowerCased));
        }

        // Update quantity with new quantity provided
        if (argMultimap.getValue(PREFIX_NEW_QUANTITY).isPresent()) {
            String newQuantity = argMultimap.getValue(PREFIX_NEW_QUANTITY).get();
            updateStockDescriptor.setQuantity(ParserUtil.parseQuantity(newQuantity));
        }

        // Increment quantity with increment value provided
        if (argMultimap.getValue(PREFIX_INCREMENT_QUANTITY).isPresent()) {
            String incrementValue = argMultimap.getValue(PREFIX_INCREMENT_QUANTITY).get();
            updateStockDescriptor.setQuantityAdder(ParserUtil.parseQuantityAdder(incrementValue));
        }

        // Update location with new location provided
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            String locationLowerCased = argMultimap.getValue(PREFIX_LOCATION).get().toLowerCase();
            updateStockDescriptor.setLocation(ParserUtil.parseLocation(locationLowerCased));
        }

        if (!updateStockDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(updateStockDescriptor);
    }
}
