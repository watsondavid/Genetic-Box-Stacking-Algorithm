package davidwatson.Solution;
import java.lang.Comparable;

/**
 *	Box
 *
 *	<p>A class to hold the 3 dimensions of a box plus an identifier so that we know
 *	if 2 boxes are the same. (Violating our search conditions)</p>
 *
 *	<p>Implements the Comparable Interface by providing a compareTo function which
 *	compares the boxes by their face area. The result is inverted (* -1) so that the
 *	resulting sorted lists decrease in face area.</p> 
 */
public class Box implements Comparable<Box>{
	// private variables, stores dimensions of box and ID number unique to the box this orientation has come from.
	private int height, width, depth, _id;

	/**
	 *	Constructor
	 *	
	 *	Creates a new Box object with given dimensions
	 *	@param	h 	Height of box
	 *	@param	w 	Width of box
	 *	@param	d 	Depth of box
	 *	@param	id 	Line number that box was read from. Used to determine if two boxes in different orientations are the same box.
	 */
	public Box(int h, int w, int d, int id){
		height = h;
		width = w;
		depth = d;
		_id = id;
	}

	/** 
	 *	gets the height
	 *	@return	int 	the height dimension of this box
	 */
	public int getHeight(){
		return height;
	}

	/** 
	 *	gets the width 
	 *	@return	int 	the width dimension of this box
	 */
	public int getWidth(){
		return width;
	}

	/** 
	 *	gets the depth
	 *	@return	int 	the depth dimension of this box
	 */
	public int getDepth(){
		return depth;
	}
	/** 
	 *	gets the ID
	 *	
	 *	<p>Boxes with the same ID indicates they are the same box but in different
	 *	orientations</p> 
	 *	@return	int 	 this boxes ID number
	 */
	public int getID(){
		return _id;
	}

	/**
	 *	calculates the face area of the top/bottom facing side
	 *
	 *	Calculates the area of the bottom/top face
	 *	@return	float 	the area of the bottom/top face
	 */
	public float getFaceArea(){
		return depth * width;
	}

	/**
	 *	Implementation of Comparable's compareTo function. 
	 *	allows box objects to be sorted by face area size.
	 */
	public int compareTo(Box b){
		return (-1) * Float.compare(this.getFaceArea(), b.getFaceArea());
	}

	public String toString(){
		return _id + ":\t" + width + "\t" + depth + "\t" + height;
	}
}