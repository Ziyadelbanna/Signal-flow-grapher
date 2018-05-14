package Test2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;


import Componenets.Branch;
import flowGraph.FlowGraph;

import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Gui {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnGraph;
	private JTextArea textArea;
	private int nodes, branches;
	private ArrayList<Branch> operations = new ArrayList<>();
	private ArrayList<Integer> Nodes = new ArrayList<>();

	public static String styleSheet = "node {" + "shape: rounded-box;" + "stroke-mode:plain;" + "stroke-width:1px;"
			+ "size-mode:fit;" + "fill-color:white;" + "text-alignment:center;" + "text-size:15px;"
			+ "stroke-color:black;" + "padding:5px;" + "text-style:bold-italic;" +

			"}" + "edge { 	" + "shape: line;" + "size:2;	" + " fill-color: blue;" + "text-size:15px;"
			+ "text-style:bold;" + "arrow-size:15px,7px;" + "text-background-mode:rounded-box;"
			+ "text-background-color:white;" + "text-padding:2px,2px;" + "}" + "node.marked {" + "fill-color: blue;"
			+ " }" +

			"node:clicked {" + "fill-color: green;" + "}";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();   
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 1086, 767);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 0, 1068, 88);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblEnterNumberOf = new JLabel("Enter Number of Nodes :");
		lblEnterNumberOf.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEnterNumberOf.setBounds(12, 13, 237, 26);
		panel.add(lblEnterNumberOf);

		textField = new JTextField();
		textField.setBounds(12, 42, 138, 33);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblEnterNumberOf_1 = new JLabel("Enter Number of Branches :");
		lblEnterNumberOf_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEnterNumberOf_1.setBounds(337, 13, 258, 26);
		panel.add(lblEnterNumberOf_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(337, 42, 138, 33);
		panel.add(textField_1);

		JButton btnNewButton = new JButton("SOLVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nodes = Integer.parseInt(textField.getText());
				branches = Integer.parseInt(textField_1.getText());
				for (int i = 0; i < branches; i++) {
					String op = JOptionPane.showInputDialog("Enter Branch like From, To , Gain");
					op = op.replaceAll(" ", "");
					String[] parameters = op.split(",");
					operations.add(new Branch(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]),
							Integer.parseInt(parameters[2])));
					if (!Nodes.contains(Integer.parseInt(parameters[0]))) {
						Nodes.add(Integer.parseInt(parameters[0]));
					}
					if (!Nodes.contains(Integer.parseInt(parameters[1]))) {
						Nodes.add(Integer.parseInt(parameters[1]));
					}
				}

				handleSteps(Nodes, operations);

				// TODO PARSE ARRAYLIST AND PASS IT
				btnGraph.setVisible(true);

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(661, 13, 129, 50);
		panel.add(btnNewButton);

		btnGraph = new JButton("GRAPH");
		btnGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Graph graph = new MultiGraph("Graph");
				// Viewer viewer = new Viewer(graph,
				// Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
				for (int i = 0; i < nodes; i++) {
					graph.addNode(String.valueOf(i + 1));
					Node n = graph.getNode(String.valueOf(i + 1));
					n.addAttribute("ui.label", "Node " + (i + 1));
				}
				for (int i = 0; i < branches; i++) {
					Branch temp = operations.get(i);
					String from = String.valueOf(temp.getFrom());
					String to = String.valueOf(temp.getTo());
					String gain = String.valueOf(temp.getGain());
					graph.addEdge(gain + "(" + from + "->" + to + ")", from, to, true);
					Edge e = graph.getEdge(gain + "(" + from + "->" + to + ")");
					e.addAttribute("ui.label", gain);
					// e.addAttribute("ui.stylesheet", styleSheet);
				}
				// graph.addAttribute("ui.stylesheet", styleSheet);
				System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

				// graph.addAttribute("ui.stylesheet", "url('file:stylesheet.css')");
				graph.addAttribute("ui.stylesheet", styleSheet);

				graph.setAutoCreate(true);
				graph.setStrict(false);

				graph.addAttribute("ui.quality");
				graph.addAttribute("ui.antialias");
				graph.getNode(0).setAttribute("xy", 0, 0);
				graph.getNode(graph.getNodeCount() - 1).setAttribute("xy", graph.getNodeCount() + 300, 0);
				graph.display();

			}
		});
		btnGraph.setForeground(Color.WHITE);
		btnGraph.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		btnGraph.setBackground(Color.BLACK);
		btnGraph.setBounds(866, 13, 129, 50);
		btnGraph.setVisible(false);
		panel.add(btnGraph);

		textArea = new JTextArea();
		textArea.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
		textArea.setBounds(1, 57, 844, 548);
		frame.getContentPane().add(textArea);
		JScrollPane c = new JScrollPane(textArea);
		c.setBounds(10, 101, 1046, 606);
		frame.getContentPane().add(c);

	}

	public void handleSteps(ArrayList<Integer> nodes, ArrayList<Branch> edges) {
		FlowGraph g = new FlowGraph();
		for (int i = 0; i < nodes.size(); i++) {
			g.addNode(nodes.get(i));
		}
		for (int i = 0; i < edges.size(); i++) {
			g.addEdge(edges.get(i).getFrom(), edges.get(i).getTo(), edges.get(i).getGain());
		}
		g.update();
//		flowGraph.Graph[] t = g.getAllLoops();
//		for (flowGraph.Graph gr : t) {
//			textArea.append(" " + gr + "\n");
//		}
		ArrayList<ArrayList<Integer>> nonTouch = g.getNonTouchingLoops();
		textArea.append("Forward Paths : "+"\n");
		for(int i=0;i<g.getForwardPaths().length;i++){
			textArea.append(g.getForwardPaths()[i].toString()+"\n");			
		}
		textArea.append("\n"+"========================================"+"\n");

		textArea.append("Loops : "+"\n");
		for(int i=0;i<g.getAllLoops().length;i++){
			textArea.append("Loop Number : "+(i+1)+"\n");
			textArea.append(g.getAllLoops()[i].toString()+"\n");
			textArea.append("\n");

		}
		textArea.append("========================================"+"\n");
		textArea.append("Non Touching Loops :" + "\n");
		if (nonTouch.size() != 0) {
			for (int i = 0; i < nonTouch.size(); i++) {
				textArea.append(" " + "Loop Number " + (i + 1) + " :" + "\n");

				for (int j = 0; j < nonTouch.get(0).size(); j++) {
					textArea.append(" " + nonTouch.get(i).get(j) + "\t");
				}
				textArea.append("\n");

			}
		} else {
			textArea.append("There is No nonTouching Loops" + "\n");

		}
		textArea.append("\n"+"========================================"+"\n");

		textArea.append("Delta Value :" + "\n");
		textArea.append(" " + String.valueOf(g.getDelta()) + "\n");
		textArea.append("\n"+"========================================"+"\n");

		textArea.append("Overall Transfer Function :" + "\n");
		textArea.append(" " + String.valueOf(g.getOverAllTF()) + "\n");


	}
}
