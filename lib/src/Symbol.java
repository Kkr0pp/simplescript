package lib.src;

public class Symbol {
    private String name;

    public Symbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Symbol: " + name;
    }
}
