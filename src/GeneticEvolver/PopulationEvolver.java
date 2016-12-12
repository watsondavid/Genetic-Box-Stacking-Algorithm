package davidwatson.GeneticEvolver;
import java.lang.Math;


/**
 *	PopulationEvolver
 *
 *	A class used to evolve a population of IEvolveable objects
 *	@author 	David Watson
 *	@version	1.0
 */

public class PopulationEvolver{
	/* Genetic Algorithm Parameters */
	private float _mutationRate;
	private int _tournamentSize;
	private boolean _elitism;


	/**
	 *	Constructor
	 *
	 *	Creates a PopulationEvolver with given parameters
	 *	@param 	tournamentSize 	the size of the random pool of which to pick eligible parents
	 *	@param 	elitism 		indicates whether or not the fittest individual is kept unaltered and passed onto new generations
	 *	@param 	mutationRate 	Probability (0-1) of new child being mutated	
	 */
	public PopulationEvolver(int tournamentSize, boolean elitism, float mutationRate){
		_tournamentSize = tournamentSize;
		_elitism = elitism;
		_mutationRate = mutationRate;
	}

	/* Public Methods */

	/**
	 *	performs one generational interation
	 *	
	 *	Evolves the population through one generational iteration
	 *	@param 	pop 	the population to be evolved
	 *	@return		 	an Evolved population consisting of a new generation of IEvolveable objects
	 */
	public Population evolve(Population pop) {
		
		Population newPopulation = new Population(pop.getExample(), pop.size(), false);

		// If elitism parameter is set, keep best individual unchanged.
		int elitismOffset;
		if(_elitism){
			newPopulation.saveIndividual(0, pop.getFittest());
			elitismOffset = 1;
		} else {
			elitismOffset = 0;
		}

		// Loop oer the population size and create a new generation of individuals
		for(int i = elitismOffset; i < pop.size(); i++) {
			IEvolveable indiv1 = tournamentSelection(pop);
			IEvolveable indiv2 = tournamentSelection(pop);
			IEvolveable newIndiv = indiv1.crossOver(indiv2);
			newPopulation.saveIndividual(i, newIndiv);
		}

		// Mutate Population
		for(int i = elitismOffset; i < newPopulation.size(); i++) {
			if(Math.random() <= _mutationRate)
				newPopulation.getIndividual(i).mutate();
		}

		return newPopulation;
	}

	/* Private Methods */
	// Returns the fittest individual from a small subset of randomly chosen individuals
	private IEvolveable tournamentSelection(Population pop) {
		// Create a tournament population
		Population tournamentPop = new Population(pop.getExample(), _tournamentSize, false);
		for (int i = 0; i < _tournamentSize; i++) {
			int randomID = (int) Math.random() * pop.size();
			tournamentPop.saveIndividual(i, pop.getIndividual(randomID));
		}
		// return the fittest
		return tournamentPop.getFittest();
	}
}