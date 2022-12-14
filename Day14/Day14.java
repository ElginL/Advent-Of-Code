import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Day14 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    static HashMap<Obstruction, Boolean> obstructions = new HashMap<>();

    public int unitsOfSand() {
        int mostLeftX = Integer.MAX_VALUE;
        int mostRightX = Integer.MIN_VALUE;

        // Data reading.
        while (fr.hasNextLine()) {
            ArrayList<int[]> tmps = new ArrayList<>();
            String[] results = fr.nextLine().split(" -> ");
            for (String result : results) {
                String[] coordinatesStr = result.split(",");
                int xCoords = Integer.parseInt(coordinatesStr[0]);
                int yCoords = Integer.parseInt(coordinatesStr[1]);

                if (xCoords < mostLeftX) {
                    mostLeftX = xCoords;
                }

                if (xCoords > mostRightX) {
                    mostRightX = xCoords;
                }

                int[] coordinates = new int[]{ xCoords, yCoords };
                tmps.add(coordinates);
            }

            for (int i = 0; i < tmps.size() - 1; i++) {
                int[] setOne = tmps.get(i);
                int[] setTwo = tmps.get(i + 1);

                if (setOne[0] == setTwo[0]) {
                    for (int j = Math.min(setOne[1], setTwo[1]); j <= Math.max(setOne[1], setTwo[1]); j++) {
                        obstructions.put(new Obstruction(setOne[0], j), true);
                    }
                }

                if (setOne[1] == setTwo[1]) {
                    for (int j = Math.min(setOne[0], setTwo[0]); j <= Math.max(setTwo[0], setOne[0]); j++) {
                        obstructions.put(new Obstruction(j, setOne[1]), true);
                    }
                }
            }
        }

        int counter = 0;
        // false when sand drops into abyss, true if successfully dropped and stabilized.
        while (dropSand(mostLeftX, mostRightX)) {
            counter += 1;
        }

        return counter;
    }

    public boolean dropSand(int mostLeftX, int mostRightX) {
        int sandSourceX = 500;
        int sandSourceY = 0;

        Sand sand = new Sand(sandSourceX, sandSourceY);

        return moveSand(sand, mostLeftX, mostRightX);
    }

    // Returns false if the sand is no longer moving (stabilized)
    public boolean moveSand(Sand sand, int mostLeftX, int mostRightX) {
        while (true) {

            if (sand.x < mostLeftX || sand.x > mostRightX) {
                return false;
            }

            if (sand.moveDown()) {
                continue;
            }

            if (sand.moveDownLeft()) {
                continue;
            }

            if (sand.moveDownRight()) {
                continue;
            }

            obstructions.put(new Obstruction(sand.x, sand.y), true);

            return true;
        }
    }

    public int part2() {
        int maxY = Integer.MIN_VALUE;

        // Data reading.
        while (fr.hasNextLine()) {
            ArrayList<int[]> tmps = new ArrayList<>();
            String[] results = fr.nextLine().split(" -> ");
            for (String result : results) {
                String[] coordinatesStr = result.split(",");
                int xCoords = Integer.parseInt(coordinatesStr[0]);
                int yCoords = Integer.parseInt(coordinatesStr[1]);

                if (yCoords > maxY) {
                    maxY = yCoords;
                }

                int[] coordinates = new int[]{ xCoords, yCoords };
                tmps.add(coordinates);
            }

            for (int i = 0; i < tmps.size() - 1; i++) {
                int[] setOne = tmps.get(i);
                int[] setTwo = tmps.get(i + 1);

                if (setOne[0] == setTwo[0]) {
                    for (int j = Math.min(setOne[1], setTwo[1]); j <= Math.max(setOne[1], setTwo[1]); j++) {
                        obstructions.put(new Obstruction(setOne[0], j), true);
                    }
                }

                if (setOne[1] == setTwo[1]) {
                    for (int j = Math.min(setOne[0], setTwo[0]); j <= Math.max(setTwo[0], setOne[0]); j++) {
                        obstructions.put(new Obstruction(j, setOne[1]), true);
                    }
                }
            }
        }

        int floorHeight = maxY + 2;

        int counter = 0;
        while (dropSandPart2(floorHeight)) {
            counter++;
        }

        return counter + 1;
    }

    public boolean dropSandPart2(int floorHeight) {
        int sandSourceX = 500;
        int sandSourceY = 0;

        Sand sand = new Sand(sandSourceX, sandSourceY);

        return moveSandPart2(sand, sandSourceX, sandSourceY, floorHeight);
    }

    public boolean moveSandPart2(Sand sand, int sandSourceX, int sandSourceY, int floorHeight) {
        while (true) {

            if (sand.moveDownPart2(floorHeight)) {
                continue;
            }

            if (sand.moveDownLeftPart2(floorHeight)) {
                continue;
            }

            if (sand.moveDownRightPart2(floorHeight)) {
                continue;
            }

            if (sand.x == sandSourceX && sand.y == sandSourceY) {
                return false;
            }

            obstructions.put(new Obstruction(sand.x, sand.y), true);

            return true;
        }
    }

    private static class Sand {
        int x;
        int y;

        public Sand(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean moveDown() {
            if (obstructions.get(new Obstruction(this.x, this.y + 1)) != null) {
                return false;
            }

            this.y += 1;
            return true;
        }

        public boolean moveDownLeft() {
            if (obstructions.get(new Obstruction(this.x - 1, this.y + 1)) != null) {
                return false;
            }

            this.y += 1;
            this.x -= 1;
            return true;
        }

        public boolean moveDownRight() {
            if (obstructions.get(new Obstruction(this.x + 1, this.y + 1)) != null) {
                return false;
            }

            this.y += 1;
            this.x += 1;
            return true;
        }

        // part 2 move down
        public boolean moveDownPart2(int floorHeight) {
            if (obstructions.get(new Obstruction(this.x, this.y + 1)) != null || this.y + 1 == floorHeight) {
                return false;
            }

            this.y += 1;
            return true;
        }

        public boolean moveDownLeftPart2(int floorHeight) {
            if (obstructions.get(new Obstruction(this.x - 1, this.y + 1)) != null || this.y + 1 == floorHeight) {
                return false;
            }

            this.y += 1;
            this.x -= 1;
            return true;
        }

        public boolean moveDownRightPart2(int floorHeight) {
            if (obstructions.get(new Obstruction(this.x + 1, this.y + 1)) != null || this.y + 1 == floorHeight) {
                return false;
            }

            this.y += 1;
            this.x += 1;
            return true;
        }
    }

    private static class Obstruction {
        int x;
        int y;

        public Obstruction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Obstruction) {
                Obstruction other = (Obstruction) o;

                return other.x == this.x && other.y == this.y;
            }

            return false;
        }

        @Override
        public String toString() {
            return "[" + this.x + ", " + this.y + "]";
        }
    }
}
