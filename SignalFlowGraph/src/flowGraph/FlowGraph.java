package flowGraph;

import java.util.ArrayList;

import FlowgraphAnalysis.DeltaFunction;
import FlowgraphAnalysis.ForwardPaths;
import FlowgraphAnalysis.Loops;

public class FlowGraph extends Graph {

	private Graph[] forwadPaths;
	private Graph[] allLoops;
	private ArrayList<ArrayList<Integer>> nonTouchedLoops;
	private double forwardPathsDelta[];
	private double delta;
	private double overAllTransferFunction;

	public FlowGraph(int size) {
		super(size);
	}

	public FlowGraph() {

	}

	public void update() {
		allLoops = Loops.getLoops(this, 1, nodes.size());
		forwadPaths = ForwardPaths.getForwardPaths(this, 1, nodes.size());
		forwardPathsDelta = new double[forwadPaths.length];
		nonTouchedLoops = Loops.nonTouching();

		for (int i = 0; i < forwadPaths.length; i++) {
			forwardPathsDelta[i] = DeltaFunction.getDelta(forwadPaths[i], allLoops, nonTouchedLoops);
		}
		updateDelta();
		updateMason();
	}

	private void updateDelta() {

		delta = 1;
		for (int i = 0; i < allLoops.length; i++) {
			delta -= allLoops[i].getGain();
		}

		int s, g;
		ArrayList<Integer> loop;

		for (int i = 0; i < nonTouchedLoops.size(); i++) {
			loop = nonTouchedLoops.get(i);
			s = loop.size();
			g = 1;
			for (int j = 0; j < s; j++) {
				g *= allLoops[loop.get(j)].getGain();
			}
			delta += (Math.pow(-1, s)) * g;

		} // end of for

	}

	private void updateMason() {

		overAllTransferFunction = 0;
		for (int i = 0; i < forwadPaths.length; i++) {
			overAllTransferFunction += forwadPaths[i].getGain() * forwardPathsDelta[i];
		}

		overAllTransferFunction /= delta;
	}

	//////////// getters and setters

	public Graph[] getForwardPaths() {
		return forwadPaths;
	}

	public Graph[] getAllLoops() {

		return allLoops;
	}

	public ArrayList<ArrayList<Integer>> getNonTouchingLoops() {
		return nonTouchedLoops;
	}

	public double getDelta() {
		return delta;
	}

	public double[] getForwardPathDelta() {
		return forwardPathsDelta;
	}

	public double getOverAllTF() {
		return overAllTransferFunction;
	}

}
