public class Day4 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    // PART 1
    public int numberOfSubsets() {
        int count = 0;

        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] intervals = line.split(",");
            String firstElfRange = intervals[0];
            String secondElfRange = intervals[1];

            if (isSubset(firstElfRange, secondElfRange)) {
                count += 1;
            }
        }

        return count;
    }

    public boolean isSubset(String intervalOne, String intervalTwo) {
        int oneStart = Integer.parseInt(intervalOne.split("-")[0]);
        int oneEnd = Integer.parseInt(intervalOne.split("-")[1]);

        int twoStart = Integer.parseInt(intervalTwo.split("-")[0]);
        int twoEnd = Integer.parseInt(intervalTwo.split("-")[1]);

        // Two is contained in One
        if (twoStart >= oneStart && twoEnd <= oneEnd) {
            return true;
        }

        // One is contained in two
        if (oneStart >= twoStart && oneEnd <= twoEnd) {
            return true;
        }

        return false;
    }

    // PART 2
    public int numberOfOverlaps() {
        int count = 0;

        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] intervals = line.split(",");
            String firstElfRange = intervals[0];
            String secondElfRange = intervals[1];

            if (hasOverlap(firstElfRange, secondElfRange)) {
                count += 1;
            }
        }

        return count;
    }

    public boolean hasOverlap(String intervalOne, String intervalTwo) {
        int oneStart = Integer.parseInt(intervalOne.split("-")[0]);
        int oneEnd = Integer.parseInt(intervalOne.split("-")[1]);

        int twoStart = Integer.parseInt(intervalTwo.split("-")[0]);
        int twoEnd = Integer.parseInt(intervalTwo.split("-")[1]);

        if (oneStart > twoEnd) {
            return false;
        }

        if (twoStart > oneEnd) {
            return false;
        }

        return true;

    }
}
