package Formulas.Tokens;

public class TokenizerHelpers {
    public static boolean isValidCellName(String input) {
        String regex = "^[A-Za-z]+[1-9][0-9]*$";
        return input != null && input.matches(regex);
    }
}
