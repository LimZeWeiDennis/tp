package seedu.stock.logic.commands;

//import static seedu.stock.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.stock.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.stock.testutil.TypicalStocks.PINEAPPLE;
import static seedu.stock.testutil.TypicalStocks.getTypicalSerialNumberSetsBook;
import static seedu.stock.testutil.TypicalStocks.getTypicalStockBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.stock.model.Model;
import seedu.stock.model.ModelManager;
import seedu.stock.model.UserPrefs;
import seedu.stock.model.stock.Stock;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStockBook(), new UserPrefs(), getTypicalSerialNumberSetsBook());
    }

    @Test
    public void execute_newStock_success() {
        Stock validStock = PINEAPPLE;

        Model expectedModel = new ModelManager(model.getStockBook(), new UserPrefs(), getTypicalSerialNumberSetsBook());
        expectedModel.addStock(validStock);

        assertCommandSuccess(new AddCommand(validStock), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validStock), expectedModel);
    }
}
