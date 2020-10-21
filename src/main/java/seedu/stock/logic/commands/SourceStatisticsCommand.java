package seedu.stock.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.stock.commons.core.LogsCenter;
import seedu.stock.model.Model;
import seedu.stock.model.stock.Stock;

public class SourceStatisticsCommand extends StatisticsCommand {

    public static final String STATISTICS_TYPE = "source";
    private static final String MESSAGE_SUCCESS = "Opened statistics for source window\n"
                    + "WARNING: Diagram shown may not be optimal for certain quantities";

    private static final Logger logger = LogsCenter.getLogger(SourceStatisticsCommand.class);

    @Override
    public CommandResult execute(Model model) {
        ObservableList<Stock> stockBookList = model.getStockBook().getStockList();
        Map<String, Integer> sourceData = new HashMap<>();
        stockBookList.forEach(stock -> {
            String sourceString = stock.getSource().value;
            if (!sourceData.containsKey(sourceString)) {
                sourceData.put(sourceString, 1);
            } else {
                int sourceCount = sourceData.get(sourceString);
                sourceData.put(sourceString, sourceCount + 1);
            }
        });

        //array of size 1, index 0 is statistics type
        String[] otherStatisticsDetails = {"source"};
        logger.log(Level.INFO, "Valid input.");
        return new CommandResult(MESSAGE_SUCCESS, sourceData, false, true, otherStatisticsDetails, false);
    }
}
