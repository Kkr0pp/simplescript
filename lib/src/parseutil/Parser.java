package lib.src.parseutil;

import lib.src.SimpleScanner;
import lib.src.tokenutil.Token;

import java.util.*;

public class Parser {
    SimpleScanner scanner;
    Stack<Integer> stateStack;
    Stack<ItemType> symbolStack;
    ParsingTable parsingTable;
    Grammar grammar;

    public Parser(SimpleScanner scanner) {
        this.scanner = scanner;
        this.stateStack = new Stack<>();
        this.symbolStack = new Stack<>();
        this.parsingTable = new ParsingTable();
        this.grammar = new Grammar();

        System.out.println("Parsing...");
        parse();
    }

    public void parse() {
        stateStack.push(0);
        Token token = scanner.getNextToken();

        while (true) {
            int currentState = stateStack.peek();
            ItemType lookahead = token != null ? new Items(token).getType() : null;
            System.out.println("Current state: " + currentState + ", Lookahead: " + lookahead);


            ParsingTable.Action action = parsingTable.getAction(currentState, lookahead);

            if (action == null) {
                error("Unexpected token: " + token);
                return;
            }

            switch (action.getType()) {
                case SHIFT:
                    stateStack.push(action.getTarget());
                    symbolStack.push(lookahead);
                    token = scanner.getNextToken();
                    break;

                case REDUCE:
                    ProductionRule rule = grammar.getAllRules().get(action.getTarget());
                    for (int i = 0; i < rule.getRHS().size(); i++) {
                        stateStack.pop();
                        symbolStack.pop();
                    }
                    int newState = parsingTable.getGoto(stateStack.peek(), rule.getLHS());
                    if (newState == -1) {
                        error("Invalid GOTO after reduce");
                        return;
                    }
                    stateStack.push(newState);
                    symbolStack.push(rule.getLHS());
                    System.out.println("Reduced by rule: " + rule);
                    break;

                case ACCEPT:
                    System.out.println("Input accepted.");
                    return;
            }
        }
    }

    private void error(String msg) {
        System.err.println("Parse Error: " + msg);
    }
}
