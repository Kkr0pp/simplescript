package lib.src.LLparserUtil;

import java.util.ArrayList;
import java.util.List;

class ASTNode {
    String production;
    List<ASTNode> children;

    public ASTNode(String production) {
        this.production = production;
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return production + (children.isEmpty() ? "" : " -> " + children.toString());
    }
}


