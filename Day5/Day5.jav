import java.util.ArrayList;
import java.util.Stack;

public class Day5 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    private ArrayList<Stack<Character>> stacks = new ArrayList<>();

    public String topStack() {
        // Initial initialization.
        String firstLine = fr.nextLine();
        int stackCount = (firstLine.length() + 1) / 4;

        for (int i = 0; i < stackCount; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = 0; i < firstLine.length(); i += 4) {
            if (firstLine.charAt(i) == '[') {
                Stack<Character> stack = stacks.get(i / 4);
                stack.push(firstLine.charAt(i + 1));
            }
        }

        // Populating stacks.
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            for (int i = 0; i < line.length(); i += 4) {
                if (line.charAt(i) == '[') {
                    Stack<Character> stack = stacks.get(i / 4);
                    stack.push(line.charAt(i + 1));
                }
            }

            if (line.equals("")) {
                break;
            }
        }

        // Reverse stack
        for (Stack<Character> s : stacks) {
            char[] tmp = new char[s.size()];
            int counter = 0;
            while (!s.isEmpty()) {
                tmp[counter] = s.pop();
                counter += 1;
            }

            for (int i = 0; i < tmp.length; i++) {
                s.push(tmp[i]);
            }
        }

        // Moving blocks
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            String[] splittedLine = line.split(" from ");
            int numberOfBlocksToMove = Integer.parseInt(splittedLine[0].substring(5));
            int startIndex = Integer.parseInt(splittedLine[1].split(" to ")[0]);
            int endIndex = Integer.parseInt(splittedLine[1].split(" to ")[1]);

            Stack<Character> s1 = stacks.get(startIndex - 1);
            Stack<Character> s2 = stacks.get(endIndex - 1);

            for (int i = 0; i < numberOfBlocksToMove; i++) {
                char c = s1.pop();
                s2.push(c);
            }
        }

        StringBuilder ans = new StringBuilder();
        for (Stack<Character> s : stacks) {
            ans.append(s.pop());
        }

        return ans.toString();
    }

    public String part2() {
        // Initial initialization.
        String firstLine = fr.nextLine();
        int stackCount = (firstLine.length() + 1) / 4;

        for (int i = 0; i < stackCount; i++) {
            stacks.add(new Stack<>());
        }

        for (int i = 0; i < firstLine.length(); i += 4) {
            if (firstLine.charAt(i) == '[') {
                Stack<Character> stack = stacks.get(i / 4);
                stack.push(firstLine.charAt(i + 1));
            }
        }

        // Populating stacks.
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            for (int i = 0; i < line.length(); i += 4) {
                if (line.charAt(i) == '[') {
                    Stack<Character> stack = stacks.get(i / 4);
                    stack.push(line.charAt(i + 1));
                }
            }

            if (line.equals("")) {
                break;
            }
        }

        // Reverse stack
        for (Stack<Character> s : stacks) {
            char[] tmp = new char[s.size()];
            int counter = 0;
            while (!s.isEmpty()) {
                tmp[counter] = s.pop();
                counter += 1;
            }

            for (int i = 0; i < tmp.length; i++) {
                s.push(tmp[i]);
            }
        }

        // Moving blocks
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            String[] splittedLine = line.split(" from ");
            int numberOfBlocksToMove = Integer.parseInt(splittedLine[0].substring(5));
            int startIndex = Integer.parseInt(splittedLine[1].split(" to ")[0]);
            int endIndex = Integer.parseInt(splittedLine[1].split(" to ")[1]);

            Stack<Character> s1 = stacks.get(startIndex - 1);
            char[] tmp = new char[numberOfBlocksToMove];
            for (int i = 0; i < numberOfBlocksToMove; i++) {
                tmp[i] = s1.pop();
            }

            Stack<Character> s2 = stacks.get(endIndex - 1);

            for (int i = tmp.length - 1; i >= 0; i--) {
                s2.push(tmp[i]);
            }
        }

        StringBuilder ans = new StringBuilder();
        for (Stack<Character> s : stacks) {
            if (!s.isEmpty()) {
                ans.append(s.pop());
            }
        }

        return ans.toString();
    }
}
