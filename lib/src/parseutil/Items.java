package lib.src.parseutil;
import lib.src.tokenutil.Token;

public class Items {
    private Token token;
    private ItemType ItemType;

    public Items(Token token) {
        this.token = token;
    }

    public ItemType getType() {
        switch (token.getType()) {

            case FUNCTION:
                if (token.getLexeme().equals("task")) return ItemType.TASK;
                if (token.getLexeme().equals("give")) return ItemType.GIVE;
                break;

            case ASSIGNMENT:
                if (token.getLexeme().equals("let")) return ItemType.LET;
                if (token.getLexeme().equals("be")) return ItemType.BE;
                break;

            case LOOP:
                switch (token.getLexeme()) {
                    case "check": return ItemType.CHECK;
                    case "otherwise": return ItemType.OTHERWISE;
                    case "while": return ItemType.WHILE;
                    case "repeat": return ItemType.REPEATID;
                }
                break;

            case PRIMITIVE:
                switch (token.getLexeme()) {
                    case "int": return ItemType.TYPE_INT;
                    case "float": return ItemType.TYPE_FLOAT;
                    case "string": return ItemType.TYPE_STRING;
                    case "bool": return ItemType.TYPE_BOOL;
                }
                break;

            case LITERAL:
                if (token.getLexeme().equals("yes")) return ItemType.TRUE;
                if (token.getLexeme().equals("no")) return ItemType.FALSE;
                if (token.getLexeme().matches("\\d+")) return ItemType.INT_LITERAL;
                if (token.getLexeme().matches("\\d+\\.\\d+")) return ItemType.FLOAT_LITERAL;
                if (token.getLexeme().matches("\".*\"")) return ItemType.STRING_LITERAL;
                break;

            case IDENTIFIER:
                return ItemType.ID;

            case OPERATOR:
                switch (token.getLexeme()) {
                    case "plus": return ItemType.PLUS;
                    case "minus": return ItemType.MINUS;
                    case "times": return ItemType.TIMES;
                    case "over": return ItemType.OVER;
                    case "mod": return ItemType.MOD;
                }
                break;

            case LOGICAL:
                switch (token.getLexeme()) {
                    case "both": return ItemType.BOTH;
                    case "or": return ItemType.OR;
                    case "not": return ItemType.NOT;
                }
                break;

            case CONDITIONAL:
                switch (token.getLexeme()) {
                    case "is": return ItemType.IS;
                    case "isnt": return ItemType.ISNT;
                    case "less": return ItemType.LESS;
                    case "more": return ItemType.MORE;
                    case "lesseq": return ItemType.LESSEQ;
                    case "moreeq": return ItemType.MOREEQ;
                }
                break;

            case PUNCTUATION:
                switch (token.getLexeme()) {
                    case "(": return ItemType.LPAREN;
                    case ")": return ItemType.RPAREN;
                    case "{": return ItemType.LBRACE;
                    case "}": return ItemType.RBRACE;
                    case ";": return ItemType.SEMICOLON;
                    case ",": return ItemType.COMMA;
                    case ".": return ItemType.DOT;
                }
                break;
        }

        return null; // Or throw an error if unexpected token
    }


}

