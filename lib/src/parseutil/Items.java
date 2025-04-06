package lib.src.parseutil;

import lib.src.tokenutil.Token;

public class Items {
    private Token token;
    private Terminal terminal;
    private NonTerminal nonTerminal;;

    public Items(Token token) {
        this.token = token;
    }

    public Terminal getType() {
        switch (token.getType()) {

            case FUNCTION:
                if (token.getLexeme().equals("task"))
                    return Terminal.task;
                if (token.getLexeme().equals("give"))
                    return Terminal.give;
                break;

            case ASSIGNMENT:
                if (token.getLexeme().equals("let"))
                    return Terminal.let;
                if (token.getLexeme().equals("be"))
                    return Terminal.be;
                break;

            case LOOP:
                switch (token.getLexeme()) {
                    case "check":
                        return Terminal.check;
                    case "orcheck":
                        return Terminal.orcheck;
                    case "otherwise":
                        return Terminal.otherwise;
                    case "while":
                        return Terminal.while_;
                    case "repeat":
                        return Terminal.repeatid;
                }
                break;

            case PRIMITIVE:
                switch (token.getLexeme()) {
                    case "int":
                        return Terminal.type_int;
                    case "float":
                        return Terminal.type_float;
                    case "string":
                        return Terminal.type_string;
                    case "bool":
                        return Terminal.type_bool;
                }
                break;

            case LITERAL:
                if (token.getLexeme().equals("yes"))
                    return Terminal.yes;
                if (token.getLexeme().equals("no"))
                    return Terminal.yes;
                if (token.getLexeme().matches("\\d+"))
                    return Terminal.int_literal;
                if (token.getLexeme().matches("\\d+\\.\\d+"))
                    return Terminal.float_literal;
                if (token.getLexeme().matches("\".*\""))
                    return Terminal.string_literal;
                break;

            case IDENTIFIER:
                return Terminal.id;

            case OPERATOR:
                switch (token.getLexeme()) {
                    case "plus":
                        return Terminal.plus;
                    case "minus":
                        return Terminal.minus;
                    case "times":
                        return Terminal.times;
                    case "over":
                        return Terminal.over;
                    case "mod":
                        return Terminal.mod;
                }
                break;

            case LOGICAL:
                switch (token.getLexeme()) {
                    case "both":
                        return Terminal.both;
                    case "or":
                        return Terminal.or;
                    case "not":
                        return Terminal.not;
                }
                break;

            case CONDITIONAL:
                switch (token.getLexeme()) {
                    case "is":
                        return Terminal.is;
                    case "isnt":
                        return Terminal.isnt;
                    case "less":
                        return Terminal.less;
                    case "more":
                        return Terminal.more;
                    case "lesseq":
                        return Terminal.lesseq;
                    case "moreeq":
                        return Terminal.moreeq;
                }
                break;

            case PUNCTUATION:
                switch (token.getLexeme()) {
                    case "(":
                        return Terminal.lparen;
                    case ")":
                        return Terminal.rparen;
                    case "{":
                        return Terminal.lbrace;
                    case "}":
                        return Terminal.rbrace;
                    case ";":
                        return Terminal.semicolon;
                    case ",":
                        return Terminal.comma;
                    case ".":
                        return Terminal.dot;
                }
                break;
        }

        return null; // Or throw an error if unexpected token
    }

}
