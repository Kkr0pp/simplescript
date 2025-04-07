package lib.src.LLparserUtil;

import java.util.*;
import java.util.regex.*;

class Token {
    String type;
    String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + type + ": " + value + "]";
    }
}

class Tokenizer {
    private String input;
    private int currentPos;
    private List<Token> tokens;

    private static final String TOKEN_REGEX =
            "(let|show|give|return|check|otherwise|yes|no|task|if|else|plus|minus|times|over|mod|is|isnt|less|more|lesseq|moreeq|and|or|not)" + // keywords
                    "|([a-zA-Z_][a-zA-Z0-9_]*)" + // Identifiers
                    "|(\\d+\\.\\d+)" + // Floats
                    "|(\\d+)" + // Integers
                    "|(\"[^\"]*\")" + // String literals
                    "|([(){};,=+\\-*/%<>&|])"; // Punctuation and operators

    public Tokenizer(String input) {
        this.input = input;
        this.currentPos = 0;
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        Pattern pattern = Pattern.compile(TOKEN_REGEX);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                tokens.add(new Token("KEYWORD", matcher.group(1)));
            } else if (matcher.group(2) != null) {
                tokens.add(new Token("IDENTIFIER", matcher.group(2)));
            } else if (matcher.group(3) != null) {
                tokens.add(new Token("FLOAT", matcher.group(3)));
            } else if (matcher.group(4) != null) {
                tokens.add(new Token("INT", matcher.group(4)));
            } else if (matcher.group(5) != null) {
                tokens.add(new Token("STRING", matcher.group(5)));
            } else if (matcher.group(6) != null) {
                tokens.add(new Token("PUNCTUATION", matcher.group(6)));
            }
        }
        return tokens;
    }
}
