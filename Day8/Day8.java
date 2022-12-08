public class Day8 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    private char[][] trees = new char[99][99];
    private boolean[][] tracked = new boolean[99][99];

    public int visibleTrees() {
        // Initializing trees 2D array.
        int counter = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            for (int i = 0; i < line.length(); i++) {
                trees[counter][i] = line.charAt(i);
            }

            counter++;
        }

        // Summing up the edges.
        int visibleCount = trees.length * 2 + trees[0].length * 2 - 4;

        // Top to bottom view.
        for (int i = 1; i < trees[0].length - 1; i++) {
            int maximumInSight = trees[0][i];
            for (int j = 0; j < trees.length - 2; j++) {
                if (trees[j + 1][i] > maximumInSight) {
                    maximumInSight = trees[j + 1][i];
                    if (!tracked[j + 1][i]) {
                        tracked[j + 1][i] = true;
                        visibleCount += 1;
                    }
                }
            }
        }

        // Bottom to top view.
        for (int i = 1; i < trees[0].length - 1; i++) {
            int maximumInSight = trees[trees.length - 1][i];
            for (int j = trees.length - 1; j > 1; j--) {
                if (trees[j - 1][i] > maximumInSight) {
                    maximumInSight = trees[j - 1][i];
                    if (!tracked[j - 1][i]) {
                        tracked[j - 1][i] = true;
                        visibleCount += 1;
                    }
                }
            }
        }

        // Left to right view.
        for (int i = 1; i < trees.length - 1; i++) {
            int maximumInSight = trees[i][0];
            for (int j = 0; j < trees[0].length - 2; j++) {
                if (trees[i][j + 1] > maximumInSight) {
                    maximumInSight = trees[i][j + 1];
                    if (!tracked[i][j + 1]) {
                        tracked[i][j + 1] = true;
                        visibleCount += 1;
                    }
                }
            }
        }

        // right to left
        for (int i = 1; i < trees.length - 1; i++) {
            int maximumInSight = trees[i][trees[0].length - 1];
            for (int j = trees[0].length - 1; j > 1; j--) {
                if (trees[i][j - 1] > maximumInSight) {
                    maximumInSight = trees[i][j - 1];
                    if (!tracked[i][j - 1]) {
                        tracked[i][j - 1] = true;
                        visibleCount += 1;
                    }
                }
            }
        }
        return visibleCount;
    }

    public int part2() {
        // Initializing trees 2D array.
        int counter = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            for (int i = 0; i < line.length(); i++) {
                trees[counter][i] = line.charAt(i);
            }

            counter++;
        }

        int maxScore = 0;
        for (int i = 0; i < trees.length; i++) {
            for (int j = 0; j < trees[0].length; j++) {
                int height = trees[i][j];

                int topTrees = topTrees(height, i - 1, j);
                int leftTrees = leftTrees(height, i, j - 1);
                int rightTrees = rightTrees(height, i, j + 1);
                int botTrees = botTrees(height, i + 1, j);

                int score = topTrees * leftTrees * rightTrees * botTrees;

                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }

        return maxScore;
    }

    public int topTrees(int height, int row, int col) {
        if (row < 0) {
            return 0;
        }

        int neighbourHeight = trees[row][col];

        if (neighbourHeight >= height) {
            return 1;
        }

        return 1 + topTrees(height, row - 1, col);
    }

    public int botTrees(int height, int row, int col) {
        if (row >= trees.length) {
            return 0;
        }

        int neighbourHeight = trees[row][col];

        if (neighbourHeight >= height) {
            return 1;
        }

        return 1 + botTrees(height, row + 1, col);
    }

    public int leftTrees(int height, int row, int col) {
        if (col < 0) {
            return 0;
        }

        int neighbourHeight = trees[row][col];

        if (neighbourHeight >= height) {
            return 1;
        }

        return 1 + leftTrees(height, row, col - 1);
    }

    public int rightTrees(int height, int row, int col) {
        if (col >= trees[0].length) {
            return 0;
        }

        int neighbourHeight = trees[row][col];

        if (neighbourHeight >= height) {
            return 1;
        }

        return 1 + rightTrees(height, row, col + 1);
    }
}
