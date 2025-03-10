package lib.src;

public class Symbol {
    private String lexeme;
    private String tokenType;

    public Symbol(String lexeme, String tokenType) {
        this.lexeme = lexeme;
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "Symbol: " + lexeme + " (Type: " + tokenType + ")";
    }
}