package davidwatson.Solution.Tools;
import java.util.Random;


/**
 *	BoxGenerator
 *
 *	<p>A Utility program created to provide random sets of boxes. Prints to Standard Output.</p>
 *	<p>Usage:</p> <code> $ java BoxGenerator [optional: num-boxes] {@literal >} /path/to/output.file </code>
 *	@author 	David Watson
 *	@version	1.0
 */
public class BoxGenerator{
	private static Random RNG = new Random();

	private static final int BOX_MIN_SIZE = 2;
	private static final int BOX_MAX_SIZE = 50;

	private static final int MIN_NUM_BOXES = 5;
	private static final int MAX_NUM_BOXES = 50;

	// Main method, a simple program
	public static void main(String[] args){
		// Start by infering number of boxes, have we been told how many?
		int boxes = 0;
		if(args.length == 1){
			try{
				boxes = Integer.parseInt(args[0]);
			} catch (Exception e){
				System.err.println("Usage: BoxGenerator <optional: number of boxes>"
										+"\n giving no argument or specifying 0 will result in a random number between 5 and 50.");
			}
		}

		// if no number given, generate random number of boxes.
		if(boxes == 0){
			boxes = randomIntBetween(MIN_NUM_BOXES, MAX_NUM_BOXES);
		}

		// Generate boxes and print to std.output
		for(int i = 0; i < boxes; i++){
			int w, h, d;
			w = randomIntBetween(BOX_MIN_SIZE, BOX_MAX_SIZE);
			h = randomIntBetween(BOX_MIN_SIZE, BOX_MAX_SIZE);
			d = randomIntBetween(BOX_MIN_SIZE, BOX_MAX_SIZE);
			System.out.println(w + " " + h + " " + d);
		}
	}

	// Returns a random integer between the specified values, inclusive of the lower.
	private static int randomIntBetween(int lower, int upper){
		return RNG.nextInt(upper - lower) + lower;
	}
}