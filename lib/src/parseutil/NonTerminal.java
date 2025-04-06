package lib.src.parseutil;

public enum NonTerminal {
    PROGRAM,
    SLIST,

    STMNT,

    VAR,

    ASS,

    IF,
    ELSEIF,
    ELSE,

    WHILE,

    REPEAT,

    FUNCDEC,

    ID,
    ID_PRIME,
    LETTER,
    DIGIT,

    PARAM,
    PARAM_PRIME,

    FUNCCALL,
    ARG,
    ARG_PRIME,

    RETURN,

    SHOWSTMNT,
    GETSTMNT,

    EXP,
    EXP_PRIME,

    LOG,
    LOG_PRIME,

    REL,
    REL_OP,

    ADD,
    ADD_PRIME,
    ADDOP,

    MULTI,
    MULTI_PRIME,
    MULTIOP,

    UNARY,
    NOT_PRIME,
    PRIMARY,

    TYPE,
    LITERAL,

    INT,
    FLOAT,
    STRING,
    BOOL,
    PRIMITIVE,

    CHARACTERS,
}