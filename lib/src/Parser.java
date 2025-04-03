package lib.src;

import java.util.*;

public class Parser {
    private SimpleScanner scanner;
    private Stack<Integer> stateStack;
    private Stack<String> symbolStack;
    private static final Map<Integer, Map<String, String>> ACTION_TABLE = new HashMap<>();
    private static final Map<Integer, Map<String, Integer>> GOTO_TABLE = new HashMap<>();

    static {
        // ACTION TABLE (Shift, Reduce, Accept)
        ACTION_TABLE.put(0, Map.of("let", "S3", "task", "S4"));
        ACTION_TABLE.put(3, Map.of("int", "S5", "float", "S5", "string", "S5", "bool", "S5"));
        ACTION_TABLE.put(5, Map.of("IDENTIFIER", "S6"));
        ACTION_TABLE.put(6, Map.of("be", "S7"));
        ACTION_TABLE.put(7, Map.of("IDENTIFIER", "S8", "INTEGER", "S9", "FLOAT", "S9", "STRING", "S9", "BOOLEAN", "S9"));
        ACTION_TABLE.put(8, Map.of("$", "ACC"));

        // GOTO TABLE (State Transitions for Non-Terminals)
        GOTO_TABLE.put(0, Map.of("Statement", 1));
        GOTO_TABLE.put(3, Map.of("Type", 2));
    }

    public Parser(SimpleScanner scanner) {
        this.scanner = scanner;
        this.stateStack = new Stack<>();
        this.symbolStack = new Stack<>();
        stateStack.push(0); // Start state
    }

    public void parse() {
        Token token = scanner.getNextToken();
        while (true) {
            int state = stateStack.peek();
            String tokenType = token != null ? token.getType().toString() : "$"; // End of input

            if (!ACTION_TABLE.containsKey(state) || !ACTION_TABLE.get(state).containsKey(tokenType)) {
                System.err.println("Syntax Error at line " + token.getLineNumber() + ", column " + token.getColumnNumber());
                return;
            }

            String action = ACTION_TABLE.get(state).get(tokenType);
            if (action.startsWith("S")) { // Shift
                int nextState = Integer.parseInt(action.substring(1));
                stateStack.push(nextState);
                symbolStack.push(tokenType);
                token = scanner.getNextToken();
            } else if (action.startsWith("R")) { // Reduce
                int ruleNumber = Integer.parseInt(action.substring(1));
                reduce(ruleNumber);
            } else if (action.equals("ACC")) { // Accept
                System.out.println("Parsing successful!");
                return;
            }
        }
    }

    private void reduce(int ruleNumber) {
        switch (ruleNumber) {
            case 1: // Variable Declaration -> let Type IDENTIFIER be Expression
                popStack(4);
                pushGoto("VariableDeclaration");
                break;
            case 2: // Assignment -> IDENTIFIER be Expression
                popStack(3);
                pushGoto("Assignment");
                break;
        }
    }

    private void popStack(int count) {
        for (int i = 0; i < count; i++) {
            stateStack.pop();
            symbolStack.pop();
        }
    }

    private void pushGoto(String nonTerminal) {
        int state = stateStack.peek();
        if (GOTO_TABLE.containsKey(state) && GOTO_TABLE.get(state).containsKey(nonTerminal)) {
            stateStack.push(GOTO_TABLE.get(state).get(nonTerminal));
            symbolStack.push(nonTerminal);
        } else {
            System.err.println("Goto Error: " + nonTerminal);
        }
    }
}