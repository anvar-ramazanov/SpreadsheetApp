package Formulas.Expressions.Evaluators.Impl;

import Formulas.Expressions.Evaluators.FunctionEvaluator;

import java.util.List;

public class MinFunctionEvaluator implements FunctionEvaluator {

    @Override
    public Object evaluate(List<Object> arguments) {
        var argument1 = Double.parseDouble( arguments.get(0).toString());
        var argument2 = Double.parseDouble( arguments.get(1).toString());

        return Math.min(argument1, argument2);
    }
}
