package lib.src.LLparserUtil;


import java.util.*;
import java.io.*;

class Tester {
    public static void main(String[] args) throws IOException {
        String filePath = "lib/src/source_file_03.simp"; // The input source file path

        StringBuilder sourceCode = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            sourceCode.append(line).append("\n");
        }
        reader.close();

        // Tokenizing the source code
        Tokenizer tokenizer = new Tokenizer(sourceCode.toString());
        List<Token> tokens = tokenizer.tokenize();

        // Print tokens
        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        // Parse the source code
        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parseProgram();

        // Print AST (k-way parse tree)
        System.out.println("\nAST:");
        System.out.println(ast);
    }
}
