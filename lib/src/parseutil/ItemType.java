package lib.src.parseutil;

public enum ItemType
{

    //NONTERMINALS
    PROGRAM,
    SLIST,
    STMNT,
    VAR,
    ASS,
    IF,
    ELSE,
    WHILE,
    REPEAT,
    FUNCDEC,
    PARAM,
    PARAM_PRIME,
    FUNCCALL,
    ARG,
    ARG_PRIME,
    RETURN,
    SHOWSTMT,
    GETSTMT,
    EXP,
    EXP_PRIME,
    LOG,
    LOG_PRIME,
    REL,
    REL_PRIME,
    ADD,
    ADD_PRIME,
    ADDOP,
    MULTI,
    MULTI_PRIME,
    MULTIOP,
    UNARY,
    PRIMARY,
    TYPE,
    LITERAL,
    INT,
    FLOAT,
    STRING,
    BOOL,
    PRIMITIVE,

    //TERMINALS
    LET,
    BE,
    SEMICOLON,          // ;
    CHECK,
    LPAREN,             // (
    RPAREN,             // )
    LBRACE,             // {
    RBRACE,             // }
    OTHERWISE,
    REPEATID,
    TASK,
    COMMA,              // ,
    GIVE,
    SHOW,
    GET,
    OR,
    BOTH,
    PLUS,
    MINUS,
    TIMES,
    OVER,
    MOD,
    NOT,
    IS,
    ISNT,
    LESS,
    MORE,
    LESSEQ,
    MOREEQ,
    TYPE_INT,           // "INT"
    TYPE_FLOAT,         // "FLOAT"
    TYPE_STRING,        // "STRING"
    TYPE_BOOL,          // "BOOL"
    TRUE,
    FALSE,
    DOT,                // .
    ID,                 // [a-zA-Z][a-zA-Z0-9]*
    INT_LITERAL,        // [0-9]+
    FLOAT_LITERAL,      // [0-9]+ "." [0-9]+
    STRING_LITERAL,
    DOLLAR// ".*" -> .[a-zA-Z][a-zA-Z0-9]*
}
