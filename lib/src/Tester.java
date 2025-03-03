package lib.src;

import java.io.FileNotFoundException;

public class Tester {
    public static void main(String[] args) {
        String filePath = "lib/src/source_file_02.simp"; // Input file

        SimpleScanner scanner = null;
        try {
            scanner = new SimpleScanner(filePath);
            SymbolTable symbolTable = scanner.getSymbolTable();

            Token token;
            while ((token = scanner.getNextToken()) != null) {
                System.out.println(token);
            }

            symbolTable.printSymbols();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } finally {
            if (scanner != null) {
                scanner.closeScanner();
            }
        }
    }
}