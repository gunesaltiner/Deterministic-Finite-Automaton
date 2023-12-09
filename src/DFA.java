import java.util.ArrayList;

public class DFA {

    String input;
    int stateNumber;
    int variableNumber;
    int goalStateNumber;
    String inputStates;
    ArrayList<String> statesList;
    ArrayList<String> goalStatesList;
    String startState;
    ArrayList<String> inputVariables;
    ArrayList<ArrayList<String>> allTransitions;

    ArrayList<String> route = new ArrayList<>();

    DFA(String input, int stateNumber, int variableNumber, int goalStateNumber,
        ArrayList<String> statesList, ArrayList<String> goalStatesList, String startState, ArrayList<String> inputVariables,
        ArrayList<ArrayList<String>> allTransitions) {

        this.input = input;
        this.stateNumber = stateNumber;
        this.variableNumber = variableNumber;
        this.goalStateNumber = goalStateNumber;
        this.statesList = statesList;
        this.goalStatesList = goalStatesList;
        this.startState = startState;
        this.inputVariables = inputVariables;
        this.allTransitions = allTransitions;
    }

    public boolean applyingDFA(ArrayList<String> statesList, ArrayList<String> goalStatesList,
                               ArrayList<ArrayList<String>> allTransitions, String input) {

        ArrayList<State> statesArrayList = new ArrayList<>();

        // creating state objects
        for (String stateName : statesList) {
            if (goalStatesList.contains(stateName)) {
                State state = new State(stateName);
                state.changeStateRole();
                statesArrayList.add(state);
            } else {
                statesArrayList.add(new State(stateName));
            }
        }

        //Creating edges and connect them with states by using transition arraylist
        for (ArrayList<String> allTransition : allTransitions) {

            int fromStateIndex = 0;
            while (!allTransition.get(0).equals(statesArrayList.get(fromStateIndex).name)) {
                fromStateIndex++;
            }

            int targetStateIndex = 0;
            while (!allTransition.get(2).equals(statesArrayList.get(targetStateIndex).name)) {
                targetStateIndex++;
            }

            Edge edge = new Edge(allTransition.get(1), statesArrayList.get(targetStateIndex));
            statesArrayList.get(fromStateIndex).addEdge(edge);
        }

        int startStateIndex = 0;
        while (!startState.equals(statesArrayList.get(startStateIndex).name)) {
            startStateIndex++;
        }

        ArrayList<String> visited = new ArrayList<>();
        State currentState = statesArrayList.get(startStateIndex);

        visited.add(currentState.name);
        // go step by step states according to given input(path)
        for (int i = 0; i < input.length(); i++) {
            currentState = currentState.goToNextState(String.valueOf(input.charAt(i)));
            visited.add(currentState.name);
            route = visited;
        }
        // If the state reached at the end of the path is the final state, return true
        if (goalStatesList.contains(currentState.name)) {
            return true;
        }else{
        return false;
        }
    }
}
