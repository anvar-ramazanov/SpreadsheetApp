package test.Helpers;

import Helpers.CellHelpers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellHelpersTests {
    @Test
    public void CellHelpers_getCellName() {
        assertEquals("A1", CellHelpers.getCellName(0, 0));
        assertEquals("Z26", CellHelpers.getCellName(25, 25));
    }
}
