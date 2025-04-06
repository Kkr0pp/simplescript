package lib.src.parseutil;

import java.util.List;

public class ProductionRule {
    private final NonTerminal lhs;
    private final List<Object> rhs;

    public ProductionRule(NonTerminal lhs, List<Object> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public NonTerminal getLHS() {
        return lhs;
    }

    public List<Object> getRHS() {
        return rhs;
    }

    @Override
    public String toString() {
        return lhs + " → " + rhs;
    }
}
