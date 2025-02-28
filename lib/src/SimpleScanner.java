package lib.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimpleScanner {
    private Scanner scanner;

    public SimpleScanner(String filePath) throws FileNotFoundException {
        // Throws FileNotFoundException if the file doesn't exist
        this.scanner = new Scanner(new File(filePath));
    }

    public void scanTokens() {
        // Wrap the scanning process in a try-catch to handle any runtime exceptions
        try {
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isToken(word)) {
                    System.out.println("TOKEN: " + word);
                } else {
                    System.out.println("IDENTIFIER: " + word);
                }
            }
        } catch (Exception e) {
            // Catch any unexpected exceptions during scanning (e.g., IO issues)
            System.err.println("Error occurred during scanning: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isToken(String word) {
        try {
            // Check if the word is a valid token by converting it to uppercase and looking it up in the enum
            SimpleTokens.valueOf(word.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            // If the word is not in the enum, it will throw IllegalArgumentException
            return false;
        }
    }

    public void closeScanner() {
        // Safely close the scanner if it's not null
        if (scanner != null) {
            scanner.close();
        }
    }

    public static void main(String[] args) {
        System.out.print("Input File Path : ");
        Scanner inputScanner = new Scanner(System.in);
        String filePath = inputScanner.nextLine();
        
        // Check for an empty file path; if empty, print usage instructions and exit.
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Usage: java lib.src.SimpleScanner <file-path>");
            inputScanner.close();
            return;
        }

        SimpleScanner simpleScanner = null;
        try {
            // Attempt to create a SimpleScanner instance
            simpleScanner = new SimpleScanner(filePath);
            // Process the tokens from the input file
            simpleScanner.scanTokens();
        } catch (FileNotFoundException e) {
            // Handles the specific case where the file is not found
            System.err.println("File not found: " + filePath);
        } catch (Exception e) {
            // Catches any other unexpected exceptions in main
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure that resources are closed, regardless of success or failure
            if (simpleScanner != null) {
                simpleScanner.closeScanner();
            }
            inputScanner.close();
        }
    }
}
