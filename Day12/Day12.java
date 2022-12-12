import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Day12 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    public int hillClimbingSSSP() {
        int[][] directions = new int[][]{ { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        ArrayList<String> lines = new ArrayList<>();

        int rowCount = 0;
        int colCount = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            lines.add(line);
            rowCount++;
            colCount = line.length();
        }

        Tile[][] map = new Tile[rowCount][colCount];
        int startRow = 0;
        int startCol = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < colCount; j++) {
                char elevation = line.charAt(j);
                if (elevation == 'S') {
                    startRow = i;
                    startCol = j;
                    elevation = 'a';
                }

                map[i][j] = new Tile(elevation, i, j);

                if (elevation == 'E') {
                    map[i][j].elevation = 'z';
                    map[i][j].isEnd = true;
                }
            }
        }

        // From here, map is ready to be used.
        Queue<Tile> bfsQueue = new LinkedList<>();

        map[startRow][startCol].isVisited = true;
        bfsQueue.offer(map[startRow][startCol]);

        while (!bfsQueue.isEmpty()) {
            Tile currentTile = bfsQueue.poll();

            if (currentTile.isEnd) {
                return currentTile.stepsToReachHere;
            }

            // Add neighbours if elevation is OK to go.
            for (int[] direction : directions) {
                int rowMove = direction[0];
                int colMove = direction[1];

                int newRow = rowMove + currentTile.row;
                int newCol = colMove + currentTile.col;

                if (newRow < 0 || newRow >= rowCount || newCol < 0 || newCol >= colCount) {
                    continue;
                }

                Tile neighbourTile = map[newRow][newCol];
                if (neighbourTile.isVisited) {
                    continue;
                }

                if (neighbourTile.elevation - currentTile.elevation <= 1) {
                    neighbourTile.stepsToReachHere = currentTile.stepsToReachHere + 1;
                    neighbourTile.isVisited = true;
                    bfsQueue.offer(neighbourTile);
                }
            }
        }

        // Will not reach here unless there's a bug.
        return 0;
    }

    public int shortestHikingDistance() {
        int[][] directions = new int[][]{ { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        ArrayList<String> lines = new ArrayList<>();

        int rowCount = 0;
        int colCount = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            lines.add(line);
            rowCount++;
            colCount = line.length();
        }

        Tile[][] map = new Tile[rowCount][colCount];
        int startRow = 0;
        int startCol = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < colCount; j++) {
                char elevation = line.charAt(j);

                map[i][j] = new Tile(elevation, i, j);

                if (elevation == 'S' || elevation == 'a') {
                    elevation = 'a';
                    map[i][j].isEnd = true;
                }

                if (elevation == 'E') {
                    map[i][j].elevation = 'z';
                    startRow = i;
                    startCol = j;
                }
            }
        }

        // From here, map is ready to be used.
        Queue<Tile> bfsQueue = new LinkedList<>();

        map[startRow][startCol].isVisited = true;
        bfsQueue.offer(map[startRow][startCol]);

        while (!bfsQueue.isEmpty()) {
            Tile currentTile = bfsQueue.poll();

            if (currentTile.isEnd) {
                return currentTile.stepsToReachHere;
            }

            // Add neighbours if elevation is OK to go.
            for (int[] direction : directions) {
                int rowMove = direction[0];
                int colMove = direction[1];

                int newRow = rowMove + currentTile.row;
                int newCol = colMove + currentTile.col;

                if (newRow < 0 || newRow >= rowCount || newCol < 0 || newCol >= colCount) {
                    continue;
                }

                Tile neighbourTile = map[newRow][newCol];
                if (neighbourTile.isVisited) {
                    continue;
                }

                if (currentTile.elevation - neighbourTile.elevation <= 1) {
                    neighbourTile.stepsToReachHere = currentTile.stepsToReachHere + 1;
                    neighbourTile.isVisited = true;
                    bfsQueue.offer(neighbourTile);
                }
            }
        }

        // Will not reach here unless there's a bug.
        return 0;
    }

    private class Tile {
        private char elevation;
        private int row;
        private int col;
        private boolean isVisited;
        private boolean isEnd;
        private int stepsToReachHere = 0;

        public Tile(char elevation, int row, int col) {
            this.elevation = elevation;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return Character.toString(this.elevation);
        }
    }
}
