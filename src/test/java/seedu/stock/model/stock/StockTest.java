package seedu.stock.model.stock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.stock.logic.commands.CommandTestUtil.VALID_LOCATION_BANANA;
import static seedu.stock.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static seedu.stock.logic.commands.CommandTestUtil.VALID_QUANTITY_BANANA;
import static seedu.stock.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BANANA;
import static seedu.stock.logic.commands.CommandTestUtil.VALID_SOURCE_BANANA;
import static seedu.stock.testutil.TypicalStocks.APPLE;
import static seedu.stock.testutil.TypicalStocks.BANANA;

import org.junit.jupiter.api.Test;

import seedu.stock.testutil.StockBuilder;

public class StockTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(APPLE.isSameStock(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameStock(null));

        // same serialnumber, different attributes -> returns true
        Stock editedApple = new StockBuilder(APPLE).withSerialNumber(VALID_SERIAL_NUMBER_BANANA)
                .build();
        assertTrue(BANANA.isSameStock(editedApple));

        // same name, same source, different attributes -> returns true
        editedApple = new StockBuilder(APPLE)
                .withSerialNumber(VALID_SERIAL_NUMBER_BANANA)
                .withQuantity(VALID_QUANTITY_BANANA)
                .withLocation(VALID_LOCATION_BANANA).build();
        assertTrue(APPLE.isSameStock(editedApple));

        // same name, same source, same serialnumber, different attributes -> returns true
        editedApple = new StockBuilder(APPLE)
                .withSerialNumber(VALID_SERIAL_NUMBER_BANANA)
                .withName(VALID_NAME_BANANA)
                .withSource(VALID_SOURCE_BANANA).build();
        assertTrue(BANANA.isSameStock(editedApple));

        // different name, same source, different serialnumber -> returns false
        editedApple = new StockBuilder(APPLE).withSource(VALID_SOURCE_BANANA)
                .build();
        assertFalse(BANANA.isSameStock(editedApple));

        // same name, different source, different serialnumber -> returns false
        editedApple = new StockBuilder(APPLE).withName(VALID_NAME_BANANA)
                .build();
        assertFalse(BANANA.isSameStock(editedApple));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Stock appleCopy = new StockBuilder(APPLE).build();
        assertTrue(APPLE.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different stock -> returns false
        assertFalse(APPLE.equals(BANANA));

        // different name, same serialnumber-> returns false
        Stock editedApple = new StockBuilder(APPLE).withName(VALID_NAME_BANANA).build();
        assertTrue(APPLE.equals(editedApple));

        // different serial number, same name, same source-> returns false
        editedApple = new StockBuilder(APPLE).withSerialNumber(VALID_SERIAL_NUMBER_BANANA).build();
        assertTrue(APPLE.equals(editedApple));

        // different source, same serialnumber -> returns true
        editedApple = new StockBuilder(APPLE).withSource(VALID_SOURCE_BANANA).build();
        assertTrue(APPLE.equals(editedApple));

        // different Quantity -> returns true
        editedApple = new StockBuilder(APPLE).withQuantity(VALID_QUANTITY_BANANA).build();
        assertTrue(APPLE.equals(editedApple));

        // different Location -> returns true
        editedApple = new StockBuilder(APPLE).withLocation(VALID_LOCATION_BANANA).build();
        assertTrue(APPLE.equals(editedApple));
    }
}
