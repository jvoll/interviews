package ca.jvoll.playground;

// http://www.programcreek.com/2014/06/leetcode-shortest-palindrome-java/
public class ShortestPalindrome {

    private boolean scanFromMiddle(String s, int leftIndex, int rightIndex) {
        while (leftIndex > -1 && rightIndex < s.length() && s.charAt(leftIndex) == s.charAt(rightIndex)) {
            leftIndex--;
            rightIndex++;
        }

        // if leftIndex has reached and gone below 0, than we can use our start indices as the mid point
        return leftIndex < 0;
    }

    String shortestPalindrome(String s) {

        if (s == null || s.length() == 0) {
            return "";
        }
        int length = s.length();

        // assume even
        int leftIndex = length/2;
        int rightIndex = length/2;

        boolean foundResult = scanFromMiddle(s, leftIndex, rightIndex);
        while (!foundResult) {
            if (leftIndex == rightIndex) {
                leftIndex--;
            } else {
                rightIndex--;
            }
            foundResult = scanFromMiddle(s, leftIndex, rightIndex);
        }

        if (leftIndex == rightIndex) {
            return new StringBuilder(s.substring(rightIndex + 1)).reverse().append(s.substring(leftIndex)).toString();
        }
        return new StringBuilder(s.substring(rightIndex)).reverse().append(s.substring(rightIndex)).toString();
    }
}
