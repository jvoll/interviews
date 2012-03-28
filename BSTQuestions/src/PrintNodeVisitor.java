/*
 * A simple visitor that prints the data
 * stored at the node it is visiting.
 */
public class PrintNodeVisitor implements BSTVisitor {

	@Override
	public void visit(BST node) {
		System.out.print(node.data + ", ");
	}

}
