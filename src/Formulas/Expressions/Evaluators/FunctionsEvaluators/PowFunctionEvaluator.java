package Formulas.Expressions.Evaluators.FunctionsEvaluators;

import Formulas.Expressions.Evaluators.FunctionEvaluator;

import java.util.List;

public class PowFunctionEvaluator implements FunctionEvaluator {

    @Override
    public Object evaluate(List<Object> arguments) {
        var argument1 = Double.parseDouble( arguments.get(0).toString());
        var argument2 = Double.parseDouble( arguments.get(1).toString());

        return Math.pow(argument1, argument2);
    }
}
