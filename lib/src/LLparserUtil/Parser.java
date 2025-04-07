package lib.src.LLparserUtil;

import java.util.*;

public class Parser {

    private List<Token> tokens;
    private int currentIndex;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
    }

    public void parse() {
        try {
            program();
            System.out.println("Parsing completed successfully!");
        } catch (Exception e) {
            System.err.println("Parsing failed: " + e.getMessage());
        }
    }

    private void program() {
        match(TokenType.KEYWORD); // "let", "return", etc.
        stmt();
        program();
    }

    private void stmt() {
        Token token = currentToken();
        if (token.getType() == TokenType.KEYWORD) {
            if (token.getValue().equals("let")) {
                declStmt(); // Process declaration statements like `let DATA_TYPE IDENTIFIER;`
            } else if (token.getValue().equals("return")) {
                returnStmt(); // Process return statements
            }
            // Add other statements (e.g., IO statements, STOP statements)
        } else {
            throw new RuntimeException("Invalid statement: " + token.getValue());
        }
    }

    private void declStmt() {
        match(TokenType.KEYWORD); // "let"
        match(TokenType.DATA_TYPE); // DATA_TYPE (e.g., int, string, etc.)
        match(TokenType.IDENTIFIER); // IDENTIFIER (variable name)
        match(TokenType.DELIMITER); // ";"
    }

    private void returnStmt() {
        match(TokenType.KEYWORD); // "return"
        match(TokenType.LITERAL); // Literal value (e.g., number)
    }

    private void match(TokenType expected) {
        Token token = currentToken();
        if (token.getType() == expected) {
            currentIndex++;
        } else {
            throw new RuntimeException("Expected " + expected + " but found " + token.getType());
        }
    }

    private Token currentToken() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex);
        } else {
            return new Token(TokenType.EOF, "");
        }
    }
}
