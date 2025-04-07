package lib.src.LLparserUtil;

import java.io.*;
import java.util.*;

public class Lexer {

    private BufferedReader reader;
    private String currentLine;
    private int currentIndex;

    // Keywords for the .simp language
    private static final Set<String> keywords = new HashSet<>(Arrays.asList("let", "return", "show", "give", "check", "orcheck", "otherwise", "stop", "task", "if", "for", "while", "void", "is", "isnt", "less", "more", "lesseq", "moreeq", "and", "or", "not"));
    private static final Set<String> dataTypes = new HashSet<>(Arrays.asList("int", "string", "bool", "float")); // Data types
    private static final Set<String> operators = new HashSet<>(Arrays.asList("plus", "minus", "times", "over", "mod", "is", "isnt", "less", "more", "lesseq", "moreeq", "and", "or", "not", "be"));
    private static final Set<String> delimiters = new HashSet<>(Arrays.asList(";", "(", ")", "{", "}", ","));

    public Lexer(String fileName) throws IOException {
        reader = new BufferedReader(new FileReader(fileName));
        currentLine = reader.readLine();
        currentIndex = 0;
    }

    public Token nextToken() throws IOException {
        if (currentLine == null) {
            return new Token(TokenType.EOF, "");
        }

        // Skip white spaces and comments
        while (currentIndex < currentLine.length() && Character.isWhitespace(currentLine.charAt(currentIndex))) {
            currentIndex++;
        }

        if (currentIndex >= currentLine.length()) {
            currentLine = reader.readLine();
            currentIndex = 0;
            if (currentLine == null) {
                return new Token(TokenType.EOF, "");
            }
        }

        // Handle single-line comments
        if (currentLine.charAt(currentIndex) == '|') {
            // Skip until end of line for single-line comment
            currentIndex = currentLine.length();
            return nextToken(); // Skip to the next token
        }

        // Handle multi-line comments
        if (currentIndex + 1 < currentLine.length() && currentLine.substring(currentIndex, currentIndex + 2).equals("|*")) {
            // Skip multi-line comment
            currentIndex += 2;
            while (currentIndex + 1 < currentLine.length() && !currentLine.substring(currentIndex, currentIndex + 2).equals("*|")) {
                currentIndex++;
            }
            currentIndex += 2;  // Skip past "*|"
            return nextToken(); // Skip to the next token
        }

        char currentChar = currentLine.charAt(currentIndex);

        // Handle keywords and data types (e.g., int, string, bool, etc.)
        if (Character.isLetter(currentChar)) {
            StringBuilder sb = new StringBuilder();
            while (currentIndex < currentLine.length() && (Character.isLetterOrDigit(currentLine.charAt(currentIndex)))) {
                sb.append(currentLine.charAt(currentIndex));
                currentIndex++;
            }
            String value = sb.toString();
            if (keywords.contains(value)) {
                return new Token(TokenType.KEYWORD, value);
            } else if (dataTypes.contains(value)) {
                return new Token(TokenType.DATA_TYPE, value); // Recognize data types
            } else {
                return new Token(TokenType.IDENTIFIER, value); // This is a variable name (identifier)
            }
        }

        // Handle numbers (literals)
        if (Character.isDigit(currentChar)) {
            StringBuilder sb = new StringBuilder();
            while (currentIndex < currentLine.length() && Character.isDigit(currentLine.charAt(currentIndex))) {
                sb.append(currentLine.charAt(currentIndex));
                currentIndex++;
            }
            return new Token(TokenType.LITERAL, sb.toString());
        }

        // Handle operators and delimiters
        if (operators.contains(String.valueOf(currentChar))) {
            StringBuilder sb = new StringBuilder();
            while (currentIndex < currentLine.length() && Character.isLetter(currentLine.charAt(currentIndex))) {
                sb.append(currentLine.charAt(currentIndex));
                currentIndex++;
            }
            return new Token(TokenType.OPERATOR, sb.toString());
        }

        if (delimiters.contains(String.valueOf(currentChar))) {
            currentIndex++;
            return new Token(TokenType.DELIMITER, String.valueOf(currentChar));
        }

        // If no valid token found
        return new Token(TokenType.ERROR, String.valueOf(currentChar));
    }
}

