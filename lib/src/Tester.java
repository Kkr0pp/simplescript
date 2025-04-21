package lib.src;

import lib.src.generators.LL1ParsingTableGenerator;
import lib.src.generators.SetTerminals;
import lib.src.parseutil.Parser;
import lib.src.generators.FirstFollowSetGenerator;
import lib.src.tokenutil.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class  Tester {

    public static void main(String[] args) throws IOException {
        // Specify the filename directly
        String filePath = "lib/src/source_file_03.simp";  // Replace this with your desired .simp file path


//        //First and Follow Sets Generation
//        ParsingTableGenerator generator = new ParsingTableGenerator("lib/src/parseutil/grammar.txt");
//        generator.generateTextOutput("firstfollow.txt");

        //Parsing Table

        //Generate First and Follow Sets
        FirstFollowSetGenerator firstFollowSetGenerator = new FirstFollowSetGenerator("lib/src/parseutil/Grammar.txt");
//        Map<String, Set<String>> firstSets = firstFollowSetGenerator.getFirstSets();
//        Map<String, Set<String>> followSets = firstFollowSetGenerator.getFollowSets();
//        Map<String, List<String>> grammar = firstFollowSetGenerator.getGrammar(); // Assuming grammar is also available

//        //Generate LL(1) Parsing Table
//        LL1ParsingTableGenerator ll1ParsingTableGenerator = new LL1ParsingTableGenerator(firstSets, followSets, grammar);
//        ll1ParsingTableGenerator.generateCSVOutput("LL1_parsing_table.csv");
//
//        // Initializing and Declaration
//        SimpleScanner scanner = new SimpleScanner(filePath);
//        SymbolTable symbolTable;
//        Stack<Token> tokens = new Stack<>();
//
//        // Print the tokens for debugging
//        System.out.println("Tokens:");
//        Token token;
//        int counter = 0;
//
//        while ((token = scanner.getNextToken()) != null) {
//            System.out.println(token.toString() + "Token #"+counter++);
//            tokens.add(token);
//        }
//
//        Parser parser = new Parser(tokens);
//
//        SymbolTable symbolTable1 = scanner.getSymbolTable();
//        symbolTable1.printSymbols();



    }
}

