/*
 * Question: Given an array of unordered objects of type R, G, and B,
 * sort the objects in place in the order R, G, B.
 * 
 * This is an in-place bucket sort on three objects and can be done
 * in O(n) time with one pass through the array.
 * 
 * Best hint: How can we get all the R's to the front of the array?
 * Once we do that, we just have to go through the non-R portion of
 * the array and put all the B's at the back. Then we are done. This
 * algorithm is O(2n), we can do it in one pass though, as implemented
 * below.
 */
public class RGBSort {
	
	private Obj[] objs;
	
	public RGBSort(Obj[] objs) {
		this.objs = objs;
	}
	
	public void doSort() {
		if (objs == null || objs.length < 2) return;
		
		int nextR = 0;
		int nextB = objs.length -1;
		
		for (int i = 0; i < objs.length; i++) {
			
			while ((nextR <= nextB) && ((objs[i].isR() && i > nextR) || (objs[i].isB() && i < nextB) ||
					(objs[i].isG() && (i < nextR || i > nextB)))) {
				
				if (objs[i].isR()) {
					swap(nextR, i);
					nextR++;
				} else if (objs[i].isB()) {
					swap(nextB, i);
					nextB--;
				} else {
					for (int j = nextR; j < nextB + 1; j++) {
						if (!objs[j].isG()) {
							swap(i, j);
							break;
						}
					}
				}
				
			}
		}
		
	}
	
	public boolean isSorted() {
		if (objs == null || objs.length < 2) return true;
		
		int state = 0;
		
		for (Obj o: objs) {
			if (o.isR()) {
				if (state != 0) return false;
			} else if (o.isG()) {
				if (state < 1) state = 1;
				else if (state != 1) return false;
			} else {
				if (state < 2) state = 2;
				else if (state !=2) return false;
			}
		}
		return true;
	}
	
	private void swap(int i, int j) {
		Obj tmp = objs[i];
		objs[i] = objs[j];
		objs[j] = tmp;
	}
	
	public void printArray() {
		if (objs == null) {
			System.out.println("Array is null/empty");
			return;
		}
		
		for (Obj o : objs) {
			System.out.print(o + ", ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// Test with R's and G's only
		System.out.println("R's and G's only");
		Obj[] t0 = new Obj[5];
		t0[0] = new R();
		t0[1] = new G();
		t0[2] = new G();
		t0[3] = new R();
		t0[4] = new G();
		runTest(t0);
		
		// Test R's and B's only
		System.out.println("R's and B's only");
		Obj[] t1 = new Obj[5];
		t1[0] = new B();
		t1[1] = new R();
		t1[2] = new B();
		t1[3] = new R();
		t1[4] = new B();
		runTest(t1);
		
		// Test G's and B's only
		System.out.println("G's and B's only");
		Obj[] t2 = new Obj[5];
		t2[0] = new G();
		t2[1] = new B();
		t2[2] = new B();
		t2[3] = new G();
		t2[4] = new B();
		runTest(t2);
		
		// Basic test with all 3 
		System.out.println("Basic test with all three");
		Obj[] t3 = new Obj[7];
		t3[0] = new G();
		t3[1] = new B();
		t3[2] = new B();
		t3[3] = new G();
		t3[4] = new B();
		t3[5] = new R();
		t3[6] = new R();
		runTest(t3);
		
		// Test with larger array
		System.out.println("Test with larger array");
		Obj[] t4 = new Obj[15];
		t4[0] = new G();
		t4[1] = new B();
		t4[2] = new B();
		t4[3] = new G();
		t4[4] = new B();
		t4[5] = new R();
		t4[6] = new B();
		t4[7] = new B();
		t4[8] = new G();
		t4[9] = new B();
		t4[10] = new R();
		t4[11] = new R();
		t4[12] = new G();
		t4[13] = new B();
		t4[14] = new R();
		runTest(t4);
	
		// Test with only R's
		System.out.println("Test with R's only");
		Obj[] t5 = new Obj[3];
		t5[0] = new R();
		t5[1] = new R();
		t5[2] = new R();
		runTest(t5);
		// Test with only B's
		System.out.println("Test with B's only");
		Obj[] t6 = new Obj[3];
		t6[0] = new B();
		t6[1] = new B();
		t6[2] = new B();
		runTest(t6);
		
		// Test with only G's
		System.out.println("Test with G's only");
		Obj[] t7 = new Obj[3];
		t7[0] = new G();
		t7[1] = new G();
		t7[2] = new G();
		runTest(t7);
		
		// Empty test
		System.out.println("Test with empty array");
		runTest(new Obj[0]);
		
		// Null array
		System.out.println("Test with null array");
		runTest(null);
	
		// Test with one element
		System.out.println("Test on one element array");
		Obj[] t8 = new Obj[1];
		t8[0] = new R();
		runTest(t8);
	
		// Test with two unsorted elements
		System.out.println("Test with two unsorted elements");
		Obj[] t9 = new Obj[2];
		t9[0] = new G();
		t9[1] = new R();
		runTest(t9);
	
		// Test with two sorted elements
		System.out.println("Test with two sorted elements");
		Obj[] t10 = new Obj[2];
		t10[0] = new R();
		t10[1] = new B();
		runTest(t10);
		
		// Tests to make sure checkSorted will actually fail if not sorted
		System.out.println("Simple fail test with all 3 elements");
		Obj[] t11 = new Obj[3];
		t11[0]= new G();
		t11[1] = new B();
		t11[2] = new R();
		runFailTest(t11);
		
		System.out.println("Simple fail test with only 2 element types");
		Obj[] t12 = new Obj[3];
		t12[0]= new G();
		t12[1] = new B();
		t12[2] = new G();
		runFailTest(t12);
		
	}
	
	private static void runTest(Obj[] t) {
		RGBSort s = new RGBSort(t);
		s.printArray();
		s.doSort();
		s.printArray();
		if (!s.isSorted()) {
			System.err.println("TEST FAILED");
		} else {
			System.out.println("TEST PASSED");
		}
		System.out.println();
	}
	
	private static void runFailTest(Obj [] t) {
		RGBSort s = new RGBSort(t);
		if (s.isSorted()) {
			System.err.println("TEST FAILED");
		} else {
			System.out.println("TEST PASSED");
		}
		System.out.println();
	}
}
