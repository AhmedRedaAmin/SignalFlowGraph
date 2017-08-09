package signalFlowGraph;
/**
 * Created by ahmed on 4/24/17.
 * SFG
 */
public class EdgeSFG {
    private int weight;
    private boolean visited;
    private int endNode;
    public EdgeSFG(int weight, int endNode){
        this.weight = weight;
        this.endNode = endNode;
    }
    public void setVisited(boolean x){
        this.visited = x;
    }
    public boolean getVisited(){
        return this.visited;
    }
    public void setWeight(int x){
        this.weight = x;
    }
    public int getWeight(){
        return this.weight;
    }
    public int getEndNode(){
        return this.endNode;
    }
}
