package lib.src.tokenutil;

public enum TokenType {
    IDENTIFIER,
    OPERATOR, //plus, minus, times, over, mod
    CONDITIONAL,// is, isnt, less, more, lesseq, moreeq
    LOGICAL, //and, or, not
    ASSIGNMENT,// be,let
    LOOP, // check,otherwise,while,repeat
    PRIMITIVE, // int, float, string, boolean (TYPES)
    PUNCTUATION, // (, ), {, }, [, ], ;, :, ., ,,
    LITERAL, // int, float, string, boolean (VALUES)
    FUNCTION, //task,give
}