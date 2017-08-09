package signalFlowGraph;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class SFGR implements SFGInterfaceI{

	private ArrayList<Integer>[] adjacencyMatrix;
	private ArrayList<Integer> oneForwardPath;
	private Stack<ArrayList<Integer>> paths;
	private Stack<ArrayList<Integer>> individualLoops;
	private Stack<Stack<ArrayList<Integer>>> allLoops;
	private Stack<Stack<ArrayList<Integer>>>  table;
	private float[][] weight;
	private int outNode;
	private int inputNode;
	private boolean[] visitedNodes;
	private float[] gainOfPaths;
	private int numOfNodes;

	public SFGR() {
		this.adjacencyMatrix = adjacencyMatrix;
		individualLoops = new Stack<ArrayList<Integer>>();
		paths = new Stack<ArrayList<Integer>>();
		oneForwardPath = new ArrayList<Integer>();
		allLoops = new Stack<Stack<ArrayList<Integer>>>();
	}

//	public static void main(String[] args) {
//		SFGInterfaceI sfg = new SFGR();
//		int numOfNodes = 6;
//		int input=0;
//		int output=5;
//		sfg.numberOfNodes(numOfNodes);
//		sfg.setInputNode(input);
//		sfg.setOutputNode(output);
//		sfg.addEdge(0, 1, 1);
//		sfg.addEdge(1, 2, 1);
//		sfg.addEdge(1, 3, 1);
//		sfg.addEdge(1, 4, 1);
//		sfg.addEdge(2, 3, 1);
//		sfg.addEdge(2, 1, -1);
//		sfg.addEdge(3, 2, -1);
//		sfg.addEdge(3, 3, -1);
//		sfg.addEdge(3, 4, 1);
//		sfg.addEdge(4, 5, 1);
//        System.out.println(sfg.calculateGain());
//	}
    private void controller(){
    	forwardPaths(inputNode, 0); // first element in the array of input node
    	removeRepeatdLoops(); // after extracting loops and paths
    	allLoops.add(individualLoops); //add individual loops to the table of loops.
    	nonTouched(0, new Stack<ArrayList<Integer>>(),allLoops,individualLoops); // non touched loops for delta.
    }
	private void forwardPaths(int i, int j) {
		if (adjacencyMatrix[i].get(j) == outNode) {
			oneForwardPath.add(outNode);
			paths.push(new ArrayList(oneForwardPath));
			oneForwardPath.remove(adjacencyMatrix[i].get(j));
		} else {
			if (visitedNodes[adjacencyMatrix[i].get(j)] == false) {
				visitedNodes[adjacencyMatrix[i].get(j)] = true;
				oneForwardPath.add(adjacencyMatrix[i].get(j));
				forwardPaths(adjacencyMatrix[i].get(j), 0);
				visitedNodes[adjacencyMatrix[i].get(j)] = false;
				oneForwardPath.remove(adjacencyMatrix[i].get(j));
			} else {
				loops(adjacencyMatrix[i].get(j));
			}
		}
		for (int counter = 1; j == 0 && counter < adjacencyMatrix[i].size(); counter++) {
			forwardPaths(i, counter);
		}
	}

	private void loops(int value) {
		boolean flag = true;
		int index = 0;
		ArrayList<Integer> loop = new ArrayList(oneForwardPath);
		loop.add(value);
		while (flag == true) {
			if (loop.get(index) != loop.get(loop.size() - 1)) {
				loop.remove(index);
			} else {
				flag = false;
			}
		}
		individualLoops.push(loop);
	}

	private void removeRepeatdLoops() {
		boolean flag = false;
		for (int j = 0; j < individualLoops.size(); j++) {
			for (int i = 0; i < visitedNodes.length; i++) {
				visitedNodes[i] = false;
			}
			for (int k = 0; k < individualLoops.get(j).size(); k++) {
				visitedNodes[individualLoops.get(j).get(k)] = true;
			}
			for (int i = j + 1; i < individualLoops.size(); i++) {
				flag = false;
				for (int k = 0; k < individualLoops.get(i).size(); k++) {
					if (!visitedNodes[individualLoops.get(i).get(k)]) {
						flag = true;
						break;
					}
				}
				if (!flag&& individualLoops.get(j).size()==individualLoops.get(i).size()) {
					individualLoops.remove(i);
					i--;
				}
			}
		}
	}

	private void nonTouched(int i, Stack<ArrayList<Integer>> temp,Stack<Stack<ArrayList<Integer>>> table,Stack<ArrayList<Integer>> individual) {
		if (i == individual.size()) {
			if (temp.size() >= 2) {
				boolean flag = false;
				if (temp.size() - 1 <=  table.size()) {

					for (int counter = 0; counter < temp.size() && !flag; counter++) {
						for (int j = 0; j < visitedNodes.length; j++) {
							visitedNodes[j] = false;
						}
						for (int count = 0; count < temp.get(0).size(); count++) {
							visitedNodes[temp.get(counter).get(count)] = true;
						}
						for (int j = counter + 1; j < temp.size() && !flag; j++) {
							for (int count = 0; count < temp.get(j).size(); count++) {
								if (visitedNodes[temp.get(j).get(count)]) {
									flag = true;
									break;
								}
							}
						}
					}
					if (!flag) {
						for (int j = 0; j < temp.size(); j++) {
							if (temp.size() >  table.size()) {
								 table.add(new Stack<ArrayList<Integer>>());
							}
							 table.get(temp.size() - 1).add(new ArrayList<Integer>(temp.get(j)));
						}
					}

				}
			}
			return;
		}
		nonTouched(i + 1, temp,table,individual);
		temp.add(new ArrayList<Integer>(individual.get(i)));
		nonTouched(i + 1, temp,table,individual);
		temp.remove(temp.size() - 1);
	}

	private float sumOfGain(Stack<ArrayList<Integer>> temp, int index) {
		float gain = 1;
		float tempgain = 1;
		float gainSum = 0;
		if (temp != null) {
			for (int i = 0; i < temp.size(); i += index) {
				for (int k = 0; k < index; k++) {
					for (int j = 0; j < temp.get(i + k).size() - 1; j++) {
						gain *= weight[temp.get(i + k).get(j)][temp.get(i + k).get(j + 1)];
					}
					tempgain *= gain;
					gain = 1;
				}
				gainSum += tempgain;
				gain = 1;
				tempgain = 1;
			}
		}
		return gainSum;
	}

	private float delta(Stack<Stack<ArrayList<Integer>>> temp) {
		float delta = 0;
		if (temp != null) {
			int factor = -1;
			for (int i = 0; i < temp.size(); i++) {
				delta += (sumOfGain(temp.get(i), i + 1) * factor);
				factor *= -1;
			}
		}
		return delta + 1;
	}

	private void gainOfForwardPaths() {
		gainOfPaths = new float[paths.size()];
		for (int i = 0; i < paths.size(); i++) {
			gainOfPaths[i] = 1;
			for (int j = 0; j < paths.get(i).size() - 1; j++) {
				gainOfPaths[i] *= weight[paths.get(i).get(j)][paths.get(i).get(j + 1)];
			}
		}
	}

	private float calculateSecondaryDelta(int index) {
		if (index < paths.size()) {
			boolean flag = false;
			boolean first = true;
			int size=0;
		    table = new Stack<Stack<ArrayList<Integer>>>();
		    Stack<ArrayList<Integer>> temp=new Stack<ArrayList<Integer>>();
			for (int i = 0; i < visitedNodes.length; i++) {
				visitedNodes[i] = false;
			}
			for (int i = 0; i < paths.get(index).size(); i++) {
				visitedNodes[paths.get(index).get(i)] = true;
			}
			int i = 0; // individual loops only;
			for (int j = 0; j < allLoops.get(i).size(); j++) {
				flag = false;
				for (int k = 0; k < allLoops.get(i).get(j).size(); k++) {
					if (visitedNodes[allLoops.get(i).get(j).get(k)]) {
						flag = true;
						break;
					}
				}
				if (flag == false) {
					temp.add(new ArrayList<Integer>(allLoops.get(i).get(j)));
					size++;
				}
			}
			if (size>0){
				table.add(temp);
            	nonTouched(0, new Stack<ArrayList<Integer>>(),table,temp);
            	return delta(table);
			}
          return 1;
			
		}
		return 1;
	}
	private float overAllTransferFunction(){
		gainOfForwardPaths();
		float sum=0;
		float delta;
		for (int i=0;i<paths.size();i++){
			sum+=(gainOfPaths[i]*calculateSecondaryDelta(i));
		}
		delta=delta(allLoops);
		if (delta!=0){
			return sum/delta;
		}
		 return Float.MAX_VALUE;
	}

	public void numberOfNodes(Integer num) {
		this.numOfNodes=num;	
		adjacencyMatrix=new ArrayList[num];
		for (int i=0;i<numOfNodes;i++){
			this.adjacencyMatrix[i]=new ArrayList<Integer>();
		}
		this.weight=new float [numOfNodes+1][numOfNodes+1];
	}

	public void addEdge(int from, int to, int gain) {
		if (weight[from][to]!=0){
			weight[from][to]+=gain;
		}else{
			weight[from][to]=gain;
			adjacencyMatrix[from].add(to);
		}
		
	}

	public void New() {
		for (int i=0;i<adjacencyMatrix.length;i++){
			adjacencyMatrix[i].clear();
		}
		for (int i=0;i<allLoops.size();i++){
			allLoops.get(i).clear();
		}
		for (int i=0;i<table.size();i++){
			table.get(i).clear();
		}
		paths.clear();
		individualLoops.clear();
		oneForwardPath.clear();
		
	}

	public float calculateGain() {
		controller();
		return overAllTransferFunction();
	}

	public void setInputNode(Integer index) {
		this.inputNode=index;
		oneForwardPath.add(inputNode);
	}

	public void setOutputNode(Integer index) {
		this.outNode=index;
		if (this.adjacencyMatrix[outNode].size() != 0) {
			this.adjacencyMatrix[outNode].add(numOfNodes);
			this.outNode = numOfNodes;
			numOfNodes++;
			this.weight[index][outNode]=1;
		}
		visitedNodes = new boolean[numOfNodes];
		visitedNodes[inputNode] = true;
		
	}

}
