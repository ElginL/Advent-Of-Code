import java.util.HashMap;

public class Day6 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    private char[] window = new char[4];

    // Part 1
    public int numberOfProcess() {
        String line = fr.nextLine();
        window[0] = line.charAt(0);
        window[1] = line.charAt(1);
        window[2] = line.charAt(2);
        window[3] = line.charAt(3);

        int counter = 4;

        for (int i = 1; i < line.length(); i++) {
            if (!hasDuplicates(window)) {
                return counter;
            }

            window[0] = line.charAt(i);
            window[1] = line.charAt(i + 1);
            window[2] = line.charAt(i + 2);
            window[3] = line.charAt(i + 3);

            counter++;
        }

        // not possible to reach here
        return -1;
    }

    // Part 2
    public int numberOfProcesses() {
        String line = fr.nextLine();
        char[] window = new char[14];
        for (int i = 0; i < 14; i++) {
            window[i] = line.charAt(i);
        }

        int counter = 14;

        for (int i = 1; i < line.length(); i++) {
            if (!hasDuplicates(window)) {
                return counter;
            }

            for (int j = 0; j < 14; j++) {
                window[j] = line.charAt(i + j);
            }

            counter++;
        }

        // not possible to reach here
        return -1;
    }

    public boolean hasDuplicates(char[] arr) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : arr) {
            if (map.containsKey(c)) {
                return true;
            }

            map.put(c, 1);
        }

        return false;
    }

}
