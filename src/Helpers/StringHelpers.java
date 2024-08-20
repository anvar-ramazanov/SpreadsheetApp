package Helpers;

public class StringHelpers {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String str) {
        if (str == null) {
            return false;
        }
        return str.trim().equalsIgnoreCase("true") || str.equalsIgnoreCase("false");
    }
}
