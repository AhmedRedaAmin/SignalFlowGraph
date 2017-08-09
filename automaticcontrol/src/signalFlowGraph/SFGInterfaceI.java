package signalFlowGraph;

public interface SFGInterfaceI {
	public void numberOfNodes(Integer num);

	/** adds an edge to the graph from string from to string to **/
	public void addEdge(int from, int to, int gain);

	/** resets the SFG **/
	public void New();

	/** returns gain **/
	public float calculateGain();

	/** sets input and output nodes **/
	public void setInputNode(Integer index);

	public void setOutputNode(Integer index);
}
