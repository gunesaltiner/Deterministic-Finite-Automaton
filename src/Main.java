import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Initialize needed variables
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the input file: ");
        String filePath = scanner.nextLine();

        int stateNumber = 0;
        int variableNumber = 0;
        int goalStateNumber = 0;
        ArrayList<String> statesList = null;
        ArrayList<String> goalStatesList = null;
        String startState = null;
        ArrayList<ArrayList<String>> allTransitions = null;
        String input = null;
        ArrayList<String> inputVariables = null;
        try {
            // Getting needed variables from file
            File inputFile = new File(filePath);
            Scanner fileScanner = new Scanner(inputFile);

            stateNumber = fileScanner.nextInt();
            variableNumber = fileScanner.nextInt();
            goalStateNumber = fileScanner.nextInt();

            statesList = new ArrayList<>();
            for (int i = 0; i < stateNumber; i++) {
                statesList.add(fileScanner.next());
            }

            goalStatesList = new ArrayList<>();
            for (int i = 0; i < goalStateNumber; i++) {
                goalStatesList.add(fileScanner.next());
            }

            startState = fileScanner.next();

            inputVariables = new ArrayList<>();
            for (int i = 0; i < variableNumber; i++) {
                inputVariables.add(fileScanner.next());
            }

            allTransitions = new ArrayList<>();
            try {
                for (int i = 0; i < (stateNumber * variableNumber); i++) {

                    if (!fileScanner.hasNext()) {
                        throw new Exception("Not enough tokens in the file. Expected more input.");
                    }

                    String sourceState = fileScanner.next();

                    if (!fileScanner.hasNext()) {
                        throw new Exception("Not enough tokens in the file. Expected more input.");
                    }

                    String variable = fileScanner.next();

                    if (!fileScanner.hasNext()) {
                        throw new Exception("Not enough tokens in the file. Expected more input.");
                    }
                    String targetState = fileScanner.next();
                    ArrayList<String> oneTransitionArray = new ArrayList<>();
                    oneTransitionArray.add(sourceState);
                    oneTransitionArray.add(variable);
                    oneTransitionArray.add(targetState);

                    allTransitions.add(oneTransitionArray);
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }

            input = fileScanner.next();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }


        try {
            for (char character : input.toCharArray()) {
                String characterAsString = String.valueOf(character);
                if (!inputVariables.contains(characterAsString)) {
                    throw new Exception("Entered invalid variable in path !");

                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // Creating DFA components by DFA object
        DFA dfa = new DFA(input, stateNumber, variableNumber, goalStateNumber, statesList, goalStatesList, startState, inputVariables, allTransitions);


        String outputFilePath = "output.txt";

        // If given path returns true, write accepted
        if (dfa.applyingDFA(statesList, goalStatesList, allTransitions, input)) {

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

                for (String str : dfa.route) {
                    writer.write(str + " ");
                }
                writer.newLine();
                writer.write("Accepted !");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

                for (String str : dfa.route) {
                    writer.write(str + " ");

                }
                writer.newLine();
                writer.write("Rejected !");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}