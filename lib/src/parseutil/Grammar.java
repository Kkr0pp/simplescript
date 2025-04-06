package lib.src.parseutil;

import java.util.*;

public class Grammar {
    private final Map<ItemType, List<ProductionRule>> rules;

    public Grammar() {
        rules = new HashMap<>();
        defineGrammar();
    }

    private void defineGrammar() {
        // PROGRAM -> SLIST
        addRule(ItemType.PROGRAM, list(ItemType.SLIST));

        // SLIST -> STMNT SEMICOLON SLIST | epsilon
        addRule(ItemType.SLIST, list(ItemType.STMNT, ItemType.SEMICOLON, ItemType.SLIST));
        addRule(ItemType.SLIST, list());

        // STMNT -> ASS | IF | WHILE | REPEAT | FUNCDEC | RETURN | SHOWSTMT | GETSTMT
        addRule(ItemType.STMNT, list(ItemType.ASS));
        addRule(ItemType.STMNT, list(ItemType.IF));
        addRule(ItemType.STMNT, list(ItemType.WHILE));
        addRule(ItemType.STMNT, list(ItemType.REPEAT));
        addRule(ItemType.STMNT, list(ItemType.FUNCDEC));
        addRule(ItemType.STMNT, list(ItemType.RETURN));
        addRule(ItemType.STMNT, list(ItemType.SHOWSTMT));
        addRule(ItemType.STMNT, list(ItemType.GETSTMT));

        // ASS -> LET ID BE EXP
        addRule(ItemType.ASS, list(ItemType.LET, ItemType.ID, ItemType.BE, ItemType.EXP));

        // IF -> CHECK LPAREN EXP RPAREN LBRACE SLIST RBRACE ELSE
        addRule(ItemType.IF, list(ItemType.CHECK, ItemType.LPAREN, ItemType.EXP, ItemType.RPAREN, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE, ItemType.ELSE));

        // ELSE -> OTHERWISE LBRACE SLIST RBRACE | epsilon
        addRule(ItemType.ELSE, list(ItemType.OTHERWISE, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));
        addRule(ItemType.ELSE, list());

        // WHILE -> WHILE LPAREN EXP RPAREN LBRACE SLIST RBRACE
        addRule(ItemType.WHILE, list(ItemType.WHILE, ItemType.LPAREN, ItemType.EXP, ItemType.RPAREN, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));

        // REPEAT -> REPEATID LBRACE SLIST RBRACE
        addRule(ItemType.REPEAT, list(ItemType.REPEATID, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));

        // FUNCDEC -> TASK ID LPAREN PARAM RPAREN LBRACE SLIST RBRACE
        addRule(ItemType.FUNCDEC, list(ItemType.TASK, ItemType.ID, ItemType.LPAREN, ItemType.PARAM, ItemType.RPAREN, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));

        // PARAM -> ID PARAM_PRIME | epsilon
        addRule(ItemType.PARAM, list(ItemType.ID, ItemType.PARAM_PRIME));
        addRule(ItemType.PARAM, list());

        // PARAM_PRIME -> COMMA ID PARAM_PRIME | epsilon
        addRule(ItemType.PARAM_PRIME, list(ItemType.COMMA, ItemType.ID, ItemType.PARAM_PRIME));
        addRule(ItemType.PARAM_PRIME, list());

        // RETURN -> GIVE EXP
        addRule(ItemType.RETURN, list(ItemType.GIVE, ItemType.EXP));

        // SHOWSTMT -> SHOW EXP
        addRule(ItemType.SHOWSTMT, list(ItemType.SHOW, ItemType.EXP));

        // GETSTMT -> GET ID
        addRule(ItemType.GETSTMT, list(ItemType.GET, ItemType.ID));

        // EXP -> LOG EXP_PRIME
        addRule(ItemType.EXP, list(ItemType.LOG, ItemType.EXP_PRIME));

        // EXP_PRIME -> OR LOG EXP_PRIME | epsilon
        addRule(ItemType.EXP_PRIME, list(ItemType.OR, ItemType.LOG, ItemType.EXP_PRIME));
        addRule(ItemType.EXP_PRIME, list());

        // LOG -> REL LOG_PRIME
        addRule(ItemType.LOG, list(ItemType.REL, ItemType.LOG_PRIME));

        // LOG_PRIME -> BOTH REL LOG_PRIME | epsilon
        addRule(ItemType.LOG_PRIME, list(ItemType.BOTH, ItemType.REL, ItemType.LOG_PRIME));
        addRule(ItemType.LOG_PRIME, list());

        // REL -> ADD REL_PRIME
        addRule(ItemType.REL, list(ItemType.ADD, ItemType.REL_PRIME));

        // REL_PRIME -> IS/ISNT/LESS/MORE/LESSEQ/MOREEQ ADD REL_PRIME | epsilon
        for (ItemType op : list(ItemType.IS, ItemType.ISNT, ItemType.LESS, ItemType.MORE, ItemType.LESSEQ, ItemType.MOREEQ)) {
            addRule(ItemType.REL_PRIME, list(op, ItemType.ADD, ItemType.REL_PRIME));
        }
        addRule(ItemType.REL_PRIME, list());

        // ADD -> MULTI ADD_PRIME
        addRule(ItemType.ADD, list(ItemType.MULTI, ItemType.ADD_PRIME));

        // ADD_PRIME -> PLUS/MINUS MULTI ADD_PRIME | epsilon
        for (ItemType op : list(ItemType.PLUS, ItemType.MINUS)) {
            addRule(ItemType.ADD_PRIME, list(op, ItemType.MULTI, ItemType.ADD_PRIME));
        }
        addRule(ItemType.ADD_PRIME, list());

        // MULTI -> UNARY MULTI_PRIME
        addRule(ItemType.MULTI, list(ItemType.UNARY, ItemType.MULTI_PRIME));

        // MULTI_PRIME -> TIMES/OVER/MOD UNARY MULTI_PRIME | epsilon
        for (ItemType op : list(ItemType.TIMES, ItemType.OVER, ItemType.MOD)) {
            addRule(ItemType.MULTI_PRIME, list(op, ItemType.UNARY, ItemType.MULTI_PRIME));
        }
        addRule(ItemType.MULTI_PRIME, list());

        // UNARY -> NOT UNARY | PRIMARY
        addRule(ItemType.UNARY, list(ItemType.NOT, ItemType.UNARY));
        addRule(ItemType.UNARY, list(ItemType.PRIMARY));

        // PRIMARY -> ID | LITERAL | FUNCCALL | ( EXP )
        addRule(ItemType.PRIMARY, list(ItemType.ID));
        addRule(ItemType.PRIMARY, list(ItemType.LITERAL));
        addRule(ItemType.PRIMARY, list(ItemType.FUNCCALL));
        addRule(ItemType.PRIMARY, list(ItemType.LPAREN, ItemType.EXP, ItemType.RPAREN));

        // FUNCCALL -> ID ( ARG )
        addRule(ItemType.FUNCCALL, list(ItemType.ID, ItemType.LPAREN, ItemType.ARG, ItemType.RPAREN));

        // ARG -> EXP ARG_PRIME | epsilon
        addRule(ItemType.ARG, list(ItemType.EXP, ItemType.ARG_PRIME));
        addRule(ItemType.ARG, list());

        // ARG_PRIME -> , EXP ARG_PRIME | epsilon
        addRule(ItemType.ARG_PRIME, list(ItemType.COMMA, ItemType.EXP, ItemType.ARG_PRIME));
        addRule(ItemType.ARG_PRIME, list());

        // LITERAL -> TRUE | FALSE | INT_LITERAL | FLOAT_LITERAL | STRING_LITERAL
        for (ItemType lit : list(ItemType.TRUE, ItemType.FALSE, ItemType.INT_LITERAL, ItemType.FLOAT_LITERAL, ItemType.STRING_LITERAL)) {
            addRule(ItemType.LITERAL, list(lit));
        }

        // TYPE -> TYPE_INT | TYPE_FLOAT | TYPE_STRING | TYPE_BOOL
        for (ItemType t : list(ItemType.TYPE_INT, ItemType.TYPE_FLOAT, ItemType.TYPE_STRING, ItemType.TYPE_BOOL)) {
            addRule(ItemType.TYPE, list(t));
        }
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