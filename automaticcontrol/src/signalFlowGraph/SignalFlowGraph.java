package signalFlowGraph;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ahmed on 4/24/17.
 * SFG
 */
public class SignalFlowGraph {
    private ArrayList<ArrayList<EdgeSFG>> adjacencyList;
    private ArrayList forwardPaths;
    private ArrayList fpGain;
    private ArrayList<ArrayList<String>> loops;
    private ArrayList<ArrayList<Integer>> lGain;
    private ArrayList<EdgeSFG> inputNode;
    private ArrayList<EdgeSFG> outputNode;
    private ArrayList<Boolean> visited;

    public SignalFlowGraph() {
        this.adjacencyList = new ArrayList<>();
        this.forwardPaths = new ArrayList();
        this.fpGain = new ArrayList();
        this.loops = new ArrayList<ArrayList<String>>();
        this.lGain = new ArrayList<ArrayList<Integer>>();
        visited = new ArrayList<>();
    }

    /**Basic directed weighted graph representation + Signal Flow Graph
     * annotations such as input and output nodes.
     */
    public void addNode() {
        adjacencyList.add(new ArrayList<>());
        visited.add(false);
    }

    public void setInputNode(int i){
        this.inputNode = adjacencyList.get(i);
    }

    public void setOutputNode(int i){
        this.outputNode = adjacencyList.get(i);
    }

    public void createEdge(int start, int end, int weight) {
        if(this.adjacencyList.size() <= start
                || this.adjacencyList.size() <= end) {
            throw new RuntimeException("Invalid edge");
        } else {
            adjacencyList.get(start).add(new EdgeSFG(weight,end));
        }
    }
    /**Fixes possible mistakes with the output node.
     * Only activated when the calculation is for sure going to start
     * in order to avoid overhead of fixing the output node prematurely.
     */
    private void fixOPNode(){
        if(outputNode == null){
            for(ArrayList<EdgeSFG> node : adjacencyList) {
                if (node.size() == 0) {
                    this.outputNode = node;
                }
            }
            if(this.outputNode == null){
                ArrayList<EdgeSFG> temp =
                        adjacencyList.get(adjacencyList.size()-1);
                this.outputNode = new ArrayList<EdgeSFG>();
                adjacencyList.add(this.outputNode);
                temp.add(new EdgeSFG(1,adjacencyList.size()-1));
            }
        } else if (this.outputNode.size() != 0) {
            ArrayList<EdgeSFG> temp =
                    this.outputNode;
            this.outputNode = new ArrayList<EdgeSFG>();
            adjacencyList.add(this.outputNode);
            temp.add(new EdgeSFG(1,adjacencyList.size()-1));
        }
    }
    /** gets all forward paths and lists them in an
     * arrayList .
     */
    public void getForwardPaths(ArrayList<EdgeSFG> x){
        this.fixOPNode();
        Stack stacker = new Stack();
        ArrayList<EdgeSFG> currentNode = x;
        do {
            for(EdgeSFG path : currentNode){
                if(!path.getVisited()) {
                    stacker.push(path);
                }
            }
            EdgeSFG e = (EdgeSFG)stacker.pop();
            e.setVisited(true);
            currentNode = adjacencyList.get(e.getEndNode());
            if(currentNode.equals(outputNode)){
                break;
            }
        }while(!stacker.isEmpty());


    }
    /**gets all loops and lists them in
     * an arraylist
     */

}
