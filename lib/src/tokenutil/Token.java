package lib.src.tokenutil;

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


    public String toString() {
        switch (type) {
            case IDENTIFIER:
                return lexeme + " (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case OPERATOR:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case CONDITIONAL:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case LOGICAL:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case ASSIGNMENT:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case LOOP:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case PRIMITIVE:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case LITERAL:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            case PUNCTUATION:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
            default:
                return "[" + lexeme + "] (Line " + lineNumber + ", Column " + columnNumber + ") " + type;
        }
    }
}
