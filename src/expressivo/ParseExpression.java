package expressivo;

import expressivo.parser.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import java.util.List;

public class ParseExpression {

    public static Expression parse(String input) {
        try {
            // Create a stream of tokens from the input
            ANTLRInputStream stream = new ANTLRInputStream(input);
            ExpressionLexer lexer = new ExpressionLexer(stream);
            lexer.reportErrorsAsExceptions();
            TokenStream tokens = new CommonTokenStream(lexer);
            
            // Generate a parse tree from the tokens
            ExpressionParser parser = new ExpressionParser(tokens);
            parser.reportErrorsAsExceptions();
            
            return makeExpression(parser.root());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("invalid expression: " + input, e);
        }
    }
    
    private static Expression makeExpression(ExpressionParser.RootContext context) {
        return makeSum(context.sum());
    }
    
    private static Expression makeSum(ExpressionParser.SumContext context) {
        List<ExpressionParser.PrimitiveContext> terms = context.primitive();
        Expression sum = makePrimitive(terms.get(0));
        
        for (int i = 1; i < terms.size(); i++) {
            sum = new AdditionExpression(sum, makePrimitive(terms.get(i)));
        }
        return sum;
    }
    
    private static Expression makePrimitive(ExpressionParser.PrimitiveContext context) {
        if (context.NUMBER() != null) {
            return new NumberExpression(Double.parseDouble(context.NUMBER().getText()));
        } else {
            return makeSum(context.sum());
        }
    }
}