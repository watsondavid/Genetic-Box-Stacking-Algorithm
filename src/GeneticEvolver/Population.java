package davidwatson.GeneticEvolver;
/**
 *	Population
 *
 *	<p>A class to hold a population of IEvolveable objects for evolving
 *	using the PopulationEvolver class.</p>
 *
 *	@author		David Watson
 *	@version	1.0
 */
public class Population{

	private IEvolveable[] individuals;
	private IEvolveable _example;

	/**
	 *	Constructor
	 *
	 *	@param	example 		an example of the type of IEvolveable object to be evolved
	 *	@param	populationSize	the size of the population
	 *	@param	initialise		if set to true, a random population will be initialised
	 */
	public Population(IEvolveable example, int populationSize, boolean initialise){
		_example = example;
		individuals = new IEvolveable[populationSize];
		// Initialise population by creating random individuals
		if(initialise) {
			for(int i = 0; i < populationSize; i++) {
				try{
					IEvolveable individual = (IEvolveable)example.clone();
					individual.generateRandomValues();
					saveIndividual(i, individual);
				} catch(Exception e){
					System.err.println("Unfortunately I am unable to create new individuals" + "\n" + e);
					System.exit(0);
				}
			}
		}
	}

	/** 
	 *	gets the fittest in the population
	 *
	 *	<p>Finds the fittest IEvolveable object in the population</p>
	 *	@return 	 The fittest individual in the population.
	 */
	public IEvolveable getFittest(){
		IEvolveable fittest = individuals[0];
		// iterate through all individuals to find fittest
		for(int i = 0; i < size(); i++){
			if(fittest.getFitness() <= getIndividual(i).getFitness()) {
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}

	/**
	 *	puts an individual into the population at a specified index
	 *
	 *	<p>adds the IEvolveable object to the population at the specified index</p>
	 *	@param	index 		index of where in the population to put 
	 *	@param	individual	the individual to be saved
	 */
	public void saveIndividual(int index, IEvolveable individual){
		individuals[index] = individual;
	}

	/**
	 *	gets an individual from a specified index
	 *
	 *	<p>Returns the individual at the specified index</p>
	 *	@param	index	index of individual
	 *	@return 		the individual at the given index
	 */
	public IEvolveable getIndividual(int index){
		return individuals[index];
	}

	/** 
	 * size of the population
	 *
	 * @return 			the size of the population */ 
	public int size(){
		return individuals.length;
	}

	/**
	 * returns the example instance used when creating this population object
	 *
	 *	@return 	the example instance used when creating this population 
	 */
	public IEvolveable getExample(){
		return _example;
	}




}