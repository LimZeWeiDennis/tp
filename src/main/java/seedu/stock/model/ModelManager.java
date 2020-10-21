package seedu.stock.model;

import static java.util.Objects.requireNonNull;
import static seedu.stock.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.stock.commons.core.GuiSettings;
import seedu.stock.commons.core.LogsCenter;
import seedu.stock.model.stock.SerialNumberSet;
import seedu.stock.model.stock.Source;
import seedu.stock.model.stock.Stock;

/**
 * Represents the in-memory model of the stock book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StockBook stockBook;
    private final SerialNumberSetsBook serialNumberSetsBook;
    private final UserPrefs userPrefs;
    private FilteredList<Stock> filteredStocks;
    private final FilteredList<SerialNumberSet> filteredSerialNumberSets;

    /**
     * Initializes a ModelManager with the given stockBook and userPrefs.
     */
    public ModelManager(ReadOnlyStockBook stockBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlySerialNumberSetsBook serialNumberSetsBook) {
        super();
        requireAllNonNull(stockBook, userPrefs);

        logger.fine("Initializing with stock book: " + stockBook + " and user prefs " + userPrefs);

        this.stockBook = new StockBook(stockBook);
        this.serialNumberSetsBook = new SerialNumberSetsBook(serialNumberSetsBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStocks = new FilteredList<>(this.stockBook.getStockList());
        filteredSerialNumberSets = new FilteredList<>(this.serialNumberSetsBook.getSerialNumberSetsList());
    }

    public ModelManager() {
        this(new StockBook(), new UserPrefs(), new SerialNumberSetsBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getStockBookFilePath() {
        return userPrefs.getStockBookFilePath();
    }

    @Override
    public void setStockBookFilePath(Path stockBookFilePath) {
        requireNonNull(stockBookFilePath);
        userPrefs.setStockBookFilePath(stockBookFilePath);
    }

    //=========== StockBook ================================================================================

    @Override
    public void setStockBook(ReadOnlyStockBook stockBook) {
        this.stockBook.resetData(stockBook);
    }

    @Override
    public ReadOnlyStockBook getStockBook() {
        return stockBook;
    }

    @Override
    public boolean hasStock(Stock stock) {
        requireNonNull(stock);
        return stockBook.hasStock(stock);
    }

    @Override
    public void deleteStock(Stock target) {
        stockBook.removeStock(target);
    }

    @Override
    public void addStock(Stock stock) {
        stockBook.addStock(stock);
        updateFilteredStockList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setStock(Stock target, Stock updatedStock) {
        requireAllNonNull(target, updatedStock);

        stockBook.setStock(target, updatedStock);
    }

    //=========== SerialNumberSetsBook ================================================================================

    @Override
    public void setSerialNumberSetsBook(ReadOnlySerialNumberSetsBook serialNumberSetsBook) {
        this.serialNumberSetsBook.resetData(serialNumberSetsBook);
    }

    @Override
    public ReadOnlySerialNumberSetsBook getSerialNumberSetsBook() {
        return serialNumberSetsBook;
    }

    @Override
    public boolean hasSerialNumberSet(SerialNumberSet serialNumberSet) {
        requireNonNull(serialNumberSet);
        return serialNumberSetsBook.hasSerialNumberSet(serialNumberSet);
    }

    @Override
    public void deleteSerialNumberSet(SerialNumberSet target) {
        serialNumberSetsBook.removeSerialNumberSet(target);
    }

    @Override
    public void updateSerialNumberSet(Source source) {
        serialNumberSetsBook.incrementSerialNumberSet(source);
    }

    @Override
    public String generateNextSerialNumber(Source source) {
        String serialNumber = serialNumberSetsBook.generateNextSerialNumber(source);
        serialNumberSetsBook.incrementSerialNumberSet(source);
        return serialNumber;
    }

    @Override
    public void addSerialNumberSet(SerialNumberSet serialNumberSet) {
        serialNumberSetsBook.addSerialNumberSet(serialNumberSet);
        //updateFilteredSerialNumberSetsList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setSerialNumberSet(SerialNumberSet target, SerialNumberSet editedSerialNumberSet) {
        requireAllNonNull(target, editedSerialNumberSet);

        serialNumberSetsBook.setSerialNumberSet(target, editedSerialNumberSet);
    }

    //=========== Filtered Stock List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Stock} backed by the internal list of
     * {@code versionedStockBook}
     */
    @Override
    public ObservableList<Stock> getFilteredStockList() {
        return filteredStocks;
    }

    @Override
    public void updateFilteredStockList(Predicate<Stock> predicate) {
        requireNonNull(predicate);
        filteredStocks.setPredicate(predicate);
    }

    @Override
    public void sortFilteredStockList(Comparator<Stock> comparator) {
        stockBook.sortStocks(comparator);
        filteredStocks = new FilteredList<>(stockBook.getStockList());
    }

    @Override
    public ObservableList<SerialNumberSet> getFilteredSerialNumberSetList() {
        return filteredSerialNumberSets;
    }

    @Override
    public void updateFilteredSerialNumberSetList(Predicate<SerialNumberSet> predicate) {
        requireNonNull(predicate);
        filteredSerialNumberSets.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return stockBook.equals(other.stockBook)
                && userPrefs.equals(other.userPrefs)
                && filteredStocks.equals(other.filteredStocks);
    }

}
