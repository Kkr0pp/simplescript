package lib.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SimpleScanner {
    private Scanner scanner;
    private SymbolTable symbolTable;
    private static final Set<String> TOKENS = new HashSet<>();
    private int lineNumber = 1;
    private int columnNumber = 1;
    private StringBuilder unreadBuffer = new StringBuilder(); // For unreading characters

    static {
        // Populate reserved words from SimpleTokens enum
        for (SimpleTokens token : SimpleTokens.values()) {
            TOKENS.add(token.name().toLowerCase());
        }
    }

    public SimpleScanner(String filePath) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filePath));
        this.symbolTable = new SymbolTable();
        this.scanner.useDelimiter("");
    }

    public Token getNextToken() {
        StringBuilder tokenBuilder = new StringBuilder();
        boolean inString = false; // Track if we're inside a string literal
        boolean inComment = false; // Track if we're inside a comment
        char stringDelimiter = '\0'; // Track the type of string delimiter ('"' or '\'')

        while (scanner.hasNext() || unreadBuffer.length() > 0) {
            char c = readChar();

            // Update line and column numbers
            if (c == '\n') {
                lineNumber++;
                columnNumber = 1;
            } else {
                columnNumber++;
            }

            // Handle comments
            if (c == '|' && !inString) {
                if (scanner.hasNext()) {
                    char nextChar = scanner.next().charAt(0);
                    if (nextChar == '*') {
                        inComment = true; // Start of multi-line comment
                    } else {
                        // Single-line comment: skip until end of line
                        while (scanner.hasNext() && scanner.next().charAt(0) != '\n') {
                            // Skip characters in the comment
                        }
                        lineNumber++; // Move to the next line
                        columnNumber = 1; // Reset column number
                        continue; // Resume scanning after the comment
                    }
                }
                continue;
            }

            // Handle multi-line comments
            if (inComment) {
                if (c == '*' && scanner.hasNext() && scanner.next().charAt(0) == '|') {
                    inComment = false; // End of multi-line comment
                }
                continue;
            }

            // Handle string literals
            if (c == '"' || c == '\'') {
                if (!inString) {
                    inString = true;
                    stringDelimiter = c; // Track the type of string delimiter
                } else if (c == stringDelimiter) {
                    inString = false; // End of string literal
                }
            }

            // If not in a string or comment, check for token boundaries
            if (!inString && !inComment && (Character.isWhitespace(c) || isDelimiter(c))) {
                // Handle the current token
                String token = tokenBuilder.toString().trim();
                if (!token.isEmpty()) {
                    Token result = createToken(token);
                    if (result != null) {
                        return result;
                    } else {
                        skipInvalidToken(); // Skip the invalid token
                        tokenBuilder.setLength(0); // Reset the token builder
                        continue; // Continue scanning
                    }
                }
                tokenBuilder.setLength(0); // Reset the token builder

                // Handle delimiters as separate tokens
                if (isDelimiter(c)) {
                    return createToken(String.valueOf(c));
                }
            } else {
                // Append the character to the current token
                tokenBuilder.append(c);
            }
        }

        // Handle the last token
        String token = tokenBuilder.toString().trim();
        if (!token.isEmpty()) {
            Token result = createToken(token);
            if (result != null) {
                return result;
            } else {
                skipInvalidToken(); // Skip the invalid token
            }
        }

        return null; // End of file
    }

    private char readChar() {
        if (unreadBuffer.length() > 0) {
            char c = unreadBuffer.charAt(unreadBuffer.length() - 1);
            unreadBuffer.deleteCharAt(unreadBuffer.length() - 1);
            return c;
        }
        return scanner.next().charAt(0);
    }

    private void skipInvalidToken() {
        while (scanner.hasNext()) {
            char c = scanner.next().charAt(0);
            if (Character.isWhitespace(c) || isDelimiter(c)) {
                break; // Stop skipping at the next whitespace or delimiter
            }
        }
    }

    private Token createToken(String token) {
        TokenType type = null;

        switch (getTokenCategory(token)) {
            case "RESERVED_WORD":
                type = TokenType.RESERVED_WORD;
                token = token.toUpperCase();
                break;
            case "PUNCTUATION":
                type = TokenType.PUNCTUATION;
                break;
            case "CONSTANT":
                type = TokenType.CONSTANT;
                break;
            case "IDENTIFIER":
                if (!symbolTable.contains(token)) {
                    symbolTable.add(token, "id");
                }
                type = TokenType.IDENTIFIER;
                break;
            case "OPERATOR":
                type = TokenType.OPERATOR;
                break;
            default:
                System.err.println("Error: Invalid token '" + token + "' at line " + lineNumber + ", column "
                        + (columnNumber - token.length()));
                return null; // Skip the invalid token
        }
        return new Token(type, token, lineNumber, columnNumber - token.length());
    }

    private boolean isToken(String word) {
        return TOKENS.contains(word.toLowerCase());
    }

    private boolean isPunctuation(String word) {
        return word.matches("[;(),{}]");
    }

    private boolean isConstant(String word) {
        // Match integers, floats, strings, booleans, and characters
        return word.matches("\\d+") || // Integers
                word.matches("[+-]?\\d+\\.\\d+") || // Floats
                word.matches("\"[^\"]*\"") || // Double-quoted strings
                word.matches("'[^']*'") || // Single-quoted strings
                word.matches("yes|no") || // Booleans
                word.matches("'.'"); // Single characters
    }

    private boolean isIdentifier(String word) {
        return word.matches("[a-zA-Z_][a-zA-Z0-9_]*") && !isToken(word);
    }

    private boolean isOperator(String word) {
        return word.matches("plus|minus|times|over|mod|is|isnt|less|more|lesseq|moreeq|and|or|not|\\+");
    }

    private boolean isDelimiter(char c) {
        return c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == ',';
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    private String getTokenCategory(String token) {
        if (isToken(token)) return "RESERVED_WORD";
        if (isPunctuation(token)) return "PUNCTUATION";
        if (isConstant(token)) return "CONSTANT";
        if (isIdentifier(token)) return "IDENTIFIER";
        if (isOperator(token)) return "OPERATOR";
        return "INVALID";
    }
}