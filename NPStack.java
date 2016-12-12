// David Watson 1219309
import davidwatson.GeneticEvolver.Population;
import davidwatson.GeneticEvolver.PopulationEvolver;
import davidwatson.Solution.Box;
import davidwatson.Solution.BoxStack;
import davidwatson.Solution.BoxManager;

/**
 *	NPStack	
 *
 *	<p>A simple command line program used to solve the problem outlined in COMP317-16B
 *	assignment 4. This program uses the classes in the {@link davidwatson.Solution Solution} package to represent
 *	Boxes and Stacks of boxes, as well as classes from the {@link davidwatson.GeneticEvolver GeneticEvolver} package which
 *	by itself is a generalised implementation of the GA which could be applied to other
 *	problems.</p>
 *
 *	<p>The program uses a {@link davidwatson.Solution.BoxManager BoxManager} to load the file of
 *	boxes into {@link davidwatson.Solution.Box Box} objects. The possible solutions are represented
 *	as {@link davidwatson.Solution.BoxStack BoxStack} objects, a class that implements the 
 *	{@link davidwatson.GeneticEvolver.IEvolveable IEvolveable} interface from the GeneticEvolver package.
 *	This allows the creation of a {@link davidwatson.GeneticEvolver.Population Population} object full of 
 *	{@link davidwatson.Solution.BoxStack BoxStack} objects which can be evolved using an instantiated 
 *	{@link davidwatson.GeneticEvolver.PopulationEvolver PopulationEvolver} object.</p>
 *	
 *	<p>A Utility program called {@link davidwatson.Solution.Tools.BoxGenerator} has been written to create random sets of boxes</p>
 *
 *	@see davidwatson.GeneticEvolver.PopulationEvolver
 * 	@see davidwatson.GeneticEvolver.Population
 *	@see davidwatson.GeneticEvolver.IEvolveable
 *	
 *	@see davidwatson.Solution.BoxStack
 *	@see davidwatson.Solution.Box
 *	@see davidwatson.Solution.BoxManager
 *
 *	@see davidwatson.Solution.Tools.BoxGenerator
 *
 *	@author 	David Watson
 *	@version	1.0
 */
public class NPStack{
	private final static int POPULATION_SIZE = 200;
	private final static int TOURNAMENT_SIZE = 5;
	private final static float MUTATION_RATE = 0.35f;

	// Program entry
	public static void main(String[] args){
		// Check inputs for a filepath and an integer.
		String path = "";
		int	candidates = 2500; // default value
		if(args.length != 2){
			System.err.println("Usage: NPStack <Path to Boxes file> <maximum-num-candidates>");
			System.exit(0);
		} else {
			try{
				path = args[0];
				candidates = Integer.parseInt(args[1]);
			} catch (Exception e){
				System.err.println(e);
				System.exit(0);
			}
		}

		// Load boxes and create an empty prototype (population is created by cloning an empty individual)
		BoxManager manager = new BoxManager(path);
		BoxStack example = new BoxStack(manager);
		// Create the population
		Population population = new Population(example, POPULATION_SIZE, true);
		// Create Evolver with alorithm parameters
		PopulationEvolver evolver = new PopulationEvolver(TOURNAMENT_SIZE, true, MUTATION_RATE); // elitism is on.

		// Evolve our population
		int generationCount = 0;
		while((generationCount * POPULATION_SIZE) < candidates){ // Stop once desired number of candidates have been evaluated
			generationCount++;
			System.out.println("Generation: " + generationCount + " Best Height: " + population.getFittest().getFitness());
			population = evolver.evolve(population);
		}

		// Print some output, see how we did
		System.out.println("Maximum Candidates reached");
		System.out.println("Generations: " + generationCount);
		System.out.println("Total Candidate Solutions produced: " + generationCount * POPULATION_SIZE);
		
		// Print out our best effort:
		BoxStack bs = (BoxStack)population.getFittest();
		if(bs.getFitness() != bs.getHeight()){	// Check if a valid solution has been found
			System.out.println("No satisfying solution found, printing my best effort");
		} else {
			System.out.println("Valid Solution Found!");
			System.out.println("Best Solution:");
		}
		System.out.println("h\tw\td\ttotal-height");

		bs.print();
	}
}