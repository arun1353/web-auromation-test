package com.goldclub.common;

/**
 * This class contains directional constants
 * for use in specifying things like movement, rotation, increment/decrement, 
 * zoom, alignment, etc.
 * @author dluu
 *
 */

public class GCDirection {

		
		/**
		 * Enumerate directions for moving items.
		 * e.g. move text/photo/item up/down/left/right.
		 * NOTE: UPLEFT = move diagonally up & left simultaneously, etc.
		 * Use in tandem with an integer unit increment to specify how much to move
		 */
		public enum Movement{
			UP,
			DOWN,
			LEFT,
			RIGHT,
			UPLEFT, //move diagonally up & left simultaneously (upper left corner)
			UPRIGHT, //move diagonally up & right simultaneously (upper right corner)
			DOWNLEFT, //move diagonally down & left simultaneously (lower left corner)
			DOWNRIGHT //move diagonally down & right simultaneously (lower right corner)
		}
		
		/**
		 * Enumerates direction to increment.
		 * For increasing or decreasing something like font size or photo zoom.
		 * Use in tandem with an integer unit increment to specify how much to increment/decrement
		 */
		public enum Incrementation{
			INCREMENT, //+
			DECREMENT  //-
		}
		
		/**
		 * Enumerates direction of rotation.
		 * Use in tandem with an integer unit increment to specify how many times to rotate per 90 degrees
		 */
		public enum Rotation{
			CLOCKWISE,
			COUNTERCLOCKWISE
		}
		
		/**
		 * Enumerates direction of zoom. 
		 * Use in tandem with an integer unit increment to specify how much to zoom in/out.
		 */
		public enum Zoom{
			IN,
			OUT
		}
		
		/**
		 * Enumerate direction of text alignment
		 */
		public enum Alignment{
			LEFT,
			CENTER, //aka Justified
			RIGHT
		}
		
		/**
		 * Enumerates sorting or display of alphanumeric entries
		 * (ascending vs descending)
		 */
		public enum Alphanumeric{
			ASCENDING,
			DESCENDING
		}
		
		/**
		 * Enumerates sorting or display of time and date
		 */
		public enum Time{
			OLDEST,
			NEWEST
		}
		
		/**
		 * Enumerates sorting or display of size such as: 
		 * filesize, image megapixels, etc.
		 */
		public enum Size{
			SMALLEST,
			BIGGEST
		}
	}
