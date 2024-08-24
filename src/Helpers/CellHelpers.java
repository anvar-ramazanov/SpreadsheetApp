package Helpers;

public class CellHelpers {
    public static String getCellName(int rowIndex, int columnIndex) {
        return String.format("%s%d", (char)('A' + columnIndex), rowIndex + 1);
    }
}
