import java.util.HashSet;
import java.util.Iterator;

/*
 * Given a set of objects, print out the power set (all unique subsets).
 * Order does not matter, the empty set is part of the definition of a
 * power set.
 */
public class PowersetGenerator {
	
	// Solution 1: Works well but takes up some space and is less efficient.
	public static HashSet<String> generatePowerset(String s) {
		
		HashSet<String> ret;
		if (s == null || s.length() < 1) {
			ret = new HashSet<String>();
			ret.add("");
			return ret;
		}
		else {
			String removed = s.substring(0, 1);
			if (s.length() == 1) {
				ret = generatePowerset("");
			} else {
				ret = generatePowerset(s.substring(1));
			}
			
			HashSet<String> composed = new HashSet<String>();
			Iterator<String> it = ret.iterator();
			while (it.hasNext()) {
				composed.add(removed + it.next());
			}
			ret.addAll(composed);
			return ret;
		}
	}
	
	public static void printHashSet(HashSet<String> set) {
		int count = 0;
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			count++;
			String p = it.next();
			if (p == null || p.equalsIgnoreCase("")) {
				System.out.println("<empty_set>");
			} else {
				System.out.println(p);
			}
		}
		System.out.println("Total: " + count);
	}
	
	// Solution 2: Generate binary numbers and print.
	public static void printPowerset(String s) {
		int binLen = (int) Math.pow(2, s.length());
		
		for (int i = 0; i < binLen; i++) {
			String curBinary = Integer.toBinaryString(i);
			
			// Add necessary padding
			for (int j = curBinary.length(); j < s.length(); j++) {
				curBinary = "0" + curBinary;
			}
			
			// Compile current string
			for (int j = 0; j < curBinary.length(); j++) {
				if (curBinary.charAt(j) == '1') {
					System.out.print(s.charAt(j));
				}
			}
			System.out.println("");
		}
		System.out.println("Total: " + binLen);
	}
	
	public static void main(String[] args) {
		PowersetGenerator.printHashSet(PowersetGenerator.generatePowerset("abcd"));
		PowersetGenerator.printPowerset("abcd");
		
	}

}
