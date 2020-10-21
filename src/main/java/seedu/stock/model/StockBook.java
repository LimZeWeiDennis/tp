package seedu.stock.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.stock.model.stock.Stock;
import seedu.stock.model.stock.UniqueStockList;

/**
 * Wraps all data at the stock-book level
 * Duplicates are not allowed (by .isSameStock comparison)
 */
public class StockBook implements ReadOnlyStockBook {

    private final UniqueStockList stocks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        stocks = new UniqueStockList();
    }

    public StockBook() {}

    /**
     * Creates an StockBook using the Stocks in the {@code toBeCopied}
     */
    public StockBook(ReadOnlyStockBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the stock list with {@code stocks}.
     * {@code stocks} must not contain duplicate stocks.
     */
    public void setStocks(List<Stock> stocks) {
        this.stocks.setStocks(stocks);
    }

    /**
     * Resets the existing data of this {@code StockBook} with {@code newData}.
     */
    public void resetData(ReadOnlyStockBook newData) {
        requireNonNull(newData);

        setStocks(newData.getStockList());
    }

    //// person-level operations

    /**
     * Returns true if a stock with the same identity as {@code stock} exists in the stock book.
     */
    public boolean hasStock(Stock stock) {
        requireNonNull(stock);
        return stocks.contains(stock);
    }

    /**
     * Adds a stock to the stock book.
     * The stock must not already exist in the stock book.
     */
    public void addStock(Stock p) {
        stocks.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedStock}.
     * {@code target} must exist in the stock book.
     * The stock identity of {@code editedStock} must not be the same as another existing stock in the stock book.
     */
    public void setStock(Stock target, Stock editedStock) {
        requireNonNull(editedStock);

        stocks.setStock(target, editedStock);
    }

    /**
     * Removes {@code key} from this {@code StockBook}.
     * {@code key} must exist in the stock book.
     */
    public void removeStock(Stock key) {
        stocks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return stocks.asUnmodifiableObservableList().size() + " stocks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Stock> getStockList() {
        return stocks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StockBook // instanceof handles nulls
                && stocks.equals(((StockBook) other).stocks));
    }

    @Override
    public int hashCode() {
        return stocks.hashCode();
    }

    public void sortStocks(Comparator<Stock> comparator) {
        stocks.sortList(comparator);
    }
}
