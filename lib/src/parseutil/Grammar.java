package lib.src.parseutil;

import java.util.*;

public class Grammar {
    private final Map<ItemType, List<ProductionRule>> rules;

    public Grammar() {
        rules = new HashMap<>();
        defineGrammar();
    }

    private void defineGrammar() {
        addRule(ItemType.PROGRAM, list(ItemType.SLIST));

        addRule(ItemType.SLIST, list(ItemType.STMNT, ItemType.SEMICOLON, ItemType.SLIST));
        addRule(ItemType.SLIST, list());

        addRule(ItemType.STMNT, list(ItemType.ASS));
        addRule(ItemType.STMNT, list(ItemType.IF));
        addRule(ItemType.STMNT, list(ItemType.WHILE));
        addRule(ItemType.STMNT, list(ItemType.REPEAT));
        addRule(ItemType.STMNT, list(ItemType.FUNCDEC));
        addRule(ItemType.STMNT, list(ItemType.RETURN));
        addRule(ItemType.STMNT, list(ItemType.SHOWSTMT));
        addRule(ItemType.STMNT, list(ItemType.GETSTMT));

        addRule(ItemType.ASS, list(ItemType.LET, ItemType.ID, ItemType.BE, ItemType.EXP));

        addRule(ItemType.IF, list(ItemType.CHECK, ItemType.LPAREN, ItemType.EXP, ItemType.RPAREN, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE, ItemType.ELSE));

        addRule(ItemType.ELSE, list(ItemType.OTHERWISE, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));
        addRule(ItemType.ELSE, list());

        addRule(ItemType.WHILE, list(ItemType.WHILE, ItemType.LPAREN, ItemType.EXP, ItemType.RPAREN, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));

        addRule(ItemType.REPEAT, list(ItemType.REPEATID, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));

        addRule(ItemType.FUNCDEC, list(ItemType.TASK, ItemType.ID, ItemType.LPAREN, ItemType.PARAM, ItemType.RPAREN, ItemType.LBRACE, ItemType.SLIST, ItemType.RBRACE));

        addRule(ItemType.PARAM, list(ItemType.ID, ItemType.PARAM_PRIME));
        addRule(ItemType.PARAM, list());

        addRule(ItemType.PARAM_PRIME, list(ItemType.COMMA, ItemType.ID, ItemType.PARAM_PRIME));
        addRule(ItemType.PARAM_PRIME, list());

        addRule(ItemType.RETURN, list(ItemType.GIVE, ItemType.EXP));

        addRule(ItemType.SHOWSTMT, list(ItemType.SHOW, ItemType.EXP));

        addRule(ItemType.GETSTMT, list(ItemType.GET, ItemType.ID));

        addRule(ItemType.EXP, list(ItemType.LOG, ItemType.EXP_PRIME));

        addRule(ItemType.EXP_PRIME, list(ItemType.OR, ItemType.LOG, ItemType.EXP_PRIME));
        addRule(ItemType.EXP_PRIME, list());

        addRule(ItemType.LOG, list(ItemType.REL, ItemType.LOG_PRIME));

        addRule(ItemType.LOG_PRIME, list(ItemType.BOTH, ItemType.REL, ItemType.LOG_PRIME));
        addRule(ItemType.LOG_PRIME, list());

        addRule(ItemType.REL, list(ItemType.ADD, ItemType.REL_PRIME));

        for (ItemType op : list(ItemType.IS, ItemType.ISNT, ItemType.LESS, ItemType.MORE, ItemType.LESSEQ, ItemType.MOREEQ)) {
            addRule(ItemType.REL_PRIME, list(op, ItemType.ADD, ItemType.REL_PRIME));
        }
        addRule(ItemType.REL_PRIME, list());

        addRule(ItemType.ADD, list(ItemType.MULTI, ItemType.ADD_PRIME));

        for (ItemType op : list(ItemType.PLUS, ItemType.MINUS)) {
            addRule(ItemType.ADD_PRIME, list(op, ItemType.MULTI, ItemType.ADD_PRIME));
        }
        addRule(ItemType.ADD_PRIME, list());

        addRule(ItemType.MULTI, list(ItemType.UNARY, ItemType.MULTI_PRIME));

        for (ItemType op : list(ItemType.TIMES, ItemType.OVER, ItemType.MOD)) {
            addRule(ItemType.MULTI_PRIME, list(op, ItemType.UNARY, ItemType.MULTI_PRIME));
        }
        addRule(ItemType.MULTI_PRIME, list());

        addRule(ItemType.UNARY, list(ItemType.NOT, ItemType.UNARY));
        addRule(ItemType.UNARY, list(ItemType.PRIMARY));

        addRule(ItemType.PRIMARY, list(ItemType.ID));
        addRule(ItemType.PRIMARY, list(ItemType.LITERAL));
        addRule(ItemType.PRIMARY, list(ItemType.FUNCCALL));
        addRule(ItemType.PRIMARY, list(ItemType.LPAREN, ItemType.EXP, ItemType.RPAREN));

        addRule(ItemType.FUNCCALL, list(ItemType.ID, ItemType.LPAREN, ItemType.ARG, ItemType.RPAREN));

        addRule(ItemType.ARG, list(ItemType.EXP, ItemType.ARG_PRIME));
        addRule(ItemType.ARG, list());

        addRule(ItemType.ARG_PRIME, list(ItemType.COMMA, ItemType.EXP, ItemType.ARG_PRIME));
        addRule(ItemType.ARG_PRIME, list());

        for (ItemType lit : list(ItemType.TRUE, ItemType.FALSE, ItemType.INT_LITERAL, ItemType.FLOAT_LITERAL, ItemType.STRING_LITERAL)) {
            addRule(ItemType.LITERAL, list(lit));
        }

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