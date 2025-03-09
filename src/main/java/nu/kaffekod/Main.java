package nu.kaffekod;

import java.util.*;

/**
* A simple calculator that calculates numbers in the console
*/
public class Main {
    private static final String[] keys = {"+", "-", "/", "*"};
    private static byte failAttempts = 0;

    public static void main(String[] args) {
        boolean active = true;
        Scanner scanner = new Scanner(System.in);
        welcomeMessage();

        while (active) {
            System.out.print("✦ ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase(":q") || userInput.equalsIgnoreCase("q")) {
                active = false; // Quit the app
            } else {
                ArrayList<Object> arrayList = isMath(userInput);

                if(!arrayList.isEmpty()) {
                    calcIt((List<Double>) arrayList.get(0), (List<String>) arrayList.get(1));
                }

            }
        }
        scanner.close();
    }

    /**
     * This is where the magic happens
     * @param numbers, operators The users input.
     */
    private static void calcIt(List<Double> numbers, List<String> operators) {
        String calculation = String.format("╰ %.0f", numbers.getFirst());
        double result = numbers.getFirst();
        boolean showResult = true;
        int i = 1;

        for (String operator : operators) {
            if (operator.equals(keys[0])) {
                result = result + numbers.get(i);
                calculation = calculation.concat(String.format(" + %.0f", numbers.get(i)));
            } else if (operator.equals(keys[1])) {
                result = result - numbers.get(i);
                calculation = calculation.concat(String.format(" - %.0f", numbers.get(i)));
            } else if (operator.equals(keys[2])) {
                if (numbers.get(i) == 0 ) {
                    failAttemptMessage("Woah, hold on. You can't divide by zero (*/ω＼) \n", "Really? this doesn't make it possible to divide by zero \n");
                    showResult = false;
                } else {
                    failAttempts = 0;
                    result = result / numbers.get(i);
                    calculation = calculation.concat(String.format(" / %.0f", numbers.get(i)));
                }
            } else if (operator.equals(keys[3])) {
                result = result * numbers.get(i);
                calculation = calculation.concat(String.format(" * %.0f", numbers.get(i)));
            }

            if (i >= numbers.size() - 1) {
                break;
            }
            i++;
        }

        if (showResult) {
            System.out.printf("%s = %.3f \n",calculation, result);
        }
    }

    /**
     * Check if it's math and return arrays with numbers and operators
     * @param userInput string user input
     * @return list with operators and numbers
     */
    private static ArrayList<Object> isMath(String userInput) {
        ArrayList<Object> arrays = new ArrayList<Object>(2);

        if(userInput.contains(",")) {
            userInput = userInput.replace(',', '.');
        }

        String[] splitInput = userInput.split(" +");
        List<Double> numbers = new ArrayList<>();
        List<String> operators = new ArrayList<>();


        for (int i = 0; i < splitInput.length - 1; i++) {
            for (String key : keys) {
                if (splitInput[i].contains(key)) {
                    operators.add(key);
                    i++;
                }
            }

            try {
                numbers.add(Double.parseDouble(splitInput[i]));
            } catch (NumberFormatException e) {
                failAttemptMessage("Plz use only numbers and operators " + Arrays.toString(keys) + " with spaces in-between", "Really? Write numbers, operators and spaces only. No algebra (￣ ￣|||)");
            }
        }

        if (numbers.size() == operators.size()+1 && !operators.isEmpty()) {
            arrays.add(numbers);
            arrays.add(operators);
        } else {
            failAttemptMessage("Woops, you need to write something like this: 5 + 5", "I still can't do math on that (￣ ￣|||)");
        }

        return arrays;
    }

    /**
     * Just a welcome message to send when starting the app
     */
    private static void welcomeMessage() {
        System.out.println("✦ Welcome to the calculator ✦");
        System.out.println("Let's do some math! ヽ(・∀・)ﾉ");
        System.out.println("Use only numbers and operators " + Arrays.toString(keys) + " with spaces in-between");
    }


    private static void failAttemptMessage(String firstMessage, String secondMessage) {
        failAttempts++;
        if (failAttempts <= 2) {
            System.out.println(firstMessage);
        } else if (failAttempts == 3) {
            System.out.println(secondMessage);
        } else {
            System.out.println("Oh, come on  ヽ(`⌒ ´メ)ノ");
        }
    }
}