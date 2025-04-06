package lib.src.parseutil;

import java.util.*;

public class Grammar {
    private final Map<ItemType, List<ProductionRule>> rules;

    public Grammar() {
        rules = new HashMap<>();
        defineGrammar();
    }

    private void defineGrammar() {
        // PROGRAM → SLIST
        addRule(ItemType.PROGRAM, list(ItemType.SLIST));

        // SLIST → STMNT SLIST | ε
        addRule(ItemType.SLIST, list(ItemType.STMNT, ItemType.SLIST));
        addRule(ItemType.SLIST, list());

        // STMNT → VAR | ASS | IF | WHILE | REPEAT | FUNCDEC | FUNCCALL | RETURN |
        // SHOWSTMT | GETSTMNT
        addRule(ItemType.STMNT, list(
                ItemType.VAR,
                ItemType.ASS,
                ItemType.IF,
                ItemType.WHILE,
                ItemType.REPEAT,
                ItemType.FUNCDEC,
                ItemType.FUNCCALL,
                ItemType.RETURN,
                ItemType.SHOWSTMNT,
                ItemType.GETSTMNT));

        // VAR → let TYPE ASS ;
        addRule(ItemType.VAR, list(ItemType.let, ItemType.TYPE, ItemType.ASS));

        // ASS → ID be EXP
        addRule(ItemType.ASS, list(ItemType.ID, ItemType.be, ItemType.EXP));

        // IF → check ( EXP ) { STMT } ELSEIF ELSE
        addRule(ItemType.IF, list(
                ItemType.check,
                ItemType.lparen,
                ItemType.EXP,
                ItemType.rparen,
                ItemType.lbrace,
                ItemType.STMNT,
                ItemType.rbrace,
                ItemType.ELSEIF,
                ItemType.ELSE));

        // ELSEIF → orcheck ( EXP ) { STMT } | ε
        addRule(ItemType.ELSEIF, list(
                ItemType.orcheck,
                ItemType.lparen,
                ItemType.EXP,
                ItemType.rparen,
                ItemType.lbrace,
                ItemType.STMNT,
                ItemType.rbrace));
        addRule(ItemType.ELSEIF, list());

        // ELSE → otherwise { STMT } | ε
        addRule(ItemType.ELSE, list(
                ItemType.otherwise,
                ItemType.lbrace,
                ItemType.STMNT,
                ItemType.rbrace));
        addRule(ItemType.ELSE, list());

        // WHILE → while ( EXP ) { STMT }
        addRule(ItemType.WHILE, list(
                ItemType.while_,
                ItemType.lparen,
                ItemType.EXP,
                ItemType.rparen,
                ItemType.lbrace,
                ItemType.STMNT,
                ItemType.rbrace));

        // REPEAT → repeat ( VAR ; EXP ; ASS_INLINE ) { STMT }
        addRule(ItemType.REPEAT, list(
                ItemType.repeatid,
                ItemType.lparen,
                ItemType.VAR,
                ItemType.semicolon,
                ItemType.EXP,
                ItemType.semicolon,
                ItemType.ASS,
                ItemType.rparen,
                ItemType.lbrace,
                ItemType.STMNT,
                ItemType.rbrace));

        // FUNCDEC → task TYPE ID ( PARAM ) { SLIST }
        addRule(ItemType.FUNCDEC, list(
                ItemType.task,
                ItemType.TYPE,
                ItemType.ID,
                ItemType.lparen,
                ItemType.PARAM,
                ItemType.rparen,
                ItemType.lbrace,
                ItemType.SLIST,
                ItemType.rbrace));

        // ID → LETTER ID'
        addRule(ItemType.ID, list(ItemType.LETTER, ItemType.ID_PRIME));

        // ID' → LETTER ID' | DIGIT ID' | _ ID' | ε
        addRule(ItemType.ID_PRIME, list(ItemType.LETTER, ItemType.ID_PRIME));
        addRule(ItemType.ID_PRIME, list(ItemType.DIGIT, ItemType.ID_PRIME));
        addRule(ItemType.ID_PRIME, list(ItemType.id, ItemType.ID_PRIME));
        addRule(ItemType.ID_PRIME, list());

        // LETTER → a | b | ... | z | A | B | ... | Z | _
        addRule(ItemType.LETTER, list(ItemType.id));

        // DIGIT → 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
        addRule(ItemType.DIGIT, list(ItemType.type_int));

        // PARAM → TYPE ID PARAM'
        addRule(ItemType.PARAM, list(ItemType.TYPE, ItemType.ID, ItemType.PARAM_PRIME));

        // PARAM' → , PARAM | ε
        addRule(ItemType.PARAM_PRIME, list(ItemType.comma, ItemType.PARAM));
        addRule(ItemType.PARAM_PRIME, list());

        // FUNCCALL → ID ( ARG )
        addRule(ItemType.FUNCCALL, list(ItemType.ID, ItemType.lparen, ItemType.ARG, ItemType.rparen));

        // ARG → EXP ARG'
        addRule(ItemType.ARG, list(ItemType.EXP, ItemType.ARG_PRIME));

        // ARG' → , ARG | ε
        addRule(ItemType.ARG_PRIME, list(ItemType.comma, ItemType.ARG));
        addRule(ItemType.ARG_PRIME, list());

        // RETURN → give EXP ;
        addRule(ItemType.RETURN, list(ItemType.give, ItemType.EXP));

        // SHOWSTMT → show ( EXP ) ;
        addRule(ItemType.SHOWSTMNT,
                list(ItemType.show, ItemType.lparen, ItemType.EXP, ItemType.rparen, ItemType.semicolon));

        // GETSTMNT → get EXP ;
        addRule(ItemType.GETSTMNT, list(ItemType.get, ItemType.EXP, ItemType.semicolon));

        // EXP → LOG EXP'
        addRule(ItemType.EXP, list(ItemType.LOG, ItemType.EXP_PRIME));

        // EXP' → or EXP | ε
        addRule(ItemType.EXP_PRIME, list(ItemType.or, ItemType.EXP));
        addRule(ItemType.EXP_PRIME, list());

        // LOG → REL LOG'
        addRule(ItemType.LOG, list(ItemType.REL, ItemType.LOG_PRIME));

        // LOG' → both REL LOG' | or REL LOG' | ε
        addRule(ItemType.LOG_PRIME, list(
                ItemType.both,
                ItemType.REL,
                ItemType.LOG_PRIME));
        addRule(ItemType.LOG_PRIME, list(
                ItemType.or,
                ItemType.REL,
                ItemType.LOG_PRIME));
        addRule(ItemType.LOG_PRIME, list());

        // REL → ADD REL_OP ADD
        addRule(ItemType.REL, list(
                ItemType.ADD,
                ItemType.REL_OP,
                ItemType.ADD));

        // REL_OP → is | isnt | more | less | lesseq | moreeq
        addRule(ItemType.REL_OP, list(ItemType.is));
        addRule(ItemType.REL_OP, list(ItemType.isnt));
        addRule(ItemType.REL_OP, list(ItemType.more));
        addRule(ItemType.REL_OP, list(ItemType.less));
        addRule(ItemType.REL_OP, list(ItemType.lesseq));
        addRule(ItemType.REL_OP, list(ItemType.moreeq));

        // ADD → MULTI ADD'
        addRule(ItemType.ADD, list(ItemType.MULTI, ItemType.ADD_PRIME));

        // ADD' → ADDOP MULTI ADD' | ε
        addRule(ItemType.ADD_PRIME, list(ItemType.ADDOP, ItemType.MULTI, ItemType.ADD_PRIME));
        addRule(ItemType.ADD_PRIME, list());

        // ADDOP → plus | minus
        addRule(ItemType.ADDOP, list(ItemType.plus));
        addRule(ItemType.ADDOP, list(ItemType.minus));

        // MULTI → UNARY MULTI'
        addRule(ItemType.MULTI, list(ItemType.UNARY, ItemType.MULTI_PRIME));

        // MULTI' → MULTIOP UNARY MULTI' | ε
        addRule(ItemType.MULTI_PRIME, list(ItemType.MULTIOP, ItemType.UNARY, ItemType.MULTI_PRIME));
        addRule(ItemType.MULTI_PRIME, list());

        // MULTIOP → times | over | mod
        addRule(ItemType.MULTIOP, list(ItemType.times));
        addRule(ItemType.MULTIOP, list(ItemType.over));
        addRule(ItemType.MULTIOP, list(ItemType.mod));

        // UNARY → NOT' PRIMARY
        addRule(ItemType.UNARY, list(ItemType.NOT_PRIME, ItemType.PRIMARY));

        // NOT' → not | ε
        addRule(ItemType.NOT_PRIME, list(ItemType.not));
        addRule(ItemType.NOT_PRIME, list());

        // PRIMARY → LITERAL | ID | FUNCCALL | ( EXP )
        addRule(ItemType.PRIMARY, list(ItemType.LITERAL));
        addRule(ItemType.PRIMARY, list(ItemType.ID));
        addRule(ItemType.PRIMARY, list(ItemType.FUNCCALL));
        addRule(ItemType.PRIMARY, list(ItemType.lparen, ItemType.EXP, ItemType.rparen));

        // TYPE → int | float | string | bool
        addRule(ItemType.TYPE, list(ItemType.type_int));
        addRule(ItemType.TYPE, list(ItemType.type_float));
        addRule(ItemType.TYPE, list(ItemType.type_string));
        addRule(ItemType.TYPE, list(ItemType.type_bool));

        // LITERAL → INT | FLOAT | STRING | BOOL
        addRule(ItemType.LITERAL, list(ItemType.INT));
        addRule(ItemType.LITERAL, list(ItemType.FLOAT));
        addRule(ItemType.LITERAL, list(ItemType.STRING));
        addRule(ItemType.LITERAL, list(ItemType.BOOL));

        // INT → DIGIT INT | DIGIT
        addRule(ItemType.INT, list(ItemType.DIGIT, ItemType.INT));
        addRule(ItemType.INT, list(ItemType.DIGIT));

        // DIGIT → 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
        addRule(ItemType.DIGIT, list(ItemType.int_literal));

        // FLOAT → INT . INT
        addRule(ItemType.FLOAT, list(ItemType.INT, ItemType.dot, ItemType.INT));

        // STRING → " CHARACTERS " | ' CHARACTERS '
        addRule(ItemType.STRING, list(ItemType.squote, ItemType.CHARACTERS, ItemType.squote));
        addRule(ItemType.STRING, list(ItemType.dquote, ItemType.CHARACTERS, ItemType.dquote));

        // CHARACTERS → LETTER CHARACTERS | DIGIT CHARACTERS | CHARACTERS CHARACTERS | ε
        addRule(ItemType.CHARACTERS, list(ItemType.LETTER, ItemType.CHARACTERS));
        addRule(ItemType.CHARACTERS, list(ItemType.DIGIT, ItemType.CHARACTERS));
        addRule(ItemType.CHARACTERS, list(ItemType.CHARACTERS, ItemType.CHARACTERS));
        addRule(ItemType.CHARACTERS, list());

        // BOOL → yes | no
        addRule(ItemType.BOOL, list(ItemType.yes));
        addRule(ItemType.BOOL, list(ItemType.no));
    }

    private void addRule(ItemType lhs, List<ItemType> rhs) {
        rules.computeIfAbsent(lhs, k -> new ArrayList<>()).add(new ProductionRule(lhs, rhs));
    }

    public List<ProductionRule> getRules(ItemType nonTerminal) {
        return rules.getOrDefault(nonTerminal, Collections.emptyList());
    }

    public List<ProductionRule> getAllRules() {
        List<ProductionRule> all = new ArrayList<>();
        for (List<ProductionRule> rlist : rules.values()) {
            all.addAll(rlist);
        }
        return all;
    }

    private List<ItemType> list(ItemType... symbols) {
        return Arrays.asList(symbols);
    }
}
