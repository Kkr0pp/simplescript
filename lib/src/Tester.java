package lib.src;

import lib.src.parseutil.Parser;
import lib.src.parseutil.ParseTreeVisualizer;
import java.io.FileNotFoundException;

public class Tester {
    public static void main(String[] args) {
        String filePath = "lib/src/source_file_03.simp"; // Input file

        SimpleScanner scanner = null;
        try {
            scanner = new SimpleScanner(filePath);
            Parser parser = new Parser(scanner);

            // After parsing is complete, we visualize the parse tree
            if (parser.getRoot() != null) {
                ParseTreeVisualizer.showParseTree(parser.getRoot()); // Visualize the tree
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } finally {
            if (scanner != null) {
                scanner.closeScanner();
            }
        }
    }
}
