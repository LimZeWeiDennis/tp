package seedu.stock.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.stock.commons.core.GuiSettings;
import seedu.stock.model.stock.SerialNumberSet;
import seedu.stock.model.stock.Source;
import seedu.stock.model.stock.Stock;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Stock> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' stock book file path.
     */
    Path getStockBookFilePath();

    /**
     * Sets the user prefs' stock book file path.
     */
    void setStockBookFilePath(Path stockBookFilePath);

    /**
     * Replaces stock book data with the data in {@code stockBook}.
     */
    void setStockBook(ReadOnlyStockBook stockBook);

    /** Returns the StockBook */
    ReadOnlyStockBook getStockBook();

    /**
     * Returns true if a stock with the same identity as {@code person} exists in the stock book.
     */
    boolean hasStock(Stock stock);

    /**
     * Deletes the given person.
     * The person must exist in the stock book.
     */
    void deleteStock(Stock target);

    /**
     * Adds the given stock.
     * {@code stock} must not already exist in the stock book.
     */
    void addStock(Stock stock);

    /**
     * Replaces the given stock {@code target} with {@code updatedStock}.
     * {@code target} must exist in the stock book.
     * The {@code updatedStock} must not be the same as another existing stock in the stock book.
     */
    void setStock(Stock target, Stock updatedStock);

    /** Returns an unmodifiable view of the filtered stock list */
    ObservableList<Stock> getFilteredStockList();

    /**
     * Updates the filter of the filtered stock list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStockList(Predicate<Stock> predicate);

    void sortFilteredStockList(Comparator<Stock> comparator);

    void setSerialNumberSetsBook(ReadOnlySerialNumberSetsBook serialNumberSetsBook);

    ReadOnlySerialNumberSetsBook getSerialNumberSetsBook();

    boolean hasSerialNumberSet(SerialNumberSet serialNumberSet);

    void deleteSerialNumberSet(SerialNumberSet target);

    void setSerialNumberSet(SerialNumberSet target, SerialNumberSet editedSerialNumberSet);

    void updateSerialNumberSet(Source source);

    void addSerialNumberSet(SerialNumberSet serialNumberSet);

    ObservableList<SerialNumberSet> getFilteredSerialNumberSetList();

    void updateFilteredSerialNumberSetList(Predicate<SerialNumberSet> predicate);

    String generateNextSerialNumber(Source source);
}
