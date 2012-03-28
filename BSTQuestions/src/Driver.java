/*
 * Simple driver class for playing with going through trees
 * in different orders using the visitor pattern.
 */
public class Driver {
	
	public static BST makeTest1(BST a) {
		a.insert(3);
		a.insert(9);
		a.insert(3);
		a.insert(2);
		a.insert(11);
		a.insert(12);
		a.insert(11);	
		return a;
	}
	
	public static BST makeTest2(BST a) {
		a.insert(9);
		a.insert(8);
		a.insert(7);
		a.insert(8);
		a.insert(10);
		return a;
	}
	
	public static BST makeTestBSTRight1() {
		BST a = new BSTDupRight(6);
		return makeTest1(a);
	}
	
	public static BST makeTestBSTRight2() {
		BST a = new BSTDupRight(10);
		return makeTest2(a);
	}
	
	public static BST makeTestBSTLeft1() {
		BST a = new BSTDupLeft(6);
		return makeTest1(a);
	}
	
	public static BST makeTestBSTLeft2() {
		BST a = new BSTDupLeft(10);
		return makeTest2(a);
	}
	
	public static void runInOrderTests(BST bst, String name) {
		System.out.println("Sort " + name);
		bst.visitInOrder(new PrintNodeVisitor());
		System.out.println();
		System.out.println("Sort reverse " + name);
		bst.visitInOrderReverse(new PrintNodeVisitor());
		System.out.println();
		System.out.println("Print duplicates " + name);
		bst.visitInOrder(new DetectDuplicatesVisitor());
		System.out.println();
		System.out.println();
	}
	
	public static void runPreOrderTests(BST bst, String name) {
		System.out.println("Print out in pre-order " + name);
		bst.visitPreOrder(new PrintNodeVisitor());
		System.out.println();
		System.out.println();
	}
	
	public static void runPostOrderTests(BST bst, String name) {
		System.out.println("Print out in post-order " + name);
		bst.visitPostOrder(new PrintNodeVisitor());
		System.out.println();
		System.out.println();
	}
	
	public static BST makeTest3(BST a) {
		a.insert(10);
		return a;
	}
	
	public static void main(String [] args) {
		System.out.println("In order traversal tests, sorting, finding duplicates");
		runInOrderTests(makeTestBSTRight1(), "BST1 duplicates right");
		runInOrderTests(makeTestBSTRight2(), "BST2 duplicates right");
		runInOrderTests(makeTestBSTLeft1(), "BST1 duplicates left");
		runInOrderTests(makeTestBSTLeft2(), "BST2 duplicates left");
		
		System.out.println("Pre order traversal, can be useful for duplicating the tree or building out an expression in polish notation");
		runPreOrderTests(makeTestBSTRight1(), "BST1 duplicates right");
		runPreOrderTests(makeTestBSTRight2(), "BST2 duplicates right");
		runPreOrderTests(makeTestBSTLeft1(), "BST1 duplicates left");
		runPreOrderTests(makeTestBSTLeft2(), "BST2 duplicates left");
		
		System.out.println("Post order traversal, not sure when this is useful really."); 
		runPostOrderTests(makeTestBSTRight1(), "BST1 duplicates right");
		runPostOrderTests(makeTestBSTRight2(), "BST2 duplicates right");
		runPostOrderTests(makeTestBSTLeft1(), "BST1 duplicates left");
		runPostOrderTests(makeTestBSTLeft2(), "BST2 duplicates left");
	}
}
