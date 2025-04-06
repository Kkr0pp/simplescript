package lib.src.parseutil;

import lib.src.tokenutil.Token;

public class Items {
    private Token token;
    private Terminal terminal;
    private NonTerminal nonTerminal;;
    private boolean terminalStatus;

    public Items(Token token) {
        this.token = token;
    }

    public ItemType getType() {
        switch (token.getType()) {


            case FUNCTION:
                if (token.getLexeme().equals("task"))
                    return ItemType.task;
                if (token.getLexeme().equals("give"))
                    return ItemType.give;
                break;

            case ASSIGNMENT:
                if (token.getLexeme().equals("let"))
                    return ItemType.let;
                if (token.getLexeme().equals("be"))
                    return ItemType.be;
                break;

            case LOOP:
                switch (token.getLexeme()) {
                    case "check":
                        return ItemType.check;
                    case "orcheck":
                        return ItemType.orcheck;
                    case "otherwise":
                        return ItemType.otherwise;
                    case "while":
                        return ItemType.while_;
                    case "repeat":
                        return ItemType.repeatid;
                }
                break;

            case PRIMITIVE:
                switch (token.getLexeme()) {
                    case "int":
                        return ItemType.type_int;
                    case "float":
                        return ItemType.type_float;
                    case "string":
                        return ItemType.type_string;
                    case "bool":
                        return ItemType.type_bool;
                }
                break;

            case LITERAL:
                if (token.getLexeme().equals("yes"))
                    return ItemType.yes;
                if (token.getLexeme().equals("no"))
                    return ItemType.yes;
                if (token.getLexeme().matches("\\d+"))
                    return ItemType.int_literal;
                if (token.getLexeme().matches("\\d+\\.\\d+"))
                    return ItemType.float_literal;
                if (token.getLexeme().matches("\".*\""))
                    return ItemType.string_literal;
                break;

            case IDENTIFIER:
                return ItemType.id;

            case OPERATOR:
                switch (token.getLexeme()) {
                    case "plus":
                        return ItemType.plus;
                    case "minus":
                        return ItemType.minus;
                    case "times":
                        return ItemType.times;
                    case "over":
                        return ItemType.over;
                    case "mod":
                        return ItemType.mod;
                }
                break;

            case LOGICAL:
                switch (token.getLexeme()) {
                    case "both":
                        return ItemType.both;
                    case "or":
                        return ItemType.or;
                    case "not":
                        return ItemType.not;
                }
                break;

            case CONDITIONAL:
                switch (token.getLexeme()) {
                    case "is":
                        return ItemType.is;
                    case "isnt":
                        return ItemType.isnt;
                    case "less":
                        return ItemType.less;
                    case "more":
                        return ItemType.more;
                    case "lesseq":
                        return ItemType.lesseq;
                    case "moreeq":
                        return ItemType.moreeq;
                }
                break;

            case PUNCTUATION:
                switch (token.getLexeme()) {
                    case "(":
                        return ItemType.lparen;
                    case ")":
                        return ItemType.rparen;
                    case "{":
                        return ItemType.lbrace;
                    case "}":
                        return ItemType.rbrace;
                    case ";":
                        return ItemType.semicolon;
                    case ",":
                        return ItemType.comma;
                    case ".":
                        return ItemType.dot;
                }
                break;
        }

        return null; // Or throw an error if unexpected token
    }

    public void setTerminalStatus(boolean terminalStatus)
    {
        this.terminalStatus = terminalStatus;
    }

   public boolean isTerminal()
   {
       return terminalStatus;
   }
}
