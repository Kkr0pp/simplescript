package lib.src;

import lib.src.LLparserUtil.*;
import java.io.*;
import java.util.*;

public class Tester {

    public static void main(String[] args) throws IOException {
        // Specify the filename directly
        String filePath = "lib/src/source_file_03.simp";  // Replace this with your desired .simp file path

        // Create a Lexer instance to read and tokenize the file
        Lexer lexer = new Lexer(filePath);

        // List to store the tokens
        List<Token> tokens = new ArrayList<>();
        Token token;

        // Tokenize the source code
        while ((token = lexer.nextToken()).getType() != TokenType.EOF) {
            tokens.add(token);
        }

        // Print the tokens for debugging (optional)
        System.out.println("Tokens:");
        for (Token t : tokens) {
            System.out.println(t);
        }

        // Create a Parser instance and parse the tokens
        Parser parser = new Parser(tokens);
        parser.parse();
    }
}

