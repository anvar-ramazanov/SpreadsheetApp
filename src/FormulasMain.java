import Formulas.AST.ASTParser;
import Formulas.Tokens.Token;
import Formulas.Tokens.Tokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class FormulasMain {
    public static void main(String[] args) {

        //var expression = "-8 + (6 + 7) * (3 + 5) + POW (a16, 2) + 8.6 > 1 + FALSE ";

        //var expression = "(6 * 7) + a1";

        //var expression = "6 + 7 * a1";

        //var expression = "(-6 + 7) * (a1 - 2)";

        //var expression = "(-6 + 7) * (a1 - 2) + POW(-3, 2) + (-2)";

        //var expression = "POW(-2, A1 - 3) * (42 + B2)";

        var expression = "(-5 * 2) + a1 * SUM(6, 8 + 1)";

        var tokenizer = new Tokenizer();

        List<Token> tokens = tokenizer.tokenize(expression);

        var parser = new ASTParser(tokens);
        
        var node = parser.parse();

        Map<String, Object> variables = new HashMap<>();
        variables.put("a1", 10.0);
        Object result = node.evaluate(variables);
        System.out.println("Result: " + result);  // Output: Result: -7.0
    }
}