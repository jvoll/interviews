/*
 * A simple BST implementation which provides
 * visitors methods to traverse the tree in
 * different orders.
 */
public abstract class BST {
	Integer data;
	BST left;
	BST right;
	
	public BST(Integer data) {
		this.data = data;
	}
	
	public abstract void insert(Integer n);
	
	// TODO implement deletion
	
	public void visitInOrder(BSTVisitor visitor) {
		if (left != null) {
			left.visitInOrder(visitor);
		}
		visitor.visit(this);
		if (right != null) {
			right.visitInOrder(visitor);
		}
	}
	
	public void visitInOrderReverse(BSTVisitor visitor) {
		if (right != null) {
			right.visitInOrderReverse(visitor);
		}
		visitor.visit(this);
		if (left != null) {
			left.visitInOrderReverse(visitor);
		}
	}
	
	public void visitPreOrder(BSTVisitor visitor) {
		visitor.visit(this);
		if (left != null) {
			left.visitPreOrder(visitor);
		}
		if (right != null) {
			right.visitPreOrder(visitor);
		}
	}
	
	public void visitPostOrder(BSTVisitor visitor) {
		if (left != null) {
			left.visitPostOrder(visitor);
		}
		if (right != null) {
			right.visitPostOrder(visitor);
		}
		visitor.visit(this);
	}
}
