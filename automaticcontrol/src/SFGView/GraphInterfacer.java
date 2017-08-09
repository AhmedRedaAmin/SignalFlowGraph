package SFGView;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

/**
 * Created by ahmed on 4/27/17.
 * SFG
 */
public class GraphInterfacer {
    private Graph graph;
    private int size;
    private View display;

    public GraphInterfacer(String name) {
        graph = new MultiGraph(name);
        Viewer displayer = new Viewer(graph,
                Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.display = displayer.addDefaultView(false);
        this.size = 0;
        graph.setStrict(false);
        graph.setAutoCreate(false);

    }

    public View getDisplay(){
        return this.display;
    }

    public void addNode(){
        String temp = "x"+this.size;
        Node n = graph.addNode(temp);
        n.addAttribute("ui.label",temp);
        size++;
//        graph.display();
    }

    /**Might throw ID already in use exception if two parallel edges
     * exist.
     * @param from ;
     * @param to ;
     * @param weight ;
     */
    public void addEdge(String from ,String to ,Integer weight){
        Edge x = graph.addEdge(from+to,from,to,true);
        x.addAttribute("ui.label",weight.toString());
//        graph.display();
    }
 /**Might throw Element not found exception.
  * **/
    public void removeEdge(String id){
        graph.removeEdge(id);
//        graph.display();
    }
    /**Might throw Element not found exception.
     * **/
    public void removeNode(String name){
        graph.removeNode(name);
        size --;
//        graph.display();
    }

    public Graph getGraph(){
        return this.graph;
    }
}
