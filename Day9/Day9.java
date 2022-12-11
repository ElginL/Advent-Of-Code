import java.util.HashMap;

public class Day9 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    public int tailVisitedOnce() {
        HashMap<Coordinates, Boolean> map = new HashMap<>();
        Coordinates headCoords = new Coordinates(0, 0);
        Coordinates tailCoords = new Coordinates(0, 0);

        map.put(new Coordinates(tailCoords.x, tailCoords.y), true);

        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            char direction = line.charAt(0);
            int magnitude = Integer.parseInt(line.substring(2));

            for (int i = 0; i < magnitude; i++) {
                Coordinates oldHeadCoords = new Coordinates(headCoords.x, headCoords.y);
                move(headCoords, direction);

                if (!isTouching(headCoords, tailCoords)) {
                    tailCoords.x = oldHeadCoords.x;
                    tailCoords.y = oldHeadCoords.y;
                }

                map.put(new Coordinates(tailCoords.x, tailCoords.y), true);
            }
        }

        return map.size();
    }

    public int part2() {
        // Initialization.
        HashMap<Coordinates, Boolean> map = new HashMap<>();
        Coordinates headCoords = new Coordinates(0, 0);
        Coordinates[] tails = new Coordinates[9];
        for (int i = 0; i < tails.length; i++) {
            tails[i] = new Coordinates(0, 0);
        }
        map.put(new Coordinates(tails[8].x, tails[8].y), true);

        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            char direction = line.charAt(0);
            int magnitude = Integer.parseInt(line.substring(2));

            for (int i = 0; i < magnitude; i++) {
                move(headCoords, direction);

                Coordinates next = headCoords;
                for (int j = 0; j < 9; j++) {
                    Coordinates curCoordinates = tails[j];
                    if (!isTouching(next, curCoordinates)) {
                        moveTail(curCoordinates, next);
                    }

                    next = curCoordinates;
                }

                map.put(new Coordinates(tails[8].x, tails[8].y), true);
            }
        }

        System.out.println(map);
        return map.size();
    }

    public void move(Coordinates coordinates, char direction) {
        switch (direction) {
        case 'U':
            coordinates.y += 1;
            break;
        case 'D':
            coordinates.y -= 1;
            break;
        case 'R':
            coordinates.x += 1;
            break;
        case 'L':
            coordinates.x -= 1;
        }
    }

    public boolean isTouching(Coordinates head, Coordinates tail) {
        if (Math.abs(tail.x - head.x) > 1) {
            return false;
        }

        if (Math.abs(tail.y - head.y) > 1) {
            return false;
        }

        return true;
    }

    public void moveTail(Coordinates tail, Coordinates head) {
        Coordinates newPosition = null;
        if (head.x != tail.x && head.y != tail.y) {
            // Move diagonally up right
            if (head.x > tail.x && head.y > tail.y) {
                newPosition = new Coordinates(tail.x + 1, tail.y + 1);
            } else if (head.x > tail.x) {
                newPosition = new Coordinates(tail.x + 1, tail.y - 1);
            } else if (head.y < tail.y) {
                newPosition = new Coordinates(tail.x - 1, tail.y - 1);
            } else {
                newPosition = new Coordinates(tail.x - 1, tail.y + 1);
            }
        }

        // Horizontal Shift
        if (head.y == tail.y) {
            if (head.x > tail.x) {
                newPosition = new Coordinates(tail.x + 1, tail.y);
            } else {
                newPosition = new Coordinates(tail.x - 1, tail.y);
            }
        }

        if (head.x == tail.x) {
            if (head.y > tail.y) {
                newPosition = new Coordinates(tail.x, tail.y + 1);
            } else {
                newPosition = new Coordinates(tail.x, tail.y - 1);
            }
        }

        tail.x = newPosition.x;
        tail.y = newPosition.y;
    }

    private static class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Coordinates) {
                Coordinates other = (Coordinates) o;

                return this.x == other.x && this.y == other.y;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return this.x + this.y;
        }

        @Override
        public String toString() {
            return "[ " + this.x + ", " + this.y + " ]";
        }
    }
}
