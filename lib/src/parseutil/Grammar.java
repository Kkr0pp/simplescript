package lib.src.parseutil;

import java.util.*;

public class Grammar {
    private final Map<NonTerminal, List<ProductionRule>> rules;

    public Grammar() {
        rules = new HashMap<>();
        defineGrammar();
    }

    private void defineGrammar() {
        // PROGRAM → SLIST
        addRule(NonTerminal.PROGRAM, list(NonTerminal.SLIST));

        // SLIST → STMNT SLIST | ε
        addRule(NonTerminal.SLIST, list(NonTerminal.STMNT, NonTerminal.SLIST));
        addRule(NonTerminal.SLIST, list());

        // STMNT → VAR | ASS | IF | WHILE | REPEAT | FUNCDEC | FUNCCALL | RETURN |
        // SHOWSTMT | GETSTMNT
        addRule(NonTerminal.STMNT, list(
                NonTerminal.VAR,
                NonTerminal.ASS,
                NonTerminal.IF,
                NonTerminal.WHILE,
                NonTerminal.REPEAT,
                NonTerminal.FUNCDEC,
                NonTerminal.FUNCCALL,
                NonTerminal.RETURN,
                NonTerminal.SHOWSTMNT,
                NonTerminal.GETSTMNT));

        // VAR → let TYPE ASS ;
        addRule(NonTerminal.VAR, list(Terminal.let, NonTerminal.TYPE, NonTerminal.ASS));

        // ASS → ID be EXP
        addRule(NonTerminal.ASS, list(NonTerminal.ID, Terminal.be, NonTerminal.EXP));

        // IF → check ( EXP ) { STMT } ELSEIF ELSE
        addRule(NonTerminal.IF, list(
                Terminal.check,
                Terminal.lparen,
                NonTerminal.EXP,
                Terminal.rparen,
                Terminal.lbrace,
                NonTerminal.STMNT,
                Terminal.rbrace,
                NonTerminal.ELSEIF,
                NonTerminal.ELSE));

        // ELSEIF → orcheck ( EXP ) { STMT } | ε
        addRule(NonTerminal.ELSEIF, list(
                Terminal.orcheck,
                Terminal.lparen,
                NonTerminal.EXP,
                Terminal.rparen,
                Terminal.lbrace,
                NonTerminal.STMNT,
                Terminal.rbrace));
        addRule(NonTerminal.ELSEIF, list());

        // ELSE → otherwise { STMT } | ε
        addRule(NonTerminal.ELSE, list(
                Terminal.otherwise,
                Terminal.lbrace,
                NonTerminal.STMNT,
                Terminal.rbrace));
        addRule(NonTerminal.ELSE, list());

        // WHILE → while ( EXP ) { STMT }
        addRule(NonTerminal.WHILE, list(
                Terminal.while_,
                Terminal.lparen,
                NonTerminal.EXP,
                Terminal.rparen,
                Terminal.lbrace,
                NonTerminal.STMNT,
                Terminal.rbrace));

        // REPEAT → repeat ( VAR ; EXP ; ASS_INLINE ) { STMT }
        addRule(NonTerminal.REPEAT, list(
                Terminal.repeatid,
                Terminal.lparen,
                NonTerminal.VAR,
                Terminal.semicolon,
                NonTerminal.EXP,
                Terminal.semicolon,
                NonTerminal.ASS,
                Terminal.rparen,
                Terminal.lbrace,
                NonTerminal.STMNT,
                Terminal.rbrace));

        // FUNCDEC → task TYPE ID ( PARAM ) { SLIST }
        addRule(NonTerminal.FUNCDEC, list(
                Terminal.task,
                NonTerminal.TYPE,
                NonTerminal.ID,
                Terminal.lparen,
                NonTerminal.PARAM,
                Terminal.rparen,
                Terminal.lbrace,
                NonTerminal.SLIST,
                Terminal.rbrace));

        // ID → LETTER ID'
        addRule(NonTerminal.ID, list(NonTerminal.LETTER, NonTerminal.ID_PRIME));

        // ID' → LETTER ID' | DIGIT ID' | _ ID' | ε
        addRule(NonTerminal.ID_PRIME, list(NonTerminal.LETTER, NonTerminal.ID_PRIME));
        addRule(NonTerminal.ID_PRIME, list(NonTerminal.DIGIT, NonTerminal.ID_PRIME));
        addRule(NonTerminal.ID_PRIME, list(Terminal.id, NonTerminal.ID_PRIME));
        addRule(NonTerminal.ID_PRIME, list());

        // LETTER → a | b | ... | z | A | B | ... | Z | _
        addRule(NonTerminal.LETTER, list(Terminal.id));

        // DIGIT → 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
        addRule(NonTerminal.DIGIT, list(Terminal.type_int));

        // PARAM → TYPE ID PARAM'
        addRule(NonTerminal.PARAM, list(NonTerminal.TYPE, NonTerminal.ID, NonTerminal.PARAM_PRIME));

        // PARAM' → , PARAM | ε
        addRule(NonTerminal.PARAM_PRIME, list(Terminal.comma, NonTerminal.PARAM));
        addRule(NonTerminal.PARAM_PRIME, list());

        // FUNCCALL → ID ( ARG )
        addRule(NonTerminal.FUNCCALL, list(NonTerminal.ID, Terminal.lparen, NonTerminal.ARG, Terminal.rparen));

        // ARG → EXP ARG'
        addRule(NonTerminal.ARG, list(NonTerminal.EXP, NonTerminal.ARG_PRIME));

        // ARG' → , ARG | ε
        addRule(NonTerminal.ARG_PRIME, list(Terminal.comma, NonTerminal.ARG));
        addRule(NonTerminal.ARG_PRIME, list());

        // RETURN → give EXP ;
        addRule(NonTerminal.RETURN, list(Terminal.give, NonTerminal.EXP));

        // SHOWSTMT → show ( EXP ) ;
        addRule(NonTerminal.SHOWSTMNT,
                list(Terminal.show, Terminal.lparen, NonTerminal.EXP, Terminal.rparen, Terminal.semicolon));

        // GETSTMNT → get EXP ;
        addRule(NonTerminal.GETSTMNT, list(Terminal.get, NonTerminal.EXP, Terminal.semicolon));

        // EXP → LOG EXP'
        addRule(NonTerminal.EXP, list(NonTerminal.LOG, NonTerminal.EXP_PRIME));

        // EXP' → or EXP | ε
        addRule(NonTerminal.EXP_PRIME, list(Terminal.or, NonTerminal.EXP));
        addRule(NonTerminal.EXP_PRIME, list());

        // LOG → REL LOG'
        addRule(NonTerminal.LOG, list(NonTerminal.REL, NonTerminal.LOG_PRIME));

        // LOG' → both REL LOG' | or REL LOG' | ε
        addRule(NonTerminal.LOG_PRIME, list(
                Terminal.both,
                NonTerminal.REL,
                NonTerminal.LOG_PRIME));
        addRule(NonTerminal.LOG_PRIME, list(
                Terminal.or,
                NonTerminal.REL,
                NonTerminal.LOG_PRIME));
        addRule(NonTerminal.LOG_PRIME, list());

        // REL → ADD REL_OP ADD
        addRule(NonTerminal.REL, list(
                NonTerminal.ADD,
                NonTerminal.REL_OP,
                NonTerminal.ADD));

        // REL_OP → is | isnt | more | less | lesseq | moreeq
        addRule(NonTerminal.REL_OP, list(Terminal.is));
        addRule(NonTerminal.REL_OP, list(Terminal.isnt));
        addRule(NonTerminal.REL_OP, list(Terminal.more));
        addRule(NonTerminal.REL_OP, list(Terminal.less));
        addRule(NonTerminal.REL_OP, list(Terminal.lesseq));
        addRule(NonTerminal.REL_OP, list(Terminal.moreeq));

        // ADD → MULTI ADD'
        addRule(NonTerminal.ADD, list(NonTerminal.MULTI, NonTerminal.ADD_PRIME));

        // ADD' → ADDOP MULTI ADD' | ε
        addRule(NonTerminal.ADD_PRIME, list(NonTerminal.ADDOP, NonTerminal.MULTI, NonTerminal.ADD_PRIME));
        addRule(NonTerminal.ADD_PRIME, list());

        // ADDOP → plus | minus
        addRule(NonTerminal.ADDOP, list(Terminal.plus));
        addRule(NonTerminal.ADDOP, list(Terminal.minus));

        // MULTI → UNARY MULTI'
        addRule(NonTerminal.MULTI, list(NonTerminal.UNARY, NonTerminal.MULTI_PRIME));

        // MULTI' → MULTIOP UNARY MULTI' | ε
        addRule(NonTerminal.MULTI_PRIME, list(NonTerminal.MULTIOP, NonTerminal.UNARY, NonTerminal.MULTI_PRIME));
        addRule(NonTerminal.MULTI_PRIME, list());

        // MULTIOP → times | over | mod
        addRule(NonTerminal.MULTIOP, list(Terminal.times));
        addRule(NonTerminal.MULTIOP, list(Terminal.over));
        addRule(NonTerminal.MULTIOP, list(Terminal.mod));

        // UNARY → NOT' PRIMARY
        addRule(NonTerminal.UNARY, list(NonTerminal.NOT_PRIME, NonTerminal.PRIMARY));

        // NOT' → not | ε
        addRule(NonTerminal.NOT_PRIME, list(Terminal.not));
        addRule(NonTerminal.NOT_PRIME, list());

        // PRIMARY → LITERAL | ID | FUNCCALL | ( EXP )
        addRule(NonTerminal.PRIMARY, list(NonTerminal.LITERAL));
        addRule(NonTerminal.PRIMARY, list(NonTerminal.ID));
        addRule(NonTerminal.PRIMARY, list(NonTerminal.FUNCCALL));
        addRule(NonTerminal.PRIMARY, list(Terminal.lparen, NonTerminal.EXP, Terminal.rparen));

        // TYPE → int | float | string | bool
        addRule(NonTerminal.TYPE, list(Terminal.type_int));
        addRule(NonTerminal.TYPE, list(Terminal.type_float));
        addRule(NonTerminal.TYPE, list(Terminal.type_string));
        addRule(NonTerminal.TYPE, list(Terminal.type_bool));

        // LITERAL → INT | FLOAT | STRING | BOOL
        addRule(NonTerminal.LITERAL, list(NonTerminal.INT));
        addRule(NonTerminal.LITERAL, list(NonTerminal.FLOAT));
        addRule(NonTerminal.LITERAL, list(NonTerminal.STRING));
        addRule(NonTerminal.LITERAL, list(NonTerminal.BOOL));

        // INT → DIGIT INT | DIGIT
        addRule(NonTerminal.INT, list(NonTerminal.DIGIT, NonTerminal.INT));
        addRule(NonTerminal.INT, list(NonTerminal.DIGIT));

        // DIGIT → 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
        addRule(NonTerminal.DIGIT, list(Terminal.int_literal));

        // FLOAT → INT . INT
        addRule(NonTerminal.FLOAT, list(NonTerminal.INT, Terminal.dot, NonTerminal.INT));

        // STRING → " CHARACTERS " | ' CHARACTERS '
        addRule(NonTerminal.STRING, list(Terminal.squote, NonTerminal.CHARACTERS, Terminal.squote));
        addRule(NonTerminal.STRING, list(Terminal.dquote, NonTerminal.CHARACTERS, Terminal.dquote));

        // CHARACTERS → LETTER CHARACTERS | DIGIT CHARACTERS | CHARACTERS CHARACTERS | ε
        addRule(NonTerminal.CHARACTERS, list(NonTerminal.LETTER, NonTerminal.CHARACTERS));
        addRule(NonTerminal.CHARACTERS, list(NonTerminal.DIGIT, NonTerminal.CHARACTERS));
        addRule(NonTerminal.CHARACTERS, list(NonTerminal.CHARACTERS, NonTerminal.CHARACTERS));
        addRule(NonTerminal.CHARACTERS, list());

        // BOOL → yes | no
        addRule(NonTerminal.BOOL, list(Terminal.yes));
        addRule(NonTerminal.BOOL, list(Terminal.no));

        /*
         * // PROGRAM -> SLIST
         * addRule(NonTerminal.PROGRAM, list(NonTerminal.SLIST));
         * 
         * // SLIST -> STMNT SLIST ; | ε
         * addRule(NonTerminal.SLIST, list(NonTerminal.STMNT, Terminal.semicolon,
         * NonTerminal.SLIST));
         * addRule(NonTerminal.SLIST, list());
         * 
         * // STMT -> VAR | ASS | IF | WHILE | REPEAT | FUNCDEC
         * // | FUNCCALL | RETURN | SHOWSTMT | GETSTMNT
         * addRule(NonTerminal.STMNT, list(NonTerminal.ASS));
         * addRule(NonTerminal.STMNT, list(NonTerminal.IF));
         * addRule(NonTerminal.STMNT, list(NonTerminal.WHILE));
         * addRule(NonTerminal.STMNT, list(NonTerminal.REPEAT));
         * addRule(NonTerminal.STMNT, list(NonTerminal.FUNCDEC));
         * addRule(NonTerminal.STMNT, list(NonTerminal.RETURN));
         * addRule(NonTerminal.STMNT, list(NonTerminal.SHOWSTMT));
         * addRule(NonTerminal.STMNT, list(NonTerminal.GETSTMT));
         * 
         * // VAR -> let TYPE ASS ;
         * addRule(NonTerminal.VAR, list(Terminal.let, NonTerminal.TYPE,
         * NonTerminal.ASS, Terminal.semicolon));
         * // ASS -> ID be EXP
         * addRule(NonTerminal.ASS, list(Terminal.id, Terminal.be, NonTerminal.EXP));
         * 
         * // IF -> check ( EXP ) { STMNT } ELSEIF ELSE
         * addRule(NonTerminal.IF, list(Terminal.check, Terminal.lparen,
         * NonTerminal.EXP, Terminal.rparen, Terminal.lbrace,
         * NonTerminal.STMNT, Terminal.rbrace, NonTerminal.ELSEIF, NonTerminal.ELSE));
         * 
         * // ELSEIF -> orcheck ( EXP ) { STMNT } | e
         * addRule(NonTerminal.ELSEIF, list(
         * Terminal.orcheck, Terminal.lparen, NonTerminal.EXP, Terminal.rparen,
         * Terminal.lbrace, NonTerminal.STMNT, Terminal.rbrace));
         * addRule(NonTerminal.ELSEIF, list());
         * 
         * // ELSE -> otherwise { STMNT } | e
         * addRule(NonTerminal.ELSE, list(Terminal.otherwise, Terminal.lbrace,
         * NonTerminal.STMNT, Terminal.rbrace));
         * addRule(NonTerminal.ELSE, list());
         * 
         * // WHILE -> while ( EXP ) { STMT }
         * addRule(NonTerminal.WHILE, list(Terminal.while_, Terminal.lparen,
         * NonTerminal.EXP, Terminal.rparen,
         * Terminal.lbrace, NonTerminal.STMNT, Terminal.rbrace));
         * 
         * // REPEAT -> repeat ( VAR ; EXP ; ASS ) { STMT }
         * addRule(NonTerminal.REPEAT,
         * list(Terminal.repeatid, Terminal.lparen, NonTerminal.VAR, Terminal.semicolon,
         * NonTerminal.EXP,
         * Terminal.semicolon, NonTerminal.ASS, Terminal.rparen, Terminal.lbrace,
         * NonTerminal.STMNT,
         * Terminal.rbrace));
         * 
         * // FUNCDEC -> task TYPE ID ( PARAM ) { SLIST }
         * addRule(NonTerminal.FUNCDEC, list(Terminal.task, NonTerminal.TYPE,
         * Terminal.id, Terminal.lparen,
         * NonTerminal.PARAM, Terminal.rparen, Terminal.lbrace, NonTerminal.SLIST,
         * Terminal.rbrace));
         * 
         * // PARAM -> TYPE ID PARAM?
         * addRule(NonTerminal.PARAM, list(NonTerminal.TYPE, Terminal.id,
         * NonTerminal.PARAM_PRIME));
         * 
         * // PARAM -> TYPE ID PARAM'
         * addRule(NonTerminal.PARAM_PRIME, list(Terminal.comma, NonTerminal.PARAM));
         * // PARAM’ -> , PARAM | ε
         * addRule(NonTerminal.PARAM_PRIME, list());
         * 
         * addRule(NonTerminal.RETURN, list(ItemType.GIVE, ItemType.EXP));
         * 
         * addRule(NonTerminal.SHOWSTMT, list(ItemType.SHOW, ItemType.EXP));
         * 
         * addRule(NonTerminal.GETSTMT, list(ItemType.GET, ItemType.ID));
         * 
         * addRule(NonTerminal.EXP, list(ItemType.LOG, ItemType.EXP_PRIME));
         * 
         * addRule(NonTerminal.EXP_PRIME, list(ItemType.OR, ItemType.LOG,
         * ItemType.EXP_PRIME));
         * addRule(NonTerminal.EXP_PRIME, list());
         * 
         * addRule(NonTerminal.LOG, list(ItemType.REL, ItemType.LOG_PRIME));
         * 
         * addRule(NonTerminal.LOG_PRIME, list(ItemType.BOTH, ItemType.REL,
         * ItemType.LOG_PRIME));
         * addRule(NonTerminal.LOG_PRIME, list());
         * 
         * addRule(NonTerminal.REL, list(ItemType.ADD, ItemType.REL_PRIME));
         * 
         * for (ItemType op : list(ItemType.IS, ItemType.ISNT, ItemType.LESS,
         * ItemType.MORE, ItemType.LESSEQ,
         * ItemType.MOREEQ)) {
         * addRule(ItemType.REL_PRIME, list(op, ItemType.ADD, ItemType.REL_PRIME));
         * }
         * addRule(ItemType.REL_PRIME, list());
         * 
         * addRule(ItemType.ADD, list(ItemType.MULTI, ItemType.ADD_PRIME));
         * 
         * for (ItemType op : list(ItemType.PLUS, ItemType.MINUS)) {
         * addRule(ItemType.ADD_PRIME, list(op, ItemType.MULTI, ItemType.ADD_PRIME));
         * }
         * addRule(ItemType.ADD_PRIME, list());
         * 
         * addRule(ItemType.MULTI, list(ItemType.UNARY, ItemType.MULTI_PRIME));
         * 
         * for (ItemType op : list(ItemType.TIMES, ItemType.OVER, ItemType.MOD)) {
         * addRule(ItemType.MULTI_PRIME, list(op, ItemType.UNARY,
         * ItemType.MULTI_PRIME));
         * }
         * addRule(ItemType.MULTI_PRIME, list());
         * 
         * addRule(ItemType.UNARY, list(ItemType.NOT, ItemType.UNARY));
         * addRule(ItemType.UNARY, list(ItemType.PRIMARY));
         * 
         * addRule(ItemType.PRIMARY, list(ItemType.ID));
         * addRule(ItemType.PRIMARY, list(ItemType.LITERAL));
         * addRule(ItemType.PRIMARY, list(ItemType.FUNCCALL));
         * addRule(ItemType.PRIMARY, list(ItemType.LPAREN, ItemType.EXP,
         * ItemType.RPAREN));
         * 
         * addRule(ItemType.FUNCCALL, list(ItemType.ID, ItemType.LPAREN, ItemType.ARG,
         * ItemType.RPAREN));
         * 
         * addRule(ItemType.ARG, list(ItemType.EXP, ItemType.ARG_PRIME));
         * addRule(ItemType.ARG, list());
         * 
         * addRule(ItemType.ARG_PRIME, list(ItemType.COMMA, ItemType.EXP,
         * ItemType.ARG_PRIME));
         * addRule(ItemType.ARG_PRIME, list());
         * 
         * for (ItemType lit : list(ItemType.TRUE, ItemType.FALSE, ItemType.INT_LITERAL,
         * ItemType.FLOAT_LITERAL,
         * ItemType.STRING_LITERAL)) {
         * addRule(ItemType.LITERAL, list(lit));
         * }
         * 
         * for (ItemType t : list(ItemType.TYPE_INT, ItemType.TYPE_FLOAT,
         * ItemType.TYPE_STRING, ItemType.TYPE_BOOL)) {
         * addRule(ItemType.TYPE, list(t));
         * }
         */
    }

    private void addRule(NonTerminal lhs, List<Object> rhs) {
        rules.computeIfAbsent(lhs, k -> new ArrayList<>()).add(new ProductionRule(lhs, rhs));
    }

    public List<ProductionRule> getRules(NonTerminal nonTerminal) {
        return rules.getOrDefault(nonTerminal, Collections.emptyList());
    }

    public List<ProductionRule> getAllRules() {
        List<ProductionRule> all = new ArrayList<>();
        for (List<ProductionRule> rlist : rules.values()) {
            all.addAll(rlist);
        }
        return all;
    }

    private List<Object> list(Object... symbols) {
        return Arrays.asList(symbols);
    }
}