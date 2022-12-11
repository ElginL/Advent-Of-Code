import java.util.ArrayList;
import java.util.List;

public class Day10 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    public int strengthSum() {
        List<Integer> cycles = new ArrayList<>();

        int val = 1;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            String op = line.split(" ")[0];

            if (op.equals("noop")) {
                cycles.add(val);
            }

            if (op.equals("addx")) {
                int magnitude = Integer.parseInt(line.split(" ")[1]);

                cycles.add(val);
                cycles.add(val);

                val += magnitude;
            }
        }

        return 20 * cycles.get(19) + 60 * cycles.get(59) + 100 * cycles.get(99) + 140 * cycles.get(139)
                + 180 * cycles.get(179) + 220 * cycles.get(219);
    }

    public void printImage() {
        List<Character> cycles = new ArrayList<>();

        int val = 1;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            String op = line.split(" ")[0];

            if (op.equals("noop")) {
                int pixelBeingDrawn = cycles.size() % 40;

                int mid = val;
                int left = val - 1;
                int right = val + 1;

                if (mid == pixelBeingDrawn || left == pixelBeingDrawn || right == pixelBeingDrawn) {
                    cycles.add('#');
                } else {
                    cycles.add('.');
                }
            }

            if (op.equals("addx")) {
                int magnitude = Integer.parseInt(line.split(" ")[1]);

                for (int i = 0; i < 2; i++) {
                    int pixelBeingDrawn = cycles.size() % 40;
                    int mid = val;
                    int left = val - 1;
                    int right = val + 1;

                    if (mid == pixelBeingDrawn || left == pixelBeingDrawn || right == pixelBeingDrawn) {
                        cycles.add('#');
                    } else {
                        cycles.add('.');
                    }
                }

                val += magnitude;
            }
        }

        int counter = 0;
        for (char c : cycles) {
            System.out.print(c);
            counter += 1;
            if (counter == 40) {
                counter = 0;
                System.out.println("");
            }
        }
    }
}
