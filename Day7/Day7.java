import java.util.ArrayList;
import java.util.HashMap;

public class Day7 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    // Directory : items listing.
    HashMap<String, ArrayList<String>> map = new HashMap<>();

    // Path : Size.
    HashMap<String, Integer> map2 = new HashMap<>();

    public Day7() {
        fillMap();
        totalSize("/");
    }

    public void fillMap() {
        StringBuilder path = new StringBuilder();

        String line = fr.nextLine();
        while (fr.hasNextLine()) {
            // Is a command
            if (line.startsWith("$")) {
                String argument = line.substring(2);

                // shift up one directory
                if (argument.startsWith("cd ..") && !path.toString().equals("/")) {
                    path = new StringBuilder(path.substring(0, path.lastIndexOf("/")));
                }

                if (argument.startsWith("cd ") && !argument.startsWith("cd ..")) {
                    String dir = argument.substring(3);

                    if (!dir.equals("/")) {
                        path = new StringBuilder(path + "/" + dir);
                    }

                    // Skip LS
                    fr.nextLine();

                    ArrayList<String> contents = new ArrayList<>();
                    while (fr.hasNextLine()) {
                        line = fr.nextLine();
                        if (line.startsWith("$ ")) {
                            break;
                        }

                        if (line.startsWith("dir ")) {
                            contents.add("dir " + path + "/" + line.substring(4));
                        } else {
                            contents.add(line);
                        }
                    }

                    if (path.toString().isEmpty()) {
                        map.put("/", contents);
                    } else {
                        map.put(path.toString(), contents);
                    }
                } else {
                    line = fr.nextLine();
                }
            }
        }
    }

    public int totalSize(String directory) {
        ArrayList<String> allContents = map.get(directory);

        int total = 0;
        for (String content : allContents) {
            int contentSize;
            if (content.startsWith("dir")) {
                contentSize = totalSize(content.substring(4));
            } else {
                contentSize = Integer.parseInt(content.split(" ")[0]);
            }

            total += contentSize;
        }

        map2.put(directory, total);
        return total;
    }

    public int part1() {
        int total = 0;
        for (int x : map2.values()) {
            if (x <= 100000) {
                total += x;
            }
        }
        return total;
    }

    public int part2() {
        int totalUsedSpace = map2.get("/");
        int totalUnusedSpace = 70000000 - totalUsedSpace;
        int requiredSpace = 30000000 - totalUnusedSpace;
        int minimum = Integer.MAX_VALUE;

        for (int x : map2.values()) {
            if (x >= requiredSpace && x < minimum) {
                minimum = x;
            }
        }

        return minimum;
    }
}
