# Java Swing App Like Excel

## Prerequisites
* **OpenJDK 22**
* **IntelliJ IDEA** to build and run (uses IntelliJ build system)

## Dependencies
* **JUnit** for unit tests
* **PicoContainer** for IoC (Inversion of Control)

## How to Run the Project
1. Open **IntelliJ IDEA**.
2. Navigate to `src/TableApp.java`.
3. Run the project.

## Supported Features and Limitations
* Supports only one spreadsheet.
* The spreadsheet has 50x50 cells, configurable in `src/TableAppModule.java`.
* Three cell types: Number, String, and Boolean.
* Extensible formula language with binary operations, unary operations, functions, and cell references.

## Supported Functions and Operators
* Arithmetic operations: `+`, `-`, `*`, `/`
* Comparison operators: `>`, `<`
* Unary minus for numbers
* Logical operator: `!` (unary not) for boolean
* Functions for numbers: `MIN`, `SUM`, `POW`

## How to Add a New Function
1. Add the function description to `src/Formulas/Language/ExpressionLanguage.java`.
2. Implement the function in `src/Formulas/Expressions/Evaluators/FunctionsEvaluators`.

## Roadmap for Future Features
* Add reference to cell via mouse click
* Range of cells in references (e.g., `A1:A10`)
* Absolute references (e.g., `$A$10`)
* Mixed references (e.g., `$A10`, `A$10`)
* Save/Load functionality
* Look and feel
* Undo operation
* Multiple spreadsheets
* Support for styles (font, content alignment, background)
* Merging cells (horizontal/vertical)
* DateTime as a cell type
* Highlight used cells for calculation
