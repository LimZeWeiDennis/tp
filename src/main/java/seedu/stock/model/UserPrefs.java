package seedu.stock.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.stock.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path stockBookFilePath = Paths.get("data" , "stockbook.json");
    private Path csvFilePath = Paths.get("data" , "stocks.csv");
    private Path serialNumberSetsBookFilePath = Paths.get("data", "serialnumbers.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setStockBookFilePath(newUserPrefs.getStockBookFilePath());
        setSerialNumberSetsBookFilePath(newUserPrefs.getSerialNumberSetsBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getStockBookFilePath() {
        return stockBookFilePath;
    }

    public Path getCsvFilePath() {
        return csvFilePath;
    }

    public void setStockBookFilePath(Path stockBookFilePath) {
        requireNonNull(stockBookFilePath);
        this.stockBookFilePath = stockBookFilePath;
    }

    public Path getSerialNumberSetsBookFilePath() {
        return serialNumberSetsBookFilePath;
    }

    public void setSerialNumberSetsBookFilePath(Path serialNumberSetsBookFilePath) {
        requireNonNull(serialNumberSetsBookFilePath);
        this.serialNumberSetsBookFilePath = serialNumberSetsBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && stockBookFilePath.equals(o.stockBookFilePath)
                && serialNumberSetsBookFilePath.equals(o.serialNumberSetsBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, stockBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + stockBookFilePath);
        sb.append("\nLocal serial number data file location : " + serialNumberSetsBookFilePath);
        return sb.toString();
    }

}
