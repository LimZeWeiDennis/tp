package seedu.stock.logic.commands;

import static seedu.stock.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import seedu.stock.logic.commands.exceptions.CommandException;
import seedu.stock.model.Model;
import seedu.stock.model.stock.SerialNumber;
import seedu.stock.model.stock.Stock;

/**
 * Adds a note to an existing stock in the stock book.
 */
public class NoteDeleteCommand extends Command {

    public static final String COMMAND_WORD = "notedelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a note,"
            + " specified by index of note shown in stock display, or all notes of the stock identified "
            + "by the serial number of the stock.\n"
            + "Special note index to delete ALL notes from the stock is 0.\n"
            + "Parameters:\n"
            + "sn/ [SERIAL NUMBER]\n"
            + "ni/ [NOTE INDEX]\n"
            + "Example: " + COMMAND_WORD + " sn/111111"
            + " ni/1 ";

    private static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted note(s) from Stock: %1$s";
    private static final String MESSAGE_SERIAL_NUMBER_NOT_FOUND =
            "Stock with given serial number does not exists";
    private static final String MESSAGE_INVALID_NOTE_INDEX = "Note at index specified is not found.";
    private static final String MESSAGE_STOCK_HAS_NO_NOTE = "Stock specified has no note.";

    private final SerialNumber serialNumber;
    private final int index;
    /**
     * @param serialNumber of the stock in the stock book
     * @param index of note to delete from the stock
     */
    public NoteDeleteCommand(SerialNumber serialNumber, int index) {
        requireAllNonNull(serialNumber, index);

        this.serialNumber = serialNumber;
        this.index = index;
    }

    /**
     * Executes the note command and returns the result.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The result of successful execution.
     * @throws CommandException If there are any errors.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Stock> lastShownStocks = model.getFilteredStockList();
        Optional<Stock> stockToDeleteNote = Optional.empty();
        // Find the stock to add note to
        for (Stock currentStock : lastShownStocks) {
            String currentStockSerialNumber = currentStock.getSerialNumber().getSerialNumberAsString();
            if (currentStockSerialNumber.equals(serialNumber.getSerialNumberAsString())) {
                stockToDeleteNote = Optional.of(currentStock);
                break;
            }
        }

        if (stockToDeleteNote.isEmpty()) {
            throw new CommandException(MESSAGE_SERIAL_NUMBER_NOT_FOUND);
        }

        if (stockToDeleteNote.get().getNotes().size() == 0) {
            throw new CommandException(MESSAGE_STOCK_HAS_NO_NOTE);
        }

        if (index > stockToDeleteNote.get().getNotes().size()) {
            throw new CommandException(MESSAGE_INVALID_NOTE_INDEX);
        }

        Stock stockWithDeletedNote = createStockWithDeletedNote(stockToDeleteNote.get(), index);
        model.setStock(stockToDeleteNote.get(), stockWithDeletedNote);

        return new CommandResult(generateSuccessMessage(stockWithDeletedNote));
    }

    /**
     * Creates the stock with added note.
     *
     * @param stockToDeleteNote The stock in the list to be updated.
     * @param index The index of note to delete from stock.
     * @return The stock with updated attributes.
     */
    private static Stock createStockWithDeletedNote(Stock stockToDeleteNote, int index) {
        assert stockToDeleteNote != null;
        Stock stockWithDeletedNote = stockToDeleteNote.deleteNote(index);

        return stockWithDeletedNote;
    }

    /**
     * Generates a command execution success message when the note is deleted from
     * {@code stockToAddNote}.
     */
    private String generateSuccessMessage(Stock stockWithDeletedNote) {
        return String.format(MESSAGE_DELETE_NOTE_SUCCESS, stockWithDeletedNote);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }
        // state check
        NoteDeleteCommand otherNoteCommand = (NoteDeleteCommand) other;
        return serialNumber.equals(otherNoteCommand.serialNumber)
                && index == otherNoteCommand.index;
    }
}
