package lib.src.parseutil;

public enum Terminal {
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