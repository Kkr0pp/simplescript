package lib.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimpleScanner {
    private Scanner scanner;

    public SimpleScanner(String filePath) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filePath));
    }

    public void scanTokens() {
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (isToken(word)) {
                System.out.println("TOKEN: " + word);
            } else {
                System.out.println("IDENTIFIER: " + word);
            }
        }
    }

    private boolean isToken(String word) {
        try {
            SimpleTokens.valueOf(word.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    public static void main(String[] args) {
        System.out.print("Input File Path : ");
        Scanner filepath = new Scanner(System.in);
        String filePath = filepath.nextLine();
        if (filePath==null) {
            System.out.println("Usage: java lib.src.SimpleScanner <file-path>");
            return;
        }

        try {
            SimpleScanner simpleScanner = new SimpleScanner(filePath);
            simpleScanner.scanTokens();
            simpleScanner.closeScanner();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }
}
