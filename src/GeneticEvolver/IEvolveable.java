package davidwatson.GeneticEvolver;

/**
 *	IEvolveable
 *
 *	<p>This interface must be implemented by whatever object is to be evolved.
 *	It will then allow it to be used with the other classes in the GeneticEvolver
 *	Package to be able to execute a Genetic Selection Search.</p>
 *	@author 	David Watson
 *	@version	1.0
 */
public interface IEvolveable{
	
	/**
	 *	a function to generate random initial values
	 *
	 *	<p>IEvolveable Objects must provide a method to generate a new
	 *	random instace used to generate the initial population.</p>
	 */
	public abstract void generateRandomValues();

	/**
	 *	a heuristic evauluation function
	 *
	 *	<p>IEvolveable objects must implement a fitness function to score their
	 *	fitness against applicable heuristics</p>
	 *	@return	 		a hueristic score of how fit this individual is
	 */
	public int getFitness();
	
	/**
	 *	The Mating Function - returns a child created from this object and another parent.
	 *
	 *	<p>IEvolveable objects must provide a function which creates a new instance by
	 *	crossing over or 'mating' with another.</p>
	 *	@param	mate	the individual to mate with
	 *	@return			the resulting offspring
	 */
	public IEvolveable crossOver(IEvolveable mate);

	/**
	 *	insert a random change
	 *
	 *	<p>IEvolveable objects must provide a function which makes a random edit
	 * 	(or mutation) on an instance.</p>
	 */
	public void mutate();

	/**
	 *	returns an empty object of the same type 
	 *
	 *	Used by the population class to populate an initial population, IEvolveable
	 *	objects must implement a clone function that at least returns a new empty object
	 *	of the same type.
	 */
	public IEvolveable clone();
}