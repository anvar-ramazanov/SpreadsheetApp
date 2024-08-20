package test.Helpers;

import Helpers.StringHelpers;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringHelpersTests {
    @Test
    public void StringHelpers_isNumeric() {
        // Valid numbers
        assertTrue(StringHelpers.isNumeric("123"));           // Integer
        assertTrue(StringHelpers.isNumeric("123.456"));       // Floating-point number
        assertTrue(StringHelpers.isNumeric("-123.456"));      // Negative floating-point
        assertTrue(StringHelpers.isNumeric("0"));             // Zero
        assertTrue(StringHelpers.isNumeric("-0"));            // Negative zero
        assertTrue(StringHelpers.isNumeric("1.23e4"));        // Scientific notation
        assertTrue(StringHelpers.isNumeric("1E10"));          // Scientific notation with integer exponent
        assertTrue(StringHelpers.isNumeric(".5"));            // Leading decimal
        assertTrue(StringHelpers.isNumeric("-.5"));           // Negative leading decimal

        // invalid numbers
        assertFalse(StringHelpers.isNumeric("abc"));          // Alphabetic string
        assertFalse(StringHelpers.isNumeric("123a"));         // Alphanumeric string
        assertFalse(StringHelpers.isNumeric("1.2.3"));        // Multiple decimals
        assertFalse(StringHelpers.isNumeric(""));             // Empty string
        assertFalse(StringHelpers.isNumeric(" "));            // Space character
        assertFalse(StringHelpers.isNumeric("--123"));        // Double negative

        // null input
        assertFalse(StringHelpers.isNumeric(null));           // Null input

        // edge cases
        assertTrue(StringHelpers.isNumeric(String.valueOf(Double.MAX_VALUE)));   // Max double value
        assertTrue(StringHelpers.isNumeric(String.valueOf(Double.MIN_VALUE)));   // Min double value
        assertTrue(StringHelpers.isNumeric("4.9E-324"));       // Smallest positive double value
        assertTrue(StringHelpers.isNumeric("1e-324"));         // Subnormal value
    }

    @Test
    public void StringHelpers_isBoolean() {
        // Valid true cases
        assertTrue(StringHelpers.isBoolean("true"));
        assertTrue(StringHelpers.isBoolean("TRUE"));
        assertTrue(StringHelpers.isBoolean("TrUe"));
        assertTrue(StringHelpers.isBoolean(" true "));

        // Valid false cases
        assertTrue(StringHelpers.isBoolean("false"));
        assertTrue(StringHelpers.isBoolean("FALSE"));
        assertTrue(StringHelpers.isBoolean("FaLsE"));
        assertFalse(StringHelpers.isBoolean("false "));

        // Invalid cases
        assertFalse(StringHelpers.isBoolean("not a boolean"));
        assertFalse(StringHelpers.isBoolean(""));
        assertFalse(StringHelpers.isBoolean(null));
        assertFalse(StringHelpers.isBoolean("123"));
        assertFalse(StringHelpers.isBoolean("trueish"));
        assertFalse(StringHelpers.isBoolean("falsey"));


        // Random capitalization cases
        assertTrue(StringHelpers.isBoolean("truE"));
        assertTrue(StringHelpers.isBoolean("fAlSe"));
    }
}
