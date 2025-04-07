package lib.src.LLparserUtil;
import java.util.*;

public class ParsingTableGenerator {

    // LL(1) Parsing table: (non-terminal, terminal) -> production
    public static Map<String, Map<String, String>> parsingTable = new HashMap<>();

    // Method to construct the LL(1) Parsing Table
    public static void constructParsingTable(Map<String, List<List<String>>> grammar,
                                             Map<String, Set<String>> firstSets,
                                             Map<String, Set<String>> followSets) {

        // Initialize the parsing table
        for (String nonTerminal : grammar.keySet()) {
            parsingTable.put(nonTerminal, new HashMap<>());
        }

        // Fill the parsing table based on the First and Follow sets
        for (String nonTerminal : grammar.keySet()) {
            for (List<String> production : grammar.get(nonTerminal)) {
                String firstSymbol = production.get(0);

                if (!grammar.containsKey(firstSymbol)) {
                    // If the first symbol is a terminal, we add the production
                    parsingTable.get(nonTerminal).put(firstSymbol, String.join(" ", production));
                } else {
                    // If the first symbol is a non-terminal, use First sets
                    Set<String> firstOfNonTerminal = firstSets.get(firstSymbol);
                    for (String terminal : firstOfNonTerminal) {
                        if (!terminal.equals("ε")) {
                            parsingTable.get(nonTerminal).put(terminal, String.join(" ", production));
                        }
                    }

                    // If the production can derive ε, use Follow sets
                    if (firstOfNonTerminal.contains("ε")) {
                        for (String terminal : followSets.get(nonTerminal)) {
                            parsingTable.get(nonTerminal).put(terminal, String.join(" ", production));
                        }
                    }
                }
            }
        }
    }
}
