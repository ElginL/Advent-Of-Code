import java.util.HashMap;

public class Day3 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    // Part 1
    public int sumOfPriorities() {
        HashMap<Character, Integer> map = new HashMap<>();

        int total = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            // First compartment
            for (int i = 0; i < line.length() / 2; i++) {
                char c = line.charAt(i);
                int priority = Character.isUpperCase(c)
                        ? c - 'A' + 27
                        : c - 'a' + 1;

                map.put(c, priority);
            }

            // Second compartment
            for (int i = line.length() / 2; i < line.length(); i++) {
                char c = line.charAt(i);

                if (map.containsKey(c)) {
                    total += map.get(c);
                    break;
                }
            }

            map.clear();
        }

        return total;
    }

    // Part 2
    public int sumOfItemTypes() {
        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Boolean> map2 = new HashMap<>();
        int counter = 1;
        int sum = 0;

        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            // Iterate the line.
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (map.containsKey(ch)) {

                    int occurCount = map.get(ch);

                    if (!map2.containsKey(ch) || !map2.get(ch)) {
                        map.put(ch, occurCount + 1);
                        map2.put(ch, true);
                    }

                    if (map.get(ch) == 3) {
                        int priority = Character.isUpperCase(ch)
                                ? ch - 'A' + 27
                                : ch - 'a' + 1;

                        sum += priority;
                        break;
                    }

                } else {
                    map.put(ch, 1);
                    map2.put(ch, true);
                }
            }

            counter += 1;
            map2.clear();
            if (counter > 3) {
                counter = 1;
                map.clear();
            }
        }

        return sum;
    }
}
