package lib.src.parseutil;

import lib.src.SimpleScanner;
import lib.src.tokenutil.Token;

import java.util.*;

public class Parser {
    SimpleScanner scanner;
    Stack<Items> Items;

    public Parser(SimpleScanner scanner) {
        this.scanner = scanner;
        Token token;

        Items = new Stack<>();
        System.out.println("Parsing...");
        while ((token = scanner.getNextToken()) != null)
        {
                Items.push(new Items(token));
                System.out.println(Items.peek().getType());
        }

    }

    public void parse()
    {

    }
}
