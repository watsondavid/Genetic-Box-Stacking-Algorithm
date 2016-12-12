package davidwatson.Solution;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *	BoxManager
 *
 *	This class holds an array of all the boxes in all 3 of their orientations, 
 *	having read them in from a file on construction.
 *	@author 	David Watson
 *	@version	1.0
 */
public class BoxManager{
	
	private ArrayList<Box> boxes;
	private int numBoxes = 0;

	/**
	 *	Constructor
	 *
	 *	<p>Creates a new BoxManager object reading from the given file. If file cannot be
	 *	read from an error message will be displayed and the resulting objects 'boxes' array
	 *	will be null.</p>
	 *	@param	path	The path to the file that holds the data of a set of boxes
	 */
	public BoxManager(String path){
		loadBoxes(path);
	}

	/**
	 *	loadBoxes
	 *
	 *	Loads boxes from the given file. Called by constructor but can also be
	 *	called publicaly. (not that I've needed to...)
	 *	@param	path	filepath of the box file.
	 */
	public void loadBoxes(String path){
		try{
			// load boxes from file and populate ArrayList.
			boxes = new ArrayList<Box>();
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int boxNumber = 1;
			while((line = reader.readLine()) != null) {
				try{
					String[] entry = line.split(" ", 3);
					
					int w, h, d;
					w = Integer.parseInt(entry[0]);
					h = Integer.parseInt(entry[1]);
					d = Integer.parseInt(entry[2]);

					if(w > 0 && h > 0 && d > 0){	// Lets just be sure our input is good.
						// Add all possible box orientations
						boxes.add(new Box(w, h, d, boxNumber));
						boxes.add(new Box(d, w, h, boxNumber));
						boxes.add(new Box(h, w, d, boxNumber));
					}

					//increment boxNumber
					boxNumber++;
				} catch(Exception e){
					System.err.println("issue loading box " + boxNumber + ": " + line
										+ "\nSkipping Entry.");
				}
			}
			reader.close();
			numBoxes = boxNumber - 1;
		} catch (Exception e){
			System.err.println("Error: Failed to load boxes + \n + e");
		}
	}

	/**
	 *	gets the box array of all loaded boxes
	 *
	 *	@return An ArrayList of all loaded boxes in all 3 orientations
	 */
	public ArrayList<Box> allBoxes(){
		return boxes;
	}

	/**
	 *	gets the number of boxes
	 *
	 *	@return int 	returns the number of boxes loaded.
	 */
	public int getNumBoxes(){
		return numBoxes;
	}
}