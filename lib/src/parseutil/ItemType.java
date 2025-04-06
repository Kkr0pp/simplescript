package lib.src.parseutil;

public enum ItemType {
    //NON TERMINAL
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

    //TERMINAL
    let,
    be,
    semicolon, // ;
    check,
    lparen, // (
    rparen, // )
    lbrace, // {
    rbrace, // }
    orcheck,
    otherwise,
    while_,
    repeatid,
    task,
    comma, // ,
    give,
    show,
    get,
    or,
    both,
    plus,
    minus,
    times,
    over,
    mod,
    not,
    is,
    isnt,
    less,
    more,
    lesseq,
    moreeq,
    type_int, // int
    type_float, // float
    type_string, // string
    type_bool, // bool
    yes,
    no,
    squote, // '
    dquote, // "
    dot, // .
    id, // [a-zA-Z][a-zA-Z0-9]*
    int_literal, // [0-9]+
    float_literal, // [0-9]+ "." [0-9]+
    string_literal,
    bool_literal,
    dollar // ".*" -> .[a-zA-Z][a-zA-Z0-9]*

}
