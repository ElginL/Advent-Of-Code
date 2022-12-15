import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Stack;

public class Day15 {
    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    public int impossibleCount(int rowToCheck) {
        ArrayList<int[][]> sourceAndBeacons = new ArrayList<>();

        // Data Reading.
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] splitted = line.split(": ");
            String[] sensor = splitted[0].substring(10).split(", ");
            int[] sensorLocation = new int[]{
                    Integer.parseInt(sensor[0].substring(2)),
                    Integer.parseInt(sensor[1].substring(2))
            };

            String[] beacon = splitted[1].substring(21).split(", ");
            int[] beaconLocation = new int[]{
                    Integer.parseInt(beacon[0].substring(2)),
                    Integer.parseInt(beacon[1].substring(2))
            };

            sourceAndBeacons.add(new int[][]{ sensorLocation, beaconLocation });
        }

        HashSet<Integer> tracker = new HashSet<>();
        HashSet<Integer> beaconInTheWay = new HashSet<>();
        for (int[][] sourceAndBeacon : sourceAndBeacons) {
            int[] sourceCoords = sourceAndBeacon[0];
            int[] beaconCoords = sourceAndBeacon[1];

            int width = findSourceCoverageWidth(sourceCoords, beaconCoords);

            int rowImpossibleWidth = Math.abs(width - (Math.abs(rowToCheck - sourceCoords[1])));

            int centre = sourceCoords[0];
            for (int i = centre - rowImpossibleWidth; i <= centre + rowImpossibleWidth; i++) {
                tracker.add(i);
            }

            if (beaconCoords[1] == rowToCheck) {
                beaconInTheWay.add(beaconCoords[0]);
            }
        }

        return tracker.size() - beaconInTheWay.size();
    }

    public long tuningFrequency(int atMost) {
        ArrayList<int[][]> sourceAndBeacons = new ArrayList<>();

        // Data Reading.
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] splitted = line.split(": ");
            String[] sensor = splitted[0].substring(10).split(", ");
            int[] sensorLocation = new int[]{
                    Integer.parseInt(sensor[0].substring(2)),
                    Integer.parseInt(sensor[1].substring(2))
            };

            String[] beacon = splitted[1].substring(21).split(", ");
            int[] beaconLocation = new int[]{
                    Integer.parseInt(beacon[0].substring(2)),
                    Integer.parseInt(beacon[1].substring(2))
            };

            sourceAndBeacons.add(new int[][]{ sensorLocation, beaconLocation });
        }

        ArrayList<int[]>[] rows = new ArrayList[atMost];
        for (int i = 0; i < atMost; i++) {
            rows[i] = new ArrayList<>();
        }

        for (int[][] sourceAndBeacon : sourceAndBeacons) {
            int[] sourceCoords = sourceAndBeacon[0];
            int[] beaconCoords = sourceAndBeacon[1];

            int width = findSourceCoverageWidth(sourceCoords, beaconCoords);

            int counter = width;
            int oppositeCounter = 0;
            while (counter >= 0) {
                int start = sourceCoords[0] - counter;
                int end = sourceCoords[0] + counter;

                if (sourceCoords[1] - oppositeCounter >= 1) {
                    rows[sourceCoords[1] - oppositeCounter].add(new int[]{ Math.max(0, start), Math.min(end, atMost - 1) });

                    mergeIntervals(rows[sourceCoords[1] - oppositeCounter]);
                }

                if (sourceCoords[1] + oppositeCounter < atMost) {
                    rows[sourceCoords[1] + oppositeCounter].add(new int[]{ Math.max(0, start), Math.min(end, atMost - 1) });

                    mergeIntervals(rows[sourceCoords[1] + oppositeCounter]);
                }

                counter -= 1;
                oppositeCounter += 1;
            }
        }

        for (int i = 0; i < atMost; i++) {
            ArrayList<int[]> a = rows[i];
            if (a.size() > 1) {
                int[] x = a.get(0);
                int xCoords = x[1] + 1;

                return xCoords * 4000000L + i;
            }
        }

        return -1;
    }

    public void mergeIntervals(ArrayList<int[]> arr) {
        arr.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return Integer.compare(o1[0], o2[0]);
                } else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });

        Stack<int[]> s = new Stack<>();
        s.push(arr.get(0));

        for (int i = 1; i < arr.size(); i++) {
            int[] stackInterval = s.peek();
            int[] cur = arr.get(i);

            if (stackInterval[1] + 1 >= cur[0] && cur[1] >= stackInterval[1]) {
                s.pop();
                s.push(new int[]{ stackInterval[0], cur[1] });
            } else if (stackInterval[1] + 1 >= cur[0] && cur[1] < stackInterval[1]) {
                s.pop();
                s.push(new int[]{ stackInterval[0], stackInterval[1] });
            } else {
                s.push(cur);
            }
        }

        ArrayList<int[]> tmp = new ArrayList<>();
        while (!s.isEmpty()) {
            tmp.add(s.pop());
        }

        arr.clear();
        for (int i = tmp.size() - 1; i >= 0; i--) {
            arr.add(tmp.get(i));
        }
    }

    public int findSourceCoverageWidth(int[] source, int[] beacon) {
        int sourceX = source[0];
        int sourceY = source[1];
        int beaconX = beacon[0];
        int beaconY = beacon[1];

        int stepsToMoveVertically = Math.abs(sourceY - beaconY);
        return stepsToMoveVertically + Math.abs(sourceX - beaconX);
    }
}
