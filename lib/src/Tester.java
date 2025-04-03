package lib.src;

import java.io.FileNotFoundException;

public class Tester {
    public static void main(String[] args) {
        String filePath = "lib/src/valid_tokens.simp"; // Input file

        SimpleScanner scanner = null;
        try {
            scanner = new SimpleScanner(filePath);
            SymbolTable symbolTable = scanner.getSymbolTable();

            System.out.println("Tokens:");
            Token token;
            while ((token = scanner.getNextToken()) != null) {
                System.out.println(token);
            }

            symbolTable.printSymbols();

            // Reset scanner to read tokens again for parsing
            scanner = new SimpleScanner(filePath);

            // Initialize parser and start parsing
            Parser parser = new Parser(scanner);
            System.out.println("\nParsing result:");
            parser.parse();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } finally {
            if (scanner != null) {
                scanner.closeScanner();
            }
        }
    }
}
