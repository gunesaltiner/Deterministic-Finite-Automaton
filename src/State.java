import java.util.ArrayList;

public class State {

    String name;

    ArrayList<Edge> edges;

    boolean IsFinalState;

    State(String name){
        this.name = name;
        this.edges = new ArrayList<>();
        this.IsFinalState = false;
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public State goToNextState(String value){

        for (int i = 0; i< edges.size() ;i++){
            if (edges.get(i).variable.equals(value)){
                return edges.get(i).targetState;
            }
        }
    return null;
    }

    public void changeStateRole(){
        this.IsFinalState = true;
    }

    public boolean getStateRole(){
        return this.IsFinalState;
    }

    public String getStateName(){
        return this.name;
    }

}








