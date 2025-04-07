package lib.src.LLparserUtil;

public enum TokenType {
    KEYWORD,        // Keywords like 'let', 'return', etc.
    IDENTIFIER,     // Variables like 'x', 'y', etc.
    LITERAL,        // Literal values (numbers, strings, booleans)
    OPERATOR,       // Operators like 'plus', 'minus', etc.
    DELIMITER,      // Delimiters like ';', '(', ')', etc.
    DATA_TYPE,      // Data types like 'int', 'string', 'float', 'bool'
    EOF,            // End of file/input
    ERROR           // Error token for invalid characters
}


