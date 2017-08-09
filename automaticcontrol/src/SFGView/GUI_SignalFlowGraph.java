package SFGView;

import signalFlowGraph.SFGR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ahmed on 4/25/17.
 * SFG - GraphStream API
 */
public class GUI_SignalFlowGraph extends JFrame implements ActionListener{
    private ToolPanel toolPanel;
    private CanvasArea  drawer;
    GraphInterfacer graph;
    SFGR graphImp;

    public GUI_SignalFlowGraph() {
        graph = new GraphInterfacer("Tutorial 1");
        toolPanel = new ToolPanel();
        drawer = new CanvasArea(graph.getGraph());
        this.setVisible(true);
        this.setTitle("SFG");
        this.setSize(1300, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.add(toolPanel,BorderLayout.SOUTH);
        this.add(drawer,BorderLayout.CENTER);
        toolPanel.createButton().addActionListener(this);
        toolPanel.newButton().addActionListener(this);
        toolPanel.addEdgeButton().addActionListener(this);
        toolPanel.gainButton().addActionListener(this);
    }

    public static void main(String args[]){

    GUI_SignalFlowGraph test = new GUI_SignalFlowGraph();




    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if(e.getSource() == toolPanel.createButton()){
            int ip = 0;
            int op = 0;
            int number = 0;
            try {
                 ip = Integer.parseInt(toolPanel.ipField());
                 op = Integer.parseInt(toolPanel.opField());
                 number = Integer.parseInt(toolPanel.numOfNodesField());
            } catch (Exception e1){
                JOptionPane.showMessageDialog(new JFrame(),"Invalid Input");
                return;
            }
        graphImp = new SFGR();
        graphImp.numberOfNodes(number);
        graphImp.setInputNode(ip);
        graphImp.setOutputNode(op);
            for(int x = 0; x < number ; x++){
            graph.addNode();
            }
            graph.getGraph().display();
        }

        if(e.getSource() == toolPanel.newButton()){
            graph.getGraph().clear();
            graphImp.New();
        }

        if(e.getSource() == toolPanel.addEdgeButton()){
            int from;
            int to;
            int gain;
            try {
                from = Integer.parseInt(toolPanel.fromField());
                to = Integer.parseInt(toolPanel.toField());
                gain = Integer.parseInt(toolPanel.gainField());
                graphImp.addEdge(from,to,gain);
                graph.addEdge("x"+from,"x"+to,gain);
            } catch (Exception e2){
                JOptionPane.showMessageDialog(new JFrame(),"Invalid Input");
            }
            graph.getGraph().display();

        }
        if(e.getSource() == toolPanel.gainButton()){
            Float overallTF = graphImp.calculateGain();
            toolPanel.getMasonLabel().setText(overallTF.toString());

        }
    }
}
