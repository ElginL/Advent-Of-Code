import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day11 {

    FileReader fr = new FileReader("C:\\Users\\Elgin\\OneDrive\\Documents\\NUS\\Advent-Of-Code\\src\\main\\java\\ThisYear\\input.txt");

    int modulo = 1;

    public long monkeyBusinessLevel() {
        int monkeyCount = 8;
        Monkey[] monkeys = new Monkey[monkeyCount];
        String[][] archivedLines = new String[monkeyCount][2];

        while (fr.hasNextLine()) {
            String line = fr.nextLine();

            if (line.contains("Monkey")) {
                List<Item> items = new ArrayList<>();
                Function<Long, Long> operation = null;
                int testDivisor = 0;
                int monkeyIndex = Integer.parseInt(line.split(" ")[1].replace(":", ""));

                // Read next 5 lines because it contains the monkey's details.
                for (int i = 0; i < 5; i++) {
                    String detailLine = fr.nextLine();

                    if (detailLine.contains("Starting items:")) {
                        String[] startingItems = detailLine.trim().split(" items: ")[1].split(", ");
                        for (String item : startingItems) {
                            items.add(new Item(Integer.parseInt(item)));
                        }
                    }

                    if (detailLine.contains("Operation:")) {
                        String op = detailLine.trim().split(" old ")[1].split(" ")[0];
                        String val = detailLine.trim().split(" old ")[1].split(" ")[1];

                        operation = longNumber -> op.equals("*")
                                ? (
                                        val.equals("old")
                                            ? longNumber * longNumber
                                            : longNumber * Long.parseLong(val)
                                )
                                : longNumber + Long.parseLong(val);
                    }

                    if (detailLine.contains("Test")) {
                        testDivisor = Integer.parseInt(detailLine.trim().split(" divisible by ")[1]);
                        modulo *= testDivisor;
                    }

                    if (detailLine.contains("true")) {
                        archivedLines[monkeyIndex][0] = detailLine;
                    }

                    if (detailLine.contains("false")) {
                        archivedLines[monkeyIndex][1] = detailLine;
                    }

                    monkeys[monkeyIndex] = new Monkey(items, testDivisor, operation);
                }
            }
        }

        for (int i = 0; i < monkeyCount; i++) {
            Monkey monkey = monkeys[i];
            String[] lines = archivedLines[i];

            for (String line : lines) {
                int index = Integer.parseInt(line.trim().split(" throw to monkey ")[1]);
                if (line.contains("true")) {
                    monkey.setTrueMonkey(monkeys[index]);
                } else {
                    monkey.setFalseMonkey(monkeys[index]);
                }
            }
        }

        // At this point, Monkeys[] is fully populated and functioning.
        for (int i = 0; i < 10000; i++) {
            executeOneRound(monkeys);
        }

        Arrays.sort(monkeys);
        return monkeys[0].inspectCount * monkeys[1].inspectCount;
    }

    public void executeOneRound(Monkey[] monkeys) {
        for (Monkey monkey : monkeys) {
            List<Item> itemsToRemove = new ArrayList<>();
            for (Item item : monkey.items) {
                monkey.inspectItem(item);
                Item itemToRemove = monkey.throwAwayItem(item);
                itemsToRemove.add(itemToRemove);
            }

            for (Item itemToRemove : itemsToRemove) {
                monkey.removeItem(itemToRemove);
            }
        }
    }

    private class Monkey implements Comparable<Monkey> {
        // Items held by Monkey.
        private List<Item> items;

        private int testDivisor;

        private Monkey trueMonkey;

        private Monkey falseMonkey;

        private Function<Long, Long> operation;

        long inspectCount = 0;

        /**
         * Constructor of a Monkey.
         *
         * @param startingItems of a Monkey.
         * @param testDivisor value to divide the worry level by to determine which other monkey to throw the item to.
         * @param operation how the worry level changes when monkey inspects the item
         */
        public Monkey(List<Item> startingItems, int testDivisor, Function<Long, Long> operation) {

            this.items = startingItems;
            this.testDivisor = testDivisor;
            this.operation = operation;
        }

        /**
         * Monkey inspects the item.
         */
        public void inspectItem(Item item) {
//            int newWorryLevel = operation.apply(item.getWorryLevel()) / 3;
            long newWorryLevel = operation.apply(item.getWorryLevel()) % modulo;
            item.setWorryLevel(newWorryLevel);
            inspectCount++;
        }

        /**
         * This method does not remove item from this monkey's items.
         * It only adds items to the other monkey.
         * @param item Item to be removed.
         * @return Item to be removed.
         */
        public Item throwAwayItem(Item item) {
            if (item.getWorryLevel() % testDivisor == 0) {
                trueMonkey.receiveItem(item);
            } else {
                falseMonkey.receiveItem(item);
            }

            return item;
        }

        public void removeItem(Item item) {
            this.items.remove(item);
        }

        public void receiveItem(Item receivedItem) {
            this.items.add(receivedItem);
        }

        public void setTrueMonkey(Monkey trueMonkey) {
            this.trueMonkey = trueMonkey;
        }

        public void setFalseMonkey(Monkey falseMonkey) {
            this.falseMonkey = falseMonkey;
        }

        @Override
        public int compareTo(Monkey o) {
            return Long.compare(o.inspectCount, this.inspectCount);
        }
    }

    private class Item {
        private long worryLevel;

        public Item(int worryLevel) {
            this.worryLevel = worryLevel;
        }

        public void setWorryLevel(long newWorryLevel) {
            this.worryLevel = newWorryLevel;
        }

        public long getWorryLevel() {
            return this.worryLevel;
        }
    }
}
