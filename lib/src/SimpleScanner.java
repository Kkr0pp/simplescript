package lib.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static lib.src.tokenutil.TokenUtil.*;
import lib.src.tokenutil.*;

public class SimpleScanner {
    private Scanner scanner;
    private SymbolTable symbolTable;
    private static final Set<String> TOKENS = new HashSet<>();
    private int lineNumber = 1;
    private int columnNumber = 1;
    private StringBuilder unreadBuffer = new StringBuilder(); // For unreading characters

    static {
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
        boolean inString = false;
        boolean inComment = false;
        char stringDelimiter = '\0';

        while (scanner.hasNext() || unreadBuffer.length() > 0) {
            char c = readChar();

            if (c == '\n') {
                lineNumber++;
                columnNumber = 1;
            } else {
                columnNumber++;
            }

            if (c == '|' && !inString) {
                if (scanner.hasNext()) {
                    char nextChar = scanner.next().charAt(0);
                    if (nextChar == '*') {
                        inComment = true;
                    } else {
                        while (scanner.hasNext() && scanner.next().charAt(0) != '\n') {
                        }
                        lineNumber++;
                        columnNumber = 1;
                        continue;
                    }
                }
                continue;
            }

            if (inComment) {
                if (c == '*' && scanner.hasNext() && scanner.next().charAt(0) == '|') {
                    inComment = false;
                }
                continue;
            }

            if (c == '"' || c == '\'') {
                if (!inString) {
                    inString = true;
                    stringDelimiter = c;
                } else if (c == stringDelimiter) {
                    inString = false;
                }
            }

            if (!inString && !inComment && (Character.isWhitespace(c) || isDelimiter(c))) {
                String token = tokenBuilder.toString().trim();
                if (!token.isEmpty()) {
                    Token result = createToken(token);
                    if (result != null) {
                        return result;
                    } else {
                        skipInvalidToken();
                        tokenBuilder.setLength(0);
                        continue;
                    }
                }
                tokenBuilder.setLength(0);

                if (isDelimiter(c)) {
                    return createToken(String.valueOf(c));
                }
            } else {
                tokenBuilder.append(c);
            }
        }

        String token = tokenBuilder.toString().trim();
        if (!token.isEmpty()) {
            Token result = createToken(token);
            if (result != null) {
                return result;
            } else {
                skipInvalidToken();
            }
        }

        return null;
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
                break;
            }
        }
    }

    private Token createToken(String token) {
        TokenType type = getTokenType(token);
        if (type == null) {
            System.err.println("Error: Invalid token '" + token + "' at line " + lineNumber + ", column "
                    + (columnNumber - token.length()));
            return null;
        }

        if (type == TokenType.IDENTIFIER && !symbolTable.contains(token)) {
            symbolTable.add(token, "id");
        }

        return new Token(type, token, lineNumber, columnNumber - token.length() + 1);
    }

    private TokenType getTokenType(String token) {
        if (isOperator(token)) return TokenType.OPERATOR;
        if (isConditional(token)) return TokenType.CONDITIONAL;
        if (isLogical(token)) return TokenType.LOGICAL;
        if (isAssignment(token)) return TokenType.ASSIGNMENT;
        if (isLoop(token)) return TokenType.LOOP;
        if (isPrimitiveType(token)) return TokenType.PRIMITIVE;
        if (isFunction(token)) return TokenType.FUNCTION;
        if (isPunctuation(token)) return TokenType.PUNCTUATION;
        if (isLiteral(token)) return TokenType.LITERAL;
        if (isIdentifier(token, TOKENS)) return TokenType.IDENTIFIER;
        return null;
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
