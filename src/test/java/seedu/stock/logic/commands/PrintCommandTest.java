package seedu.stock.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.stock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.stock.testutil.TypicalStocks.getTypicalSerialNumberSetsBook;
import static seedu.stock.testutil.TypicalStocks.getTypicalStockBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.stock.model.Model;
import seedu.stock.model.ModelManager;
import seedu.stock.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code PrintCommand}.
 */
public class PrintCommandTest {
    private Model model = new ModelManager(getTypicalStockBook(), new UserPrefsStub(),
            getTypicalSerialNumberSetsBook());

    @Test
    public void execute_stockAcceptedByModel_addSuccessful() {
        Model expectedModel = new ModelManager(model.getStockBook(), model.getUserPrefs(),
                getTypicalSerialNumberSetsBook());

        assertCommandSuccess(new PrintCommand(), model, PrintCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        PrintCommand printCommand = new PrintCommand();

        // same object -> returns true
        assertTrue(printCommand.equals(printCommand));

        // same values -> returns true
        PrintCommand printCommandCopy = new PrintCommand();
        assertTrue(printCommand.equals(printCommandCopy));

        // different types -> returns false
        assertFalse(printCommand.equals(1));

    }

    /**
     * UserPrefs stub that creates csv file in test class.
     */
    private static class UserPrefsStub extends UserPrefs {
        private final Path csvFilePath = Paths.get("datatest", "stocks.csv");

        @Override
        public Path getCsvFilePath() {
            System.out.println(csvFilePath);
            return csvFilePath;
        }
    }
}
