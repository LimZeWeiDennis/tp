package seedu.stock.model.stock;

import static java.util.Objects.requireNonNull;
import static seedu.stock.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.stock.model.stock.exceptions.DuplicateStockException;
import seedu.stock.model.stock.exceptions.StockNotFoundException;

/**
 * A list of stock that enforces uniqueness between its elements and does not allow nulls.
 * A stock is considered unique by comparing using {@code Stock#isSameStock(Stock)}. As such, adding and updating of
 * persons uses Stock#isSameStock(Stock) for equality so as to ensure that the stock being added or updated is
 * unique in terms of identity in the UniqueStockList. However, the removal of a stock uses Stock#equals(Object) so
 * as to ensure that the stock with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Stock#isSameStock(Stock)
 */
public class UniqueStockList implements Iterable<Stock> {

    private final ObservableList<Stock> internalList = FXCollections.observableArrayList();
    private final ObservableList<Stock> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Stock toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStock);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Stock toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStockException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the stock {@code target} in the list with {@code updatedStock}.
     * {@code target} must exist in the list.
     * The person identity of {@code updatedStock} must not be the same as another existing stock in the list.
     */
    public void setStock(Stock target, Stock updatedStock) {
        requireAllNonNull(target, updatedStock);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StockNotFoundException();
        }

        if (!target.isSameStock(updatedStock) && contains(updatedStock)) {
            throw new DuplicateStockException();
        }

        internalList.set(index, updatedStock);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Stock toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StockNotFoundException();
        }
    }

    public void setStocks(UniqueStockList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setStocks(List<Stock> stocks) {
        requireAllNonNull(stocks);
        if (!personsAreUnique(stocks)) {
            throw new DuplicateStockException();
        }

        internalList.setAll(stocks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Stock> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Stock> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStockList // instanceof handles nulls
                        && internalList.equals(((UniqueStockList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Stock> stocks) {
        for (int i = 0; i < stocks.size() - 1; i++) {
            for (int j = i + 1; j < stocks.size(); j++) {
                if (stocks.get(i).isSameStock(stocks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sorts the internal list according to the comparator.
     *
     * @param comparator The comparator used for sorting.
     */
    public void sortList(Comparator<Stock> comparator) {
        FXCollections.sort(internalList, comparator);
    }
}
