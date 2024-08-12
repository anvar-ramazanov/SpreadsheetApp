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

    @Test
    public void isNumeric_ValidNumbers() {
        assertTrue(TokenizerHelpers.isNumeric("123"));           // Integer
        assertTrue(TokenizerHelpers.isNumeric("123.456"));       // Floating-point number
        assertTrue(TokenizerHelpers.isNumeric("-123.456"));      // Negative floating-point
        assertTrue(TokenizerHelpers.isNumeric("0"));             // Zero
        assertTrue(TokenizerHelpers.isNumeric("-0"));            // Negative zero
        assertTrue(TokenizerHelpers.isNumeric("1.23e4"));        // Scientific notation
        assertTrue(TokenizerHelpers.isNumeric("1E10"));          // Scientific notation with integer exponent
        assertTrue(TokenizerHelpers.isNumeric(".5"));            // Leading decimal
        assertTrue(TokenizerHelpers.isNumeric("-.5"));           // Negative leading decimal
    }

    @Test
    public void isNumeric_InvalidNumbers() {
        assertFalse(TokenizerHelpers.isNumeric("abc"));          // Alphabetic string
        assertFalse(TokenizerHelpers.isNumeric("123a"));         // Alphanumeric string
        assertFalse(TokenizerHelpers.isNumeric("1.2.3"));        // Multiple decimals
        assertFalse(TokenizerHelpers.isNumeric(""));             // Empty string
        assertFalse(TokenizerHelpers.isNumeric(" "));            // Space character
        assertFalse(TokenizerHelpers.isNumeric("--123"));        // Double negative
    }


    @Test
    public void isNumeric_NullInput() {
        assertFalse(TokenizerHelpers.isNumeric(null));           // Null input
    }

    @Test
    public void isNumeric_EdgeCases() {
        assertTrue(TokenizerHelpers.isNumeric(String.valueOf(Double.MAX_VALUE)));   // Max double value
        assertTrue(TokenizerHelpers.isNumeric(String.valueOf(Double.MIN_VALUE)));   // Min double value
        assertTrue(TokenizerHelpers.isNumeric("4.9E-324"));       // Smallest positive double value
        assertTrue(TokenizerHelpers.isNumeric("1e-324"));         // Subnormal value
    }

}
