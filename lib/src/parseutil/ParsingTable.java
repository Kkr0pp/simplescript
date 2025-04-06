package lib.src.parseutil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParsingTable {
    public enum ActionType { SHIFT, REDUCE, ACCEPT }

    public static class Action {
        private final ActionType type;
        private final int target; // state or rule index

        public Action(ActionType type, int target) {
            this.type = type;
            this.target = target;
        }

        public ActionType getType() {
            return type;
        }

        public int getTarget() {
            return target;
        }

        @Override
        public String toString() {
            return type + (type != ActionType.ACCEPT ? " " + target : "");
        }
    }

    private static class StateSymbolPair {
        int state;
        ItemType symbol;

        StateSymbolPair(int state, ItemType symbol) {
            this.state = state;
            this.symbol = symbol;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof StateSymbolPair)) return false;
            StateSymbolPair other = (StateSymbolPair) o;
            return this.state == other.state && this.symbol == other.symbol;
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, symbol);
        }
    }

    private final Map<StateSymbolPair, Action> actionTable = new HashMap<>();
    private final Map<StateSymbolPair, Integer> gotoTable = new HashMap<>();

    public ParsingTable() {
        buildTable();
    }

    public Action getAction(int state, ItemType terminal) {
        return actionTable.get(new StateSymbolPair(state, terminal));
    }

    public int getGoto(int state, ItemType nonTerminal) {
        return gotoTable.getOrDefault(new StateSymbolPair(state, nonTerminal), -1);
    }

    private void buildTable() {
        // Manually constructed table based on earlier LR(1) setup

        addAction(0, ItemType.LET, new Action(ActionType.SHIFT, 5));
        addGoto(0, ItemType.PROGRAM, 1);
        addGoto(0, ItemType.SLIST, 2);
        addGoto(0, ItemType.STMNT, 3);
        addGoto(0, ItemType.ASS, 4);

        addAction(1, ItemType.DOLLAR, new Action(ActionType.ACCEPT, 0));

        addAction(2, ItemType.DOLLAR, new Action(ActionType.REDUCE, 1));

        addAction(3, ItemType.SEMICOLON, new Action(ActionType.SHIFT, 6));

        addAction(4, ItemType.SEMICOLON, new Action(ActionType.REDUCE, 4));

        addAction(5, ItemType.ID, new Action(ActionType.SHIFT, 7));

        addGoto(6, ItemType.SLIST, 8);
        addGoto(6, ItemType.STMNT, 9);
        addGoto(6, ItemType.ASS, 10);
        addAction(6, ItemType.LET, new Action(ActionType.SHIFT, 5));

        addAction(7, ItemType.BE, new Action(ActionType.SHIFT, 11));

        addAction(8, ItemType.DOLLAR, new Action(ActionType.REDUCE, 2));

        addAction(9, ItemType.SEMICOLON, new Action(ActionType.SHIFT, 6));

        addAction(10, ItemType.SEMICOLON, new Action(ActionType.REDUCE, 4));

        addAction(11, ItemType.ID, new Action(ActionType.SHIFT, 12));

        addAction(12, ItemType.SEMICOLON, new Action(ActionType.REDUCE, 5));
    }

    public void addAction(int state, ItemType symbol, Action action) {
        actionTable.put(new StateSymbolPair(state, symbol), action);
    }

    public void addGoto(int state, ItemType symbol, int newState) {
        gotoTable.put(new StateSymbolPair(state, symbol), newState);
    }
}
