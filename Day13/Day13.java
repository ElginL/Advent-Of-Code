package ThisYear;

import java.util.ArrayList;
import java.util.Collections;

public class Day13 {

    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    public int ans() {
        ArrayList<String[]> pairs = new ArrayList<>();

        String[] pair = new String[2];
        int counter = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            if (line.isEmpty()) {
                pairs.add(pair);
                pair = new String[2];
                counter = 0;
                continue;
            }

            pair[counter] = line;
            counter += 1;
        }

        pairs.add(pair);

        int sum = 0;
        for (int i = 0; i < pairs.size(); i++) {
            String[] x = pairs.get(i);
            String valOne = x[0];
            String valTwo = x[1];

            if (isRightOrder(valOne, valTwo) == 1) {
                sum += i + 1;
            }
        }

        return sum;
    }

    public static int isRightOrder(String valOne, String valTwo) {

        // Remove the start and end brackets
        String trimmedValOne = valOne.substring(1, valOne.length() - 1);
        String trimmedValTwo = valTwo.substring(1, valTwo.length() - 1);

        ArrayList<String> oneValues = new ArrayList<>();
        ArrayList<String> twoValues = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        boolean isBuilding = false;
        int openingCount = 0;
        for (int i = 0; i < trimmedValOne.length(); i++) {
            char ch = trimmedValOne.charAt(i);

            if (ch == '[') {
                openingCount += 1;
                isBuilding = true;
            }

            if (ch == ']') {
                openingCount -= 1;
            }

            if (ch == ']' && openingCount == 0) {
                isBuilding = false;
            }

            sb.append(ch);
            while (!isBuilding && i + 1 < trimmedValOne.length() && trimmedValOne.charAt(i + 1) != ',' && ch != ',') {
                sb.append(trimmedValOne.charAt(i + 1));
                i++;
            }

            if (!isBuilding) {
                if (!sb.toString().equals(",")) {
                    oneValues.add(sb.toString());
                }
                sb.setLength(0);
            }
        }

        for (int i = 0; i < trimmedValTwo.length(); i++) {
            char ch = trimmedValTwo.charAt(i);

            if (ch == '[') {
                openingCount += 1;
                isBuilding = true;
            }

            if (ch == ']') {
                openingCount -= 1;
            }

            if (ch == ']' && openingCount == 0) {
                isBuilding = false;
            }

            sb.append(ch);
            while (!isBuilding && i + 1 < trimmedValTwo.length() && trimmedValTwo.charAt(i + 1) != ',' && ch != ',') {
                sb.append(trimmedValTwo.charAt(i + 1));
                i++;
            }

            if (!isBuilding) {
                if (!sb.toString().equals(",")) {
                    twoValues.add(sb.toString());
                }
                sb.setLength(0);
            }
        }

        for (int i = 0; i < oneValues.size(); i++) {
            String one = oneValues.get(i);
            if (i >= twoValues.size()) {
                return -1;
            }
            String two = twoValues.get(i);

            // Both are numbers
            if (!one.startsWith("[") && !two.startsWith("[")) {

                int leftNum = Integer.parseInt(one);
                int rightNum = Integer.parseInt(two);

                if (rightNum < leftNum) {
                    return -1;
                }

                if (leftNum < rightNum) {
                    return 1;
                }
            }

            // Both are lists
            if (one.startsWith("[") && two.startsWith("[")) {
                int val = isRightOrder(one, two);
                if (val == 1 || val == -1) {
                    return val;
                }
            }

            // Two is not a list, but One is.
            if (one.startsWith("[") && !two.startsWith("[")) {
                String listTwo = "[" + two + "]";
                int val = isRightOrder(one, listTwo);
                if (val == 1 || val == -1) {
                    return val;
                }
            }

            if (!one.startsWith("[") && two.startsWith("[")) {
                String listOne = "[" + one + "]";
                int val = isRightOrder(listOne, two);
                if (val == 1 || val == -1) {
                    return val;
                }
            }
        }

        return Integer.compare(twoValues.size(), oneValues.size());
    }

    public int part2() {
        // File Reading
        ArrayList<Packet> packets = new ArrayList<>();

        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            if (line.isEmpty()) {
                continue;
            }

            packets.add(new Packet(line));
        }

        Collections.sort(packets);
        Collections.reverse(packets);

        Packet[] packetsToInsert = new Packet[2];
        packetsToInsert[0] = new Packet("[[2]]");
        packetsToInsert[1] = new Packet("[[6]]");
        Integer firstPacketIndex = null;
        Integer secondPacketIndex = null;
        for (int i = 0; i < packets.size() - 1; i++) {
            Packet first = packets.get(i);
            Packet second = packets.get(i + 1);

            for (Packet packetToInsert : packetsToInsert) {
                if (isRightOrder(first.details, packetToInsert.details) == 1
                    && isRightOrder(packetToInsert.details, second.details) == 1) {

                    if (firstPacketIndex == null) {
                        firstPacketIndex = i + 2;
                    } else {
                        secondPacketIndex = i + 3;
                        break;
                    }
                }
            }
        }

        return firstPacketIndex * secondPacketIndex;
    }

    private static class Packet implements Comparable<Packet> {
        private String details;

        public Packet(String details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return this.details;
        }

        @Override
        public int compareTo(Packet o) {
            return isRightOrder(this.details, o.details);
        }
    }
}
