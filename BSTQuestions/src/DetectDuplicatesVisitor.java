/*
 * A visitor that prints out the Node's data if
 * it is the same as the data at the last node
 * visited.
 */
public class DetectDuplicatesVisitor implements BSTVisitor {

	Integer last;
	
	@Override
	public void visit(BST node) {
		if (last != null && last.equals(node.data)) {
			System.out.print(node.data + ", ");
		}
		last = node.data;
	}

}
