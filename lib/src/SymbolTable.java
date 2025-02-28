package lib.src;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Symbol> symbols;

    public SymbolTable() {
        symbols = new HashMap<>();
        initializeReservedSymbols();
    }

    // Pre-populate the symbol table with reserved symbols from the SimpleScript Phase 1PDF.
    private void initializeReservedSymbols() {
        // Control Flow Tokens
        addReservedSymbol("check");
        addReservedSymbol("orcheck");
        addReservedSymbol("otherwise");
        
        // Switch Statement Tokens
        addReservedSymbol("choose");
        addReservedSymbol("when");
        addReservedSymbol("fallback");
        
        // Looping Tokens
        addReservedSymbol("repeat");
        addReservedSymbol("while");
        addReservedSymbol("stop");
        addReservedSymbol("skip");
        
        // Data Type Tokens
        addReservedSymbol("int");
        addReservedSymbol("float");
        addReservedSymbol("bool");
        addReservedSymbol("string");
        addReservedSymbol("char");
        
        // Type Conversion Token
        addReservedSymbol("as");
        
        // Function and Method Tokens
        addReservedSymbol("task");
        addReservedSymbol("void");
        addReservedSymbol("give");
        
        // Operators
        addReservedSymbol("plus");
        addReservedSymbol("minus");
        addReservedSymbol("times");
        addReservedSymbol("over");
        addReservedSymbol("mod");
        addReservedSymbol("is");
        addReservedSymbol("isnt");
        addReservedSymbol("less");
        addReservedSymbol("more");
        addReservedSymbol("lesseq");
        addReservedSymbol("moreeq");
        addReservedSymbol("both");
        addReservedSymbol("or");
        addReservedSymbol("not");
        
        // Assignment and Declaration Tokens
        addReservedSymbol("be");
        addReservedSymbol("let");
        
        // Input/Output Tokens
        addReservedSymbol("show");
        addReservedSymbol("ask");
        
        // Punctuation and Delimiters
        addReservedSymbol("(");
        addReservedSymbol(")");
        addReservedSymbol("{");
        addReservedSymbol("}");
        addReservedSymbol(",");
        addReservedSymbol(";");
        
        // Comment Symbols
        addReservedSymbol("|");       // single-line comment symbol
        addReservedSymbol("|*");      // multi-line comment start
        addReservedSymbol("*|");      // multi-line comment end
    }

    // Helper method to add a reserved symbol.
    private void addReservedSymbol(String symbol) {
        if (!symbols.containsKey(symbol)) {
            symbols.put(symbol, new Symbol(symbol));
        }
    }

    // Adds an identifier (or non-reserved symbol) to the symbol table if it's not already there.
    public void add(String identifier) {
        if (!symbols.containsKey(identifier)) {
            symbols.put(identifier, new Symbol(identifier));
        }
    }

    // Retrieve a symbol if needed.
    public Symbol get(String identifier) {
        return symbols.get(identifier);
    }

    // Print out all symbols for debugging.
    public void printSymbols() {
        System.out.println("\n--- Symbol Table ---");
        for (Symbol symbol : symbols.values()) {
            System.out.println(symbol);
        }
    }
}
