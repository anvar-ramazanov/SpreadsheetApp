package Formulas.Exceptions.Expressions.TreeAnalyzer;

import java.util.HashSet;

public class CircularDependencyException extends ExpressionTreeAnalyzerException {

    private final HashSet<String> visitedCells;

    public CircularDependencyException() {
        super();
        visitedCells = null;
    }

    public CircularDependencyException(String message) {
        super(message);
        visitedCells = null;
    }

    public CircularDependencyException(String message, HashSet<String> visitedCells) {
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

    public HashSet<String> getVisitedCells() {
        return visitedCells;
    }
}
