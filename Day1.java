import java.util.Arrays;
import java.util.Collections;

public class Day1 {
    FileReader fr;

    public Day1() {
        String filePath = "C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt";
        fr = new FileReader(filePath);
    }

    // O(N) solution
    public int maxCaloriesOneElf() {
        int maxCalories = Integer.MIN_VALUE;
        int currentElfCalories = 0;

        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            if (line.equals("")) {
                if (currentElfCalories > maxCalories) {
                    maxCalories = currentElfCalories;
                }
                currentElfCalories = 0;
                continue;
            }

            int foodCalories = Integer.parseInt(line);
            currentElfCalories += foodCalories;
        }

        return Math.max(currentElfCalories, maxCalories);
    }

    // O(N) solution
    public int topThreeElvesCalories() {
        Integer[] topCalories = new Integer[3];
        Arrays.fill(topCalories, 0);

        int elfCalories = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            if (line.equals("")) {
                replaceMinCalories(topCalories, elfCalories);
                elfCalories = 0;
                continue;
            }

            elfCalories += Integer.parseInt(line);

        }

        replaceMinCalories(topCalories, elfCalories);

        int sum = 0;
        for (int i = 0; i < topCalories.length; i++) {
            sum += topCalories[i];
        }

        return sum;
    }

    public void replaceMinCalories(Integer[] calories, int newCalorie) {
        int min = Collections.min(Arrays.asList(calories));

        if (newCalorie < min) {
            return;
        }

        for (int i = 0; i < calories.length; i++) {
            if (calories[i] == min) {
                calories[i] = newCalorie;
                break;
            }
        }
    }
}
