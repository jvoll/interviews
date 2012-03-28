/*
 * A BST where duplicates are inserted to the left
 */
public class BSTDupLeft extends BST {

	public BSTDupLeft(Integer data) {
		super(data);
	}

	// Insert --> duplicates go to the left
	@Override
	public void insert(Integer n) {
		if (n > data) {
			if (right == null) right = new BSTDupLeft (n);
			else right.insert(n);
		} else {
			if (left == null) left = new BSTDupLeft (n);
			else left.insert(n);
		}
	}

}
