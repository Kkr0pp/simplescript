package lib.src.LLparserUtil;

import java.util.*;

class Parser {
    private List<Token> tokens;
    private int currentPos;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentPos = 0;
        this.currentToken = tokens.get(currentPos);
    }

    private Token consume() {
        Token token = currentToken;
        currentPos++;
        if (currentPos < tokens.size()) {
            currentToken = tokens.get(currentPos);
        }
        return token;
    }

    private boolean match(String type) {
        if (currentToken != null && currentToken.type.equals(type)) {
            consume();
            return true;
        }
        return false;
    }

    public ASTNode parseProgram() {
        return parseSList();
    }

    private ASTNode parseSList() {
        ASTNode stmt = parseStmt();
        ASTNode sListPrime = parseSListPrime();
        ASTNode node = new ASTNode("SLIST");
        node.addChild(stmt);
        node.addChild(sListPrime);
        return node;
    }

    private ASTNode parseSListPrime() {
        if (currentToken != null && currentToken.type.equals("KEYWORD") && currentToken.value.equals("let")) {
            return parseSList(); // SLIST
        } else {
            return new ASTNode("e"); // epsilon
        }
    }

    private ASTNode parseStmt() {
        if (match("KEYWORD") && (currentToken.value.equals("let") || currentToken.value.equals("show") || currentToken.value.equals("give"))) {
            return parseSimpleStmt(); // Simple statement
        } else if (match("KEYWORD") && currentToken.value.equals("check")) {
            return parseCompoundStmt(); // Compound statement
        } else if (currentToken.type.equals("IDENTIFIER")) {
            // Identifier might begin DECL_STMT or FUNC_DEC
            if (peekNextToken().type.equals("PUNCTUATION") && peekNextToken().value.equals("(")) {
                return parseFuncDec();  // Function declaration
            } else {
                return parseDeclStmt(); // Variable declaration
            }
        }
        throw new RuntimeException("Unexpected token: " + currentToken);
    }
    private Token peekNextToken() {
        if (currentPos + 1 < tokens.size()) {
            return tokens.get(currentPos + 1);
        }
        return null;  // End of tokens
    }
    private ASTNode parseFuncDec() {
        match("KEYWORD"); // task
        match("KEYWORD"); // return type (e.g., int, string)
        match("IDENTIFIER"); // function name (e.g., simple)
        match("PUNCTUATION"); // (

        ASTNode params = parseParams(); // Parse parameters

        match("PUNCTUATION"); // )
        ASTNode funcBody = parseCompoundStmt(); // Function body

        ASTNode node = new ASTNode("FUNC_DEC");
        node.addChild(params);
        node.addChild(funcBody);
        return node;
    }

    private ASTNode parseParams() {
        ASTNode paramsNode = new ASTNode("PARAMS");

        // Parse the first parameter
        ASTNode paramNode = parseParam();
        paramsNode.addChild(paramNode);

        // Check if there are more parameters
        while (match("PUNCTUATION") && currentToken.value.equals(",")) {
            paramNode = parseParam(); // Parse the next parameter
            paramsNode.addChild(paramNode);
        }

        return paramsNode;
    }

    private ASTNode parseParam() {
        // A parameter consists of a data type followed by an identifier
        ASTNode paramNode = new ASTNode("PARAM");

        // Parse the data type
        match("KEYWORD"); // string, int, float, etc.

        // Parse the identifier
        match("IDENTIFIER"); // variable name

        return paramNode;
    }



    private ASTNode parseSimpleStmt() {
        // Parsing for DECL_STMT, ASSIGN_STMT, RETURN_STMT, IO_STMT, LITERAL, STOP_STMT, SKIP_STMT
        if (match("KEYWORD") && currentToken.value.equals("let")) {
            return parseDeclStmt();
        }
        // Add other cases for each SIMPLE_STMT here
        return null;
    }

    private ASTNode parseDeclStmt() {
        match("KEYWORD"); // let
        match("KEYWORD"); // type (string, int, etc.)
        match("IDENTIFIER"); // identifier
        match("PUNCTUATION"); // ;
        return new ASTNode("DECL_STMT");
    }

    private ASTNode parseCompoundStmt() {
        // Implement based on grammar
        return new ASTNode("COMPOUND_STMT");
    }

    // You can continue defining all the other parsing methods based on your grammar
}
