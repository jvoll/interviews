/*
 * A BST where duplicates are inserted to the right
 */
public class BSTDupRight extends BST {
	
	public BSTDupRight(Integer data) {
		super(data);
	}

	// Insert --> duplicates go to the right
	@Override
	public void insert(Integer n) {
		if (n < data) {
			if (left == null) left = new BSTDupRight (n);
			else left.insert(n);
		} else {
			if (right == null) right = new BSTDupRight (n);
			else right.insert(n);
		}
	}
}
