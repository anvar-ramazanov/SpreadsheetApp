package Models;

public class SpreadsheetModelFabric {
    private final int rowCount;
    private final int columnCount;
    private SpreadsheetModel model;

    public  SpreadsheetModelFabric(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public SpreadsheetModel getSpreadsheetModel() {
        if (model == null) {
            model = new SpreadsheetModel(this.rowCount, this.columnCount);
        }
        return model;
    }
}
