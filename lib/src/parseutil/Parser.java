package lib.src.parseutil;

import lib.src.SimpleScanner;
import lib.src.tokenutil.Token;
import java.util.*;

public class Parser {
    SimpleScanner scanner;
    Stack<Integer> stateStack;
    Stack<ItemType> symbolStack;
    Stack<ParseTreeNode> parseTreeStack;
    ParsingTable parsingTable;
    Grammar grammar;

    private ParseTreeNode root;

    public Parser(SimpleScanner scanner) {
        this.scanner = scanner;
        this.stateStack = new Stack<>();
        this.symbolStack = new Stack<>();
        this.parseTreeStack = new Stack<>();
        this.parsingTable = new ParsingTable();
        this.grammar = new Grammar();
        this.root = null;

        System.out.println("Parsing...");
        parse();
    }

    public ParseTreeNode getRoot() {
        return root;
    }

    public void parse() {
        stateStack.push(0);
        Token token = scanner.getNextToken();

        while (true) {
            int currentState = stateStack.peek();
            ItemType lookahead = token != null ? new Items(token).getType() : null;

            // Debugging logs
            System.out.println("Current state: " + currentState + ", Lookahead: " + lookahead);
            if (token == null) {
                System.out.println("End of input reached.");
                break;  // Break out of loop when no more tokens are available
            }

            ParsingTable.Action action = parsingTable.getAction(currentState, lookahead);

            if (action == null) {
                error("Unexpected token: " + token);
                return;
            }

            switch (action.getType()) {
                case SHIFT:
                    stateStack.push(action.getTarget());
                    symbolStack.push(lookahead);
                    token = scanner.getNextToken(); // Get the next token
                    break;

                case REDUCE:
                    ProductionRule rule = grammar.getAllRules().get(action.getTarget());
                    ParseTreeNode reducedNode = new ParseTreeNode(rule.getLHS().toString());

                    for (int i = 0; i < rule.getRHS().size(); i++) {
                        stateStack.pop();
                        ItemType symbol = symbolStack.pop();
                        reducedNode.addChild(new ParseTreeNode(symbol.toString()));
                    }

                    parseTreeStack.push(reducedNode);
                    symbolStack.push(rule.getLHS());

                    int newState = parsingTable.getGoto(stateStack.peek(), rule.getLHS());
                    if (newState == -1) {
                        error("Invalid GOTO after reduce");
                        return;
                    }

                    stateStack.push(newState);
                    if (root == null) {
                        root = reducedNode;  // Set the root when parsing is done
                    }
                    break;

                case ACCEPT:
                    System.out.println("Input accepted.");
                    if (root != null) {
                        ParseTreeVisualizer.showParseTree(root);  // Display the tree
                    }
                    return;
            }
        }
    }

    private void error(String msg) {
        System.err.println("Parse Error: " + msg);
    }
}
