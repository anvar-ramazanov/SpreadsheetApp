package Formulas.Exceptions.Expressions.TreeAnalyzer;

import java.util.LinkedHashSet;

public class CircularDependencyException extends ExpressionTreeAnalyzerException {

    private final LinkedHashSet<String> visitedCells;

    public CircularDependencyException() {
        super();
        visitedCells = null;
    }

    public CircularDependencyException(String message) {
        super(message);
        visitedCells = null;
    }

    public CircularDependencyException(String message, LinkedHashSet<String> visitedCells) {
        super(message);
        this.visitedCells = visitedCells;
    }

    public CircularDependencyException(String message, Throwable cause) {
        super(message, cause);
        visitedCells = null;
    }

    public CircularDependencyException(Throwable cause) {
        super(cause);
        visitedCells = null;
    }

    public LinkedHashSet<String> getVisitedCells() {
        return visitedCells;
    }
}
