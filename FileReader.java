import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    File file;
    Scanner sc;

    public FileReader(String filePath) {
        this.file = new File(filePath);

        try {
            this.sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read file, aborting...");
        }
    }

    public boolean hasNextLine() {
        return sc.hasNextLine();
    }

    public String nextLine() {
        return sc.nextLine();
    }

}
