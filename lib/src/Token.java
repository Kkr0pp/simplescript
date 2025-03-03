package lib.src;

public class Token {
    private TokenType type;
    private String lexeme;
    private int lineNumber;
    private int columnNumber;

    public Token(TokenType type, String lexeme, int lineNumber, int columnNumber) {
        this.type = type;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        switch (type) {
            case RESERVED_WORD:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ")";
            case IDENTIFIER:
                return lexeme + " (Line " + lineNumber + ", Column " + columnNumber + ")";
            case CONSTANT:
                return lexeme + " (Line " + lineNumber + ", Column " + columnNumber + ")";
            case PUNCTUATION:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ")";
            default:
                return "UNKNOWN (Line " + lineNumber + ", Column " + columnNumber + ")";
        }
    }
}