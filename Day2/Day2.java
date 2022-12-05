import java.util.HashMap;

public class Day2 {
    private static final int WIN_SCORE = 6;
    private static final int DRAW_SCORE = 3;
    private static final int LOSE_SCORE = 0;

    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    public int calculateOverallScore() {
        int totalScore = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] roundInputs = line.split(" ");
            totalScore += calculateRoundScore(roundInputs[0], roundInputs[1]);
        }

        return totalScore;
    }

    // Part 1
    public int calculateRoundScore(String enemyInput, String yourInput) {
        assert enemyInput.length() == 1 : "Enemy input should be a single character string!";
        assert yourInput.length() == 1 : "Your input should be a single character string!";

        int enemy = enemyInput.charAt(0) - 'A' + 1;
        int yours = yourInput.charAt(0) - 'X' + 1;

        int difference = enemy - yours;

        switch (difference) {
        case 0:
            return DRAW_SCORE + yours;
        case -1:
        case 2:
            return WIN_SCORE + yours;
        case 1:
        case -2:
            return LOSE_SCORE + yours;
        default:
            System.out.println("Program should not reach here.");
            return 0;
        }
    }

    // Part 2
    public int calculateTotalScore() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("A X", 3);
        map.put("A Y", 4);
        map.put("A Z", 8);
        map.put("B X", 1);
        map.put("B Y", 5);
        map.put("B Z", 9);
        map.put("C X", 2);
        map.put("C Y", 6);
        map.put("C Z", 7);

        int totalScore = 0;
        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            totalScore += map.get(line);
        }

        return totalScore;
    }
}
