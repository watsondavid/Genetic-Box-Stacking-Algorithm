package davidwatson.Solution;
import davidwatson.GeneticEvolver.IEvolveable;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

/**
 *	BoxStack
 *
 *	represents a stack of boxes (which can be sorted by face area). This class
 *	Implements the IEvolveable interface from the GeneticEvolver package. 
 *	@author 	David Watson
 *	@version 	1.0
 */
public class BoxStack implements IEvolveable {
	
	private BoxManager boxManager;
	private ArrayList<Box> boxes;
	private Random RNG = new Random();

	/**
	 *	Constructor
	 *
	 *	Creates a new empty BoxStack object.
	 *	@param _boxManager 	The BoxManager object holding all the possible boxes
	 *						in every possible orientation.
	 */
	public BoxStack(BoxManager _boxManager){
		boxManager = _boxManager;
		boxes = new ArrayList<Box>();
	}

	/**
	 *	Constructor
	 *
	 *	Creates a new BoxStack object with given list of boxes.
	 *	@param	_boxManager The BoxManager object holding all the possible boxes
	 *						in every possible orientation.
	 *	@param	_boxes 		The given list of boxes.
	 */
	public BoxStack(BoxManager _boxManager, ArrayList<Box> _boxes){
		boxes = _boxes;
		boxManager = _boxManager;
	}

	/**
	 *	sort
	 *
	 *	Sorts boxes using java's default Sort operation according to their
	 *	.compareTo() values. 
	 */
	private void sort(){
		Collections.sort(boxes);
	}

	/**
	 *	getBoxes
	 *
	 *	@return  	the arraylist of boxes.
	 */
	public ArrayList<Box> getBoxes(){
		return boxes;
	}

	/**
	 *	getHeight()
	 *
	 *	@return	int 	the total height of the stack
	 */
	public int getHeight(){
		int height = 0;
		for(Box b : boxes){
			height += b.getHeight();
		}
		return height;
	}
	/**
	 *	print()
	 *
	 *	prints the stack of boxes to the ddefault output in the manor specified
	 *	in the assignment spec.
	 */
	public void print(){
		int total_height = 0;
		for(int i = 0; i < boxes.size(); i++){
			Box box = boxes.get(i);
			total_height += box.getHeight();
			System.out.println(box.getHeight() + "\t" + box.getWidth() + "\t" + box.getDepth() + "\t" + total_height);
		}
	}

	/* IEvloveable Method Implementations */
	/**
	 *	generateIndividual
	 *
	 *	<p>IEvolveable Objects must provide a method to generate a new
	 *	random instace used to generate the initial population.</p>
	 *
	 *	<p>Generates a new stack of boxes of a Gaussian random number of height, uniformly randomly
	 *	chosen from the box managers total population.</p>
	 */
	public void generateRandomValues(){
		// Get a random set of boxes
		int numBoxes =  RNG.nextInt(5);
		boxes = new ArrayList<Box>();
		for(int i = 0; i < numBoxes; i++){
			Box rndBox = boxManager.allBoxes().get(RNG.nextInt(boxManager.allBoxes().size() - 1));
			boxes.add(rndBox);
		}
		sort();
	}

	/**
	 *	getFitness
	 *
	 *	<p>IEvolveable objects must implement a fitness function to score their
	 *	fitness against applicable heuristics</p>
	 *
	 *	<p>This implementaion works by scoring the individual by the height of the stack
	 *	then negating large values of points for every infringement such that only valid 
	 *	solutions score above 0</p>
	 *	@return	int 	a hueristic score of how fit this individual is
	 */
	public int getFitness(){
		// initialise score to the height of all the boxes
		int score = 0;
		for(Box b : boxes){
			score += b.getHeight();
		}
		
		// negate 500 points every time a box is used twice.
		ArrayList<Integer> boxesSeen = new ArrayList<Integer>();
		for(int i = 0; i < boxes.size(); i++){
			int boxID = boxes.get(i).getID();
			if(boxesSeen.contains(boxID)){
				score -= 500;
			} else {
				boxesSeen.add(boxID);
			}
		}

		// negate 200 points whenever a box overhangs on either the width or depth
		for(int i = 0; i < boxes.size() - 1; i++){
			Box bottom = boxes.get(i);
			Box top = boxes.get(i + 1);
			if(bottom.getWidth() <= top.getWidth()){
				score -= 200;
			}
			if(bottom.getDepth() <= top.getDepth()){
				score -= 200;
			}
		}

		return score;
	}
	
	/**
	 *	crossOver - The Mating Function
	 *
	 *	<p>IEvolveable objects must provide a function which creates a new instance by
	 *	crossing over or 'mating' with another.</p>
	 *	<p>This Implementation works by replacing the top half of the stack with the top
	 *	half of it's mate.</p>
	 *	@param	mate	the individual to mate with
	 *	@return			the resulting offspring
	 */
	public IEvolveable crossOver(IEvolveable mate){
		/* Cut and Splice CrossOver method: */
		
		BoxStack _mate = (BoxStack) mate;

		int size = boxes.size();
		int mateSize = _mate.getBoxes().size();

		// Get half of each parents boxes.
		int splitBottom = Math.abs((int)RNG.nextFloat() * size);
		int splitTop	= Math.abs((int)RNG.nextFloat() * mateSize);
		ArrayList<Box> bottom = new ArrayList<Box>(boxes.subList(0, splitBottom));
		ArrayList<Box> top = new ArrayList<Box>(_mate.getBoxes().subList(splitTop, mateSize));
		// Adjoin 'genes' or boxes...
		bottom.addAll(top);

		BoxStack bs = new BoxStack(boxManager, bottom);
		bs.sort();
		return bs;
		

		// 50/50 Random Split implementation 
		// unused since cut and splice appears to work better.
		/*
		BoxStack partner = (BoxStack) mate;
		int height = Math.min(boxes.size(), partner.getBoxes().size());
		ArrayList<Box> newStack = new ArrayList<Box>();
		for(int i = 0; i< height; i++){
			if(Math.random() < 0.5){
				newStack.add(boxes.get(i));
			} else {
				newStack.add(partner.getBoxes().get(i));
			}
		}

		BoxStack child = new BoxStack(boxManager, newStack);
		child.sort();
		return child;
		*/
	}

	/**
	 *	performs a random change somewhere in the stack
	 *
	 *	<p>IEvolveable objects must provide a function which makes a random edit
	 * 	(or mutation) on an instance.</p>
	 *
	 *	<p>This implementation works by randomly choosing a mutation function,
	 *	(replace box, add box, remove box)</p>
	 */
	public void mutate(){
		int rnd = RNG.nextInt(10);
		if(rnd > 5){	// replace a box
			if(boxes.size() > 0)
				boxes.remove(RNG.nextInt(boxes.size()));
			boxes.add(boxManager.allBoxes().get(RNG.nextInt(boxManager.allBoxes().size())));
		} else if (rnd > 3) { // add box
			boxes.add(boxManager.allBoxes().get(RNG.nextInt(boxManager.allBoxes().size())));
		} else {	// remove box
			if(boxes.size() > 0)
				boxes.remove(RNG.nextInt(boxes.size()));
		}

		this.sort();
	}

	/**
	 *	returns a new empty BoxStack object for population creation.
	 *
	 *	Probably not the best name as this implementation doesn't pass on it's information
	 * 	(as that caused issues with different stacks using the same ArrayList object pointer)
	 *	Instead simply makes a new BoxStack object with the same BoxManager, does enough for
	 *	what this method is used for by the Population method.
	 */
	public BoxStack clone(){
		BoxStack bs = new BoxStack(boxManager);
		return bs;
	}
}