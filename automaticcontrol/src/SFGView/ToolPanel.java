package SFGView;

import javax.swing.*;
import java.awt.*;
/**
 * Created by ahmed on 4/27/17.
 * SFG
 */
public class ToolPanel extends JPanel{
    private JTextField fromField;
    private JButton gainButton;
    private JButton newButton;
    private JButton createButton;
    private JTextField toField;
    private JTextField gainField;
    private JTextField opField;
    private JTextField ipField;
    private JTextField numOfNodesField;
    private JButton addEdgeButton;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel gainLabel;
    private JLabel masonLabel;
    private JLabel opLabel;
    private JLabel ipLabel;
    private JLabel nONLabel;

    public ToolPanel(){
        this.setBounds(0, 50, 105, 620);
        this.setLayout(new GridLayout(4, 7,10 , 20));
        this.setBackground(new Color(139, 130, 133));
        gainButton = new JButton("OverAll TF");
        newButton = new JButton("Reset");
        addEdgeButton = new JButton("Add Edge");
        createButton = new JButton("CreateGraph");
        fromField = new JTextField();
        toField = new JTextField();
        opField = new JTextField();
        ipField = new JTextField();
        gainField = new JTextField();
        numOfNodesField = new JTextField();
        fromLabel = new JLabel("From x:");
        toLabel = new JLabel("To x:");
        gainLabel = new JLabel("Gain");
        masonLabel = new JLabel("OverallTF");
        opLabel = new JLabel("OutputNode");
        ipLabel = new JLabel("InputNode");
        nONLabel = new JLabel("NumberOfNodes");
        this.add(createButton);
        this.add(nONLabel);
        this.add(numOfNodesField);
        this.add(ipLabel);
        this.add(ipField);
        this.add(opLabel);
        this.add(opField);
        this.add(addEdgeButton);
        this.add(fromLabel);
        this.add(fromField);
        this.add(toLabel);
        this.add(toField);
        this.add(gainLabel);
        this.add(gainField);
        this.add(gainButton);
        this.add(masonLabel);
        this.add(newButton);
    }
    public JButton gainButton(){
        return this.gainButton;
    }
    public JButton newButton(){
        return this.newButton;
    }
    public JButton addEdgeButton(){
        return this.addEdgeButton;
    }
    public JButton createButton(){
        return this.createButton;
    }
    public String ipField(){
        return this.ipField.getText();
    }
    public String fromField(){
        return this.fromField.getText();
    }
    public String toField(){
        return this.toField.getText();
    }
    public String opField(){
        return this.opField.getText();
    }
    public String gainField(){
        return this.gainField.getText();
    }
    public String numOfNodesField(){
        return this.numOfNodesField.getText();
    }
    public JLabel getMasonLabel(){return this.masonLabel;}
}
