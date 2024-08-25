import Formulas.Expressions.*;
import Formulas.Tokens.Tokenizer;
import Formulas.Tokens.TokenizerImpl;

import Controller.SpreadsheetController;
import Models.SpreadsheetModelFabric;
import Views.SpreadsheetView;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;

public class TableAppModule {
    public static PicoContainer newContainer() {
        final MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(PicoContainer.class, pico);

        // formulas
        pico.addComponent(Tokenizer.class, TokenizerImpl.class);
        pico.addComponent(ExpressionTreeParser.class, ExpressionTreeParserImpl.class);
        pico.addComponent(ExpressionTreeAnalyzer.class, ExpressionTreeAnalyzerImpl.class);
        pico.addComponent(ExpressionTreeEvaluator.class, ExpressionTreeEvaluatorImpl.class);

        // app
        pico.addComponent(SpreadsheetController.class);
        pico.addComponent(SpreadsheetView.class);
        pico.addComponent(new SpreadsheetModelFabric(50, 26));

        return pico;
    }
}