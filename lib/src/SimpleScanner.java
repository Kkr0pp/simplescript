package lib.src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimpleScanner {
    private Scanner scanner;

    public SimpleScanner(String filePath) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filePath));
    }

        //dito natin ipapasok yung mga token shits
    public void readFile() {
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java SimpleScanner <file-path>");
            return;
        }
// dito natin itrtry irun yung text file. args yung text path so irun natin to thru terminal
        try {
            SimpleScanner simpleScanner = new SimpleScanner(args[0]);
            simpleScanner.readFile();
            simpleScanner.closeScanner();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + args[0]);
        }
    }
}
