package Formulas.Tokens;

public class TokenizerHelpers {
    public static boolean isValidCellName(String input) {
        String regex = "^[A-Za-z]+[1-9][0-9]*$";
        return input != null && input.matches(regex);
    }

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
}
