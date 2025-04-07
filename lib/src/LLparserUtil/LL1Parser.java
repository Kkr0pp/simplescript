package lib.src.LLparserUtil;

import java.io.*;
import java.util.*;
import com.opencsv.CSVWriter;

public class LL1Parser {

    // Grammar representation: non-terminal -> list of production rules
    static Map<String, List<List<String>>> grammar = new HashMap<>();
    // First sets: non-terminal -> First set
    static Map<String, Set<String>> firstSets = new HashMap<>();
    // Follow sets: non-terminal -> Follow set
    static Map<String, Set<String>> followSets = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // Read grammar from the text file
        readGrammarFromFile("lib/src/LLparserUtil/grammar.txt");

        // Compute First sets
        computeFirstSets();

        // Compute Follow sets
        computeFollowSets();

        // Generate CSV output for First and Follow sets
        generateFirstFollowCSV();

        // Construct the LL(1) Parsing table using the ParsingTableGenerator class
        ParsingTableGenerator.constructParsingTable(grammar, firstSets, followSets);

        // Generate CSV output for Parsing Table
        generateParsingTableCSV();
    }

    // Read grammar from the text file
    public static void readGrammarFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            // Skip empty lines and comments
            if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                continue;
            }

            // Split the line into non-terminal and its production rules
            String[] parts = line.split("->");
            String nonTerminal = parts[0].trim();
            String[] rules = parts[1].split("\\|");

            // Add production rules for the non-terminal
            List<List<String>> productionList = new ArrayList<>();
            for (String rule : rules) {
                List<String> production = Arrays.asList(rule.trim().split(" "));
                productionList.add(production);
            }

            grammar.put(nonTerminal, productionList);
        }

        reader.close();
    }

    // Compute First sets
    public static void computeFirstSets() {
        // Initialize First sets for each non-terminal
        for (String nonTerminal : grammar.keySet()) {
            firstSets.put(nonTerminal, new HashSet<>());
        }

        // Keep track of changes in First sets
        boolean changed = true;
        while (changed) {
            changed = false;
            for (String nonTerminal : grammar.keySet()) {
                for (List<String> production : grammar.get(nonTerminal)) {
                    for (String symbol : production) {
                        if (!grammar.containsKey(symbol)) {
                            // Terminal symbol (add to First set)
                            if (firstSets.get(nonTerminal).add(symbol)) {
                                changed = true;
                            }
                            break;
                        } else {
                            // Non-terminal symbol (add its First set)
                            Set<String> firstOfSymbol = firstSets.get(symbol);
                            int oldSize = firstSets.get(nonTerminal).size();
                            firstSets.get(nonTerminal).addAll(firstOfSymbol);

                            // If the non-terminal produces ε, continue to the next symbol
                            if (!firstOfSymbol.contains("ε")) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    // Compute Follow sets
    public static void computeFollowSets() {
        // Initialize Follow sets for each non-terminal
        for (String nonTerminal : grammar.keySet()) {
            followSets.put(nonTerminal, new HashSet<>());
        }
        // Follow(START) = {$}
        followSets.get("START").add("$");

        // Keep track of changes in Follow sets
        boolean changed = true;
        while (changed) {
            changed = false;
            for (String nonTerminal : grammar.keySet()) {
                for (List<String> production : grammar.get(nonTerminal)) {
                    for (int i = 0; i < production.size(); i++) {
                        String symbol = production.get(i);
                        if (grammar.containsKey(symbol)) {
                            // If symbol is a non-terminal
                            if (i + 1 < production.size()) {
                                String nextSymbol = production.get(i + 1);
                                if (!grammar.containsKey(nextSymbol)) {
                                    // If next symbol is a terminal, add it to Follow(symbol)
                                    if (followSets.get(symbol).add(nextSymbol)) {
                                        changed = true;
                                    }
                                } else {
                                    // If next symbol is a non-terminal, add its First set to Follow(symbol)
                                    Set<String> firstOfNext = firstSets.get(nextSymbol);
                                    if (followSets.get(symbol).addAll(firstOfNext)) {
                                        changed = true;
                                    }
                                    // If next symbol can derive ε, add Follow(nonTerminal) to Follow(symbol)
                                    if (firstOfNext.contains("ε")) {
                                        if (followSets.get(symbol).addAll(followSets.get(nonTerminal))) {
                                            changed = true;
                                        }
                                    }
                                }
                            } else {
                                // If we are at the end of a production, add Follow(nonTerminal) to Follow(symbol)
                                if (followSets.get(symbol).addAll(followSets.get(nonTerminal))) {
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Generate CSV for First and Follow sets
    public static void generateFirstFollowCSV() throws IOException {
        FileWriter fileWriter = new FileWriter("first_follow_sets.csv");
        CSVWriter csvWriter = new CSVWriter(fileWriter);

        // Write header
        csvWriter.writeNext(new String[] {"Non-Terminal", "First Set", "Follow Set"});

        // Write First and Follow sets for each non-terminal
        for (String nonTerminal : grammar.keySet()) {
            String firstSet = firstSets.get(nonTerminal).toString();
            String followSet = followSets.get(nonTerminal).toString();
            csvWriter.writeNext(new String[] {nonTerminal, firstSet, followSet});
        }

        csvWriter.close();
    }

    // Generate CSV for Parsing Table
    public static void generateParsingTableCSV() throws IOException {
        FileWriter fileWriter = new FileWriter("parsing_table.csv");
        CSVWriter csvWriter = new CSVWriter(fileWriter);

        // Get terminals for columns
        Set<String> terminals = new TreeSet<>();
        for (String nonTerminal : grammar.keySet()) {
            for (List<String> production : grammar.get(nonTerminal)) {
                for (String symbol : production) {
                    if (!grammar.containsKey(symbol) && !symbol.equals("ε")) {
                        terminals.add(symbol);
                    }
                }
            }
        }
        terminals.add("$"); // Add the end-of-input symbol

        // Write header row (terminals)
        List<String> header = new ArrayList<>(terminals);
        header.add(0, "Non-Terminal"); // Non-Terminal column
        csvWriter.writeNext(header.toArray(new String[0]));

        // Write rows for non-terminals
        for (String nonTerminal : grammar.keySet()) {
            List<String> row = new ArrayList<>();
            row.add(nonTerminal);  // Non-Terminal column

            for (String terminal : terminals) {
                String entry = ParsingTableGenerator.parsingTable.get(nonTerminal).getOrDefault(terminal, "");
                row.add(entry);
            }
            csvWriter.writeNext(row.toArray(new String[0]));
        }

        csvWriter.close();
    }
}
