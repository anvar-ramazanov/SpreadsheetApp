package test.Formulas.tokens;

import Formulas.Tokens.TokenizerHelpers;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenizerHelpersTest {
    @Test
    public void isValidCellName_ValidCellNames() {
        assertTrue(TokenizerHelpers.isValidCellName("A1"));
        assertTrue(TokenizerHelpers.isValidCellName("B2"));
        assertTrue(TokenizerHelpers.isValidCellName("AA10"));
        assertTrue(TokenizerHelpers.isValidCellName("Z99"));
        assertTrue(TokenizerHelpers.isValidCellName("AZ123"));
        assertTrue(TokenizerHelpers.isValidCellName("XFD1048576")); // Excel's last cell
    }

    @Test
    public void isValidCellName_InvalidCellNames() {
        assertFalse(TokenizerHelpers.isValidCellName("1A"));  // Starts with a number
        assertFalse(TokenizerHelpers.isValidCellName("A0"));  // Invalid row number
        assertFalse(TokenizerHelpers.isValidCellName(""));    // Empty string
        assertFalse(TokenizerHelpers.isValidCellName("A"));   // Missing row number
        assertFalse(TokenizerHelpers.isValidCellName("123")); // Only numbers
        assertFalse(TokenizerHelpers.isValidCellName("A-1")); // Contains a dash
        assertFalse(TokenizerHelpers.isValidCellName("A 1")); // Contains a space
        assertFalse(TokenizerHelpers.isValidCellName("A1B2")); // Multiple cell names in one
    }

    @Test
    public void isValidCellName_NullInput() {
        assertFalse(TokenizerHelpers.isValidCellName(null));
    }

}
