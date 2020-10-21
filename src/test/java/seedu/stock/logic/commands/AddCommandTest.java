package seedu.stock.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.stock.testutil.Assert.assertThrows;
import static seedu.stock.testutil.TypicalStocks.APPLE;
import static seedu.stock.testutil.TypicalStocks.BANANA;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.stock.commons.core.GuiSettings;
import seedu.stock.logic.commands.exceptions.CommandException;
import seedu.stock.model.Model;
import seedu.stock.model.ReadOnlySerialNumberSetsBook;
import seedu.stock.model.ReadOnlyStockBook;
import seedu.stock.model.ReadOnlyUserPrefs;
import seedu.stock.model.StockBook;
import seedu.stock.model.stock.SerialNumberSet;
import seedu.stock.model.stock.Source;
import seedu.stock.model.stock.Stock;
import seedu.stock.testutil.StockBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullStock_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_stockAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStockAdded modelStub = new ModelStubAcceptingStockAdded();
        Stock validStock = new StockBuilder().build();

        CommandResult commandResult = new AddCommand(validStock).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validStock), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStock), modelStub.stocksAdded);
    }

    @Test
    public void execute_duplicateStock_throwsCommandException() {
        Stock validStock = new StockBuilder().build();
        AddCommand addCommand = new AddCommand(validStock);
        ModelStub modelStub = new ModelStubWithStock(validStock);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_STOCK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Stock apple = new StockBuilder(APPLE).build();
        Stock banana = new StockBuilder(BANANA).build();
        AddCommand addAppleCommand = new AddCommand(apple);
        AddCommand addBananaCommand = new AddCommand(banana);

        // same object -> returns true
        assertTrue(addAppleCommand.equals(addAppleCommand));

        // same values -> returns true
        AddCommand addAppleCommandCopy = new AddCommand(apple);
        assertTrue(addAppleCommand.equals(addAppleCommandCopy));

        // different types -> returns false
        assertFalse(addAppleCommand.equals(1));

        // null -> returns false
        assertFalse(addAppleCommand.equals(null));

        // different stock -> returns false
        assertFalse(addAppleCommand.equals(addBananaCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getStockBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStockBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStock(Stock stock) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStockBook(ReadOnlyStockBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStockBook getStockBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStock(Stock stock) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStock(Stock target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStock(Stock target, Stock editedStock) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Stock> getFilteredStockList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStockList(Predicate<Stock> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredStockList(Comparator<Stock> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSerialNumberSetsBook(ReadOnlySerialNumberSetsBook serialNumberSetsBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySerialNumberSetsBook getSerialNumberSetsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSerialNumberSet(SerialNumberSet serialNumberSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSerialNumberSet(SerialNumberSet target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSerialNumberSet(SerialNumberSet target, SerialNumberSet editedSerialNumberSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSerialNumberSet(Source source) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSerialNumberSet(SerialNumberSet serialNumberSet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<SerialNumberSet> getFilteredSerialNumberSetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSerialNumberSetList(Predicate<SerialNumberSet> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String generateNextSerialNumber(Source source) {
            return "0";
        }
    }

    /**
     * A Model stub that contains a single stock.
     */
    private class ModelStubWithStock extends ModelStub {
        private final Stock stock;

        ModelStubWithStock(Stock stock) {
            requireNonNull(stock);
            this.stock = stock;
        }

        @Override
        public boolean hasStock(Stock stock) {
            requireNonNull(stock);
            return this.stock.isSameStock(stock);
        }
    }

    /**
     * A Model stub that always accept the stock being added.
     */
    private class ModelStubAcceptingStockAdded extends ModelStub {
        final ArrayList<Stock> stocksAdded = new ArrayList<>();

        @Override
        public boolean hasStock(Stock stock) {
            requireNonNull(stock);
            return stocksAdded.stream().anyMatch(stock::isSameStock);
        }

        @Override
        public void addStock(Stock stock) {
            requireNonNull(stock);
            stocksAdded.add(stock);
        }

        @Override
        public ReadOnlyStockBook getStockBook() {
            return new StockBook();
        }
    }
}
